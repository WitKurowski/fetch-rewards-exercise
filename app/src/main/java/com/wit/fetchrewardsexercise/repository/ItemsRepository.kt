package com.wit.fetchrewardsexercise.repository

import com.wit.fetchrewardsexercise.model.Item
import javax.inject.Inject

class ItemsRepository @Inject constructor() {
	suspend fun getAll(): List<Item> {
		return emptyList()
	}
}