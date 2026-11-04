package com.cea.infrastructure.config;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            System.out.println("Conexión exitosa a PostgreSQL.");
            System.out.println(
                    "Base de datos: " + connection.getCatalog()
            );

        } catch (SQLException | IllegalStateException e) {

            System.out.println(
                    "Error de conexión: " + e.getMessage()
            );
        }
    }
}