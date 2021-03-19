package com.android.gitusers.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.gitusers.model.ResultItemsSearch
import com.android.gitusers.repository.GitUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {

    fun getDataUser(gitUserRepository: GitUserRepository) = gitUserRepository.getDataFromDb()

    fun deleteDataUser(data: ResultItemsSearch, gitUserRepository: GitUserRepository) = viewModelScope.launch {
        gitUserRepository.deleteDataFromDb(data)
    }

}