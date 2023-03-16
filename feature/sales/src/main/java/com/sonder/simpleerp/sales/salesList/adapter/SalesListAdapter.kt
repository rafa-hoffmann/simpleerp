package com.sonder.simpleerp.sales.salesList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.simpleerp.feature.activity_list.databinding.ItemSaleBinding
import com.sonder.simpleerp.model.data.SaleWithValueResource
import com.sonder.simpleerp.sales.toMonetaryUnit

class SalesListAdapter :
    ListAdapter<SaleWithValueResource, SalesListAdapter.SaleItemViewHolder>(this) {

    inner class SaleItemViewHolder(private val binding: ItemSaleBinding) :
        ViewHolder(binding.root) {

        fun bind(saleWithValueResource: SaleWithValueResource) {
            with(binding) {
                saleId.text = saleWithValueResource.sale.id.toString()
                saleClientName.text = saleWithValueResource.sale.clientName

                saleValue.text = saleWithValueResource.value.toMonetaryUnit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleItemViewHolder {
        val binding = ItemSaleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SaleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object : DiffUtil.ItemCallback<SaleWithValueResource>() {
        override fun areItemsTheSame(
            oldItem: SaleWithValueResource,
            newItem: SaleWithValueResource
        ) =
            oldItem.sale.id == newItem.sale.id

        override fun areContentsTheSame(
            oldItem: SaleWithValueResource,
            newItem: SaleWithValueResource
        ) =
            oldItem == newItem
    }
}
