import Exces.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if(conn == null){
            try{
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    private static Properties loadProperties(){

        try (FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
    //metodos para fechar connections com o DataBase devidamente tratadas
    public static void closeConnection(){
        if(conn!=null){
            try {
                conn.close();
                System.out.println("DB CLOSED");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    public static void closeStatement(Statement st){
        if(st != null){
            try {
                st.close();
                System.out.println("ST CLOSED");

            }catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null){
            try {
                rs.close();System.out.println("RS CLOSED");
            }catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }
}
