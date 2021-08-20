package com.nuu.cloudinteractive.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.nuu.cloudinteractive.ApiInterface
import com.nuu.cloudinteractive.config.AppConfig
import com.nuu.cloudinteractive.model.ThumbnailInfo
import com.nuu.cloudinteractive.model.ThumbnailRepository
import java.util.*

class ThumbnailViewModel: ViewModel(){
    val thumbInfoAllArray = MutableLiveData<Array<ThumbnailInfo>>()

    init {
        getPhotosInfo()  // call api
    }

    private fun getPhotosInfo() {
        ThumbnailRepository.getInstance().getDataApi(AppConfig.URL_CALL_PHOTOS, null, object :
            ApiInterface {
            override fun onSuccess(dataSting: String) {
                val dataModelArray = Gson().fromJson(dataSting, Array<ThumbnailInfo>::class.java)
                thumbInfoAllArray.postValue(dataModelArray)
            }

            override fun onFailed(errorMsg: String?) {

            }
        })
    }
}