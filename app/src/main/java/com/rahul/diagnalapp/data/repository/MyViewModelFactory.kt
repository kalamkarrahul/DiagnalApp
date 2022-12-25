package com.rahul.dignaltask.data.network.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.diagnalapp.data.repository.PagingRepository
import com.rahul.diagnalapp.ui.viewModel.PagingViewModel

class MyViewModelFactory constructor(private val repository: PagingRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PagingViewModel::class.java)) {
            PagingViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}