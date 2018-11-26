package app.calyr.com.projectmojix

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_user.*
import kotlinx.android.synthetic.main.toolbar.*

class UserActivity : AppCompatActivity(), UserContract.ViewUser {

    override fun getFormUser(): User {
        val user =  User(
                name = fuserName.text.toString(),
                address = fuserAddress.text.toString(),
                birthDate = fuserBirthDate.text.toString(),
                phoneNumber = fuserPhoneNumber.text.toString(),
                email = fuserEmail.text.toString()
        )
        return user
    }

    companion object {
        var modeEdit: Boolean = false
        var userId: String = ""
    }


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

    override fun navigateToList() {
        startActivity( Intent(this, MainActivity::class.java) )
    }

    override fun showErrorName( message: String) {
        fuserName.error = message
    }

    override fun loading() {

    }

    override fun loadUser(user: User) {
        fuserName.setText(user.name)
        fuserAddress.setText(user.address)
        fuserBirthDate.setText(user.birthDate)
        fuserPhoneNumber.setText(user.phoneNumber)
        fuserEmail.setText(user.email)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val presenterUser: UserContract.Presenter = UserContract.PresenterImpl(applicationContext, this)
        initFormEdit(presenterUser)
        btnUserSave.setOnClickListener {

            if (modeEdit) {
                presenterUser.editUser(userId)

            } else {
                presenterUser.saveUser()
            }
        }
    }

    fun initFormEdit(presenterUser: UserContract.Presenter) {
        intent.getStringExtra("userId").let {
            if (it != null) {
                modeEdit = true
                userId = it
                formUserTitle.setText(R.string.form_user_title_edit)
                presenterUser.loadUser(userId)
            }
        }
    }
}
