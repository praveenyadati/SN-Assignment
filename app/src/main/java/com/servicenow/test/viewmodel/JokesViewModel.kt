package com.servicenow.test.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.servicenow.test.model.Joke
import com.servicenow.test.network.JokesRepository
import com.servicenow.test.network.UseCaseResult
import com.servicenow.test.ui.LoginForm
import com.servicenow.test.utils.Constants
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class JokesViewModel(
    private val handle: SavedStateHandle,
    private val jokesRepository: JokesRepository
) : ViewModel(), CoroutineScope {

    private var login: LoginForm? = null
    private var onFocusFirstName: TextWatcher? = null
    private var onFocusLastName: TextWatcher? = null
    private val savedStateHandle = handle
    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val resultResponse = MutableLiveData<Array<out Joke>>()
    val isNetworkAvailable = MutableLiveData<Boolean>()

    fun init() {
        login = LoginForm()
        login?.firstNameField?.name = savedStateHandle.get(Constants.KEY_FIRST_NAME)
        login?.lastNameField?.name = savedStateHandle.get(Constants.KEY_LAST_NAME)
        onFocusFirstName = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                login?.firstNameField?.validate = true
            }

            override fun afterTextChanged(s: Editable) {
                if (login?.firstNameField?.validate!! && s.toString().length >= 0) {
                    login?.notifyFirstName()
                    savedStateHandle.set(Constants.KEY_FIRST_NAME, s.toString())
                }

            }
        }

        onFocusLastName = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                login?.lastNameField?.validate = true
            }

            override fun afterTextChanged(s: Editable) {
                if (login?.lastNameField?.validate!! && s.toString().length >= 0) {
                    login?.notifyLastName()
                    savedStateHandle.set(Constants.KEY_LAST_NAME, s.toString())
                }
            }
        }
    }

    fun getJokes(firstName: String?, lastName: String?) {
        showLoading.value = true

        launch {
            coroutineContext
            val result: UseCaseResult<Joke>?
            if (isNetworkAvailable.value ?: false) {
                result =
                    withContext(Dispatchers.IO) { jokesRepository.getJokes(firstName, lastName) }
            } else {
                result = withContext(Dispatchers.IO) { jokesRepository.getJokesFromCache() }
            }
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> resultResponse.value = result.data
                is UseCaseResult.Error -> resultResponse.value = null
            }
        }
    }


    fun getLogin(): LoginForm? {
        return login
    }

    fun getFirstNameOnFocusChangeListener(): TextWatcher? {
        return onFocusFirstName
    }

    fun getLastNameOnFocusChangeListener(): TextWatcher? {
        return onFocusLastName
    }

    fun onButtonClick() {
        login!!.onClick()
    }

    fun getLoginFields(): MutableLiveData<Pair<String?, String?>> {
        return login!!.loginFields
    }

    fun onDestroy() {
        showLoading.value = false
    }


}

@BindingAdapter("onTextChanged")
fun bindFocusChange(editText: EditText, textWatcher: TextWatcher?) {
    editText.addTextChangedListener(textWatcher)
}

@BindingAdapter("error")
fun bindError(editText: EditText, error: String?) {
    editText.error = error
}

@BindingAdapter("android:text")
fun setStringWIthSelection(view: EditText, str: String?) {
    val updateTextLength = if (str == null) 0 else str.length
    view.setText(str)
    view.setSelection(updateTextLength)
}