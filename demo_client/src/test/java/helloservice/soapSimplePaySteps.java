package helloservice;


import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.After;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/* Hint:
 * The step classes do not do the HTTP requests themselves.
 * Instead, the tests use the class HelloService, which encapsulates the
 * HTTP requests. This abstractions help to write easier and more understandable
 * test classes.
 */
public class soapSimplePaySteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    soapSimplePay soapPay = new soapSimplePay();
    boolean successful;
    List<HashMap> transactions;
    String error;
    User costumer;
    User merchant;
    Account cAccount;
    Account mAccount;
    int accountBalance;
    String latestAcc;
    boolean soap = false;

     
    
    @Given("the customer {string} {string} with CPR {string} has a bank account")
    public void theCustomerWithCPRHasABankAccount(String string, String string2, String string3) {
    	soap = true;
        User u1 = new User();
        u1.setCprNumber(string3);
        u1.setFirstName(string);
        u1.setLastName(string2);
        costumer = u1;
        
    }

    @Given("the balance of that account is {int}")
    public void theBalanceOfThatAccountIs(Integer int1) {
    	accountBalance = int1;
    }

    @Given("the customer is registered with DTUPay")
    public void theCustomerIsRegisteredWithDTUPay() {
    	try {
			latestAcc = soapPay.createAccount(costumer, BigDecimal.valueOf(accountBalance));
			cAccount = soapPay.getAccountByCpr(costumer.getCprNumber());
		} catch (BankServiceException_Exception e) {
			System.out.println("ACC ALREADY EXISTS?");
		}
    	
    }

    @Given("the merchant {string} {string} with CPR number {string} has a bank account")
    public void theMerchantWithCPRNumberHasABankAccount(String string, String string2, String string3) {
    	User mu1 = new User();
        mu1.setCprNumber(string3);
        mu1.setFirstName(string);
        mu1.setLastName(string2);
        merchant = mu1;
        
    }

    @Given("the merchant is registered with DTUPay")
    public void theMerchantIsRegisteredWithDTUPay() {
    	try {
			latestAcc = soapPay.createAccount(merchant, BigDecimal.valueOf(accountBalance));
			mAccount = soapPay.getAccountByCpr(merchant.getCprNumber());
		} catch (BankServiceException_Exception e) {			
			System.out.println("EXCEPTION IN REGISTRATION");
		}
    }
    
    @When("the merchant starts a payment for {int} kr by the customer")
    public void theMerchantStartsAPaymentForKrByTheCustomer(Integer int1) {
    	try {			
			soapPay.pay(cAccount, mAccount, int1);
			cAccount = soapPay.getAccountByCpr(costumer.getCprNumber());
			mAccount = soapPay.getAccountByCpr(merchant.getCprNumber());
		} catch (BankServiceException_Exception e) {
			System.out.println("EXCEPTION IN PAYMENT");
			e.printStackTrace();
		}
    }

    @Then("we see the payment is successful")
    public void weSeeThePaymentIsSuccessful() {
    }

    @Then("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(Integer int1) {
    	assertEquals(BigDecimal.valueOf(int1), mAccount.getBalance());
    }
    
    
    @Then("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(Integer int1) {
    	assertEquals(BigDecimal.valueOf(int1), cAccount.getBalance());
    }

   
    @After
    public void clearBankAccounts() {
    	if (soap) {
	    	try {
				soapPay.deleteAccount(cAccount);
				soapPay.deleteAccount(mAccount);
			} catch (BankServiceException_Exception e) {
				System.out.println("Deletion excepction");
				e.printStackTrace();
			}
	    	soap = false;
    	}
    }
   

}