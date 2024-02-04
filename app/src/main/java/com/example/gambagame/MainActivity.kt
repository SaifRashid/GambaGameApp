package com.example.gambagame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieve the updated balance from the intent
        val updatedBalance = intent.getIntExtra("updatedBalance", 0)

        // Update the UI with the new balance
        if (updatedBalance != 0) {
            updateBalance(updatedBalance)
        }
    }
    fun plusMinusPlay(view: View) {
        var betValueInt = findViewById<TextView>(R.id.text_bet_value).text.toString().replace("$", "").toInt()
        var balanceValueInt = findViewById<TextView>(R.id.text_balance_value).text.toString().replace("$", "").toInt()

        if (view.id == R.id.button_plus) {
            if (betValueInt < balanceValueInt) {
                betValueInt++
                updateBet(betValueInt)
            }
        } else if (view.id == R.id.button_minus) {
                if (betValueInt > 1) {
                    betValueInt--
                    updateBet(betValueInt)
            }
        } else if (view.id == R.id.button_play) {
            if (betValueInt <= balanceValueInt) {
                updateSlots("random")

                if (equalSlots()) {
                    balanceValueInt += betValueInt * 5
                    updateBalance(balanceValueInt)
                    updateMessage("Hooray! You've won!", "visible", "#4CAF50")
                } else {
                    balanceValueInt -= betValueInt
                    updateBalance(balanceValueInt)
                    if (balanceValueInt != 0) {
                        updateMessage("You lost! Try again!", "visible", "#F44336")
                    } else {
                        updateButtons(false)
                        updateMessage("You lost, game is over!", "visible", "#F44336")
                    }
                }
            } else {
                updateMessage("Insufficient funds for $$betValueInt bet", "visible", "#F44336")
            }
        }
    }
    fun resetGame(view: View) {
        updateBet(1)
        updateBalance(10)
        updateSlots("reset")
        updateButtons(true)
        updateMessage("Hello", "invisible", "#FFFFFF")
    }
    fun creditActivity(view: View) {
        val balanceValueInt = findViewById<TextView>(R.id.text_balance_value).text.toString().replace("$", "").toInt()
        val intent = Intent(this, CreditActivity::class.java)

        intent.putExtra("balanceValueInt", balanceValueInt)
        startActivity(intent)
    }
    private fun equalSlots(): Boolean {
        val slot1 = findViewById<TextView>(R.id.text_slot1).text.toString().toInt()
        val slot2 = findViewById<TextView>(R.id.text_slot2).text.toString().toInt()
        val slot3 = findViewById<TextView>(R.id.text_slot3).text.toString().toInt()

        return slot1 == slot2 && slot1 == slot3
    }
    private fun updateSlots(string: String) {
        val slot1 = findViewById<TextView>(R.id.text_slot1)
        val slot2 = findViewById<TextView>(R.id.text_slot2)
        val slot3 = findViewById<TextView>(R.id.text_slot3)

        if (string == "random") {
            slot1.text = "${generateRandomNum()}"
            slot2.text = "${generateRandomNum()}"
            slot3.text = "${generateRandomNum()}"
        } else if (string == "reset") {
            slot1.text = "1"
            slot2.text = "2"
            slot3.text = "3"
        }
    }
    private fun updateMessage(text: String, visibility: String, color: String) {
        val message = findViewById<TextView>(R.id.text_feedback_message)

        message.text = text
        message.setTextColor(Color.parseColor(color))
        message.visibility = when (visibility) {
            "visible" -> View.VISIBLE
            "invisible" -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }
    private fun updateButtons(isEnabled: Boolean) {
        val play = findViewById<Button>(R.id.button_play)
        val plus = findViewById<Button>(R.id.button_plus)
        val minus = findViewById<Button>(R.id.button_minus)

        play.isEnabled = isEnabled
        plus.isEnabled = isEnabled
        minus.isEnabled = isEnabled
    }
    private fun updateBalance(value: Int) {
        val balanceValue = findViewById<TextView>(R.id.text_balance_value)

        balanceValue.text = "$$value"
    }
    private fun updateBet(value: Int) {
        val betValue = findViewById<TextView>(R.id.text_bet_value)

        betValue.text = "$$value"
    }
    private fun generateRandomNum(): Int {
        return Random().nextInt(9) + 1
    }
}