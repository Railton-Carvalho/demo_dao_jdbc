package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department obj = new Department("Books",1);
        Seller seller = new Seller(21,"Railton","Railton2@gmail.com",new Date(),3500.0);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        
        System.out.println(seller);
    }
}
