package com.sonder.simpleerp.sales.productsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sonder.simpleerp.feature.activity_list.R
import com.sonder.simpleerp.feature.activity_list.databinding.FragmentProductsListBinding
import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.sales.UiState
import com.sonder.simpleerp.sales.productsList.adapter.ProductsListAdapter
import com.sonder.simpleerp.sales.productsList.bottomsheet.AddProductBottomSheet
import com.sonder.simpleerp.sales.productsList.bottomsheet.AddProductViewModel
import com.sonder.simpleerp.sales.showToast
import com.sonder.simpleerp.sales.toMonetaryUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private val viewModel: ProductsListViewModel by viewModels()
    private val addProductViewModel: AddProductViewModel by activityViewModels()

    private var _binding: FragmentProductsListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: ProductsListAdapter? = null
    private val adapter get() = _adapter!!

    private val saleId: Long by lazy {
        requireArguments().getLong(SALE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        _adapter = ProductsListAdapter { deleteProduct(it) }
        return binding.root
    }

    private fun deleteProduct(productResource: ProductResource) {
        viewModel.deleteProduct(productResource)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        setupButtons()

        viewModel.getProducts(saleId)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButtons() {
        binding.addProduct.setOnClickListener {
            val bottomSheet = AddProductBottomSheet()
            val bundle = bundleOf(AddProductBottomSheet.SALE_ID to saleId)
            bottomSheet.arguments = bundle
            bottomSheet.show(parentFragmentManager, AddProductBottomSheet.TAG)
        }

        binding.productsDiscountValue.onFocusChangeListener = (OnFocusChangeListener { _, _ ->
            binding.productsDiscountValue.text.toString().toFloatOrNull()?.let {
                viewModel.updateProductsDiscount(it)
            }
        })
    }

    private fun setupRecyclerView() {
        binding.productsList.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.productsState.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.submitList(it.value)
                                updateProductsTotal(
                                    it.value.sumOf { item -> item.quantity.toDouble() },
                                    it.value.sumOf { item -> item.quantity * item.value.toDouble() }
                                )
                                updateProductsDiscount()
                                updateProgressBar(loading = false)
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(it.exception?.message ?: getString(R.string.data_error))
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }

                launch {
                    addProductViewModel.addProductState.collect {
                        if (it is UiState.Success) {
                            viewModel.getProducts(saleId)
                        }
                    }
                }

                launch {
                    viewModel.deleteProductState.collect {
                        when (it) {
                            is UiState.Success -> {
                                viewModel.getProducts(saleId)
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(it.exception?.message ?: getString(R.string.data_error))
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }

                launch {
                    viewModel.productsDiscountState.collect {
                        updateProductsDiscount()
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateProductsDiscount() {
        val totalDiscount = viewModel.productsDiscountState.value

        val totalOrderValue = adapter.currentList.sumOf { item -> item.quantity * item.value.toDouble() }
        adapter.currentList.forEach {
            val totalItemValue = it.quantity * it.value
            val productPercentage = (totalItemValue / totalOrderValue).toFloat()
            it.discount = productPercentage * totalDiscount
        }

        updateProductsTotal(
            adapter.currentList.sumOf { item -> item.quantity.toDouble() },
            totalOrderValue - totalDiscount
        )

        adapter.notifyDataSetChanged()
    }

    private fun updateProductsTotal(qty: Double, value: Double) {
        binding.productsTotalQuantity.text =
            getString(R.string.products_total_quantity, qty.toString())
        binding.productsTotalValue.text =
            getString(R.string.products_total_value, value.toMonetaryUnit())
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.productsProgressBar.isVisible = loading
    }

    companion object {
        const val SALE_ID = "saleId"
    }
}
