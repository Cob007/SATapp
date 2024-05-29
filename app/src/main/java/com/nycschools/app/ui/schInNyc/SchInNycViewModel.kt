package com.nycschools.app.ui.schInNyc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nycschools.app.model.ApiResponse
import com.nycschools.app.repo.AppRepository
import com.practice.nextapplication.model.api.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SchInNycViewModel() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val empty = MutableLiveData<Boolean>().apply { value = false }
    val dataLoading = MutableLiveData<Boolean>().apply { value = false }
    val list = MutableLiveData<List<ApiResponse>>()

    fun init() {
        /*withTimeoutOrNull(250){

        }

        val time = measureTimeMillis{

        }
        Log.v("LOG","time taken $time")*/
        dataLoading.value = true
        viewModelScope.launch {
            AppRepository.getInstance().coroutineCallApi()
                //.filter { it[1].dbn == "A5" }
                .collect {
                    dataLoading.value = false
                    if (it.isEmpty()) {
                        empty.value = true
                    } else {
                        list.value = it
                        empty.value = true
                    }
                }
        }
    }



    fun fetchList() {
        dataLoading.value = true
        compositeDisposable.add(
            ApiClient.instance
                .getSchInNycRxJava()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                        response ->
                            list.value = response
                }
        )


        /*viewModelScope.launch(Dispatchers.IO) {
            appRepository.RegetSchools{ isSuccess, response ->
                dataLoading.value = false
                if(isSuccess){
                    list.value = response
                    empty.value = false
                }else{
                    empty.value = true
                }
            }
        }*/

    }
}