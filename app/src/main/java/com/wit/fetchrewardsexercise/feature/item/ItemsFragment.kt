package com.wit.fetchrewardsexercise.feature.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wit.fetchrewardsexercise.databinding.FragmentItemsBinding
import com.wit.fetchrewardsexercise.databinding.ItemListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsFragment : Fragment() {
	private val itemsAdapter = ItemsAdapter()
	private lateinit var viewBinding: FragmentItemsBinding
	private val viewModel: ItemsViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		lifecycle.addObserver(viewModel)

		viewBinding = FragmentItemsBinding.inflate(inflater, container, false)

		with(viewBinding.items) {
			adapter = itemsAdapter
			layoutManager = LinearLayoutManager(context)
		}

		val view = viewBinding.root

		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.CREATED) {
				viewModel.itemStatesStateFlow.collect {
					itemsAdapter.submitList(it)
				}
			}
		}
	}

	private class ItemsAdapter :
		ListAdapter<ItemState, ItemsAdapter.ItemViewHolder>(ItemCallback()) {
		override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
			val itemState = getItem(position)
			holder.itemListItemBinding.name.text = itemState.name
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val itemListItemBinding = ItemListItemBinding.inflate(layoutInflater, parent, false)
			val itemViewHolder = ItemViewHolder(itemListItemBinding)

			return itemViewHolder
		}

		private class ItemCallback : DiffUtil.ItemCallback<ItemState>() {
			override fun areContentsTheSame(oldItem: ItemState, newItem: ItemState): Boolean =
				oldItem == newItem

			override fun areItemsTheSame(oldItem: ItemState, newItem: ItemState): Boolean =
				oldItem.id == newItem.id
		}

		private class ItemViewHolder(val itemListItemBinding: ItemListItemBinding) :
			RecyclerView.ViewHolder(itemListItemBinding.root)
	}
}