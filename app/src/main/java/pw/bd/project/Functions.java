package pw.bd.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static pw.bd.project.DB_utils.getConn;

/**
 * A class to provide functions to interact between user and database.
 */
public class Functions {
    private static Connection conn = getConn();

    public static void displayPlayers() throws SQLException {
        System.out.println("Lista zarejestrowanych szachistów:");

        Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT name, surname FROM players");

        System.out.println("---------------------------------");
        // iteracyjnie odczytujemy rezultaty zapytania
        while (rs.next())
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();
    }

    public static void displayTournaments() throws SQLException {
        System.out.println("Lista turniejów:");

        Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT tournament_id, name, start_date FROM tournaments");

        System.out.println("---------------------------------");
        while (rs.next())
            System.out.println("id: "+String.format("%02d", rs.getInt(1))+ " nazwa: " + String.format("%-40s",rs.getString(2))
                    + " data rozpoczęcia: "+rs.getString(3));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();

    }
   

    public void addPlayer(){


    }


}
