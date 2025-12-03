/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.NhaCungCapDAL;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

/**
 *
 * @author Nghia
 */
public class NhaCungCapBLL {
    private NhaCungCapDAL dal = new NhaCungCapDAL();

    public ArrayList<NhaCungCapDTO> layDanhSach() {
        return dal.getAll();
    }

    public String themNCC(NhaCungCapDTO ncc) {
        if (ncc.getMaNCC().isEmpty()) return "Mã NCC không được để trống!";
        if (ncc.getTenCongTy().isEmpty()) return "Tên công ty không được để trống!";
        
        if (dal.them(ncc)) return "Thêm thành công";
        else return "Thêm thất bại (Trùng mã)!";
    }

    public String suaNCC(NhaCungCapDTO ncc) {
        if (dal.sua(ncc)) return "Cập nhật thành công";
        else return "Cập nhật thất bại";
    }

    public String xoaNCC(String ma) {
        if (dal.xoa(ma)) return "Xóa thành công";
        else return "Không thể xóa (Nhà cung cấp này đang có Hàng Hóa)!";
    }

    public ArrayList<NhaCungCapDTO> timKiemNCC(String tuKhoa) {
        if (tuKhoa.trim().isEmpty() || tuKhoa.equals("Tìm Kiếm")) {
            return dal.getAll();
        }
        return dal.timKiem(tuKhoa);
    }
}
