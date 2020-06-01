package com.hassan.library.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hassan.library.DatabaseHandler
import com.hassan.library.R
import com.hassan.library.adapter.BooksAdapter
import com.hassan.library.models.Book
import com.hassan.library.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {

    lateinit var bookAdapter: BooksAdapter
    lateinit var user: User
    lateinit var booksList: ArrayList<Book>
    lateinit var booksRecyclerView: RecyclerView
    val databaseHandler: DatabaseHandler =
        DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //get all Images form MainActivity
        val b = intent.extras
        user = b!!.get("User") as User
        booksList = ArrayList()
        val calender = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy")
        currentDate.text = dateFormat.format(calender)
        AddBooks()
        initialization()
        tv_username.text = user.username
        Picasso.get().load(user.image).into(userImage)

        userImage.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("User", user as Serializable)
            startActivity(intent)
        }
    }


    fun AddBooks() { // this function we use to add books to the library
        if (databaseHandler.viewAllBooks().isEmpty()) {
            databaseHandler.addBook(
                Book(
                    1,
                    "First book",
                    "https://i.pinimg.com/564x/be/74/23/be7423ba95040dbf7b6d1cee84478dc2.jpg",
                    1,
                    "Author 1"
                )
            )
            databaseHandler.addBook(
                Book(
                    2,
                    "Second book",
                    "https://cdn11.bigcommerce.com/s-x3k2fq1/images/stencil/2048x2048/products/2348/10238/Movie_v_Book__87349.1562354103.jpg?c=2",
                    2,
                    "Author 2"
                )
            )
            databaseHandler.addBook(
                Book(
                    3,
                    "Third book",
                    "https://images-na.ssl-images-amazon.com/images/I/61H4WV8EaXL._AC_SY741_.jpg",
                    2,
                    "Author 3"
                )
            )
            databaseHandler.addBook(
                Book(
                    4,
                    "Fourth book",
                    "https://thumbs.dreamstime.com/z/book-festival-poster-concept-vector-illustration-52078387.jpg",
                    2,
                    "Author 4"
                )
            )
            databaseHandler.addBook(
                Book(
                    5,
                    "Fifth book",
                    "https://www.charlestonmusichall.com/wp-content/uploads/2019/09/web_YFPoster11-17_2019.jpg",
                    2,
                    "Author 5"
                )
            )
            databaseHandler.addBook(
                Book(
                    6,
                    "Sixth book",
                    "https://marketplace.canva.com/EADaouA6ngQ/1/0/283w/canva-blue-international-children%E2%80%99s-book-day-commercial-poster-lPx83auW_W8.jpg",
                    2,
                    "Author 6"
                )
            )
            databaseHandler.addBook(
                Book(
                    7,
                    "Seventh book",
                    "https://bexarbibliotech.files.wordpress.com/2015/10/the-life-of-a-musing-21.png",
                    2,
                    "Author 7"
                )
            )
            databaseHandler.addBook(
                Book(
                    8,
                    "Eighth book",
                    "https://66.media.tumblr.com/67230b577b3dcbb0700866dc9f0c05ac/b2c5bc7d3f3e1e2c-8c/s1280x1920/8e889c2c97f202679e5e0c5580113ed4d3bff918.jpg",
                    2,
                    "Author 8"
                )
            )

            databaseHandler.addBook(
                Book(
                    9,
                    "Book 9",
                    "https://i.pinimg.com/564x/be/74/23/be7423ba95040dbf7b6d1cee84478dc2.jpg",
                    2,
                    "Author 9"
                )
            )
            databaseHandler.addBook(
                Book(
                    10,
                    "Book 10",
                    "https://cdn11.bigcommerce.com/s-x3k2fq1/images/stencil/2048x2048/products/2348/10238/Movie_v_Book__87349.1562354103.jpg?c=2",
                    2,
                    "Author 10"
                )
            )
            databaseHandler.addBook(
                Book(
                    11,
                    "Book 11",
                    "https://images-na.ssl-images-amazon.com/images/I/61H4WV8EaXL._AC_SY741_.jpg",
                    2,
                    "Author 11"
                )
            )
            databaseHandler.addBook(
                Book(
                    12,
                    "Book 12",
                    "https://thumbs.dreamstime.com/z/book-festival-poster-concept-vector-illustration-52078387.jpg",
                    2,
                    "Author 12"
                )
            )
            databaseHandler.addBook(
                Book(
                    13,
                    "Book 13",
                    "https://www.charlestonmusichall.com/wp-content/uploads/2019/09/web_YFPoster11-17_2019.jpg",
                    2,
                    "Author 13"
                )
            )
            databaseHandler.addBook(
                Book(
                    14,
                    "Book 14",
                    "https://marketplace.canva.com/EADaouA6ngQ/1/0/283w/canva-blue-international-children%E2%80%99s-book-day-commercial-poster-lPx83auW_W8.jpg",
                    2,
                    "Author 14"
                )
            )
            databaseHandler.addBook(
                Book(
                    15,
                    "Book 15",
                    "https://bexarbibliotech.files.wordpress.com/2015/10/the-life-of-a-musing-21.png",
                    2,
                    "Author 15"
                )
            )
            databaseHandler.addBook(
                Book(
                    16,
                    "Book 16",
                    "https://66.media.tumblr.com/67230b577b3dcbb0700866dc9f0c05ac/b2c5bc7d3f3e1e2c-8c/s1280x1920/8e889c2c97f202679e5e0c5580113ed4d3bff918.jpg",
                    2,
                    "Author 16"
                )
            )
        }
    }

    fun initialization() {
        booksList = databaseHandler.viewAllBooks()

        bookAdapter = BooksAdapter(booksList, user)
        booksRecyclerView = findViewById(R.id.recycler_view)
        booksRecyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            // set the custom adapter to the RecyclerView
            adapter = bookAdapter
            bookAdapter.notifyDataSetChanged()

        }
    }

    override fun onResume() {
        super.onResume()
        booksList.clear()
        initialization()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        finish()
    }
}
