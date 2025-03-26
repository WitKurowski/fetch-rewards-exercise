package com.wit.fetchrewardsexercise.repository

import com.wit.fetchrewardsexercise.datasource.remote.ItemsRemoteDataSource
import com.wit.fetchrewardsexercise.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemsRepository @Inject constructor(private val itemsRemoteDataSource: ItemsRemoteDataSource) {
	suspend fun getAll(): List<Item> {
		val items = withContext(Dispatchers.IO) {
			itemsRemoteDataSource.getAll()
		}

		return items
	}
}