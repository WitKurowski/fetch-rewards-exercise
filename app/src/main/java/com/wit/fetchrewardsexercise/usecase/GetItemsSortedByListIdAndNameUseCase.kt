package com.wit.fetchrewardsexercise.usecase

import com.wit.fetchrewardsexercise.model.Item
import com.wit.fetchrewardsexercise.repository.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetItemsSortedByListIdAndNameUseCase @Inject constructor(private val itemsRepository: ItemsRepository) {
	suspend operator fun invoke(): List<Item> = withContext(Dispatchers.Default) {
		val items = itemsRepository.getAll()

		items.sortedBy {
			it.name
		}.sortedBy {
			it.listId
		}
	}
}