package com.example.conversordemoedas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class activity_deposit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        val etDepositAmount: EditText = findViewById(R.id.et_deposit_amount)
        val btnConfirmDeposit: Button = findViewById(R.id.btn_confirm_deposit)
        val btnBack: Button = findViewById(R.id.btn_back)

        btnConfirmDeposit.setOnClickListener {
            val input = etDepositAmount.text.toString()
            val depositAmount = input.toDoubleOrNull()
            if (depositAmount != null && depositAmount > 0) {
                WalletManager.addBalance(depositAmount)
                Toast.makeText(this, "Depósito realizado!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Digite um valor válido.", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
