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
			val name = it.name!!
			val number = name.split(" ")[1].toInt()

			number
		}.sortedBy {
			it.listId
		}
	}
}