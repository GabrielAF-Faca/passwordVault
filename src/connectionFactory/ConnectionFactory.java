
package connectionFactory;


import java.sql.*;

/**
 *
 * @author Gabriel Azenha Fachim
 */

public class ConnectionFactory {
    
    private final String url = "jdbc:mysql://localhost:3306/vault";
    //private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String password = "root";
    private final String user = "root";
    
    
    private Connection con;
    
    public Connection getConnection() {
        
        try {

            con = DriverManager.getConnection(url,user,password);
            return con;
            
        } catch (SQLException e){
            
            System.out.println("ERRO NA CONEXÃO:\n"+e);
            return null;
        
        }
        
    }
}
