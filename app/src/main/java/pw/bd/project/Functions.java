package pw.bd.project;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static pw.bd.project.DB_utils.getConn;
import static pw.bd.project.DB_utils.getScanner;

/**
 * A class to provide functions to interact between user and database.
 */
public class Functions {
    private static Connection conn = getConn();

    public static void displayPlayers() throws SQLException {
        System.out.println("Lista zarejestrowanych szachistów:");

        Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT player_id, name, surname FROM players");

        System.out.println("---------------------------------");
        // iteracyjnie odczytujemy rezultaty zapytania
        while (rs.next())
            System.out.println(String.format("%02d", rs.getInt(1)) + " " + String.format("%-20s", rs.getString(2)) + " " + String.format("%-20s", rs.getString(3)));
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
            System.out.println("id: " + String.format("%02d", rs.getInt(1)) + " nazwa: " + String.format("%-40s", rs.getString(2))
                    + " data rozpoczęcia: " + rs.getString(3));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();

    }
    public static void displayCountries() throws SQLException {
        System.out.println("Lista krajów:");

        Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT country_id, name FROM countries");

        System.out.println("---------------------------------");
        while (rs.next())
            System.out.println("id: " + String.format("%02d", rs.getInt(1)) + " nazwa: " + String.format("%-30s", rs.getString(2)));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();

    }

    /* public static void displayPlayersByTournament() throws SQLException {
         System.out.println("Lista zawodników w danym turnieju, najpierw zostanie wyświetlona lista turniejów:");
         displayTournaments();


         Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL


         System.out.println("Podaj id turnieju:");
         Scanner in = new Scanner(System.in);

         ResultSet rs = statement.executeQuery("display_tournamet_players("+in.nextLine()+")");

         System.out.println("---------------------------------");
         System.out.println(rs);

         System.out.println("---------------------------------");

         rs.close();
         statement.close();
     }*/
    private static int findMaxId(String tableName, String idColumnName) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select max(" + idColumnName + ") from " + tableName);
        rs.next();
        //System.out.println(rs.getInt(1));
        return rs.getInt(1);
    }

    public static void addPlayer() throws SQLException {
        System.out.println("Dodanie zawodnika:");
        conn.setAutoCommit(false);
        int maxId = findMaxId("players", "player_id");
        PreparedStatement preparedStatement = conn
                .prepareStatement("insert into players values(?,?,?,?,?,?,?)");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj imię: ");
        String name = scanner.nextLine();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner.nextLine();
        String organization_id = "null";
        int categoryId = 10;
        displayCountries();
        System.out.println("Podaj id kraju z wyświetlonych: ");
        int countryId = scanner.nextInt();
        int titleId = 1;

        preparedStatement.setInt(1, maxId+1);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, surname);
        preparedStatement.setNull(4, Types.NULL);
        preparedStatement.setInt(5,categoryId);
        preparedStatement.setInt(6, countryId);
        preparedStatement.setInt(7,titleId);
        preparedStatement.executeUpdate();
        conn.commit();
        preparedStatement.close();
        System.out.println("Pomyślnie dodano zawodnika");
    }

    public static void addMatch() throws SQLException {
        System.out.println("Dodanie meczu:");
        conn.setAutoCommit(false);
        int maxId = findMaxId("matches", "match_id");
        PreparedStatement preparedStatement = conn
                .prepareStatement("insert into matches values(?,?,?,?,?,?,?,?)");
        Scanner scanner = getScanner();

        displayPlayers();
        System.out.println("Podaj id zawodnika o białych bierkach z wyświetlonych: ");
        int whitePlayerId = scanner.nextInt();
        System.out.println("Podaj id zawodnika o czarnych bierkach z wyświetlonych: ");
        int blackPlayerId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj datę meczu w formacie dd/MM/yyyy");
        String date = scanner.nextLine();
        displayAddresses();
        System.out.println("Podaj id miejsca z wyświetlonych");
        int addressId = scanner.nextInt();
        //zakładam że mecz się jeszcze nie odbył i resultat jest nieznany
        displayTournaments();
        System.out.println("Podaj id turnieju z wyświetlonych");
        int tournamentId = scanner.nextInt();
        displayRounds();
        System.out.println("Podaj id typu rundy z wyświetlonych");
        int roundType = scanner.nextInt();

        preparedStatement.setInt(1, maxId+1);
        preparedStatement.setInt(2,whitePlayerId);
        preparedStatement.setInt(3,blackPlayerId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        preparedStatement.setDate(4, Date.valueOf(localDate));//"TO_DATE('"+date+"', 'dd/mm/yyyy')");
       // preparedStatement.setNull(4, Types.NULL);
        preparedStatement.setInt(5,addressId);
        preparedStatement.setInt(6, 4);
        preparedStatement.setInt(7, tournamentId);
        preparedStatement.setInt(8,roundType);
        preparedStatement.executeUpdate();
        conn.commit();
        preparedStatement.close();
        System.out.println("Pomyślnie dodano mecz");

    }

    private static void displayRounds() throws SQLException {
        System.out.println("Lista typów rund:");

        Statement statement = conn.createStatement();

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT round_type_id, name FROM rounds");

        System.out.println("---------------------------------");
        while (rs.next())
            System.out.println("id: " + String.format("%02d", rs.getInt(1)) + " nazwa: " + String.format("%-30s", rs.getString(2)));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();
    }

    private static void displayAddresses() throws SQLException {

        System.out.println("Lista adresów:");

        Statement statement = conn.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = statement.executeQuery("SELECT address_id, place FROM addresses");

        System.out.println("---------------------------------");
        while (rs.next())
            System.out.println("id: " + String.format("%02d", rs.getInt(1)) + " nazwa: " + String.format("%-30s", rs.getString(2)));
        System.out.println("---------------------------------");

        rs.close();
        statement.close();
    }

}
