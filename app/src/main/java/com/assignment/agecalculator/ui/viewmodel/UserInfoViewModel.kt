package com.assignment.agecalculator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.agecalculator.exception.DateEmptyException
import com.assignment.agecalculator.exception.FirstNameEmptyException
import com.assignment.agecalculator.exception.LastNameEmptyException
import com.assignment.agecalculator.model.UserAgeModel
import com.assignment.agecalculator.model.UserDataModel
import java.util.*

/*
* User Information view model
* */
class UserInfoViewModel : ViewModel() {

    var userDataModel: UserDataModel = UserDataModel()
    private var ageInfoMutableLiveData = MutableLiveData<UserAgeModel>()
    private var errorInfoMutableLiveData = MutableLiveData<Throwable>()
    val ageInfoLiveData: LiveData<UserAgeModel> get() = ageInfoMutableLiveData
    val errorInfoLiveData: LiveData<Throwable> get() = errorInfoMutableLiveData

    fun userAgeCalculation() {
        val currentDate = Calendar.getInstance()
        val dobDate = Calendar.getInstance()
        dobDate.set(userDataModel.year, userDataModel.month, userDataModel.day)

        val ageYear = currentDate.get(Calendar.YEAR) - dobDate.get(Calendar.YEAR)
        val ageMonth = currentDate.get(Calendar.MONTH) - dobDate.get(Calendar.MONTH) + 1
        val ageDay = currentDate.get(Calendar.DAY_OF_MONTH) - dobDate.get(Calendar.DAY_OF_MONTH)
        ageInfoMutableLiveData.value = UserAgeModel(ageYear, ageMonth, ageDay)
    }

    fun isInputFieldValidate(): Boolean = when {
        userDataModel.firstName.isEmpty() -> {
            errorInfoMutableLiveData.value = FirstNameEmptyException()
            false
        }
        userDataModel.lastName.isEmpty() -> {
            errorInfoMutableLiveData.value = LastNameEmptyException()
            false
        }
        userDataModel.year == 0 -> {
            errorInfoMutableLiveData.value = DateEmptyException()
            false
        }
        else -> true
    }
}