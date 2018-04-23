package com.s14222.tau.repository;

import com.s14222.tau.dbdemo.service.UndeadManagerImpl;
import com.s14222.tau.domain.*;
import static java.sql.DriverManager.getConnection;
import java.sql.*;


public class UndeadRepositoryFactory{

        public static UndeadRepository getInstance() {

            try {

                String url = "jdbc:hsqldb:hsql://localhost/workdb";
                return new UndeadManagerImpl(DriverManager.getConnection(url));

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }

}

/*
public UndeadManagerImpl() throws SQLException {
        public static UndeadRepository getInstance(){

        String url = "jdbc:mysql://localhost/aga";
        try {
            Class.forName("com.mysql.jdbc.Driver");  //STEP 2: Register JDBC driver

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.connection = DriverManager.getConnection(url,"root", "");*/