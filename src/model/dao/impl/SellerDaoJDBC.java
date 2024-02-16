package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    public SellerDaoJDBC(Connection conn){//criando um injeção de depedencia com o Construtor
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getEmail());
            ps.setDate(3, new java.sql.Date(obj.getBithDate().getTime()));
            ps.setDouble(4,obj.getBaseSalary());
            ps.setInt(5, obj.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){
                rs = ps.getGeneratedKeys();
                if (rs.next()){
                    obj.setId(rs.getInt(1));
                    System.out.println("Insert Done! Id: "+obj.getId());
                }
            }else{
                throw new DbException("Unexpected ERROR! no rows affected");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Seller obj) {
        ResultSet rs = null;
        PreparedStatement pst  =null;
        try{
            pst = conn.prepareStatement("UPDATE seller\n" +
                    "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                    "WHERE Id = ?");
            pst.setString(1,obj.getName());
            pst.setString(2,obj.getEmail());
            pst.setDate(3,new java.sql.Date(obj.getBithDate().getTime()));
            pst.setDouble(4, obj.getBaseSalary());
            pst.setInt(5,obj.getDepartment().getId());
            pst.setInt(6, obj.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0){
                System.out.println("Update Done! rowsAffected: "+rowsAffected);
            }else{
                System.out.println("Unexpected ERROR! no rows affected");
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(pst);
        }
    }

    @Override
    public void deleleById(Seller id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?");

            pst.setInt(1, id);
            rs = pst.executeQuery();

            if(rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);
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
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = conn.prepareStatement("SELECT seller.*, department.Name as DepName\n" +
                    "FROM seller\n" +
                    "INNER JOIN department ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name\n");

            pst.setInt(1, department.getId());
            rs = pst.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep ==  null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(pst);
            //a conexão conn não é fechada pq precisamos dela pra as outras operações
        }
    }
    @Override
    public List<Seller> findAll() {
        ResultSet rs = null;
        Statement st = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT seller.*, department.Name as DepName\n" +
                    "FROM seller\n" +
                    "INNER JOIN department ON seller.DepartmentId = department.Id\n" +
                    "ORDER BY Name\n");

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(dep.getId(),dep);
                }
                Seller seller = instantiateSeller(rs,dep);
                sellers.add(seller);
            }
            return sellers;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }
    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
        String name = rs.getString("Name");
        String email = rs.getString("Email");
        Date date = rs.getDate("BirthDate");
        Double BaseSalary =  rs.getDouble("BaseSalary");

        Seller seller =  new Seller(rs.getInt("Id"), name, email, date, BaseSalary);
        seller.setDepartment(dep);
        return  seller;
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
