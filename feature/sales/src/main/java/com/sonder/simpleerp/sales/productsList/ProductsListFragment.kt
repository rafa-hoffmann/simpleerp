package com.sonder.simpleerp.sales.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        _adapter = ProductsListAdapter()
        return binding.root
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
                                    it.value.sumOf { item -> item.quantity },
                                    it.value.sumOf { item -> item.quantity * item.value.toDouble() }
                                )
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
            }
        }
    }

    private fun updateProductsTotal(qty: Int, value: Double) {
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
