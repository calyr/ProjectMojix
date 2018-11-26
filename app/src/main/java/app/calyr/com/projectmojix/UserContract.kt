package app.calyr.com.projectmojix

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.text.TextUtils
import android.util.Log
import java.util.*

@Entity(tableName = "user")
data class User(
        @ColumnInfo(name = "id")
        @PrimaryKey
        var id: String = "",
        @ColumnInfo(name = "name")
        var name: String? = null,
        @ColumnInfo(name = "address")
        var address: String? = null,
        @ColumnInfo(name = "birth_date")
        var birthDate: String? = null,
        @ColumnInfo(name = "phone_number")
        var phoneNumber: String? = null,
        @ColumnInfo(name = "email")
        var email: String? = null ) {
    @Ignore constructor():this("","", "", "", "")
}

class UserContract {
    interface Presenter {
        fun saveUser()
        fun editUser(userId: String)
        fun loadUser(userId: String)

    }

    interface ViewUser {
        fun showErrorName(message:String)
        fun showErrorAddress(message:String)
        fun showErrorPhoneNumber(message: String)
        fun showErrorBirthDate(message: String)
        fun showErrorEmail(message: String)
        fun loading()
        fun loadUser( user: User)
        fun navigateToList()
        fun getFormUser(): User
    }

    interface Interactor {

        fun save(user:User, listener: OnListener)
        fun edit(user:User, listener: OnListener)
        fun getUser(userId: String, listener: OnListener): User
        interface OnListener {
            fun onSuccess()
            fun onErrorName(errorId:Int)
            fun onErrorAddress(errorId:Int)
            fun onErrorPhoneNumber(errorId:Int)
            fun onErrorBirthDate(errorId:Int)
            fun onErrorEmail(errorId:Int)
            fun onLoadUser(user: User)
        }
    }

    class PresenterImpl(val context: Context, val view: ViewUser) : Presenter, Interactor.OnListener {

        override fun loadUser(userId: String) {
            view.let {
                val userEdit = interactorUser.getUser(userId, this)
                view.loadUser(userEdit)
            }
        }

        private val interactorUser: Interactor
        init {
            interactorUser = InteractorImpl()
        }
        override fun onSuccess() {
            view.let {
                view.navigateToList()
            }
        }

        override fun onErrorName(errorId: Int) {
            view.showErrorName(context.getString(errorId))
        }

        override fun onErrorAddress(errorId: Int) {
            view.showErrorAddress(context.getString(errorId))
        }

        override fun onErrorPhoneNumber(errorId: Int) {
            view.showErrorPhoneNumber(context.getString(errorId))
        }

        override fun onErrorBirthDate(errorId: Int) {
            view.showErrorBirthDate(context.getString(errorId))
        }

        override fun onErrorEmail(errorId: Int) {
            view.showErrorEmail(context.getString(errorId))
        }

        override fun saveUser() {
            view.let {
                interactorUser.save(view.getFormUser(), this)
            }
        }

        override fun editUser(userId: String) {
            view.let {
                val user = view.getFormUser()
                user.id = userId
                interactorUser.edit(user, this)
            }
        }

        override fun onLoadUser(user: User) {
            view.let {
                view.loadUser(user)
            }
        }


    }

    class InteractorImpl : Interactor  {

        override fun getUser(userId: String, listener: Interactor.OnListener): User {
            val user = db.getUserDato().findById(userId)
            Log.d("VER", user.toString())
            return user
        }

        override fun save(user: User, listener: Interactor.OnListener) {


            if (validateUser(user, listener)) {
                user.id = UUID.randomUUID().toString()
                db.getUserDato().insertUser(user)
                listener.onSuccess()
            }
        }

        override fun edit(user: User, listener: Interactor.OnListener) {
            if (validateUser(user, listener)) {
                db.getUserDato().editUser(user)
                listener.onSuccess()
            }
        }

        fun validateUser(user:User, listener: Interactor.OnListener) : Boolean {
            var error = false

            if (TextUtils.isEmpty(user.name)) {
                error = true
                listener.onErrorName(R.string.form_empty)
            }
            if (TextUtils.isEmpty(user.address)) {
                error = true
                listener.onErrorAddress(R.string.form_empty)
            }
            if (TextUtils.isEmpty(user.birthDate)) {
                error = true
                listener.onErrorBirthDate(R.string.form_empty)
            }
            if (TextUtils.isEmpty(user.email)) {
                error = true
                listener.onErrorEmail(R.string.form_empty)
            }
            if (TextUtils.isEmpty(user.phoneNumber)) {
                error = true
                listener.onErrorPhoneNumber(R.string.form_empty)
            }
            return !error
        }


    }



}

