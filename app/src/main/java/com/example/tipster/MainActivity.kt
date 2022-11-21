package com.example.tipster

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val intitialTipPercent = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentageLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDiscription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentageLabel = findViewById(R.id.tvTipPercentageLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDiscription = findViewById(R.id.tvTipDiscription)

        seekBarTip.max = 30

        seekBarTip.progress = intitialTipPercent
        tvTipPercentageLabel.text = "$intitialTipPercent%"
        updateTipDisc(intitialTipPercent)


        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tvTipPercentageLabel.text = "$p1%"
                computeTipAndTotal()
                updateTipDisc(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged: $p0")
                computeTipAndTotal()
            }

        })

    }

    private fun updateTipDisc(progressPercent: Int) {
        tvTipDiscription.text="Good"
        val tipDescription =when(progressPercent){
            in 0..9-> "Poor"
            in 10..14->"Acceptable"
            in 15..19-> "Good"
            in 20..14-> "Great"
            else -> "Amazing"
        }
        tvTipDiscription.text=tipDescription

        val color= ArgbEvaluator().evaluate(
            progressPercent.toFloat()/seekBarTip.max,
            ContextCompat.getColor(this,R.color.color_worst),
            ContextCompat.getColor(this,R.color.color_best)
        )as Int
        tvTipDiscription.setTextColor(color)

    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.toString()== ""){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return

        }
        val baseAmount =java.lang.Double.parseDouble(etBaseAmount.text.toString())
        val tippercent=seekBarTip.progress
        val tipAmount=baseAmount * tippercent/100
        val totalAmount=baseAmount-tipAmount
        tvTipAmount.text=tipAmount.toString()
        tvTotalAmount.text=totalAmount.toString()


    }
}