package com.nuu.cloudinteractive

interface ApiInterface {
    fun onSuccess(dataSting: String)
    fun onFailed(errorMsg: String?)
}