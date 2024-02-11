import Exces.DbException;

import java.sql.*;

import java.text.SimpleDateFormat;

public class programTwo {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            conn = DB.getConnection();

//            //st = conn.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ? WHERE (DepartmentId = ?)");
//
//            st.setDouble(1 , 200.0);
//            //st.setInt(2, 2);
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = (?)");

            st.setInt(1,16);
            int rowsAffected = st.executeUpdate();

            System.out.println("Done! rowsAffect: "+rowsAffected);

        }catch (SQLException e ){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
