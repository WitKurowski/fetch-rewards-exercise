package com.wit.fetchrewardsexercise.feature.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.wit.fetchrewardsexercise.R
import com.wit.fetchrewardsexercise.databinding.ChildListItemBinding
import com.wit.fetchrewardsexercise.databinding.FragmentItemsBinding
import com.wit.fetchrewardsexercise.databinding.ParentListItemBinding
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

		viewLifecycleOwner.lifecycle.addObserver(viewModel)

		viewBinding = FragmentItemsBinding.inflate(inflater, container, false)

		val view = viewBinding.root

		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with(viewBinding.items) {
			adapter = itemsAdapter
			layoutManager = LinearLayoutManager(context).apply {
				val dividerItemDecoration =
					DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
				addItemDecoration(dividerItemDecoration)

				orientation = LinearLayoutManager.VERTICAL
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			lifecycleScope.launch {
				repeatOnLifecycle(Lifecycle.State.RESUMED) {
					viewModel.listItemStatesStateFlow.collect {
						itemsAdapter.submitList(it)
					}
				}
			}

			lifecycleScope.launch {
				repeatOnLifecycle(Lifecycle.State.RESUMED) {
					viewModel.loadingStateStateFlow.collect {
						viewBinding.progressIndicator.isVisible = it.visible
					}
				}
			}

			lifecycleScope.launch {
				repeatOnLifecycle(Lifecycle.State.RESUMED) {
					viewModel.errorStateStateFlow.collect {
						viewBinding.errorContainer.isVisible = it.visible
					}
				}
			}
		}
	}

	private class ItemsAdapter :
		ListAdapter<ListItemState, ItemsAdapter.ViewHolder>(ItemCallback()) {
		override fun getItemViewType(position: Int): Int {
			val listItemState = getItem(position)
			val itemViewType = if (listItemState is ParentListItemState) {
				0
			} else {
				1
			}

			return itemViewType
		}

		override fun onBindViewHolder(holder: ViewHolder, position: Int) {
			val listItemState = getItem(position)

			if (holder is ChildViewHolder) {
				val childListItemState = listItemState as ChildListItemState
				holder.childListItemBinding.name.text = childListItemState.name
			} else if (holder is ParentViewHolder) {
				val parentListItemState = listItemState as ParentListItemState
				val context = holder.parentListItemBinding.root.context
				val name = context.getString(R.string.parent_name, parentListItemState.id)
				holder.parentListItemBinding.name.text = name
			}
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
			val context = parent.context
			val layoutInflater = LayoutInflater.from(context)
			val viewHolder = if (viewType == 0) {
				val parentListItemBinding =
					ParentListItemBinding.inflate(layoutInflater, parent, false)
				ParentViewHolder(parentListItemBinding)
			} else {
				val childListItemBinding =
					ChildListItemBinding.inflate(layoutInflater, parent, false)
				ChildViewHolder(childListItemBinding)
			}

			return viewHolder
		}

		private class ItemCallback : DiffUtil.ItemCallback<ListItemState>() {
			override fun areContentsTheSame(
				oldItem: ListItemState, newItem: ListItemState
			): Boolean = oldItem == newItem

			override fun areItemsTheSame(
				oldItem: ListItemState, newItem: ListItemState
			): Boolean {
				val itemsTheSame =
					if (oldItem is ChildListItemState && newItem is ChildListItemState) {
						oldItem.id == newItem.id
					} else if (oldItem is ParentListItemState && newItem is ParentListItemState) {
						oldItem.id == newItem.id
					} else {
						false
					}

				return itemsTheSame
			}
		}

		private class ChildViewHolder(val childListItemBinding: ChildListItemBinding) :
			ViewHolder(childListItemBinding)

		private class ParentViewHolder(val parentListItemBinding: ParentListItemBinding) :
			ViewHolder(parentListItemBinding)

		private open class ViewHolder(viewBinding: ViewBinding) :
			RecyclerView.ViewHolder(viewBinding.root)
	}
}