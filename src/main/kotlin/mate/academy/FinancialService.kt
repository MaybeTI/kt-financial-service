package mate.academy


const val MAX_LENGTH_ACCOUNT_NUMBER = 10
const val MAX_LENGTH_CURRENCY_CODE = 3
const val EUR_RATE = 0.93
const val GBP_RATE = 0.82
const val DEFAULT_RATE = 1.0


@JvmInline
value class AccountNumber(val accountNumber: String) {
    init {
        require(accountNumber.filter {it in '0'..'9' }
            .length == MAX_LENGTH_ACCOUNT_NUMBER) { throw IllegalArgumentException("Error") }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0) { throw IllegalArgumentException("Error") }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(code.length == MAX_LENGTH_CURRENCY_CODE && code.uppercase() == code)
        { throw IllegalArgumentException("Error") }
    }
}

@JvmInline
value class TransactionId(val transactionId: String) {
    init {
        require(transactionId.isNotEmpty()) { throw IllegalArgumentException("Error") }
    }
}

class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ) : String {
        return "Transferred ${amount.amount} ${currencyCode.code} " +
                "from ${source.accountNumber} to ${destination.accountNumber}. " +
                "Transaction ID: ${transactionId.transactionId}"

    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        return CurrencyAmount(amount.amount * getExchangeRate(fromCurrency, toCurrency))
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> EUR_RATE
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> GBP_RATE
            else -> DEFAULT_RATE
        }
    }
}
