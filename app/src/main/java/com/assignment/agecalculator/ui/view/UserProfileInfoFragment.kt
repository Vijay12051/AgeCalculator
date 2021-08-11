package com.assignment.agecalculator.ui.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.agecalculator.R
import com.assignment.agecalculator.databinding.FragmentUserProfileInfoBinding
import com.assignment.agecalculator.exception.DateEmptyException
import com.assignment.agecalculator.exception.FirstNameEmptyException
import com.assignment.agecalculator.exception.LastNameEmptyException
import com.assignment.agecalculator.ui.viewmodel.UserInfoViewModel
import com.assignment.agecalculator.utils.hideKeyboard
import com.assignment.agecalculator.utils.showKeyboard
import com.assignment.agecalculator.utils.showToast
import java.util.*

/*
* User Profile Information Fragment
* */
class UserProfileInfoFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileInfoBinding
    private val sharedUserInfoViewModel: UserInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.firstNameEditText.showKeyboard()
        clickListener()
        observeValidateError()
    }

    //Onclick event
    private fun clickListener() {
        binding.nextButton.setOnClickListener {
            it.hideKeyboard()
            sharedUserInfoViewModel.userDataModel.firstName = binding.firstNameEditText.text.toString()
            sharedUserInfoViewModel.userDataModel.lastName = binding.lastNameEditText.text.toString()

            if (sharedUserInfoViewModel.isInputFieldValidate()) {
                findNavController().navigate(R.id.action_userProfileInfoFragment_to_userInformationFragment)
            }
        }
        binding.dobDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    //Date picker dialog
    private fun showDatePickerDialog() {
        val today = Calendar.getInstance()
        if (sharedUserInfoViewModel.userDataModel.day != 0) {
            today.set(
                sharedUserInfoViewModel.userDataModel.year,
                sharedUserInfoViewModel.userDataModel.month - 1,
                sharedUserInfoViewModel.userDataModel.day
            )
        }
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                sharedUserInfoViewModel.userDataModel.day = dayOfMonth
                sharedUserInfoViewModel.userDataModel.month = monthOfYear + 1
                sharedUserInfoViewModel.userDataModel.year = year
                binding.dobDate.setText("""$year - ${monthOfYear + 1} - $dayOfMonth""")

            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    //Error validation
    private fun observeValidateError() =
        sharedUserInfoViewModel.errorInfoLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is FirstNameEmptyException -> {
                    requireContext().showToast(getString(R.string.first_name_error_text))
                }
                is LastNameEmptyException -> {
                    requireContext().showToast(getString(R.string.last_name_error_text))
                }
                is DateEmptyException -> {
                    requireContext().showToast(getString(R.string.dob_error_text))
                }
                else -> requireContext().showToast(getString(R.string.common_error_text))
            }
        })
}

