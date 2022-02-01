package com.sadeghjfr22.covid19.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sadeghjfr22.covid19.data.repository.MainRepository
import com.sadeghjfr22.covid19.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getGlobal() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getGlobal()))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

    fun getCountries() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getCountries()))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

    fun getIran() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = mainRepository.getIran()))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

}