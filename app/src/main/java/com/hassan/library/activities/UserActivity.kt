package com.hassan.library.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hassan.library.DatabaseHandler
import com.hassan.library.R
import com.hassan.library.adapter.BooksAdapter
import com.hassan.library.models.Book
import com.hassan.library.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user.*


class UserActivity : AppCompatActivity() {
    val databaseHandler: DatabaseHandler =
        DatabaseHandler(this)
    lateinit var booksList: ArrayList<Book>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BooksAdapter
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.DetailsTheme)
        getSupportActionBar()?.setElevation(0f)
        setContentView(R.layout.activity_user)

        val actionbar = supportActionBar
        title = resources.getString(R.string.user)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        booksList = ArrayList()
        val b = intent.extras
        user = b!!.get("User") as User

        user_name.text = user.username
        Picasso.get().load(user.image).into(user_img)
        initialization()

    }

    fun initialization() {
        booksList = databaseHandler.viewUserBooks(user.id!!)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@UserActivity, 2)
            // set the custom adapter to the RecyclerView
            adapter = BooksAdapter(booksList, user)

        }
        count.text = "You have " + booksList.size.toString() + " Borrowed Books"
        if (booksList.isNotEmpty()) {
            empty_data.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            count.visibility = View.GONE
            empty_data.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        initialization()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut -> {
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                Toast.makeText(this, R.string.logedout, Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
