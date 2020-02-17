package com.zimpo.githubdashboard.ModelView

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RepoModelView (application: Application) : AndroidViewModel(application)
{
    val userList : MutableLiveData<MutableList<ClotheResponse>> by lazy {
        val newdata = MutableLiveData<MutableList<ClotheResponse>>()
        newdata.value = mutableListOf()
        newdata
    }
    //    var imageClothesList: MutableList<ClotheResponse> = mutableListOf()
    private val dao = fr.epita.android.vintedapp.dataAccess.listImages.ListImages()

    fun getAllClothe(context: Context) {
        val threadPool: ExecutorService = Executors.newFixedThreadPool(1)
        threadPool.submit {
            var newData = dao.getClothe(context)
            imageClothesList.postValue(newData)
        }
    }

    fun deleteAll(context: Context) {
        val threadPool: ExecutorService = Executors.newFixedThreadPool(1)
        threadPool.submit {
            dao.deleteClothe(context)

        }
        imageClothesList.value = mutableListOf()
    }
}