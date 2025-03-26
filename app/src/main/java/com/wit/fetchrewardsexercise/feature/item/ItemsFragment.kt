package com.wit.fetchrewardsexercise.feature.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wit.fetchrewardsexercise.databinding.FragmentItemsBinding

class ItemsFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		super.onCreateView(inflater, container, savedInstanceState)

		val fragmentItemsBinding = FragmentItemsBinding.inflate(inflater, container, false)
		val view = fragmentItemsBinding.root

		return view
	}
}