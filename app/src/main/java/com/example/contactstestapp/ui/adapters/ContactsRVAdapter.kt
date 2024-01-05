package com.example.contactstestapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactstestapp.Contact
import com.example.contactstestapp.R

class ContactsRVAdapter(
    private var contacts: List<Contact>,
    private val itemCLickListener: ContactItemClickListener<Contact>? = null
) : RecyclerView.Adapter<ContactsRVAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.tvContactName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentPerson = contacts[position]

        holder.apply {
            textName.text =
                StringBuilder()
                    .append(currentPerson.firstName)
                    .append(" ")
                    .append(currentPerson.lastName)

            itemView.setOnClickListener {
                itemCLickListener?.onItemClicked(currentPerson)
            }
        }
    }

    fun updateData(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}