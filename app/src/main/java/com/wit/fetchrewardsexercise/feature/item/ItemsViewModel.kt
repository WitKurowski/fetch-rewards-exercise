package com.wit.fetchrewardsexercise.feature.item

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wit.fetchrewardsexercise.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(private val itemsRepository: ItemsRepository) :
	DefaultLifecycleObserver, ViewModel() {
	override fun onCreate(owner: LifecycleOwner) {
		super.onCreate(owner)

		viewModelScope.launch {
			itemsRepository.getAll()
		}
	}
}