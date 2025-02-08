package com.example.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBconnection {
    public static void main(String[] args) {
        try (Connection conn = DBconnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conexiunea cu baza de date a fost realizată cu succes!");
            } else {
                System.out.println("Conexiunea a eșuat!");
            }
        } catch (SQLException e) {
            System.err.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
    }
}
