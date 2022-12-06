
package model;

import connectionFactory.ConnectionFactory;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel Azenha Fachim
 */

public class UserDAO {
    
    private Connection con;
    
    public ArrayList<User> getUsers() {
        
        ArrayList<User> users = new ArrayList<>();
        
        String query = "select * from users;";
        
        try {
            //conecta com o banco
            con = new ConnectionFactory().getConnection();
            
            //prepara a query
            PreparedStatement pst = con.prepareStatement(query);
            
            //executa a query
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                
                int id = Integer.parseInt(rs.getString(1));
                String site = rs.getString(2);
                String name = rs.getString(3);
                String email = rs.getString(4);
                String password = rs.getString(5);
                
                byte[] bytes = Base64.getDecoder().decode(password);
                
                String decodedPassword = new String(bytes, StandardCharsets.UTF_8);
                
                int identifierSize = "facaVault@".length();
                int stringSize = decodedPassword.length();
                
                int pwdStart = identifierSize;
                int pwdEnd = stringSize-1;
                
                System.out.println(decodedPassword);
                System.out.println(identifierSize);
                System.out.println(stringSize);
                System.out.println(pwdStart);
                System.out.println(pwdEnd);
                
                decodedPassword = decodedPassword.substring(pwdStart, pwdEnd);
                
                System.out.println(decodedPassword);
                
                users.add(new User(id, site, name, email, decodedPassword));
                
            }
            
            con.close();
            
        } catch (SQLException e) {
            System.out.println("ERRO: " + e);
            JOptionPane.showMessageDialog(null, 
                    "Error connecting to database", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
            
            users = null;
        }
        
        return users;
    }
    
    public void insertUser(User u) throws NoSuchAlgorithmException, UnsupportedEncodingException {  
        
        String query = "insert into users values(null, ?, ?, ?, ?);";
        
        try {
            //conecta com o banco
            con = new ConnectionFactory().getConnection();
            //prepara a query
            PreparedStatement pst = con.prepareStatement(query);
            //executa a query
            pst.setString(1, u.getSite());
            pst.setString(2, u.getName());
            pst.setString(3, u.getEmail());
            
            String password = "facaVault@" + u.getPassword() + "?";
            
            byte bytes[] = password.getBytes("UTF-8");

            String passwordB64 = Base64.getEncoder().encodeToString(bytes);
            
            pst.setString(4, passwordB64);
            
            pst.execute();
            
            con.close();
            
            JOptionPane.showMessageDialog(null, "Account added!");
            
        } catch (SQLException e) {
            System.out.println(e);
            
            JOptionPane.showMessageDialog(null, 
                    "Error connecting to database", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public void updateUser(User u, String user_site, String user_name, String user_email, String user_password) throws UnsupportedEncodingException {
        
        String query = "update users set user_site = ?, user_name = ?, user_email = ?, user_password = ? where user_id = ?;";
        
        try {
            //conecta com o banco
            con = new ConnectionFactory().getConnection();
            
            //prepara a query
            PreparedStatement pst = con.prepareStatement(query);
            
            //executa a query
            pst.setString(1, user_site);
            pst.setString(2, user_name);
            pst.setString(3, user_email);
            
            String password = "facaVault@" + user_password+ "?";
            
            byte bytes[] = password.getBytes("UTF-8");

            String passwordB64 = Base64.getEncoder().encodeToString(bytes);
            
            pst.setString(4, passwordB64);
            
            pst.setInt(5, u.getId());

            pst.executeUpdate();

            
            con.close();
            
            JOptionPane.showMessageDialog(null, "Account updated!");
            
        } catch (SQLException e) {
            System.out.println(e);
            
            JOptionPane.showMessageDialog(null, 
                    "Error connecting to database", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public void deleteUser(int id) {
        String query = "delete from users where user_id = ?;";
        
        try {
            //conecta com o banco
            con = new ConnectionFactory().getConnection();
            
            //prepara a query
            PreparedStatement pst = con.prepareStatement(query);
            
            //executa a query
            pst.setInt(1, id);

            pst.execute();
            
            
            con.close();
            
            JOptionPane.showMessageDialog(null, "Account deleted!");
            
        } catch (SQLException e) {
            System.out.println(e);
            
            JOptionPane.showMessageDialog(null, 
                    "Error connecting to database", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
