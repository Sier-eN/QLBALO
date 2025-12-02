/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import App.LuuTruTK;
import DAL.TaiKhoanDAL;
import DTO.TaiKhoanDTO;

/**
 *
 * @author Nghia
 */
public class TaiKhoanBLL {
    
    private TaiKhoanDAL dal = new TaiKhoanDAL();
    
    public String kiemtraDangNhap(String tenDangNhap, String matKhau){
        
        if (tenDangNhap.trim().isEmpty() || tenDangNhap.equals("Nhập Tài Khoản")){
            return "Vui Lòng Nhập Tài Khoản!";
        }
        
        if(matKhau.trim().isEmpty()){
            return "Vui Lòng Nhập Mật Khẩu!";
        }
        
        TaiKhoanDTO tk = dal.kiemtraDN(tenDangNhap, matKhau);
        
        if (tk != null) {
            
            LuuTruTK.taiKhoanHienTai = tk;
            return "Success";
        } else {
            return "Tài khoản hoặc mật khẩu không chính xác";
        }
    }
}
