package com.hassan.library.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hassan.library.DatabaseHandler
import com.hassan.library.R
import com.hassan.library.models.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    val databaseHandler: DatabaseHandler =
        DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.DetailsTheme)
        getSupportActionBar()?.setElevation(0f)
        setContentView(R.layout.activity_register)
        val actionbar = supportActionBar
        title = resources.getString(R.string.register)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        btn_register.setOnClickListener {
            if (tv_username.text.isNotEmpty()&&tv_password.text.isNotEmpty()&&tv_re_password.text.isNotEmpty()) {
                if (tv_password.text.toString().equals(tv_re_password.text.toString())) {
                    if(!databaseHandler.checkUsername(tv_username.text.toString())) {
                        databaseHandler.addUser(
                            User(
                                null,
                                tv_username.text.toString(),
                                tv_password.text.toString(),
                                "https://microhealth.com/assets/images/illustrations/personal-user-illustration-@2x.png",
                                0
                            )
                        )
                        Toast.makeText(this,
                            R.string.register_done, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else Toast.makeText(this,
                        R.string.user_exist,Toast.LENGTH_LONG).show()
                }
                else Toast.makeText(this,
                    R.string.password_didnt_match,Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this, R.string.fill_all,Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
