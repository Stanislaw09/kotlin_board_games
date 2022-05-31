package com.example.project_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun sendUserName(view: View) {
        val userName=findViewById<EditText>(R.id.userNameInput).text.toString()

        var intent = Intent(this@Login, MainActivity::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
    }
}