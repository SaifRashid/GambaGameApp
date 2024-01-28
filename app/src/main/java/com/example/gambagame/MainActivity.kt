package com.example.gambagame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun play(view: View) {
        val betValue = findViewById<TextView>(R.id.text_bet_value)
        val balanceValue = findViewById<TextView>(R.id.text_balance_value)
        val betValueInt = betValue.text.toString().replace("$", "").toInt()
        var balanceValueInt = balanceValue.text.toString().replace("$", "").toInt()
        val slot1 = findViewById<TextView>(R.id.text_slot1)
        val slot2 = findViewById<TextView>(R.id.text_slot2)
        val slot3 = findViewById<TextView>(R.id.text_slot3)
        val play = findViewById<Button>(R.id.button_play)
        val plus = findViewById<Button>(R.id.button_plus)
        val minus = findViewById<Button>(R.id.button_minus)
        val message = findViewById<TextView>(R.id.text_feedback_message)

        message.setTextColor(Color.parseColor("#F44336"))

        if (betValueInt <= balanceValueInt) {
            slot1.text = "${generateRandomNum()}"
            slot2.text = "${generateRandomNum()}"
            slot3.text = "${generateRandomNum()}"

            if (slot1.text.toString().toInt() == slot2.text.toString().toInt() && slot1.text.toString().toInt() == slot3.text.toString().toInt()) {
                balanceValueInt += betValueInt * 5
                balanceValue.text = "$${balanceValueInt}"
                message.text = "Hooray! You've won!"
                message.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                balanceValueInt -= betValueInt
                balanceValue.text = "$${balanceValueInt}"
                message.text = "You lost! Try again!"
                if (balanceValueInt == 0) {
                    play.isEnabled = false
                    plus.isEnabled = false
                    minus.isEnabled = false
                    message.text = "You lost, game is over!"
                }
            }

        } else {
            message.text = "Insufficient funds for $${betValueInt} bet"
        }

        message.visibility = View.VISIBLE
    }

    fun plus(view: View) {
        val betValue = findViewById<TextView>(R.id.text_bet_value)
        val balanceValue = findViewById<TextView>(R.id.text_balance_value)
        var betValueInt = betValue.text.toString().replace("$", "").toInt()
        val balanceValueInt = balanceValue.text.toString().replace("$", "").toInt()

        if (betValueInt < balanceValueInt) {
            betValueInt++
            betValue.text = "$${betValueInt}"
        }
    }

    fun minus(view: View) {
        val betValue = findViewById<TextView>(R.id.text_bet_value)
        val balanceValue = findViewById<TextView>(R.id.text_balance_value)
        var betValueInt = betValue.text.toString().replace("$", "").toInt()
        var balanceValueInt = balanceValue.text.toString().replace("$", "").toInt()

        if (betValueInt > 1) {
            betValueInt--
            betValue.text = "$${betValueInt}"
        }
    }

    fun resetGame(view: View) {
        val betValue = findViewById<TextView>(R.id.text_bet_value)
        val balanceValue = findViewById<TextView>(R.id.text_balance_value)
        val slot1 = findViewById<TextView>(R.id.text_slot1)
        val slot2 = findViewById<TextView>(R.id.text_slot2)
        val slot3 = findViewById<TextView>(R.id.text_slot3)
        val play = findViewById<Button>(R.id.button_play)
        val plus = findViewById<Button>(R.id.button_plus)
        val minus = findViewById<Button>(R.id.button_minus)
        val message = findViewById<TextView>(R.id.text_feedback_message)

        betValue.text = "$1"
        balanceValue.text = "$10"
        slot1.text = "1"
        slot2.text = "2"
        slot3.text = "3"
        play.isEnabled = true
        plus.isEnabled = true
        minus.isEnabled = true
        message.visibility = View.INVISIBLE
    }

    private fun generateRandomNum(): Int {
        return Random().nextInt(9) + 1
    }

}