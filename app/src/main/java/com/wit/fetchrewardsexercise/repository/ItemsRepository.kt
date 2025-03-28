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
		val filteredItems = items.filter {
			!it.name.isNullOrEmpty()
		}

		return filteredItems
	}
}