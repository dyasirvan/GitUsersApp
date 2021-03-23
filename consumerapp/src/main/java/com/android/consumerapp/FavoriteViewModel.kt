package com.android.consumerapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.consumerapp.model.ResultItem
import com.android.consumerapp.repository.GitUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {

    fun getDataUser(gitUserRepository: GitUserRepository) = gitUserRepository.getDataFromDb()

    fun deleteDataUser(data: ResultItem, gitUserRepository: GitUserRepository) = viewModelScope.launch {
        gitUserRepository.deleteDataFromDb(data)
    }

}