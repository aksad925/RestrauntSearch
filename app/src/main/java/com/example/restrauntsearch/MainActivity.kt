package com.example.restrauntsearch

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restrauntsearch.adapter.RecyclerAdapter
import com.example.restrauntsearch.data.Menu
import com.example.restrauntsearch.data.MenuItem
import com.example.restrauntsearch.data.Restaurant
import com.example.restrauntsearch.viewmodel.MainViewModel
import com.example.restrauntsearch.viewmodel.MainViewModelFactory
import com.google.gson.JsonArray
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var restaurant: Restaurant
    lateinit var menuItem: MenuItem
    lateinit var searchView: SearchView
    lateinit var listAdapter: RecyclerAdapter
    private var totalRestaurant: ArrayList<Restaurant> = arrayListOf()
    private var matchedRestaurant: ArrayList<Restaurant> = arrayListOf()
    private var menuItemList: ArrayList<MenuItem> = arrayListOf()
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val res: String? = read_json("Restraunt.json")
        recyclerView = findViewById(R.id.recycler_view)
        searchView = findViewById(R.id.search_Restraunt)
        supportActionBar?.hide()
        val factory = MainViewModelFactory()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)


        //parsing json for restraunt
        val jsonObjectOfRestraunt = JSONObject(res)
        val jsonArrayOfRestraunt = jsonObjectOfRestraunt.getJSONArray("restaurants")
        //parsing json for Menu


        val menu: String? = read_json("Menu.json")
        val jsonObjectOfMenu = JSONObject(menu)
        val jsonArrayOfMenu = jsonObjectOfMenu.getJSONArray("menus")

        //For Restraunt
        for (i in 0 until jsonArrayOfRestraunt.length()) {
            val name = jsonArrayOfRestraunt.getJSONObject(i).get("name")
            val photoGraph = jsonArrayOfRestraunt.getJSONObject(i).get("photograph")
            val id = jsonArrayOfRestraunt.getJSONObject(i).get("id")
            val cuisine_type = jsonArrayOfRestraunt.getJSONObject(i).get("cuisine_type")
            restaurant = Restaurant(
                id as Int, cuisine_type as String, name as String,
                photoGraph as String
            )

            totalRestaurant.add(restaurant)
            listAdapter = RecyclerAdapter(totalRestaurant, this)
            mainViewModel.lst.observe(this, Observer {
                Log.i("data", it.toString())
                mainViewModel.lst.value = it
                listAdapter = RecyclerAdapter(it, this)
                recyclerView.adapter = listAdapter
                listAdapter.notifyDataSetChanged()
            })
        }

        recyclerView.adapter = listAdapter
        performSearch()


    }


    private fun performSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        matchedRestaurant = arrayListOf()
        text?.let {
            totalRestaurant.forEach { restraunt ->
                if (restraunt.name.startsWith(text, true) || restraunt.name.contains(text, true) ||
                    restraunt.cuisine_type.startsWith(
                        text,
                        true
                    ) || restraunt.cuisine_type.contains(text, true)
                ) {
                    matchedRestaurant.add(restraunt)
                }
            }
            updateRecyclerView()
            if (matchedRestaurant.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {

        listAdapter = RecyclerAdapter(matchedRestaurant, this@MainActivity)
        recyclerView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()

    }

    private fun read_json(jsonType: String): String? {
        var json: String? = null

        try {
            val inputStream: InputStream = assets.open(jsonType)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {

        }
        return json
    }


}


