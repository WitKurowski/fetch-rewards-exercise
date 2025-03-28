package com.wit.fetchrewardsexercise.repository

import com.wit.fetchrewardsexercise.datasource.remote.ItemsRemoteDataSource
import com.wit.fetchrewardsexercise.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemsRepository @Inject constructor(private val itemsRemoteDataSource: ItemsRemoteDataSource) {
	/**
	 * Returns the full list of items.
	 * Note: Any items with a null or blank name are filtered out before returning.
	 */
	suspend fun getAll(): List<Item> {
		// TODO: Inject the dispatcher into this class.
		val items = withContext(Dispatchers.IO) {
			itemsRemoteDataSource.getAll()
		}
		// TODO: Consider moving this filtering either into the use case we just added,
		//  GetItemsSortedByListIdAndNameUseCase, or into a brand new one.
		val filteredItems = items.filter {
			!it.name.isNullOrBlank()
		}

		return filteredItems
	}
}