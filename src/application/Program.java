package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.util.Date;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("-> (Seller) findById TEST <-");

        Seller cobaia = sellerDao.findById(3);

        System.out.println(cobaia.toString());
    }
}
