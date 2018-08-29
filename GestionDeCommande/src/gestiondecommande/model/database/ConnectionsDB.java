/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiondecommande.model.database;

import java.sql.*;

/**
 *
 * @author johnyftr
 */
public class ConnectionsDB {
        private static Connection cnx;
        
        public ConnectionsDB(){
            
        }
    
     public static Connection getConnect() throws SQLException{
         
         if(cnx == null){
             try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/javatest";
            String user = "root";
            String password = "";
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("connection ok");
           
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return cnx;
     }
     
     public void deconnercter(){
         if(cnx != null) cnx = null;
     }
    
}
