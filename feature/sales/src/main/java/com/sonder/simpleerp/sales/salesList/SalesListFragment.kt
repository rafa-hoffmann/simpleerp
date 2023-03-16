package com.sonder.simpleerp.sales.salesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sonder.simpleerp.feature.activity_list.R
import com.sonder.simpleerp.feature.activity_list.databinding.FragmentSalesListBinding
import com.sonder.simpleerp.sales.UiState
import com.sonder.simpleerp.sales.salesList.adapter.SalesListAdapter
import com.sonder.simpleerp.sales.salesList.bottomsheet.AddSaleBottomSheet
import com.sonder.simpleerp.sales.salesList.bottomsheet.AddSaleViewModel
import com.sonder.simpleerp.sales.showToast
import com.sonder.simpleerp.sales.toMonetaryUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalesListFragment : Fragment() {

    private val viewModel: SalesListViewModel by viewModels()
    private val addSaleViewModel: AddSaleViewModel by activityViewModels()

    private var _binding: FragmentSalesListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: SalesListAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalesListBinding.inflate(inflater, container, false)
        _adapter = SalesListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        setupButtons()

        viewModel.getSalesWithValue()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButtons() {
        binding.addSale.setOnClickListener {
            val bottomSheet = AddSaleBottomSheet()

            bottomSheet.show(childFragmentManager, AddSaleBottomSheet.TAG)
        }
    }

    private fun setupRecyclerView() {
        binding.salesList.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.salesState.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.submitList(it.value)
                                updateSalesTotal(it.value.sumOf { item -> item.value.toDouble() })
                                updateProgressBar(loading = false)
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(it.exception?.message ?: getString(R.string.sales_error))
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }

                launch {
                    addSaleViewModel.addSaleState.collect {
                        if (it is UiState.Success) {
                            viewModel.getSalesWithValue()
                        }
                    }
                }
            }
        }
    }

    private fun updateSalesTotal(value: Double) {
        binding.salesTotal.isVisible = true
        binding.salesTotal.text = getString(R.string.sales_total, value.toMonetaryUnit())
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.salesProgressBar.isVisible = loading
    }
}
