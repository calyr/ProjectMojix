package app.calyr.com.projectmojix

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ListContract.ListView {

    override fun showRecyclerView(list:ArrayList<User>) {
        setManagerRecyclerView()
        setAdapter(list)
    }

    override fun setManagerRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvUser.layoutManager = linearLayoutManager
    }

    override fun setAdapter(list:ArrayList<User>) {
        val userListAdapter = UserListAdapter(list, this)
        rvUser.adapter = userListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listPresenter: ListContract.Presenter = ListContract.PresenterImpl(this, this)
        listPresenter.getList()
        btnCreateUser.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }
}
