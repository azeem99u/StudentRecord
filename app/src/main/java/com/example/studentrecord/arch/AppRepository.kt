package com.example.studentrecord.arch

import com.example.studentrecord.database.AppDatabase
import com.example.studentrecord.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDatabase: AppDatabase) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    fun getAllItems(): Flow<List<ItemEntity>> {
        return appDatabase.itemEntityDao().getAllEntities()
    }

    suspend fun updateItem(itemEntity: ItemEntity) {
        return appDatabase.itemEntityDao().update(itemEntity)
    }

}