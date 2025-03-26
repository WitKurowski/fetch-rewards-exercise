package com.wit.fetchrewardsexercise.di

import com.wit.fetchrewardsexercise.datasource.remote.ItemsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class Module {
	@Provides
	fun provideRetrofit(): Retrofit {
		val retrofit = Retrofit.Builder() //
			.baseUrl("https://fetch-hiring.s3.amazonaws.com/") //
			.addConverterFactory(GsonConverterFactory.create()) //
			.build()

		return retrofit
	}

	@Provides
	fun provideItemsRemoteDataSource(retrofit: Retrofit): ItemsRemoteDataSource {
		val itemsRemoteDataSource = retrofit.create(ItemsRemoteDataSource::class.java)

		return itemsRemoteDataSource
	}
}