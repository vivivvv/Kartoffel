package com.app.mybase


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.app.mybase.base.BaseViewModel
import com.app.mybase.helper.ApisResponse
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    var showAddBtn = MutableLiveData<Boolean>()

    init {
        hideAddBtn()
    }

    fun showAddBtn() {
        showAddBtn.value = true
    }

    fun hideAddBtn() {
        showAddBtn.value = false
    }

    fun getResponseData(catId: String) = liveData(Dispatchers.IO) {
        emit(ApisResponse.Loading)
        emit(mainRepository.getResponseData(catId))
        emit(ApisResponse.Complete)
    }

}