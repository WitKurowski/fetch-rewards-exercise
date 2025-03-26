package com.wit.fetchrewardsexercise.feature.item

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wit.fetchrewardsexercise.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemsRepository: ItemsRepository) :
	DefaultLifecycleObserver, ViewModel() {
	private val _itemStatesStateFlow = MutableStateFlow<List<ItemState>>(emptyList())
	val itemStatesStateFlow = _itemStatesStateFlow.asStateFlow()

	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)

		viewModelScope.launch {
			val items = itemsRepository.getAll()
			val itemStates = items.map {
				// TODO: Consider handling this more cleanly than with "!!".
				ItemState(it.id, it.listId, it.name!!)
			}

			_itemStatesStateFlow.emit(itemStates)
		}
	}
}