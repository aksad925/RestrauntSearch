package com.example.restrauntsearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restrauntsearch.R
import com.example.restrauntsearch.data.Restaurant
import org.w3c.dom.Text

class RecyclerAdapter(private val restaruntList: List<Restaurant>, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val dataItem = restaruntList[position]
        //Setting image
        Glide.with(context).load(dataItem.photograph).
        placeholder(R.drawable.ic_launcher_background).into(holder.itemImage)

        //settiing name
        holder.itemName.text=dataItem.name

    }

    override fun getItemCount(): Int {
        return restaruntList.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      lateinit var itemImage:ImageView
      lateinit var itemName:TextView

      init {
          itemImage=itemView.findViewById(R.id.res_image)
          itemName=itemView.findViewById(R.id.res_Name)
      }
    }
}