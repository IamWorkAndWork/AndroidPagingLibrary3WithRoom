package practice.app.myapplication.data.db

import androidx.paging.PagingSource
import androidx.room.*
import practice.app.myapplication.data.model.Cheese

@Dao
interface CheeseDao {

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): PagingSource<Int, Cheese>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cheese: List<Cheese>)

    @Insert
    fun insert(cheese: Cheese)

    @Delete
    fun delete(cheese: Cheese)

}