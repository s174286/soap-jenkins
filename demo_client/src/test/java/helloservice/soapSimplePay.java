package helloservice;

import dtu.ws.fastmoney.BankServiceService;

import javax.ws.rs.client.WebTarget;
import java.math.BigDecimal;
import java.util.List;

import dtu.ws.fastmoney.*;

public class soapSimplePay {


    WebTarget baseUrl;

    BankService bank;

    public soapSimplePay() {
        bank = new  BankServiceService().getBankServicePort();
    }

    public String createAccount(User user, BigDecimal amount) throws BankServiceException_Exception {
        String r = bank.createAccountWithBalance(user, amount);
        return r;
    }

    public void pay(Account deb, Account cred, int amount) throws BankServiceException_Exception {
        bank.transferMoneyFromTo(deb.getId(), cred.getId(), BigDecimal.valueOf(amount), "Transfer");
    }

    public List<AccountInfo> getAccounts() {
        return bank.getAccounts();
    }

    public void deleteAccount(Account acc) throws BankServiceException_Exception {
        bank.retireAccount(acc.getId());
    }

    public Account getAccount(String accountId) throws BankServiceException_Exception {
        return bank.getAccount(accountId);
    }
    
    public Account getAccountByCpr(String cpr) throws BankServiceException_Exception {
        return bank.getAccountByCprNumber(cpr);
    }

}
