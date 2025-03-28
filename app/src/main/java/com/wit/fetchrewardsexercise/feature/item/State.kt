package com.wit.fetchrewardsexercise.feature.item

data class ErrorState(val visible: Boolean)

data class LoadingState(val visible: Boolean)

data class ChildListItemState(val id: Int, val name: String) : ListItemState()

sealed class ListItemState

data class ParentListItemState(val id: Int) : ListItemState()