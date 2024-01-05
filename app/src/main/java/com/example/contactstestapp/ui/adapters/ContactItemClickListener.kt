package com.example.contactstestapp.ui.adapters

fun interface ContactItemClickListener<T> {
    fun onItemClicked(item: T)
}