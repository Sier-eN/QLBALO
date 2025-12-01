/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import DTO.TaiKhoanDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Nghia
 */
public class TaiKhoanDAL {
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
    
    public TaiKhoanDTO kiemtraDN(String user, String pass){
        
        TaiKhoanDTO tk = null;
        String query = "Select * from TaiKhoan "
                     + "Where TenDangNhap =? and MatKhau =?";
        
        try (Connection ketnoi = getConnection();
                PreparedStatement pstmt = ketnoi.prepareStatement(query)){
            
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                tk = new TaiKhoanDTO();
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setMatKhau(rs.getString("MatKhau"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }
}
