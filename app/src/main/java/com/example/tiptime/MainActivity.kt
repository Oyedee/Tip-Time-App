package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
   lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //calculate the tip
        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    fun calculateTip() {
        //get cost of service
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDouble()

        //get the tip percentage
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when (selectedId) {
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

        //format the tip
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //display the tip
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}