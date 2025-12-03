/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.VoucherDAL;
import DTO.VoucherDTO;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class VoucherBLL {
    private VoucherDAL dal = new VoucherDAL();

    public ArrayList<VoucherDTO> layDanhSach() {
        return dal.getAll();
    }

    public String themVoucher(VoucherDTO v) {
        if (v.getMaVoucher().isEmpty()) return "Vui lòng nhập Mã Voucher!";
        if (v.getTenVoucher().isEmpty()) return "Vui lòng nhập Tên Voucher!";
        if (v.getGiaTri() < 0) return "Giá trị không được âm!";
        if (v.getSoLuong() < 0) return "Số lượng không được âm!";
        
        if (dal.them(v)) return "Thêm thành công";
        else return "Thêm thất bại (Trùng mã)!";
    }

    public String suaVoucher(VoucherDTO v) {
        if (dal.sua(v)) return "Cập nhật thành công";
        else return "Cập nhật thất bại";
    }

    public String xoaVoucher(String ma) {
        if (dal.xoa(ma)) return "Xóa thành công";
        else return "Không thể xóa (Voucher đang được sử dụng)!";
    }

    public ArrayList<VoucherDTO> timKiemVoucher(String tuKhoa) {
        if (tuKhoa.trim().isEmpty() || tuKhoa.equals("Tìm Kiếm")) {
            return dal.getAll();
        }
        return dal.timKiem(tuKhoa);
    }
}
