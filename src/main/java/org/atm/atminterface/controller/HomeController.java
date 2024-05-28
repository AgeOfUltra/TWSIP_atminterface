package org.atm.atminterface.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Data;
import org.atm.atminterface.dao.CustomerRepo;
import org.atm.atminterface.model.Customer;
import org.atm.atminterface.model.History;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

@Data
public class HomeController {
    @FXML
    public Button depositBtn;
    @FXML
    public TextField deposit_amt;
    @FXML
    public BorderPane home_bdr;
    @FXML
    public Button withDrawBtn;
    public TextField account_no;
    public TextField toAccount;
    public TextField fromAccount;
    public TextField transferAmt;
    public Text transferSts;
    public Tab transferTab;
    public TableView<History> transaction_history;
    public TableColumn<History,String> transaction_time;
    public TableColumn<History,Double> history_amt;
    public TableColumn<History,Integer> sl_no;
    public TableColumn<History,String> trans_type;
    public Tab history_tab;
    @FXML
    private TextField balance;

    @FXML
    private Tab balance_check;

    @FXML
    private Tab depositeTab;

    @FXML
    private Text deposit_sts;

    @FXML
    private TextField dob;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private Tab profileTab;

    @FXML
    private TextField withdraw_amt;

    @FXML
    private Text withdraw_sts;

    @FXML
    private Tab withdraw_tab;

    private Stage stage;
    protected static Customer customer;
    protected CustomerRepo repo;
    private int cid;

    public void setCustomer(Customer customer) {
       HomeController.customer = customer;
        cid = repo.getIdOfCustomer(customer.getAccount_no());
        setProfileTab();
        setBalanceTab();
        fetchHistory();
    }
    public void initialize(){
        repo = new CustomerRepo();
        initializeHistoryTable();
    }
    private void initializeHistoryTable() {
        sl_no.setCellValueFactory(cellData -> cellData.getValue().slProperty().asObject());
        transaction_time.setCellValueFactory(cellData -> cellData.getValue().timeStampProperty());
        history_amt.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        trans_type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    }
    private void fetchHistory(){
        try {
            Collection<History> histories = repo.getCustomerHistory(cid);
            ObservableList<History> historyList = FXCollections.observableArrayList(histories);
            transaction_history.setItems(historyList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setProfileTab(){
        name.setText(customer.getFName().concat(" ").concat(customer.getLName()));
        phone.setText(customer.getPhone());
        dob.setText(customer.getDob());
        account_no.setText(customer.getAccount_no());
        if(profileTab.isSelected()){
            initializeHistoryTable();
        }
    }
    private void setBalanceTab(){
        balance.setText(String.valueOf(customer.getAmount()));
        if(balance_check.isSelected()){
            withdraw_sts.setVisible(false);
            deposit_sts.setVisible(false);
            transferSts.setVisible(false);
        }
    }
    @FXML
    public void depositAmtConfirmation(ActionEvent event) throws SQLException {

        String amountText = deposit_amt.getText().trim();
        if (!amountText.isEmpty()) {
            double amount = Double.parseDouble(amountText);
            showConfirmationAlert("Deposit Amount Confirmation","Are you sure want to deposit "+amount+" amount ?",pin->{
                if (pin.length() == 3 && pin.matches(customer.getPin())) {
                    try {
                        performTransaction(amount);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        transactionHistory("deposit",amount,cid);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    deposit_sts.setVisible(true);
                    deposit_sts.setText("Invalid PIN. Please enter a 3-digit number.");
                    deposit_amt.setText("");
                }
            },deposit_sts);
        } else {
            deposit_sts.setVisible(true);
            deposit_sts.setText("Operation Cancelled");
            deposit_amt.setText("");
        }
        if(depositeTab.isSelected()){
            withdraw_sts.setVisible(false);
            transferSts.setVisible(false);
        }
    }

    private void performTransaction(double amt) throws SQLException {
        double currentAmt = customer.getAmount();
        double newAnt = currentAmt+amt;
        customer.setAmount(newAnt);
        repo.updateAmt(customer.getAccount_no(), customer.getAmount());
        balance.setText(String.valueOf(newAnt));
        deposit_sts.setVisible(true);
        deposit_sts.setText("Transaction SuccessFull");
        deposit_amt.setText("");
    }
    private void transactionHistory(String type,double amount,int id) throws SQLException {
        System.out.println(type+" "+amount+" "+id);
        boolean res = repo.insertHistory(type,amount,id);
        if(res){
            System.out.println("Successfully Inserted History");
        }else {
            System.out.println("Failed to Insert History");
        }
    }
    @FXML
    public void withDrawTransaction(ActionEvent event) throws SQLException {
        String amt = withdraw_amt.getText().trim();
        if(!amt.isEmpty()){
           double amount = Double.parseDouble(amt);
           if(amount>customer.getAmount()){
               withdraw_sts.setVisible(true);
               withdraw_sts.setText("Insufficient Amount");
               return;
           }
           showConfirmationAlert("WithDraw Amount Confirmation","Are you sure you want to withdraw " + amount + " ?",pin->{
               if (pin.length() == 3 && pin.matches(customer.getPin())) {
                   try {
                       performWithDraw(amount);
                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
                   try{
                       transactionHistory("withdraw",amount,cid);
                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
               } else {
                   withdraw_sts.setVisible(true);
                   withdraw_sts.setText("Invalid PIN. Please enter a 3-digit number.");
                   withdraw_amt.setText("");
               }
           },withdraw_sts);
        }else{
            withdraw_sts.setVisible(true);
            withdraw_sts.setText("Operation Cancelled");
            withdraw_amt.setText("");
        }
        if(withdraw_tab.isSelected()){
            deposit_sts.setVisible(false);
            transferSts.setVisible(false);
        }
    }

    private void performWithDraw(double amount) throws SQLException {
        double currentAmt = customer.getAmount();
        double newAmt = currentAmt-amount;
        customer.setAmount(newAmt);
        repo.updateAmt(customer.getAccount_no(), customer.getAmount());
        balance.setText(String.valueOf(newAmt));
        withdraw_sts.setVisible(true);
        withdraw_sts.setText("Transaction SuccessFull");
        withdraw_amt.setText("");
    }

    public void exitBtn(ActionEvent event) {
        Stage st = (Stage) home_bdr.getScene().getWindow();
        st.close();
    }

    public void transferAmount(ActionEvent event) {
        String to = toAccount.getText();
        String from = fromAccount.getText();
        String tranAmt = transferAmt.getText();
        transferSts.setVisible(true);
        if(to.isEmpty() || from.isEmpty() || tranAmt.isEmpty()){
            transferSts.setText("please fill the fields with valid details");
            return;
        }
        if(to.equals(from)){
            transferSts.setText("Both account number cannot be same");
            return;
        }
        double money;
        try{
            transferSts.setText("");
            money = Double.parseDouble(tranAmt);
        }catch (NumberFormatException e){

            transferSts.setText("Please enter a valid amount.");
            return;
        }
        Customer toCustomer ;
        try{
            toCustomer = repo.getCustomerByAccountNumber(to);
        }catch (SQLException e){
            transferSts.setText("To Account not found");
            toAccount.clear();
            fromAccount.clear();
            transferAmt.clear();
            return;
        }
        if(toCustomer.getAmount()==0.0){
            transferSts.setText("To Account not found");
            toAccount.clear();
            fromAccount.clear();
            transferAmt.clear();
            return;
        }

        if(!customer.getAccount_no().equals(from)){
            transferSts.setText("from Account is invalid");
            toAccount.clear();
            fromAccount.clear();
            transferAmt.clear();
            return;
        }

        if(money > customer.getAmount()){
            transferSts.setText("Insufficient Balance");
            transferAmt.clear();
            return;
        }

        if(money>0){

            showConfirmationAlert("Transfer amount Confirmation","Are you sure want to transfer "+money+" amount ?",pin->{
                if (pin.length() == 3 && pin.matches(customer.getPin())) {
                    toCustomer.setAmount(toCustomer.getAmount()+money);
                    updateAccounts(toCustomer);
                    customer.setAmount(customer.getAmount()-money);
                    balance.setText(String.valueOf(customer.getAmount()));
                    updateAccounts(customer);
                    try{
                        transactionHistory("transfer",money, cid);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        transactionHistory("Received/Deposit",money,toCustomer.getId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    transferSts.setVisible(true);
                    transferSts.setStyle("-fx-text-fill:green");
                    transferSts.setText("Transaction SuccessFull");
                    toAccount.clear();
                    fromAccount.clear();
                    transferAmt.clear();
                } else {
                    transferSts.setVisible(true);
                    transferSts.setText("Invalid PIN. Please enter a 3-digit number.");
                }
            },transferSts);

        }

        if(transferTab.isSelected()){
            deposit_sts.setVisible(false);
            withdraw_sts.setVisible(false);
        }


    }
    public void updateAccounts(Customer tranferCustomer){
        try{
            repo.updateAmt(tranferCustomer.getAccount_no(),tranferCustomer.getAmount());

        }catch (SQLException e){
            transferSts.setText("Some thing went wrong try again later");
            toAccount.clear();
            fromAccount.clear();
            transferAmt.clear();

        }
    }
    private void showConfirmationAlert(String title,String content, Consumer<String> confirm, Text sts_field){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        PasswordField pinField = new PasswordField();
        pinField.setPromptText("Enter 3-digit PIN");

        GridPane gridPane = new GridPane();
        gridPane.add(new Text("PIN:"), 0, 0);
        gridPane.add(pinField, 1, 0);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(gridPane);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            confirm.accept(pinField.getText());
        } else {
            sts_field.setVisible(true);
            sts_field.setText("Operation Cancelled");
        }
    }

    public void refreshAction(ActionEvent event) {
        fetchHistory();
    }
}
