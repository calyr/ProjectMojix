package app.calyr.com.projectmojix

import android.content.Context
import android.text.TextUtils


data class User( val name: String? = null, val address: String? = null, val birthDate: String? = null, val phoneNumber: String? = null, val email: String? = null )

class UserContract {
    interface Presenter {
        fun saveUser(user:User)

    }

    interface ViewUser {
        fun SaveUser(user:User)
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
                listener.onSuccess()
            }
        }


    }



}