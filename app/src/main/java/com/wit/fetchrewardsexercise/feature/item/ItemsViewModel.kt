package com.wit.fetchrewardsexercise.feature.item

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wit.fetchrewardsexercise.usecase.GetItemsSortedByListIdAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val getItemsSortedByListIdAndNameUseCase: GetItemsSortedByListIdAndNameUseCase) :
	DefaultLifecycleObserver, ViewModel() {
	private val _errorStateStateFlow = MutableStateFlow(ErrorState(visible = false))
	val errorStateStateFlow = _errorStateStateFlow.asStateFlow()
	private val _listItemStatesStateFlow = MutableStateFlow<List<ListItemState>>(emptyList())
	val listItemStatesStateFlow = _listItemStatesStateFlow.asStateFlow()
	private val _loadingStateStateFlow = MutableStateFlow(LoadingState(visible = false))
	val loadingStateStateFlow = _loadingStateStateFlow.asStateFlow()

	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)

		viewModelScope.launch {
			_errorStateStateFlow.update {
				it.copy(visible = false)
			}

			_loadingStateStateFlow.update {
				it.copy(visible = true)
			}

			try {
				val items = getItemsSortedByListIdAndNameUseCase()
				var previousParentId: Int? = null
				val listItemStates = mutableListOf<ListItemState>()

				items.forEach {
					val parentId = it.listId

					if (parentId != previousParentId) {
						listItemStates.add(ParentListItemState(parentId))
						previousParentId = parentId
					}

					// TODO: Consider handling this more cleanly than with "!!".
					listItemStates.add(ChildListItemState(it.id, it.name!!))
				}

				_listItemStatesStateFlow.emit(listItemStates)
			} catch (httpException: HttpException) {
				_errorStateStateFlow.update {
					it.copy(visible = true)
				}
			} catch (ioException: IOException) {
				_errorStateStateFlow.update {
					it.copy(visible = true)
				}
			}

			_loadingStateStateFlow.update {
				it.copy(visible = false)
			}
		}
	}
}