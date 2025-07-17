package com.yourcompany.focusapp.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yourcompany.focusapp.data.repository.TaskRepository
import com.yourcompany.focusapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(repository: TaskRepository) : BaseViewModel() {
    val tasks: LiveData<List<com.yourcompany.focusapp.data.model.Task>> =
        repository.getTasks().asLiveData(viewModelScope.coroutineContext)
}
