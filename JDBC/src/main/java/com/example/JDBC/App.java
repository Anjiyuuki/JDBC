package com.example.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class App 
{
    // Connection is responsible to make connection with DB
	Connection conn = null; 
	// Statement is use to execute sql queries in mysql server
	Statement stm = null;
	// its good to use for insert lot of data
	PreparedStatement prestatement = null;
	
	
	
	
	
	App(){
		try {
		// using here driver to connect with database
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root","cogent");
		System.out.println("Connected ");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	void fetchAllData() throws SQLException {
		String query = "select * from records";
		stm = conn.createStatement();
		//execute select query and return ResultSet (Data in interface
		ResultSet set = stm.executeQuery(query);
		while(set.next()) {
			System.out.println(set.getInt(1)+set.getString(2)+set.getString(3)+set.getString(4)+set.getString(5)+set.getString(6));
		}
	}
	void insertData(String name, String purchase, String email, String password, String phone) throws SQLException {
		String query= "insert into records(name,purchase,email,password,phone) values(?,?,?,?,?)";
		prestatement = conn.prepareStatement(query);
		prestatement.setString(1, name);
		prestatement.setString(2, purchase);
		prestatement.setString(3, email);
		prestatement.setString(4, password);
		prestatement.setString(5, phone);
		prestatement.executeUpdate(); // making change into record table
	}
	void fetchSingleData(String email) throws SQLException {
		prestatement = conn.prepareStatement("select * from records where email = ?");
		prestatement.setString(1,email);
		ResultSet set = prestatement.executeQuery();
		set.next();
		System.out.println(set.getInt(1)+set.getString(2)+set.getString(3)+set.getString(4)+set.getString(5));
	}
	Record fetchSingleData2(String email) throws SQLException {
		prestatement = conn.prepareStatement("select * from records where email = ?");
		prestatement.setString(1,email);
		ResultSet set = prestatement.executeQuery();
		set.next();
		return new Record(set.getInt(1),set.getString(2),set.getString(3),set.getString(4),
				set.getString(5),set.getString(6));	
	}
	
	void updateRecord(String email) throws SQLException {
		// collected all data from mail id 
		Record record = fetchSingleData2(email);
		
		record.setPhone("12345632432"); //changing mobile number
		
		//query to update
		String query = "update records set phone = ? where email = ?";
		prestatement = conn.prepareStatement(query);
		prestatement.setString(1, record.getPhone());// setting lastest phone for record
		prestatement.setString(2, record.getEmail());
		prestatement.executeUpdate(); //updated based on email id
		
		
		
	}

	public static void main( String[] args )throws Exception
    {
        
    	App app = new App();
    	//app.fetchAllData();
    	//app.insertData("Richard","12000","Richard@gmail.com","asd","289371923");
    	//app.fetchAllData();
    	// app.fetchSingleData("Richard@gmail.com");
    	app.updateRecord("Richard@gmail.com");
    	app.fetchAllData();
    	
    	
    	
    }
}
