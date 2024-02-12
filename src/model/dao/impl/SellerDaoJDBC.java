package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    public SellerDaoJDBC(Connection conn){//criando um injeção de depedencia com o Construtor
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleleById(Seller id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            conn = DB.getConnection();
            pst = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));

                String name = rs.getString("Name");
                String email = rs.getString("Email");
                Date date = rs.getDate("BirthDate");
                Double BaseSalary =  rs.getDouble("BaseSalary");

                Seller seller =  new Seller(rs.getInt("Id"), name, email, date, BaseSalary);
                seller.setDepartment(dep);

                return seller;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(pst);
            //a conexão conn não é fechada pq precisamos dela pra as outras operações
        }
    }
    @Override
    public List<Seller> findAll() {
        return null;
    }
}
