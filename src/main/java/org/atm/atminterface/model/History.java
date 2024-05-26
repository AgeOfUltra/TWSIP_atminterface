package org.atm.atminterface.model;

import javafx.beans.property.*;

public class History {
    private final IntegerProperty sl;
    private final IntegerProperty id;
    private final StringProperty timeStamp;
    private final DoubleProperty amount;
    private final StringProperty type;

    public History() {
        this.sl = new SimpleIntegerProperty();
        this.id = new SimpleIntegerProperty();
        this.timeStamp = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty();
        this.type = new SimpleStringProperty();
    }

    public int getSl() {
        return sl.get();
    }

    public void setSl(int sl) {
        this.sl.set(sl);
    }

    public IntegerProperty slProperty() {
        return sl;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }

    public StringProperty timeStampProperty() {
        return timeStamp;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }
}
