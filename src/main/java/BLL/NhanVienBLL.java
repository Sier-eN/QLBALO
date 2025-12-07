/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.NhanVienDAL;
import DTO.NhanVienDTO;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class NhanVienBLL {
    private NhanVienDAL dal = new NhanVienDAL();

    // 1. Lấy danh sách
    public ArrayList<NhanVienDTO> layDanhSach() {
        return dal.layDanhSach();
    }

    // 2. Thêm nhân viên (Có kiểm tra trùng)
    public String themNhanVien(NhanVienDTO nv) {
        // Validate dữ liệu rỗng
        if (nv.getMaNV().isEmpty()) return "Vui lòng nhập Mã Nhân Viên!";
        if (nv.getTenNV().isEmpty()) return "Vui lòng nhập Tên Nhân Viên!";
        if (nv.getTenDangNhap().isEmpty()) return "Vui lòng nhập Tài khoản!";
        if (nv.getMatKhau().isEmpty()) return "Vui lòng nhập Mật khẩu!";

        // Kiểm tra trùng trong CSDL
        if (dal.kiemTraTrungMa(nv.getMaNV())) {
            return "Mã nhân viên đã tồn tại!";
        }
        if (dal.kiemTraTrungTaiKhoan(nv.getTenDangNhap())) {
            return "Tên tài khoản đã tồn tại!";
        }

        if (dal.themNhanVien(nv)) {
            return "Thêm thành công";
        }
        return "Thêm thất bại (Lỗi hệ thống)";
    }

    // 3. Cập nhật nhân viên
    public String capNhatNhanVien(NhanVienDTO nv) {
        if (nv.getTenNV().isEmpty()) return "Tên không được để trống!";
        // Lưu ý: Cập nhật thì không check trùng mã NV vì mã đó chính là của người đang sửa
        
        if (dal.capNhatNhanVien(nv)) {
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    // 4. Xóa nhân viên
    public String xoaNhanVien(String maNV) {
        if (maNV.isEmpty()) return "Chưa chọn nhân viên!";
        if (dal.xoaNhanVien(maNV)) {
            return "Xóa thành công";
        }
        return "Xóa thất bại";
    }
    
    public ArrayList<NhanVienDTO> timKiem(String tuKhoa) {
        if (tuKhoa.isEmpty()) {
            return dal.layDanhSach(); // Nếu rỗng thì trả về toàn bộ
        }
        return dal.timKiemNhanVien(tuKhoa);
    }
    
    public NhanVienDTO layThongTinCaNhan(String tenDangNhap) {
        return dal.layThongTinCaNhan(tenDangNhap);
    }
}
