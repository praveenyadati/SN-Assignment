package com.servicenow.test.viewmodel

import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.databinding.InverseMethod
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.servicenow.test.model.Joke
import com.servicenow.test.network.JokesRepository
import com.servicenow.test.network.UseCaseResult
import com.servicenow.test.ui.LoginForm
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class JokesViewModel(private val jokesRepository: JokesRepository) : ViewModel(), CoroutineScope {

    private var login: LoginForm? = null

    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val jokesListData = MutableLiveData<Array<out Joke>>()
    val jokeData = MutableLiveData<Joke>()
    val isNetworkAvailable = MutableLiveData<Boolean>()

    fun init() {
        if (login == null)
            login = LoginForm()
    }

    fun notifyFirstName() {
        login?.notifyFirstName()
    }

    fun notifyLastName() {
        login?.notifyLastName()
    }

    fun getLogin(): LoginForm? {
        return login
    }

    fun onButtonClick() {
        login!!.onClick()
    }

    val lastName: String?
        get() = login?.lastNameField?.name

    val firstName: String?
        get() = login?.firstNameField?.name

    fun getLoginFields(): MutableLiveData<Pair<String?, String?>> {
        return login!!.loginFields
    }

    fun getJokes(firstName: String?, lastName: String?, addCache: Boolean) {
        showLoading.value = true
        launch {
            coroutineContext
            var result: UseCaseResult<Joke>? = null
            if (isNetworkAvailable.value ?: false) {
                result = withContext(Dispatchers.IO) { jokesRepository.getJokes(firstName, lastName, addCache)
                }
            } else if (addCache) {
                result = withContext(Dispatchers.IO) { jokesRepository.getJokesFromCache() }
            }
            showLoading.value = false
            if (result == null) {
                postNullData(addCache)
            } else {
                when (result) {
                    is UseCaseResult.Success ->  if (addCache) jokesListData.value = result.jokesList else jokeData.value = result.joke
                    is UseCaseResult.Error -> postNullData(addCache)
                }
            }
        }
    }

    fun postNullData(addCache: Boolean) {
        if (addCache)
            jokesListData.value = null
        else
            jokeData.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}