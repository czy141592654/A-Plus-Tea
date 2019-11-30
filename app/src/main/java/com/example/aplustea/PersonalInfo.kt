package com.example.aplustea

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bubbleDB")
class PersonalInfo(
    @PrimaryKey
    var phone:String,
    var name:String,
    var address:String)
