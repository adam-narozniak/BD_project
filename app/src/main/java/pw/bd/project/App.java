package pw.bd.project;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.System.exit;

public class App {

    public static void main(String[] args) {
        System.out.println("It's BD1_Z58 team project application");
        try {
            DB_utils.establishConnection();
            Functions.displayPlayers();
            Functions.displayTournaments();

        } catch (IOException | SQLException e) {
            System.out.println("An error occurred: " + e.getMessage() + " please contact a programmer to solve this issue.");
            exit(1);
        }
        try {
            DB_utils.closeConnection();
        } catch ( SQLException e) {
            System.out.println("An error occurred: " + e.getMessage() + " please contact a programmer to solve this issue.");
            exit(1);
        }

    }





}
