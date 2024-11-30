package com.example.conversordemoedas

import AwesomeApiService
import ExchangeRate
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class activity_convert_resources : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_resources)

        val etAmount: EditText = findViewById(R.id.et_amount_to_convert)
        val spFromCurrency: Spinner = findViewById(R.id.sp_currency_from)
        val spToCurrency: Spinner = findViewById(R.id.sp_currency_to)
        val btnConvert: Button = findViewById(R.id.btn_convert)
        val btnBack: Button = findViewById(R.id.btn_back)
        progressBar = findViewById(R.id.progress_bar)

        val currencies = WalletManager.currencies.keys.toList()
        spFromCurrency.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        spToCurrency.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)

        btnConvert.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull()
            val fromCurrency = spFromCurrency.selectedItem.toString()
            val toCurrency = spToCurrency.selectedItem.toString()

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fromCurrency == "BRL" && WalletManager.balanceInBRL < amount) {
                Toast.makeText(this, "Saldo insuficiente em BRL!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (WalletManager.getCurrencyBalance(fromCurrency) < amount) {
                Toast.makeText(this, "Saldo insuficiente!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            convertCurrency(fromCurrency, toCurrency, amount)
        }

        btnBack.setOnClickListener {
            finish() // Volta para a tela anterior
        }
    }

    private fun convertCurrency(from: String, to: String, amount: Double) {
        progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AwesomeApiService::class.java)
        service.getExchangeRate("$from-$to").enqueue(object : Callback<Map<String, ExchangeRate>> {
            override fun onResponse(
                call: Call<Map<String, ExchangeRate>>,
                response: Response<Map<String, ExchangeRate>>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val rate = response.body()?.values?.first()?.bid?.toDouble() ?: 0.0
                    val convertedAmount = amount * rate

                    if (from == "BRL") WalletManager.balanceInBRL -= amount
                    else WalletManager.updateCurrency(from, WalletManager.getCurrencyBalance(from) - amount)

                    if (to == "BRL") WalletManager.balanceInBRL += convertedAmount
                    else WalletManager.updateCurrency(to, WalletManager.getCurrencyBalance(to) + convertedAmount)

                    Toast.makeText(this@activity_convert_resources, "Conversão concluída!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@activity_convert_resources, "Erro na conversão!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, ExchangeRate>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@activity_convert_resources, "Erro de conexão!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
