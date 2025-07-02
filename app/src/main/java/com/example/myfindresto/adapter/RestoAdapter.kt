package com.example.myfindresto.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfindresto.R
import com.example.myfindresto.viewModel.RestoWithDistance

class RestoAdapter(
    private  var list: List<RestoWithDistance>
) : RecyclerView.Adapter<RestoAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgResto = view.findViewById<ImageView>(R.id.img_resto)
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvJarakRating = view.findViewById<TextView>(R.id.tv_jarak_rating)
        val tvAlamat = view.findViewById<TextView>(R.id.tv_alamat)
        val tvBuka = view.findViewById<TextView>(R.id.tv_buka)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_retoran, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resto = list[position].restaurant
        val distance = list[position].distance
        Glide.with(holder.imgResto.context).load(resto.imageUrl).into(holder.imgResto)
        holder.tvNama.text = resto.name
        holder.tvJarakRating.text = String.format("%.1f Km | %.1f ★", distance, resto.rating)
        holder.tvAlamat.text = resto.address
        holder.tvBuka.text = if (resto.isOpen) "Buka" else "Tutup"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<RestoWithDistance>) {
        list = newList
        notifyDataSetChanged()
    }

}