package com.servicenow.test.ui

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.servicenow.test.BR

class LoginForm : BaseObservable() {
    val firstNameField = LoginField()
    val lastNameField = LoginField()

    val loginFields = MutableLiveData<Pair<String?, String?>>()

    @get:Bindable
    val isValid: Boolean
        get() {
            return isValidInfutField(firstNameField) && isValidInfutField(lastNameField)
        }

    fun notifyFirstName() {
        notifyPropertyChanged(BR.valid)
        notifyPropertyChanged(BR.firstNameLength)
    }

    fun notifyLastName() {
        notifyPropertyChanged(BR.lastNameLength)
        notifyPropertyChanged(BR.valid)
    }

    fun isValidInfutField(loginField: LoginField?) : Boolean {
        val name = loginField?.name
        val empty = TextUtils.isEmpty(name)
        return !empty
    }

    fun onClick() {
        if (isValid) {
            loginFields.setValue(Pair(firstNameField.name, lastNameField.name))
        }
    }

    @get:Bindable
    val lastNameLength: String
        get() {
            val len: Int? = if (!TextUtils.isEmpty(lastNameField.name)) lastNameField.name?.length else 0
            return "${len}/${lastNameField.maxLength}"
        }

    @get:Bindable
    val firstNameLength: String
        get() {
            val len: Int? = if (!TextUtils.isEmpty(firstNameField.name)) firstNameField.name?.length else 0
            return "${len}/${firstNameField.maxLength}"
        }
}

class LoginField {
    var name: String? = null
    var maxLength : Int = 20
}