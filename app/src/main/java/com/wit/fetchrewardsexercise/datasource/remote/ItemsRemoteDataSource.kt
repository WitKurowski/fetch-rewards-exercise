package com.wit.fetchrewardsexercise.datasource.remote

import com.wit.fetchrewardsexercise.model.Item
import retrofit2.http.GET

interface ItemsRemoteDataSource {
	@GET("hiring.json")
	suspend fun getAll(): List<Item>
}