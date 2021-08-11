package com.assignment.agecalculator.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment.agecalculator.R
import com.assignment.agecalculator.databinding.FragmentUserInformationBinding
import com.assignment.agecalculator.model.UserAgeModel
import com.assignment.agecalculator.ui.viewmodel.UserInfoViewModel
/*
* User Information Details Fragment
* */
class UserInformationFragment : Fragment() {

    private val sharedUserInfoViewModel: UserInfoViewModel by activityViewModels()
    private lateinit var binding: FragmentUserInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeAgeInformation()
        init()
        sharedUserInfoViewModel.userAgeCalculation()
        clickListener()
    }

    private fun observeAgeInformation() {
        sharedUserInfoViewModel.ageInfoLiveData.observe(viewLifecycleOwner, {
            uiUpdate(it)
        })
    }

    private fun init() {
        binding.firstName.text = sharedUserInfoViewModel.userDataModel.firstName
        binding.lastName.text = sharedUserInfoViewModel.userDataModel.lastName
    }

    //Onclick event
    private fun clickListener() = binding.backButton.setOnClickListener {
        findNavController().popBackStack()
    }

    //Update UI
    private fun uiUpdate(userAgeInfoDetails: UserAgeModel) {
        binding.firstName.text = sharedUserInfoViewModel.userDataModel.firstName
        binding.lastName.text = sharedUserInfoViewModel.userDataModel.lastName
        binding.age.text = getString(
            R.string.age_text,
            userAgeInfoDetails.years,
            userAgeInfoDetails.months,
            userAgeInfoDetails.days
        )
    }
}