package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
   private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //calculate the tip
        binding.calculateButton.setOnClickListener {calculateTip()}
        //Hide keyboard when user presses enter key
        binding.costOfServiceEditText.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    //Hide Keyboard when Enter key is pressed
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {
        binding.costOfService.setEndIconOnClickListener {
            // Respond to end icon presses
            binding.costOfServiceEditText.clearComposingText()
        }

        //get cost of service
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        //// If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        //get the tip percentage
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //calculate the tip and round it up
        var tip = tipPercentage * cost

        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = ceil(tip)
        }

        // Display the formatted tip value on screen
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        //format the tip
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //display the tip
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}