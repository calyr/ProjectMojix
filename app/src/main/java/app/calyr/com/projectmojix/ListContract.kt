package app.calyr.com.projectmojix

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_user.view.*
import java.util.*

class ListContract {
    interface ListView{
        fun setManagerRecyclerView()
        fun setAdapter(list:ArrayList<User>)
        fun showRecyclerView(list:ArrayList<User>)
    }
    interface Presenter {
        fun getList()
    }
    interface Interactor {
        fun getList(listener: OnListener): ArrayList<User>

        interface OnListener {
            fun onSuccess()
            fun onError(errorId:Int)
        }
    }

    class PresenterImpl(val context: Context, val listView: ListContract.ListView) : ListContract.Presenter, Interactor.OnListener {
        var interactor: ListContract.Interactor
        init {
            interactor = ListContract.InteractorImpl()
        }

        override fun onSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(errorId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getList() {

            listView.let {
                listView.showRecyclerView(interactor.getList(this))
            }

        }
    }

    class InteractorImpl : ListContract.Interactor {
        override fun getList(listener: Interactor.OnListener): ArrayList<User> {
            return db.getUserDato().findByAll() as ArrayList<User>
        }
    }
}

class UserListAdapter(val items: ArrayList<User>, val context: Context, val clickListener : (User) -> Unit): RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
            return UserListViewHolder(v)
        }

        override fun getItemCount(): Int {
            return items.count()
        }



        override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
            val user = items.get(position)
            holder.itemView.cvName.text = user.name
            holder.itemView.cvAddress.text = user.address
            holder.itemView.cvEmail.text = user.email
            holder.itemView.cvCellphone.text = user.phoneNumber
            holder.itemView.cvBirthDate.text = user.birthDate
            holder.bind(user, clickListener )
        }

        class UserListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(user: User, clickListener: (User) -> Unit){
                itemView.setOnClickListener{
                    clickListener(user)
                }
            }
        }
    }
