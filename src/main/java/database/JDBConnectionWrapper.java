package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD = "AlleDB2003";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schema){
        try{
            Class.forName(JDBC_DRIVER); //cartela telefon
            connection = DriverManager.getConnection(DB_URL + schema, USER, PASSWORD); //apelam nr si stabilim o conexiune
            createTables();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException{
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author VARCHAR(500) NOT NULL," +
                " title VARCHAR(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " price DECIMAL(10,2) DEFAULT NULL," +
                " stock INT DEFAULT NULL," +
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        String sql2 = "CREATE TABLE IF NOT EXISTS orders(" +
                "  id bigint NOT NULL AUTO_INCREMENT," +
                "  user_id INT NOT NULL," +
                "  title varchar(500) NOT NULL," +
                "  author varchar(500) NOT NULL," +
                "  price DECIMAL(10,2) DEFAULT NULL," +
                "  stock INT DEFAULT NULL," +
                "  orderDate datetime DEFAULT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)," +
                "  INDEX user_id_ido (user_id ASC),"+
                "  CONSTRAINT user_fkido" +
                "    FOREIGN KEY (user_id)" +
                "    REFERENCES user (id)" +
                "    ON DELETE CASCADE" +
                "    ON UPDATE CASCADE" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);
        statement.execute(sql2);


    }

    public boolean testConnection() throws SQLException{
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection(){
        return connection;
    }
}
