package com.wit.fetchrewardsexercise.feature.home

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.wit.fetchrewardsexercise.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : FragmentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_home)
	}
}