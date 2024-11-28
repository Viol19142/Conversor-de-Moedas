object WalletManager {
    var balanceInBRL: Double = 0.0
    val currencies: MutableMap<String, Double> = mutableMapOf(
        "USD" to 0.0,
        "EUR" to 0.0,
        "BTC" to 0.0,
        "ETH" to 0.0
    )

    fun addBalance(amount: Double) {
        balanceInBRL += amount
    }

    fun updateCurrency(currency: String, amount: Double) {
        currencies[currency] = amount
    }

    fun getCurrencyBalance(currency: String): Double {
        return currencies[currency] ?: 0.0
    }
}
