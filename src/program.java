import Exces.DbException;
import Exces.DbIntegrityException;

import java.sql.*;

import java.text.SimpleDateFormat;

public class program {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement st = null;
        try{
            conn = DB.getConnection();

            conn.setAutoCommit(false);// se falso todas as operações só serão feitas de acordo com o programador ou
            // seja o
            // conn.commit()

            st = conn.createStatement();

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 1500 WHERE (DepartmentId = 2)");


//            //Simulando um error no meio das transactions
//            int x = 1;
//            if (x < 2) {
//                throw new SQLException("Fake Error!");
//            }

            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 2500 WHERE (DepartmentId = 1)");

            conn.commit();//autorizando operações no Banco
            System.out.println("Done! rowsAffect: "+rows1+" - "+rows2);


        }catch (SQLException e ){
            try {
                conn.rollback();//desfazendo as operaçÕes feitas
                throw  new DbException("Transaction rolled back! Cause by: "+e.getMessage());
            } catch (SQLException ex) {
                throw new DbException("Error trying to rollback ! Caused by: "+ex.getMessage());
            }
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
