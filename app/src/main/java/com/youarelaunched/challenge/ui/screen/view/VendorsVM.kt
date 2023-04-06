package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _uiState = MutableStateFlow(
        VendorsScreenUiState(
            vendors = null
        )
    )

    @OptIn(FlowPreview::class)
    val uiState = _uiState.debounce(500)
        .combine(searchText) { vendors, text ->
            if (text.length < 3) {
                vendors
            } else {
                VendorsScreenUiState(
                    getVendors()
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            VendorsScreenUiState(
                vendors = _uiState.value.vendors
            )
        )

    init {
        performSearch()
    }

    suspend fun getVendors(): List<Vendor> {
        return if (_searchText.value.isNotEmpty())
            repository.getVendorsWithCompanyName(_searchText.value)
        else
            repository.getVendors()
    }

    fun performSearch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    vendors = getVendors()
                )
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}