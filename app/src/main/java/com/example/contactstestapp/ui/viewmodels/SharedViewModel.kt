package com.example.contactstestapp.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contactstestapp.Contact
import com.example.contactstestapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val contacts = MutableLiveData<List<Contact>>()

    fun readContacts() {
        val context = getApplication<Application>().applicationContext

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val inputStream = context.resources.openRawResource(R.raw.data)

                // Read JSON file into a string
                val reader = InputStreamReader(inputStream)

                // Convert JSON string to a list of objects using Gson
                val contactsListType = object : TypeToken<List<Contact>>() {}.type
                val jsonContacts: MutableList<Contact> = Gson().fromJson(reader, contactsListType)

                Log.d("ViewModel", "readRawFile: $jsonContacts")

                contacts.postValue(jsonContacts)

                inputStream.close()
            } catch (e: Exception) {
                Log.e("ViewModel", "readRawFile: $e")
            }
        }
    }
}