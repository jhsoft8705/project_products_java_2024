 
package Conexion;
 
import java.sql.Connection;
import java.sql.DriverManager;

  
 
public class ConexionMysql {
    Connection cn;
    
    public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/Empresa","root","");
            System.out.println("Conexion Exitosa");
            
        } catch (Exception e) {
            System.out.println("ERROR DE CONEXION"+e);
        }
        return cn;
        
    }
}
