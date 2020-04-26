package connection;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class DatabaseConnection {

    final static String URL = "jdbc:hsqldb:file:C:/Applications/CreditSuisse_EventApp/db";
    final static String USERNAME = "sa";
    final static String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public DatabaseConnection() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            createEventTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createEventTable() throws SQLException {
        final String sql =
                "create table if not exists event(\n " +
                        "id varchar(15) not null,\n" +
                        "duration int not null,\n" +
                        "type varchar(20),\n" +
                        "host varchar(15),\n" +
                        "alert boolean\n" +
                        " );";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

