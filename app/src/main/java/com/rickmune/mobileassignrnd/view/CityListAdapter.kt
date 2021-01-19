package com.rickmune.mobileassignrnd.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickmune.mobileassignrnd.databinding.CityItemBinding
import com.rickmune.mobileassignrnd.domain.City

class CityListAdapter(private val cityClicked: (City) -> Unit) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    private val items = mutableListOf<City>()

    fun setCities(cities: List<City>) {
        Log.d("CityListAdapter", "setCities: ${cities.size}")
        items.clear()
        items.addAll(cities)
        notifyDataSetChanged()
    }

    fun resetList(){
        items.clear()
        notifyDataSetChanged()
    }

    fun setCity(city: City) {
        items.add(city)
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            CityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CityViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                cityClicked.invoke(items[adapterPosition])
            }
        }

        fun bind(city: City) {
            binding.apply {
                title.text = "${city.name}  ${city.country}"
                cordinates.text = "${city.coord.lat}, ${city.coord.lon}"
            }
        }
    }

}