package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("-> (Seller) findById TEST 1 <-");
        Seller cobaia = sellerDao.findById(3);
        System.out.println(cobaia);

        System.out.println("\n-> (Seller) findByDepartment TEST 2 <-");
        Department depi = new Department(null, 2);
        List<Seller> sellers = sellerDao.findByDepartment(depi);
        for(Seller sl : sellers){
            System.out.println(sl);
        }

        System.out.println("\n-> (Seller) findByALL TEST 3 <-");
        List<Seller> sellers2 = sellerDao.findAll();
        for(Seller sl : sellers2){
            System.out.println(sl);
        }

        System.out.println("\n-> (Department) findById TEST 4 <-");
        Department dep = depDao.findById(5);
        System.out.println(dep);

        System.out.println("\n-> (Seller) insert TEST 5 <-");
        Department dp = new Department("Fashion",3);
        Seller sl = new Seller(null,"Thiago","thiagoalca@gmail.com",new Date(),5120.0);
        sl.setDepartment(dp);
        sellerDao.insert(sl);

        System.out.println("\n-> (Seller) update TEST 6 <-");
        sellerDao.update(sl);

        System.out.println("\n-> (Seller) delete TEST 7 <-");
        System.out.print("Enter with the Id to delele: ");
        int id = sc.nextInt();
        sellerDao.deleleById(id);

    }
}
