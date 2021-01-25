package pw.bd.project;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import static java.lang.System.exit;
import static pw.bd.project.DB_utils.getScanner;

public class App {

    private static Scanner scanner = getScanner();

    public static void main(String[] args) {
        System.out.println("It's BD1_Z58 team project application");

        try {
            DB_utils.establishConnection();
            displayMenu();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

       /* try {

            Functions.displayPlayers();
            Functions.displayTournaments();
            Functions.addMatch();

        } catch (IOException | SQLException e) {
            System.out.println("An error occurred: " + e.getMessage() + "please contact a programmer to solve this issue.");
            exit(1);
        }*/
        try {
            DB_utils.closeConnection();
        } catch ( SQLException e) {
            System.out.println("An error occurred: " + e.getMessage() + "please contact a programmer to solve this issue.");
            exit(1);
        }

    }

    public static void displayMenu() throws SQLException {
        String input = "";

        while (!input.equals("q")){
            System.out.println("Podaj numer operacji którą chesz wykonać\n" +
                    "1)Wyświetl zawodników\n" +
                    "2)Wyświetl turnieje\n" +
                    "3)Dodaj zawodnika\n" +
                    "4)Dodaj mecz\n" +
                    "q)Koniec");

            input = scanner.nextLine();
            while(input.equals("\n")){scanner.next();}
            switch (input){
                case "1":
                    Functions.displayPlayers();
                    break;
                case "2":
                    Functions.displayTournaments();
                    break;
                case "3":
                    Functions.addPlayer();
                    break;
                case "4":
                    Functions.addMatch();
                    break;
                case "q":
                    break;

            }


        }
        scanner.close();
    }





}

