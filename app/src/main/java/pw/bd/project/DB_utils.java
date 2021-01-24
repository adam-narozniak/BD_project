package pw.bd.project;

import oracle.jdbc.pool.OracleDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.System.exit;

/*
 * A static class to handle connection with database.
 * Is is used to establish, close connection, fetch for user data and password.
 */
public class DB_utils {

    private static Connection conn; // obiekt Connection do nawiazania polaczenia z baza danych
    private static String host;
    private static String username;
    private static String password;
    private static String port;
    private static String serviceName;


    private static void loadData() throws IOException {
        Properties prop = new Properties();
        FileInputStream in;

            in = new FileInputStream("src/main/resources/connection.properties");
            prop.load(in);
            in.close();

        host = prop.getProperty("jdbc.host");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
        port = prop.getProperty("jdbc.port");
        serviceName = prop.getProperty("jdbc.service.name");
    }

        public static void establishConnection() throws SQLException, IOException {
        loadData();
        String connectionString = String.format(
                "jdbc:oracle:thin:%s/%s@//%s:%s/%s",
                username, password, host, port, serviceName);

        System.out.println (connectionString);
        OracleDataSource ods;

            ods = new OracleDataSource();
            ods.setURL(connectionString);
            conn = ods.getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Polaczenie do bazy danych nawiazane.");
            System.out.println("Baza danych:" + " " + meta.getDatabaseProductVersion());


    }

    public static void closeConnection() throws SQLException {

            conn.close();

        System.out.println("Polaczenie z baza zamkniete poprawnie.");
    }

    public static Connection getConn() {
        return conn;
    }
}
