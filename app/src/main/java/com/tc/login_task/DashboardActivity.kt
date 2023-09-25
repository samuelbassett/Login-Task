package com.tc.login_task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.tc.login_task.ui.login.LoginScreen

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var welcomeMessageView = findViewById<TextView>(R.id.WelcomeMessage)
        var username: String? = ""

        val receivedIntent = intent
        if (receivedIntent != null && receivedIntent.hasExtra("username")) {
            username = receivedIntent.getStringExtra("username")
        } else {
            Log.e("DashboardActivity: ", "Username was not passed")
        }

        val extractedName = extractAndCapitalizeName(username ?: "NAME")
        welcomeMessageView.text = "Welcome back,\n$extractedName"

        val cardView = findViewById<CardView>(R.id.logOutCard)

        cardView.setOnClickListener {
            // Create an Intent to navigate to SpecificActivity
            val intent = Intent(this, LoginScreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            startActivity(intent)
        }

    }

    private fun extractAndCapitalizeName(email: String): String {
        val atIndex = email.indexOf('@')

        // Check if the email contains an "@" symbol
        return if (atIndex != -1) {
            // Extract the substring before "@" as the name
            val name = email.substring(0, atIndex)

            // Capitalize the first letter of the name
            name.substring(0, 1).toUpperCase() + name.substring(1)
        } else {
            // If no "@" symbol is found, return the original email
            email
        }
    }

}