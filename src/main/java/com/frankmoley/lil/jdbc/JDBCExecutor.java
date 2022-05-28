package com.frankmoley.lil.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {
    public static void main(String[] args){
        DatabaseConnectionManager dcm=new DatabaseConnectionManager("localhost", "hplussport","postgres", "password");
        try{
            Connection connection=dcm.getConnection();
            CustomerDAO customerDAO=new CustomerDAO(connection);
            Customer customer=new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("jadams.wh.gov");
            customer.setAddress("1234 Main St");
            customer.setCity("Arlington");
            customer.setState("VA");
            customer.setPhone("(555) 555-9845");
            customer.setZipCode("01234");
            Customer dbcustomer=customerDAO.create(customer);
            System.out.println(dbcustomer);
            dbcustomer.setEmail("john.adams@wh.gov");
            dbcustomer=customerDAO.update(dbcustomer);
            System.out.println(dbcustomer);
            customerDAO.delete(dbcustomer.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
