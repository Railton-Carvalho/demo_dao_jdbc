package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("-> (Seller) findById TEST <-");
        Seller cobaia = sellerDao.findById(3);
        System.out.println(cobaia);

        System.out.println("\n-> (Seller) findByDepartment TEST <-");
        Department depi = new Department(null, 2);
        List<Seller> sellers = sellerDao.findByDepartment(depi);
        for(Seller sl : sellers){
            System.out.println(sl);
        }

        System.out.println("\n-> (Department) findById TEST <-");
        Department dep = depDao.findById(5);
        System.out.println(dep);
    }
}
