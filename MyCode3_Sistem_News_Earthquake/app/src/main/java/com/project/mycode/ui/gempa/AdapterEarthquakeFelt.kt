package com.project.mycode.ui.gempa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mycode.data.local.FeltEarthquakeDB
import com.project.mycode.databinding.ItemNewearthquakeBinding

class AdapterEarthquakeFelt(private val feltList: List<FeltEarthquakeDB>) :
    RecyclerView.Adapter<AdapterEarthquakeFelt.AdapterViewHolder>() {

    inner class AdapterViewHolder(val binding: ItemNewearthquakeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(ItemNewearthquakeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        with(holder) {
            with(feltList[position]) {
                binding.tvDate.text = date
                binding.tvClock.text = clock
                binding.tvWilayah.text = region
                binding.tvMagnitude.text = magnitude
                binding.tvKedalaman.text = depth
                binding.tvPotensi.text = potency
            }
        }
    }

    override fun getItemCount(): Int = feltList.size

}
