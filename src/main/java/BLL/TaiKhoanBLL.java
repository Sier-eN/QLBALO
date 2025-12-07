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
    
    public String doiMatKhau(String tenDangNhap, String mkCu, String mkMoi, String mkNhapLai) {
        // 1. Kiểm tra rỗng
        if (mkCu.trim().isEmpty() || mkMoi.trim().isEmpty() || mkNhapLai.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // 2. Kiểm tra mật khẩu mới và nhập lại
        if (!mkMoi.equals(mkNhapLai)) {
            return "Mật khẩu mới và nhập lại không khớp!";
        }

        // 3. Kiểm tra mật khẩu mới trùng mật khẩu cũ
        if (mkMoi.equals(mkCu)) {
            return "Mật khẩu mới không được trùng với mật khẩu cũ!";
        }

        // 4. Kiểm tra mật khẩu cũ có đúng không
        // Tận dụng lại hàm kiemtraDN bên DAL để check
        TaiKhoanDTO tk = dal.kiemtraDN(tenDangNhap, mkCu);
        if (tk == null) {
            return "Mật khẩu cũ không chính xác!";
        }

        // 5. Nếu mọi thứ OK -> Gọi DAL để Update
        if (dal.capNhatMatKhau(tenDangNhap, mkMoi)) {
            return "Đổi mật khẩu thành công";
        } else {
            return "Lỗi hệ thống: Không thể cập nhật mật khẩu.";
        }
    }
}
