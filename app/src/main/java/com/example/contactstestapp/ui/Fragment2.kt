package com.example.contactstestapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contactstestapp.Contact
import com.example.contactstestapp.databinding.Fragment2Binding
import com.example.contactstestapp.ui.viewmodels.SharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Contact? = null
    private var param2: String? = null

    private val binding by lazy {
        Fragment2Binding.inflate(layoutInflater)
    }

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Contact
            param2 = it.getString(ARG_PARAM2)
        }

        setupButtons()
        setupText()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupButtons() {
        binding.apply {
            btnCancelFragment2.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }

            btnSaveFragment2.setOnClickListener {
                if (checkRequiredFields()) {
                    param1?.let {
                        sharedViewModel.updateContacts(
                            Contact(it.id).apply {
                                firstName = etFirstName.text.toString()
                                lastName = etLastName.text.toString()
                                email = etEmail.text.toString()
                                phone = etPhone.text.toString()
                            })
                        activity?.supportFragmentManager?.popBackStack()
                    }
                }
            }
        }
    }

    private fun setupText() {
        param1?.let {
            binding.apply {
                etFirstName.setText(it.firstName)
                etLastName.setText(it.lastName)
                etEmail.setText(it.email)
                etPhone.setText(it.phone)

                etFirstName.apply {
                    addTextChangedListener {
                        etlFirstName.error = null
                    }
                }

                etLastName.apply {
                    addTextChangedListener {
                        etlLastName.error = null
                    }
                }
            }
        }
    }

    private fun checkRequiredFields(): Boolean {
        var isValid = true
        binding.let {
            if (TextUtils.isEmpty(it.etFirstName.text?.trim())
            ) {
                it.etlFirstName.error = "Cannot be empty"
                isValid = false
            }

            if (TextUtils.isEmpty(it.etLastName.text?.trim())
            ) {
                it.etlLastName.error = "Cannot be empty"
                isValid = false
            }
        }

        return isValid
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Contact) =
            Fragment2().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}