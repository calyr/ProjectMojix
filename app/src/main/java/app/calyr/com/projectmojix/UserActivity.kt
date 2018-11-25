package app.calyr.com.projectmojix

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_user.*

class UserActivity : AppCompatActivity(), UserContract.ViewUser {
    override fun showErrorAddress(message: String) {
        fuserAddress.error = message
    }

    override fun showErrorPhoneNumber(message: String) {
        fuserPhoneNumber.error = message
    }

    override fun showErrorBirthDate(message: String) {
        fuserBirthDate.error = message
    }

    override fun showErrorEmail(message: String) {
        fuserEmail.error = message
    }


    override fun saveUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToList() {
        startActivity( Intent(this, MainActivity::class.java) )
    }

    override fun showErrorName( message: String) {
        fuserAddress.error = message
    }

    override fun loading() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val presenterUser: UserContract.Presenter = UserContract.PresenterImpl(applicationContext, this)

        btnUserSave.setOnClickListener {
            val user =  User(
                    name = fuserName.text.toString(),
                    address = fuserAddress.text.toString(),
                    birthDate = fuserBirthDate.text.toString(),
                    phoneNumber = fuserPhoneNumber.text.toString(),
                    email = fuserEmail.text.toString()
            )
            presenterUser.saveUser(user)
        }
    }
}
