package com.app.mybase.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.mybase.R
import com.app.mybase.databinding.RvCatCardBinding
import com.app.mybase.model.CatDetails

class CatAdapter(val context: Context) :
    RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    private var rvList = ArrayList<LinkedHashMap<String, CatDetails>>()
    var productList = ArrayList<String>()

    fun addItem(
        productList: ArrayList<String>,
        rvList: ArrayList<LinkedHashMap<String, CatDetails>>,
        position: Int
    ) {
        this.productList = productList
        this.rvList = rvList
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<RvCatCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_cat_card,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int {
        return rvList.size
    }

    inner class ViewHolder(itemView: RvCatCardBinding) : RecyclerView.ViewHolder(itemView.root) {
        var filterList: Spinner = itemView.catList
        var delBtn: Button = itemView.deleteBtn
        var price: TextView = itemView.priceValue
        var quantity: TextView = itemView.quantityValue
        var sum: TextView = itemView.sumValue
        var plusBtn: Button = itemView.plusBtn
        var minusBtn: Button = itemView.minusBtn

        @SuppressLint("SetTextI18n")
        fun setData(rvPosition: Int) {
            val adapter = ArrayAdapter(
                context.applicationContext,
                android.R.layout.simple_list_item_1, productList
            )
            filterList.adapter = adapter
            filterList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    price.text = rvList[adapterPosition][productList[position]]?.price
                    sum.text = rvList[adapterPosition][productList[position]]?.sum
                    quantity.text = rvList[adapterPosition][productList[position]]?.quantity
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            // Deleting values
            delBtn.setOnClickListener {
                Log.d("TAG", "setData: $adapterPosition")
                if(adapterPosition >= 0){
                    rvList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
            // Adding quantity
            plusBtn.setOnClickListener {
                quantity.text = (quantity.text.toString().toInt() + 1).toString()
                sum.text = (price.text.toString().toFloat() * quantity.text.toString()
                    .toFloat()).toString()
            }
            // Reducing quantity
            minusBtn.setOnClickListener {
                if (quantity.text.toString().toInt() > 0) {
                    quantity.text = (quantity.text.toString().toInt() - 1).toString()
                    sum.text = (price.text.toString().toFloat() * quantity.text.toString()
                        .toFloat()).toString()
                }
            }

        }
    }

}