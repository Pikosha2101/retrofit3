package com.example.retrofit3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit3.databinding.RecyclerItemBinding
import com.squareup.picasso.Picasso

class Adapter(private var items : ArrayList<CountryModel>) : RecyclerView.Adapter<Adapter.Holder>() {



    class Holder(item : View) : RecyclerView.ViewHolder(item){
        private val binding = RecyclerItemBinding.bind(item)
        fun bind(countryModel: CountryModel) = with(binding){
            countryName.text = countryModel.name
            populationTextView.text = countryModel.population.toString()
            Picasso.get().load(countryModel.flags.png).into(flagImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(
            inflater.inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(
            items[position]
        )
    }

    fun updateData(newData: ArrayList<CountryModel>) {
         items = newData
        notifyDataSetChanged() // Уведомляем RecyclerView, что данные изменились
    }
}