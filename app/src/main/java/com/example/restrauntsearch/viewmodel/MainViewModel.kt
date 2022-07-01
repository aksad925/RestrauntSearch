package com.example.restrauntsearch.viewmodel

import android.content.Context
import android.os.ParcelFileDescriptor.open
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restrauntsearch.data.Restaurant
import com.google.android.material.internal.ContextUtils.getActivity

import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject



class MainViewModel @Inject constructor() :ViewModel() {

    lateinit var listOfRestraunt:MutableLiveData<List<Restaurant>>

    init {
        listOfRestraunt=MutableLiveData()
    }

    fun getItemList():MutableLiveData<List<Restaurant>> {
      return listOfRestraunt
    }

 fun setItemList( list:List<Restaurant> )
 {
     listOfRestraunt.value=list
 }
}