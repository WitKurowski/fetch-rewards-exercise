package com.wit.fetchrewardsexercise.feature.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wit.fetchrewardsexercise.databinding.FragmentItemsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsFragment : Fragment() {
	private val viewModel: ItemsViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		lifecycle.addObserver(viewModel)

		val fragmentItemsBinding = FragmentItemsBinding.inflate(inflater, container, false)
		val view = fragmentItemsBinding.root

		return view
	}
}