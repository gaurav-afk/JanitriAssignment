package com.towerofapp.janitriassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towerofapp.janitriassignment.MyApp
import com.towerofapp.janitriassignment.data.local.VitalEntity
import com.towerofapp.janitriassignment.data.repository.VitalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VitalViewModel(private val repository: VitalRepository) : ViewModel() {
    private val _vitals = MutableStateFlow<List<VitalEntity>>(emptyList())
    val vitals: StateFlow<List<VitalEntity>> = _vitals

    init {
        getVitals()
    }

    fun getVitals() {
        viewModelScope.launch {
            repository.getVitals().collect { list ->
                _vitals.value = list
            }
        }
    }

    fun addVitals(vital: VitalEntity){
        viewModelScope.launch {
            repository.addVitals(vital = vital)
        }
    }

}