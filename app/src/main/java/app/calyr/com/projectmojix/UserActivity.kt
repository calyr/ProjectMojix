package app.calyr.com.projectmojix

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.content_user.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

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
        var calendar: Calendar = Calendar.getInstance()
        private var YEAR = calendar.get(Calendar.YEAR)
        private var MONTH = calendar.get(Calendar.MONTH);
        private var DAY = calendar.get(Calendar.DAY_OF_MONTH);

    }

    override fun showBtnDelete() {
        btnUserDelete.visibility = View.VISIBLE
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

    fun setDate(view: View) {
        showDialog(999)
    }

    override fun onCreateDialog(id: Int): Dialog? {
        // TODO Auto-generated method stub
        return if (id == 999) {
            DatePickerDialog(this,
                    myDateListener, YEAR, MONTH, DAY)
        } else null
    }
    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        // TODO Auto-generated method stub
        // arg1 = year
        // arg2 = month
        // arg3 = day

        showDate(arg1, arg2 + 1, arg3)
    }

    fun showDate(year: Int, month: Int, day: Int) {
        fuserBirthDate.setText("$day/$month/$year")
        fuserBirthDate.requestFocus()
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
        btnUserDelete.setOnClickListener {
            presenterUser.deleteUser(userId)
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
