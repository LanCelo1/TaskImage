package com.example.taskapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskapp.R
import com.example.taskapp.data.model.ImageData
import de.hdodenhof.circleimageview.CircleImageView

class ImageVrAdapter() : ListAdapter<ImageData,ImageVrAdapter.VH>(diffUtills) {
    var listener : ((Int)->Unit)? = null
    inner class VH(val view : View) : RecyclerView.ViewHolder(view){
        val imageView : CircleImageView = view.findViewById(R.id.imageview)
        val title : TextView = view.findViewById(R.id.title)
        fun bind(){
//            if (absoluteAdapterPosition % 19 == 0) listener?.invoke(absoluteAdapterPosition/19)
            val item = getItem(absoluteAdapterPosition)
            if (item.imageUrl.isNotEmpty()) Glide.with(view).load(item.imageUrl).into(imageView)
            title.text = item.author
        }
    }

    fun pagingListener(block : ((Int)->Unit)){
        listener = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_vr_rv,parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind()
    }

    companion object {
        val diffUtills = object : DiffUtil.ItemCallback<ImageData>() {

            override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData): Boolean {
                return oldItem.imageUrl == newItem.imageUrl && oldItem.id == newItem.id
            }
        }
    }
}