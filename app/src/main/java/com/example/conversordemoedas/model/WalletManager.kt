object WalletManager {
    var balanceInBRL: Double = 0.0
    val currencies: MutableMap<String, Double> = mutableMapOf(
        "USD" to 0.0,
        "EUR" to 0.0,
        "BTC" to 0.0,
        "ETH" to 0.0,
        "BRL" to balanceInBRL
    )

    fun addBalance(amount: Double) {
        balanceInBRL += amount
        currencies["BRL"] = balanceInBRL
    }

    fun updateCurrency(currency: String, amount: Double) {
        currencies[currency] = amount
        if (currency == "BRL") balanceInBRL = amount
    }

    fun getCurrencyBalance(currency: String): Double {
        return currencies[currency] ?: 0.0
    }
}
