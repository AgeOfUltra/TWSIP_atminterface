package org.atm.atminterface.service;

import org.atm.atminterface.dao.CustomerRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountNumberGenerator {

    private Random random;

    private List<String> accountNumbers ;

    public AccountNumberGenerator() throws SQLException {
        random = new Random();
        CustomerRepo repo = new CustomerRepo();
        accountNumbers = new ArrayList<>();
        accountNumbers= repo.getBankAccountNumber();
    }

    public String generateAccountNumber() {
        String result = null;
        String id = String.format("%04d", random.nextInt(100000000));
        String accNumber = "SB"+id;
        if(!accountNumbers.contains(accNumber)) {
            result = accNumber;
        }else{
            return generateAccountNumber();
        }
        return result;
    }
}
