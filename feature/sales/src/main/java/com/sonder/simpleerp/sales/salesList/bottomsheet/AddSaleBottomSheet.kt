package com.sonder.simpleerp.sales.salesList.bottomsheet

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
import com.sonder.simpleerp.feature.activity_list.databinding.BottomSheetAddSaleBinding
import com.sonder.simpleerp.model.data.SaleResource
import com.sonder.simpleerp.sales.UiState
import com.sonder.simpleerp.sales.showToast
import kotlinx.coroutines.launch

class AddSaleBottomSheet : BottomSheetDialogFragment() {

    private val addSaleViewModel: AddSaleViewModel by activityViewModels()

    private var _binding: BottomSheetAddSaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButtons()
        setupObservers()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButtons() {
        binding.addSaleAction.setOnClickListener {
            binding.addSaleClientName.text?.let {
                if (it.isNotBlank()) {
                    addSaleViewModel.addSale(
                        SaleResource(clientName = it.toString())
                    )
                } else {
                    showToast(getString(R.string.add_sale_error_client_name))
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            addSaleViewModel.addSaleState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED,
            ).collect {
                when (it) {
                    is UiState.Success -> {
                        showToast(getString(R.string.add_sale_success))
                        dismiss()
                    }
                    is UiState.Error -> showToast(
                        it.exception?.message ?: getString(R.string.sales_error)
                    )
                    else -> {}
                }
            }
        }
    }

    companion object {
        const val TAG = "FilterActivitiesBottomSheet"
    }
}
