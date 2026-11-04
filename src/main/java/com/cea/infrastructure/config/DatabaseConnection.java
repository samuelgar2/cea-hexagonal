package com.cea.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            System.getenv("CEA_DB_URL");

    private static final String USER =
            System.getenv("CEA_DB_USER");

    private static final String PASSWORD =
            System.getenv("CEA_DB_PASSWORD");

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {

        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException(
                    "Faltan las variables de entorno de PostgreSQL."
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
