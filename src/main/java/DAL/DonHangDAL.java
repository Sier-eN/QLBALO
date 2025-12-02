/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Nghia
 */
public class DonHangDAL {
    private final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBaloCapSach;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa"; 
    private final String PASS = "123";
    
    public Connection getConnection(){
        Connection ketnoi = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ketnoi = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ketnoi;
    }
}
