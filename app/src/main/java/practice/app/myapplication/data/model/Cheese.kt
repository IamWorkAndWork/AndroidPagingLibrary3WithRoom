package practice.app.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cheese(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "name")
    val name: String)