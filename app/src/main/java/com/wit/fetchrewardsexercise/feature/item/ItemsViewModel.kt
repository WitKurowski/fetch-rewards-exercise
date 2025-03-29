package com.wit.fetchrewardsexercise.feature.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wit.fetchrewardsexercise.usecase.GetItemsSortedByListIdAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val getItemsSortedByListIdAndNameUseCase: GetItemsSortedByListIdAndNameUseCase) :
	ViewModel() {
	private val _errorStateStateFlow = MutableStateFlow(ErrorState(visible = false))
	val errorStateStateFlow = _errorStateStateFlow.asStateFlow()
	private val _listItemStatesStateFlow = MutableStateFlow<List<ListItemState>>(emptyList())
	val listItemStatesStateFlow = _listItemStatesStateFlow.asStateFlow()
	private val _loadingStateStateFlow = MutableStateFlow(LoadingState(visible = false))
	val loadingStateStateFlow = _loadingStateStateFlow.asStateFlow()
	private val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
		// TODO: Log the unexpected exception to, for example, Crashlytics.

		_errorStateStateFlow.update {
			it.copy(visible = true)
		}
	}

	fun fetchItems() {
		viewModelScope.launch(coroutineExceptionHandler) {
			_errorStateStateFlow.update {
				it.copy(visible = false)
			}

			_loadingStateStateFlow.update {
				// Do not show the loading state if we have data from a previous load.
				it.copy(visible = _listItemStatesStateFlow.value.isEmpty())
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
			} finally {
				_loadingStateStateFlow.update {
					it.copy(visible = false)
				}
			}
		}
	}
}