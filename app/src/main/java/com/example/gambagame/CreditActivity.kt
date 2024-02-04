package com.example.gambagame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class CreditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)


        updateBalance2(intent.getIntExtra("balanceValueInt", 0))

        // Set actionBar title
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Credit Card"
        }

        // SeekBar Listener
        val amount = findViewById<TextView>(R.id.text_amount)
        // Listen seekBar change events: There are three override methods that must be implemented
        // though you may not necessarily use the last two
        findViewById<SeekBar>(R.id.seek_amount).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, fromUser: Boolean) {
                // As the seekbar moves, the progress value is obtained and displayed in our seekBar label
                amount.text = "$$value"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
    fun checkBoxClick(view: View) {
        val buyNow = findViewById<Button>(R.id.button_buy_now)

        if (view is CheckBox) {
            buyNow.isEnabled = view.isChecked
        }
    }
    fun buyNow(view: View) {
        val amountValueInt = findViewById<TextView>(R.id.text_amount).text.toString().replace("$", "").toInt()
        val balanceValueInt = findViewById<TextView>(R.id.text_balance).text.toString().toInt()

        if (cardValidation()) {
            toastMessage("Payment Success!")

            // Create the Intent with the correct context
            val intent = Intent(this, MainActivity::class.java)

            val updatedBalance = amountValueInt + balanceValueInt

            // Pass the updated balance to MainActivity
            intent.putExtra("updatedBalance", updatedBalance)

            // Start MainActivity with the updated balance
            startActivity(intent)
        }
    }
    private fun cardValidation(): Boolean {
        val cardNumber = findViewById<TextView>(R.id.text_card_number)
        val expiration = findViewById<TextView>(R.id.text_expiration)
        val cvv = findViewById<TextView>(R.id.text_cvv)
        val postalCode = findViewById<TextView>(R.id.text_postal_code)
        val regex = """^(0[1-9]|1[0-2])/(2[4-9])$""".toRegex()

        if (cardNumber.length() < 16 || cardNumber.length() > 19) {
            toastMessage("Invalid Card Number")
            return false
        } else if (!regex.matches(expiration.text)) {
            toastMessage("Invalid Expiration Date")
            return false
        } else if (cvv.length() < 3 || cvv.length() > 4) {
            toastMessage("Invalid CVV")
            return false
        } else if (postalCode.length() != 5) {
            toastMessage("Invalid Postal Code")
            return false
        }
        return true
    }
    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun updateBalance2(value: Int) {
        val balanceValue = findViewById<TextView>(R.id.text_balance)

        balanceValue.text = "$value"
    }
}