Feature: PaymentSoap
Scenario: Successful Payment
	Given the customer "Cstmr-02" "B" with CPR "CSTMR-00a0" has a bank account
	And the balance of that account is 1000
	And the customer is registered with DTUPay
	And the merchant "Mrchnt-02" "D" with CPR number "MERCH-00a0" has a bank account
	And the balance of that account is 2000
	And the merchant is registered with DTUPay
	When the merchant starts a payment for 10 kr by the customer
	Then we see the payment is successful
	And the balance of the merchant at the bank is 2010 kr
	And the balance of the customer at the bank is 990 kr
	
