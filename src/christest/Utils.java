/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package christest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author assessor
 */
public class Utils {
    Connection conn = null;
    
    public Utils(Connection conn){
        this.conn = conn;
    }
    
    //gets a value from the config table
    public String getConfigValue(String name){
        String value = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select * from configuration where name = '" + name + "'");
            rset.next();
            value = rset.getString("value");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Unable to get configuration value: " + name);
            e.printStackTrace();
        }
        
        return value;
    }
    
    public void setConfigValue(String name, String value){
         try{
            Statement stmt = conn.createStatement();
            stmt.execute("update configuration set value = '" + value + "' where name = '" + name + "'");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Unable to set configuration value: " + name + " = " + value);
            e.printStackTrace();
        }
    }
}
