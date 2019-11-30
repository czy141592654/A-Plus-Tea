package com.example.aplustea

import androidx.room.*


@Dao
interface BubbleDAO {
    @Delete
    fun delete(info: PersonalInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(info: PersonalInfo)

    @Query("SELECT * FROM bubbleDB WHERE phone=:phoneNumber")
    fun getInfoByPhone(phoneNumber: String): PersonalInfo

    @Query("SELECT * FROM bubbleDB")
    fun getAll():List<PersonalInfo>
}