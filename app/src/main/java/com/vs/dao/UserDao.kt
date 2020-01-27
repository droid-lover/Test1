package com.vs.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vs.models.User


/**
 * Created By Sachin
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM User WHERE `id` =:id")
    fun delete(id: Int)

    @Query("UPDATE User SET title = :title,description=:description,time=:time WHERE id = :id")
    fun update(id: Int, title: String,description:String,time:String): Int

    @Query("Select * from User ORDER BY User.time DESC")
    fun getAllNotes(): LiveData<List<User>>

}