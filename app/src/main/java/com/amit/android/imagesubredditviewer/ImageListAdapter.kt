package com.amit.android.imagesubredditviewer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amit.android.imageloader.ImageLoader
import kotlinx.android.synthetic.main.image_item_list.view.*

class ImageListAdapter(val myList : ArrayList<ImageDataLink>, val context: Context,val imageLoader: ImageLoader): RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.image_item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = myList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("asdf","position $position")
        imageLoader.DisplayImage(
            myList[position].thumbnail,
            holder.imageView
        )

        holder.imageView.setOnClickListener {
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra("image_url",myList[position].url)
            context.startActivity(intent)
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val imageView = view.image_item
    }

}