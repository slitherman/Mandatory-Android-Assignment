package com.example.mobilemandatoryassignmentbirthdaylist.Models

interface OnPersonInteractionListener {

    fun onFriendDetailsClick(person: DisplayablePerson)
    fun onEditFriendDetailsClick(position: Int)
}