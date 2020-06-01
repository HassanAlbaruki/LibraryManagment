package com.hassan.library.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hassan.library.Consts
import com.hassan.library.activities.DetailsActivity
import com.hassan.library.R
import com.hassan.library.models.Book
import com.hassan.library.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_item.view.*


class BooksAdapter(private var booksList: ArrayList<Book>,private var user: User) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mcontext: Context

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: ArrayList<Book>) {
        this.booksList.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Book? {
        return booksList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context
        return if (viewType == Consts.VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(mcontext).inflate(R.layout.loading_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (booksList[position] == null) {
            Consts.VIEW_TYPE_LOADING
        } else {
            Consts.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Consts.VIEW_TYPE_ITEM) {

            holder.itemView.setOnClickListener{
                val intent = Intent(mcontext, DetailsActivity::class.java)
                intent.putExtra("Book", booksList.get(position).id )
                intent.putExtra("User", user.id)
                mcontext.startActivity(intent)
            }
           // holder.itemView.ly_background.setBackgroundColor(getColor())
            holder.itemView.book_name.text= booksList[position]!!.book_name
            Picasso.get().load(booksList[position]!!.book_img).into(holder.itemView.book_img)


        }
    }
}