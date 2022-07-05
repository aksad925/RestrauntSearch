package com.example.restrauntsearch.viewmodel

import android.content.Context
import android.os.ParcelFileDescriptor.open
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restrauntsearch.data.Restaurant
import com.example.restrauntsearch.data.RestrauntDatas
import com.google.android.material.internal.ContextUtils.getActivity

import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject



class MainViewModel @Inject constructor() :ViewModel() {

    var lst = MutableLiveData<ArrayList<Restaurant>>()
    var newlist = arrayListOf<Restaurant>()

    fun add(restraunt: Restaurant){
        newlist.add(restraunt)
        lst.value=newlist
    }


}