package com.nycschools.app.ui.satNyc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nycschools.app.model.ApiSATResponse
import com.nycschools.app.repo.AppRepository

class SatNycViewModel : ViewModel() {
    var loading = MutableLiveData<Boolean>().apply { value = false }
    var model = MutableLiveData<ApiSATResponse>()
    var empty = MutableLiveData<String>()

    fun getByDbn(userDbn: String) {
        loading.value = true
        AppRepository.getInstance().getSatByDbn(userDbn){
            isSuccess, response ->
            loading.value = false
            if (isSuccess){
                if (!response.isEmpty()){
                    model.value = response[0]
                    empty.value = ""
                }else{
                    empty.value = "There is not response for this school "
                }
            }else{
                empty.value = "Network Error"
            }
        }
    }

}