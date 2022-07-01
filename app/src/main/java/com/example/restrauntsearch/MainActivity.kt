package com.example.restrauntsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restrauntsearch.adapter.RecyclerAdapter
import com.example.restrauntsearch.data.Restaurant
import com.example.restrauntsearch.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var list_item: List<Restaurant>

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val res: String? = read_json()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        list_item = ArrayList<Restaurant>()


        mainViewModel.setItemList(list_item)
        mainViewModel.getItemList()
        recyclerView.adapter = RecyclerAdapter(list_item, this)
    }

    private fun read_json(): String? {
        var json: String? = null

        try {
            val inputStream: InputStream = assets.open("Restraunt.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {

        }
        return json
    }


}