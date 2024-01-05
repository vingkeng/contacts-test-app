package com.example.contactstestapp

import java.io.Serializable

data class Contact(val id: String) : Serializable {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var phone: String? = ""

    override fun toString(): String {
        return "Contact(id='$id', firstName=$firstName, lastName=$lastName, email=$email, phone=$phone)"
    }
}
