package com.sonder.simpleerp.sales.productsList.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonder.simpleerp.feature.activity_list.R
import com.sonder.simpleerp.feature.activity_list.databinding.BottomSheetAddProductBinding
import com.sonder.simpleerp.model.data.ProductResource
import com.sonder.simpleerp.sales.UiState
import com.sonder.simpleerp.sales.showToast
import kotlinx.coroutines.launch

class AddProductBottomSheet : BottomSheetDialogFragment() {

    private val addProductViewModel: AddProductViewModel by activityViewModels()

    private var _binding: BottomSheetAddProductBinding? = null
    private val binding get() = _binding!!

    private val saleId: Long by lazy {
        requireArguments().getLong(SALE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButtons()
        setupObservers()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButtons() {
        binding.addProductAction.setOnClickListener {
            if (inputsAreValid()) {
                addProductViewModel.addProduct(
                    ProductResource(
                        name = getProductName(),
                        quantity = getValidQuantity(),
                        value = getProductValue(),
                        saleId = saleId
                    )
                )
            } else {
                showToast(getString(R.string.add_product_error))
            }
        }
    }

    private fun inputsAreValid(): Boolean {
        return binding.addProductName.text?.isNotBlank() == true &&
            binding.addProductQuantity.text?.isNotBlank() == true &&
            binding.addProductValue.text?.isNotBlank() == true
    }

    private fun getProductValue() = binding.addProductValue.text?.toString()?.toFloat() ?: 0.0f

    private fun getProductName() = binding.addProductName.text.toString()

    private fun getValidQuantity() = binding.addProductQuantity.text?.toString()?.toFloat() ?: 0.0f

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            addProductViewModel.addProductState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED,
            ).collect {
                when (it) {
                    is UiState.Success -> {
                        showToast(getString(R.string.add_product_success))
                        dismiss()
                        addProductViewModel.resetState()
                    }
                    is UiState.Error -> showToast(
                        it.exception?.message ?: getString(R.string.data_error)
                    )
                    else -> {}
                }
            }
        }
    }

    companion object {
        const val TAG = "AddProductBottomSheet"
        const val SALE_ID = "saleId"
    }
}
