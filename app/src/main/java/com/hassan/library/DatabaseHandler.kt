package com.hassan.library


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.hassan.library.models.Book
import com.hassan.library.models.User

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "LibraryManagement"

        //BooksTable
        private val TABLE_BOOKS = "BookTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "book_name"
        private val KEY_IMAGE = "book_image"
        private val KEY_COUNT = "count"
        private val KEY_AUTHOR = "author"

        //UserTable
        private val TABLE_USER = "UserTable"
        private val USER_ID = "id"
        private val USER_NAME = "username"
        private val USER_PASSWORD = "password"
        private val USER_IMAGE = "image"
        private val USER_BORROWED_COUNT = "borrowedCounts"

        //UserBook table
        private val TABLE_USER_BOOK = "UserBookTable"
        private val USER_BOOK_ID = "id"
        private val KEY_USER_ID = "user_id"
        private val KEY_BOOK_ID = "book_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_BOOKS_TABLE = ("CREATE TABLE " + TABLE_BOOKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT," + KEY_COUNT + " INTEGER," + KEY_AUTHOR + " TEXT" + ")")

        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + USER_NAME + " TEXT,"
                + USER_PASSWORD + " TEXT," + USER_IMAGE + " TEXT," + USER_BORROWED_COUNT + " INTEGER" + ")")

        val CREATE_USER_BOOK_TABLE = ("CREATE TABLE " + TABLE_USER_BOOK + "("
                + USER_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_ID + " TEXT,"
                + KEY_BOOK_ID + " TEXT" + ")")



        db?.execSQL(CREATE_BOOKS_TABLE)
        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_USER_BOOK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_BOOK)
        onCreate(db)
    }

    fun borrowBook(bookId: Int, userId: Int, bookCount: Int, borrowedCount: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_USER_ID, userId)
        contentValues.put(KEY_BOOK_ID, bookId)
        val success = db.insert(TABLE_USER_BOOK, null, contentValues)
        updateBook(bookId, bookCount, "-")//the operation is - to decrease number of book's copies
        updateUser(userId, borrowedCount, "+") //the operation is - to increase number of user's borrowed books count
        db.close() // Closing database connection
        return success
    }

    fun addBook(book: Book): Long { // add a book to the books table
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, book.id)
        contentValues.put(KEY_NAME, book.book_name)
        contentValues.put(KEY_IMAGE, book.book_img)
        contentValues.put(KEY_COUNT, book.count)
        contentValues.put(KEY_AUTHOR, book.author)
        val success = db.insert(TABLE_BOOKS, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    fun addUser(user: User): Long { // add user to the users table
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user.username)
        contentValues.put(USER_PASSWORD, user.password)
        contentValues.put(USER_IMAGE, user.image)
        contentValues.put(USER_BORROWED_COUNT, user.borrowedCounts)
        val success = db.insert(TABLE_USER, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    fun viewAllBooks(): ArrayList<Book> { // view all books in the library
        val bookList: ArrayList<Book> = ArrayList<Book>()
        val selectQuery = "SELECT  * FROM $TABLE_BOOKS WHERE $KEY_COUNT>0"
        val db = this.readableDatabase
        var cursor: Cursor? =null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var bookId: Int
        var bookName: String
        var bookImage: String
        var bookCount: Int
        var bookAuthor: String
        if (cursor.moveToFirst()) {
            do {
                bookId = cursor.getInt(cursor.getColumnIndex("id"))
                bookName = cursor.getString(cursor.getColumnIndex("book_name"))
                bookImage = cursor.getString(cursor.getColumnIndex("book_image"))
                bookCount = cursor.getInt(cursor.getColumnIndex("count"))
                bookAuthor = cursor.getString(cursor.getColumnIndex("author"))
                val book = Book(bookId, bookName, bookImage, bookCount, bookAuthor)
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        return bookList
    }

    fun getBook(bookId: Int): Book { // get a book by bookId
        var book: Book? = null
        val selectQuery = "SELECT  * FROM $TABLE_BOOKS WHERE $KEY_ID=$bookId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var bookId: Int
        var bookName: String
        var bookImage: String
        var bookCount: Int
        var bookAuthor: String
        if (cursor!!.moveToFirst()) {
            do {
                bookId = cursor.getInt(cursor.getColumnIndex("id"))
                bookName = cursor!!.getString(cursor.getColumnIndex("book_name"))
                bookImage = cursor.getString(cursor.getColumnIndex("book_image"))
                bookCount = cursor.getInt(cursor.getColumnIndex("count"))
                bookAuthor = cursor.getString(cursor.getColumnIndex("author"))
                book = Book(bookId, bookName, bookImage, bookCount, bookAuthor)
            } while (cursor!!.moveToNext())
        }
        return book!!
    }

    fun login(username: String, password: String): User? {
        val selectQuery =
            "SELECT  * FROM $TABLE_USER WHERE $USER_NAME='$username' AND $USER_PASSWORD='$password'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var user: User? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var userId: Int
        var userName: String
        var userPassword: String
        var userImage: String
        var borrowedCount: Int

        if (cursor!!.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("username"))
                userPassword = cursor.getString(cursor.getColumnIndex("password"))
                userImage = cursor.getString(cursor.getColumnIndex("image"))
                borrowedCount = cursor.getInt(cursor.getColumnIndex("borrowedCounts"))
                user = User(userId, userName, userPassword, userImage, borrowedCount)
            } while (cursor.moveToNext())
            return user
        } else
            return null
    }

    fun checkUsername(username: String): Boolean { // check if the username already exist
        val selectQuery = "SELECT  * FROM $TABLE_USER WHERE $USER_NAME='$username'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        return cursor!!.moveToFirst()
    }

    fun getUser(userId: Int): User? { // get user by userId
        val selectQuery = "SELECT  * FROM $TABLE_USER WHERE $USER_ID=$userId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var user: User? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var userId: Int
        var userName: String
        var userPassword: String
        var userImage: String
        var borrowedCount: Int

        if (cursor!!.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("username"))
                userPassword = cursor.getString(cursor.getColumnIndex("password"))
                userImage = cursor.getString(cursor.getColumnIndex("image"))
                borrowedCount = cursor.getInt(cursor.getColumnIndex("borrowedCounts"))
                user = User(userId, userName, userPassword, userImage, borrowedCount)
            } while (cursor.moveToNext())
            return user
        } else
            return null
    }

    fun isBorrowed(userId: Int, bookId: Int): Boolean { //return true if this book already borrowed by the user
        val selectQuery =
            "SELECT  * FROM $TABLE_USER_BOOK WHERE $KEY_USER_ID=$userId AND $KEY_BOOK_ID=$bookId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var user: User? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }

        return cursor!!.moveToFirst()
    }

    fun viewUserBooks(userid: Int): ArrayList<Book> {//return array of user's borrowed books

        val bookList: ArrayList<Book> = ArrayList()
        val selectQuery =
            "SELECT  * FROM $TABLE_USER_BOOK,$TABLE_BOOKS  WHERE $KEY_USER_ID=$userid AND $TABLE_BOOKS.id = $KEY_BOOK_ID"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var bookId: Int
        var bookName: String
        var bookImage: String
        var bookCount: Int
        var bookAuthor: String
        if (cursor.moveToFirst()) {
            do {
                bookId = cursor.getInt(cursor.getColumnIndex("id"))
                bookName = cursor.getString(cursor.getColumnIndex("book_name"))
                bookImage = cursor.getString(cursor.getColumnIndex("book_image"))
                bookCount = cursor.getInt(cursor.getColumnIndex("count"))
                bookAuthor = cursor.getString(cursor.getColumnIndex("author"))
                val book = Book(bookId, bookName, bookImage, bookCount, bookAuthor)
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        return bookList
    }

    fun updateBook(bookId: Int, bookCount: Int, operation: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        if (operation.equals("-"))
            contentValues.put(KEY_COUNT, bookCount - 1)
        else contentValues.put(KEY_COUNT, bookCount + 1)
        val success = db.update(TABLE_BOOKS, contentValues, "id=" + bookId, null)
        db.close() // Closing database connection
        return success
    }

    fun updateUser(userid: Int, userBorrowedCount: Int, operation: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        if (operation.equals("+"))
            contentValues.put(USER_BORROWED_COUNT, userBorrowedCount + 1)
        else
            contentValues.put(USER_BORROWED_COUNT, userBorrowedCount - 1)
        val success = db.update(TABLE_USER, contentValues, "id=" + userid, null)
        db.close() // Closing database connection
        return success
    }

    fun returnBook(book: Book, user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, book.id)
        val success =
            db.delete(TABLE_USER_BOOK, "book_id=" + book.id + " and user_id=" + user.id, null)
        updateBook(book.id, book.count, "+")
        updateUser(user.id!!, user.borrowedCounts, "-")
        db.close() // Closing database connection
        return success
    }
}