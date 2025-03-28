package com.wit.fetchrewardsexercise.feature.item

data class ChildListItemState(val id: Int, val name: String) : ListItemState()

sealed class ListItemState

data class ParentListItemState(val id: Int) : ListItemState()