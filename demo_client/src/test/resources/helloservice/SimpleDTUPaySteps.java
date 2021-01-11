package helloservice;


import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.User;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

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
public class SimpleDTUPaySteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    boolean successful;
    List<HashMap> transactions;
    String error;
    User costumer;
    int cAccountBalance = 0;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        try {
            successful = dtuPay.pay(amount, cid, mid);
        } catch (Exception e) {
            successful = false;
            error = e.getMessage();
        }
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @When("the manager asks for a list of transactions")
    public void theManagerAsksForAListOfTransactions() {
        transactions = dtuPay.transactions();
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentOfKrFromCustomerToMerchant(int arg0, String arg1, String arg2) {
        try {
            successful = dtuPay.pay(arg0, arg1, arg2);
        } catch (Exception e) {
            successful = false;
            error = e.getMessage();
        }
    }

    @Then("the list contains a transaction where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsATransactionWhereCustomerPaidKrToMerchant(String arg0, int arg1, String arg2) {
        boolean contains = false;
        for (HashMap map : transactions) {
            int am = Integer.parseInt(map.get("amount").toString());
            if ((am == arg1) && map.get("cid").equals(arg0) && map.get("mid").equals(arg2)) {
                contains = true;
            }
        }
        assertTrue(contains);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(successful);
    }

    @And("an error message is returned saying {string}")
    public void anErrorMessageIsReturnedSaying(String arg0) {
        assertEquals(arg0, error);
    }
    
    
    
    @Given("the customer {string} {string} with CPR {string} has a bank account")
    public void theCustomerWithCPRHasABankAccount(String string, String string2, String string3) {
        User u1 = new User();
        u1.setCprNumber(string3);
        u1.setFirstName(string);
        u1.setLastName(string2);
        BigDecimal balance = BigDecimal.valueOf(0.0);
        costumer = u1;
        
    }

    @Given("the balance of that account is {int}")
    public void theBalanceOfThatAccountIs(Integer int1) {
    	cAccountBalance = int1;
    }

    @Given("the customer is registered with DTUPay")
    public void theCustomerIsRegisteredWithDTUPay() {
    	dtuPay.create
    }

    @Given("the merchant {string} {string} with CPR number {string} has a bank account")
    public void theMerchantWithCPRNumberHasABankAccount(String string, String string2, String string3) {
    }

    @Given("the merchant is registered with DTUPay")
    public void theMerchantIsRegisteredWithDTUPay() {
    }

    @Then("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(Integer int1) {
    }

    @Then("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(Integer int1) {
    }

}