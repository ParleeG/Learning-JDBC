package com.frankmoley.lil.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.frankmoley.lil.jdbc.util.DataAccessObject;

public class CustomerDAO extends DataAccessObject<Customer> {
    private static final String INSERT="INSERT INTO customer (first_name, "+
    "last_name, email, phone, address, city, state, zipcode) VALUES"+
    "(?,?,?,?,?,?,?,?)"; 

    private static final String Get_ONE="SELECT customer_id, first_name,"+
    "last_name, email, phone, address, city, state, zipcode FROM "+
    "customer WHERE customer_id=?";

    private static final String UPDATE= "UPDATE customer SET first_name = ?, last_name=?, " +
    "email = ?, phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";

    private static final String DELETE="delete from customer where customer_id=?";

    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer=new Customer();
        try {
            PreparedStatement statement=this.connection.prepareStatement(Get_ONE);
            statement.setLong(1, id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                customer.setId(rs.getInt(1));
                customer.setFirstName(rs.getString(2));
                customer.setLastName(rs.getString(3));
                customer.setEmail(rs.getString(4));
                customer.setPhone(rs.getString(5));
                customer.setAddress(rs.getString(6));
                customer.setCity(rs.getString(7));
                customer.setState(rs.getString(8));
                customer.setZipCode(rs.getString(9));
            }
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer=new Customer();
        try {
            PreparedStatement statement=this.connection.prepareStatement(UPDATE);
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.setLong(9, dto.getId());
            statement.execute();
            customer = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement statement=this.connection.prepareStatement(INSERT)){
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.execute();
            int id=this.getLastVal(CUSTOMER_SEQUENCE);
            return this.findById(id);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id){
        try {
            PreparedStatement statement=this.connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
