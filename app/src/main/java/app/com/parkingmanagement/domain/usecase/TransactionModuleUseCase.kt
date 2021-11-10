package app.com.parkingmanagement.domain.usecase

import app.com.parkingmanagement.domain.usecase.transaction.GetTransactionUseCase
import app.com.parkingmanagement.domain.usecase.transaction.PayAmountUseCase
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [TransactionViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class TransactionModuleUseCase @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val payAmountUseCase: PayAmountUseCase
) {

    fun getAllTransaction(): GetTransactionUseCase {
        return getTransactionUseCase
    }

    fun payAmount(): PayAmountUseCase {
        return payAmountUseCase
    }
}