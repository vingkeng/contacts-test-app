package com.example.contactstestapp

data class Contact(val id: String) {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var phone: String? = ""

    override fun toString(): String {
        return "Contact(id='$id', firstName=$firstName, lastName=$lastName, email=$email, phone=$phone)"
    }
}
