package com.example.contactstestapp.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.contactstestapp.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val filename = "data.json"

    val contactsMap = MutableLiveData<LinkedHashMap<String, Contact>>()
    val navigateToFragment2 = MutableLiveData<Contact?>()

    fun onNavigateToFragment2(contact: Contact) {
        navigateToFragment2.value = contact
    }

    fun onNavigationHandled() {
        navigateToFragment2.value = null
    }

    fun readContacts() {
        val context = getApplication<Application>().applicationContext

        CoroutineScope(Dispatchers.IO).launch {
            val jsonFile = File(context.filesDir, filename)

            if (!jsonFile.exists()) {
                copyFileToInternalStorage(filename)
            }

            try {
                val inputStream = context.openFileInput(filename)

                // Read JSON file into a string
                val reader = InputStreamReader(inputStream)

                // Convert JSON string to a list of objects using Gson
                val contactsListType = object : TypeToken<List<Contact>>() {}.type
                val jsonContacts: MutableList<Contact> = Gson().fromJson(reader, contactsListType)

                Log.d("ViewModel", "readRawFile: $jsonContacts")

                val linkedHashMap =
                    jsonContacts.associateBy { it.id } as LinkedHashMap<String, Contact>

                contactsMap.postValue(linkedHashMap)

                inputStream.close()
            } catch (e: Exception) {
                Log.e("ViewModel", "readRawFile: $e")
            }
        }
    }

    fun updateContacts(contact: Contact) {
        val context = getApplication<Application>().applicationContext

        CoroutineScope(Dispatchers.IO).launch {
            try {
                contactsMap.value?.set(contact.id, contact)

                val outputStream: OutputStream =
                    context.openFileOutput(filename, Context.MODE_PRIVATE)

                val myObjectsList: List<Contact> =
                    contactsMap.value?.values?.toList() ?: arrayListOf()

                // Convert List to JSON
                val gson = Gson()
                val json = gson.toJson(myObjectsList)

                // Save JSON to a file
                val writer = BufferedWriter(OutputStreamWriter(outputStream))
                writer.write(json.toString())
                writer.close()

                contactsMap.postValue(contactsMap.value)
            } catch (e: Exception) {
                Log.e("ViewModel", "writeJson: $e")
            }
        }
    }

    private fun copyFileToInternalStorage(fileName: String) {
        val context = getApplication<Application>().applicationContext
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val outputFile = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(outputFile)

            val buffer = ByteArray(1024)
            var length: Int

            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            // Now the file is copied from assets to internal storage
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}