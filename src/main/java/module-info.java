module org.atm.atminterface {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires jakarta.persistence;
    requires spring.jdbc;
    requires org.apache.commons.dbcp2;
    requires java.transaction;
    opens org.atm.atminterface to javafx.fxml;
    exports org.atm.atminterface.controller;
    exports org.atm.atminterface to javafx.graphics;
    opens org.atm.atminterface.controller to javafx.fxml;


}