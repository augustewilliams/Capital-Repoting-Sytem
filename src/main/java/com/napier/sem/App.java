package com.napier.sem; // Change this match your exact package statement if different

import java.sql.*;

public class App {
    // 1. Keep your database connection variable here at the class level
    private Connection con = null;

    public static void main(String[] args) {
        // Create an instance of the App class
        App a = new App();

        // Connect to database
        a.connect();

        // RUN THE REPORT METHOD HERE
        a.getCapitalCitiesReport();

        // Disconnect from database
        a.disconnect();
    }

    // 2. Your existing connect method should live here
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait for DB to boot
                Thread.sleep(30000);
                // Connect to database using 'world'
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Cleanup.");
            }
        }
    }

    // 3. Your disconnect method
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    // 4. App Class Syntax
    public void getCapitalCitiesReport() {
        try {
            Statement stmt = con.createStatement();

            String strSelect = "SELECT city.Name AS Capitalcity, country.Name AS Country, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.ID = country.Capital " +
                    "WHERE country.Continent = 'asia' " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT 10";


            ResultSet rset = stmt.executeQuery(strSelect);

            System.out.println("Top N Capital Cities in Asia by Population:(LARGEST TO LOWEST) ---");
            System.out.printf("%-25s %-30s %-15s\n", "Capital City", "Country", "Population");


            while (rset.next()) {
                String capitalCity = rset.getString("CapitalCity");
                String country = rset.getString("Country");
                int population = rset.getInt("Population");

                System.out.printf("%-25s %-30s %,15d\n", capitalCity, country, population);
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }
} // Closes the App class