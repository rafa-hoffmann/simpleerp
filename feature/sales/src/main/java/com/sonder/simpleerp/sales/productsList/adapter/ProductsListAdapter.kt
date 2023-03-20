package com.sonder.simpleerp.sales.productsList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.simpleerp.feature.activity_list.R
import com.sonder.simpleerp.feature.activity_list.databinding.ItemProductBinding
import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.sales.toMonetaryUnit

class ProductsListAdapter(private val deleteListener: (ProductResource) -> (Unit)) :
    ListAdapter<ProductResource, ProductsListAdapter.ProductItemViewHolder>(this) {

    inner class ProductItemViewHolder(private val binding: ItemProductBinding) :
        ViewHolder(binding.root) {

        fun bind(productResource: ProductResource) {
            with(binding) {
                productName.text = productResource.name
                productQuantity.text = root.context.getString(
                    R.string.product_item_quantity,
                    productResource.quantity.toString()
                )
                productUnitValue.text = root.context.getString(
                    R.string.product_item_unit_value,
                    productResource.value.toMonetaryUnit()
                )
                productTotalValue.text = root.context.getString(
                    R.string.product_item_total_value,
                    (productResource.value * productResource.quantity).toMonetaryUnit()
                )

                productDeleteAction.setOnClickListener {
                    deleteListener(productResource)
                }

                productDiscountValue.isVisible = productResource.discount != null && productResource.discount != 0.0f
                productDiscountValue.text = root.context.getString(R.string.product_item_discount_value, productResource.discount?.toMonetaryUnit())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object : DiffUtil.ItemCallback<ProductResource>() {
        override fun areItemsTheSame(
            oldItem: ProductResource,
            newItem: ProductResource
        ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ProductResource,
            newItem: ProductResource
        ) =
            oldItem == newItem
    }
}
