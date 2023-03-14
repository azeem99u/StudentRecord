package com.example.studentrecord.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentrecord.database.AppDatabase
import com.example.studentrecord.database.entity.ItemEntity
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private lateinit var repository: AppRepository

    val categoryEntitiesLiveData = MutableLiveData<List<ItemEntity>>()

    fun init(appDatabase: AppDatabase) {
        repository = AppRepository(appDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { categories ->
                categoryEntitiesLiveData.postValue(categories)
            }
        }
    }
    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)
        }

    }
    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }

    }
    fun updateItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)
        }
    }

}