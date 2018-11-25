package app.calyr.com.projectmojix

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.text.TextUtils
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
    constructor():this("","", "", "", "")
}

class UserContract {
    interface Presenter {
        fun saveUser(user:User)

    }

    interface ViewUser {
        fun saveUser(user:User)
        fun showErrorName(message:String)
        fun showErrorAddress(message:String)
        fun showErrorPhoneNumber(message: String)
        fun showErrorBirthDate(message: String)
        fun showErrorEmail(message: String)
        fun loading()
        fun navigateToList()
    }

    interface Interactor {

        fun save(user:User, listener: OnListener)

        interface OnListener {
            fun onSuccess()
            fun onErrorName(errorId:Int)
            fun onErrorAddress(errorId:Int)
            fun onErrorPhoneNumber(errorId:Int)
            fun onErrorBirthDate(errorId:Int)
            fun onErrorEmail(errorId:Int)
        }
    }

    class PresenterImpl(val context: Context, val view: ViewUser) : Presenter, Interactor.OnListener {

        val interactorUser: Interactor
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



        override fun saveUser(user: User) {
            view.let {
                interactorUser.save(user, this)
            }
        }
    }

    class InteractorImpl : Interactor  {
        override fun save(user: User, listener: Interactor.OnListener) {
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

            if (!error) {
                user.id = UUID.randomUUID().toString()
                db.getUserDato().insertUser(user)
                listener.onSuccess()
            }
        }


    }



}

