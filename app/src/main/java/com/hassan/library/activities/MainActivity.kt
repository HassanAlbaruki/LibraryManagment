package com.hassan.library.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hassan.library.DatabaseHandler
import com.hassan.library.R
import com.hassan.library.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tv_password
import kotlinx.android.synthetic.main.activity_main.tv_username
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    val databaseHandler: DatabaseHandler =
        DatabaseHandler(this)
    var user: User? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener{
            user = databaseHandler.login(tv_username.text.toString(),tv_password.text.toString())
            if (user != null) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("User", user as Serializable)
                startActivity(intent)
            }
            else
                Toast.makeText(this,
                    R.string.wrong_username_passwprd,Toast.LENGTH_LONG).show()
        }

        tv_register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}
