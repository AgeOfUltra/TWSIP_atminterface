package org.atm.atminterface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String fName;
    private String lName;
    private String phone;
    private String dob;
    private String account_no;
    private double amount;
    private String pin;

}
