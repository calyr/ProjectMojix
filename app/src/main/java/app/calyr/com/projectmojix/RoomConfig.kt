package app.calyr.com.projectmojix

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:User): Long

    @Delete
    fun delelteUser(user:User)

    @Query("SELECT * FROM User")
    fun findByAll(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun findById(id:Long): User

}

@Database(entities = arrayOf(User::class), version = 1)
abstract class MyDataBase: RoomDatabase() {

    abstract fun getUserDato() : UserDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllTables() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}