package com.frankmoley.lil.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {
    public static void main(String[] args){
        DatabaseConnectionManager dcm=new DatabaseConnectionManager("localhost", "hplussport","postgres", "password");
        try{
            Connection connection=dcm.getConnection();
            CustomerDAO customerDAO=new CustomerDAO(connection);
            Customer customer=customerDAO.findById(10000);
            System.out.println(customer.getFirstName()+" "+customer.getLastName());
            customer=customerDAO.update(customer);
            System.out.println(customer.getFirstName()+" "+customer.getLastName());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
