package com.example.studentrecord.database.dao

import androidx.room.*
import com.example.studentrecord.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemEntityDao {
    @Query("select * from item_entity")
    fun getAllEntities():Flow<List<ItemEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: ItemEntity)
    @Delete
    suspend fun delete(itemEntity: ItemEntity)
    @Update
    suspend fun update(itemEntity: ItemEntity)
}