package com.app.storemanager;

import com.app.storemanager.item.ItemController;
import com.app.storemanager.login.LoginController;
import com.app.storemanager.warehouse.WarehouseService;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class StoremanagerApplication {

    public static void main(String[] args) {
        startH2DBServer();
        ConfigurableApplicationContext context = SpringApplication.run(StoremanagerApplication.class, args);
        context.getBean(LoginController.class).createTestUser();
        context.getBean(WarehouseService.class).insertDummyWarehouse();
        context.getBean(ItemController.class).createDummyItems();

    }

    public static void startH2DBServer() {
        try {
            Class.forName("org.h2.Driver");
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
