package com.example.gambagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CreditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)

        val actionBar = supportActionBar

        if(actionBar != null) {
            actionBar.title = "Credit Card"
        }
    }
}