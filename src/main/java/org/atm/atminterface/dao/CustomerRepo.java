package org.atm.atminterface.dao;

import org.atm.atminterface.model.Customer;
import org.atm.atminterface.model.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.atm.atminterface.dao.DatabaseUtil.dSource;

public class CustomerRepo {

    public List<String> getBankAccountNumber() throws SQLException {
        List<String> accounts = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            con = dSource.getConnection();
            String query = "select account_no from customers";
            st = con.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                accounts.add(rs.getString("account_no"));
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(st != null){
                st.close();
            }
            if(con != null){
                con.close();
            }
            if (rs != null){
                rs.close();
            }
        }
    }
    public boolean save(Customer customer) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query = "insert into customers (fName, lName,phone,dob,account_no,amount,pin) values (?,?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, customer.getFName());
            ps.setString(2, customer.getLName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getDob());
            ps.setString(5,customer.getAccount_no());
            ps.setDouble(6,customer.getAmount());
            ps.setInt(7,Integer.parseInt(customer.getPin()));
            int res = ps.executeUpdate();
            return res > 0;
        }catch (SQLException e){
            return false;
        }finally {
            if(con!=null){
                con.close();
            }
            if(ps!=null){
                con.close();
            }
        }
    }
    public Customer getCustomerByAccountNumber(String accountNumber) throws SQLException {
        Customer customer = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
          con = dSource.getConnection();
          String query = "select * from customers where account_no = ?";
          ps = con.prepareStatement(query);
          ps.setString(1, accountNumber);
          rs = ps.executeQuery();
          if(rs.next()){
              customer = new Customer();
              customer.setId(rs.getInt("id"));
              customer.setFName(rs.getString("fName"));
              customer.setLName(rs.getString("lName"));
              customer.setDob(rs.getString("dob"));
              customer.setPhone(rs.getString("phone"));
              customer.setAccount_no(rs.getString("account_no"));
              customer.setAmount(rs.getDouble("amount"));
              customer.setPin(String.valueOf(rs.getInt("pin")));
          }
          if(customer == null) {
              customer = new Customer();
          }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(con != null){
                con.close();
            }
            if (rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
        }

    }
    public void updateAmt(String account_no,double amt) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con= dSource.getConnection();
            String query = "update customers set amount = ? where account_no = ?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, amt);
            ps.setString(2, account_no);
             ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(con != null){
                con.close();
            }
            if(ps != null){
                ps.close();
            }
        }

    }
    public Collection<History> getCustomerHistory(int customer_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Collection<History> histories = new ArrayList<>();
        try{
            con= dSource.getConnection();
            String query = "select * from history where customer_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1,customer_id);
            rs = ps.executeQuery();
            int count =1;
            while (rs.next()){
                History history = new History();
                history.setSl(count);
                history.setId(rs.getInt("customer_id"));
                history.setAmount(rs.getDouble("amount"));
                history.setType(rs.getString("transaction_type"));
                history.setTimeStamp(rs.getTimestamp("timestamp").toString());
                histories.add(history);
                count++;
            }
            return histories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(con != null){
                con.close();
            }
            if (rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
        }
    }

    public boolean insertHistory(String type,double amount,int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dSource.getConnection();
            String query = "insert into history (customer_id,amount,transaction_type) values (?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.setDouble(2, amount);
            ps.setString(3, type);
            int res = ps.executeUpdate();
            return res > 0;
        }catch (SQLException e){
            System.out.println(e.getMessage());
           return false;
        }finally {
            if(con != null){
                con.close();
            }
            if(ps != null){
                ps.close();
            }
        }

    }
    public int getIdOfCustomer(String account_no){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int res = -1;
        try{
            con = dSource.getConnection();
            String query = "select id from customers where account_no = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, account_no);
            rs = ps.executeQuery();
            if(rs.next()){
                res =  rs.getInt("id");
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

