package com.hassan.library.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hassan.library.DatabaseHandler
import com.hassan.library.R
import com.hassan.library.models.Book
import com.hassan.library.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var book: Book

    lateinit var user: User
    val databaseHandler: DatabaseHandler =
        DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.DetailsTheme)
        getSupportActionBar()?.setElevation(0f)
        setContentView(R.layout.activity_details)
        val actionbar = supportActionBar
        title = "Book Details"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        //receive bookId by Bundle
        val b = intent.extras!!
        val bookId = b.get("Book") as Int

        //get book info
        book=databaseHandler.getBook(bookId)
        Picasso.get().load(book.book_img).into(book_img)
        var bookCount=book.count
        book_name.text = book.book_name
        author.text = book.author
        count.text = book.count.toString() +" "+ resources.getString(R.string.in_stuck)

        //receive userId by Bundle
        val userId = b.get("User") as Int
        user = databaseHandler.getUser(userId)!!

        if (databaseHandler.isBorrowed(user.id!!, book.id)) {
            btn_borrow.visibility = View.GONE
            btn_return.visibility = View.VISIBLE
        }
        btn_borrow.setOnClickListener {
            if (user.borrowedCounts >= 2)
                Toast.makeText(this,
                    R.string.you_can_borrow_only_tow, Toast.LENGTH_LONG).show()
            else {
                databaseHandler.borrowBook(book.id, user.id!!, book.count, user.borrowedCounts)
                btn_borrow.visibility = View.GONE
                btn_return.visibility = View.VISIBLE
                count.text = (book.count - 1).toString() +" "+ resources.getString(
                    R.string.in_stuck
                )
                bookCount=book.count-1
            }

        }

        btn_return.setOnClickListener {
            databaseHandler.returnBook(book,user)
            btn_borrow.visibility = View.VISIBLE
            btn_return.visibility = View.GONE
            count.text = (bookCount +1).toString() +" "+  resources.getString(R.string.in_stuck)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
