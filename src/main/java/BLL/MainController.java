/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import App.LuuTruTK;
import GUI.UImain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Nghia
 */
public class MainController {
    private UImain uimain;
    
    public MainController(UImain uimain){
        this.uimain = uimain;
        HienthiForm();
    }
    
    private boolean KiemTraQuyen(){
        if (LuuTruTK.taiKhoanHienTai == null) return false;
        return LuuTruTK.taiKhoanHienTai.getVaiTro().equalsIgnoreCase("Admin");
    }
    
    private void PhanQuyenGiaoDien() {
        if (KiemTraQuyen() == false) {
            uimain.getBtnNCC().setEnabled(false);
            uimain.getBtnDVVC().setVisible(false);
            uimain.getBtnVoucher().setEnabled(false);
            uimain.getBtnNhanVien().setEnabled(false);
        }
    }
    
    public void HienthiForm(){
        uimain.getBtnThongKe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uimain.HienthiFormcon("thongke");
            }   
        });
        uimain.getBtnDonHang().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uimain.HienthiFormcon("donhang");
            }
        });

        uimain.getBtnNCC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (KiemTraQuyen()) {
                    uimain.HienthiFormcon("nhacungcap");
                } else {
                    JOptionPane.showMessageDialog(uimain, "Chức năng này chỉ dành cho Quản lý (Admin)!");
                }
            }
        });

        uimain.getBtnDVVC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (KiemTraQuyen()) {
                    uimain.HienthiFormcon("donvivanchuyen");
                } else {
                    JOptionPane.showMessageDialog(uimain,"Chức năng này chỉ dành cho Quản lý (Admin)!");
                }
            }
        });

        uimain.getBtnHangHoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uimain.HienthiFormcon("hanghoa");
            }
        });

        uimain.getBtnVoucher().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (KiemTraQuyen()) {
                    uimain.HienthiFormcon("voucher");
                } else {
                    JOptionPane.showMessageDialog(uimain,"Chức năng này chỉ dành cho Quản lý (Admin)!");
                }
            }
        });

        uimain.getBtnTaiKhoan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uimain.HienthiFormcon("taikhoan");
            }
        });

        uimain.getBtnNhanVien().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (KiemTraQuyen()) {
                    uimain.HienthiFormcon("nhanvien");
                } else {
                    JOptionPane.showMessageDialog(uimain,"Chức năng này chỉ dành cho Quản lý (Admin)!");
                }
            }
        });
    }
}
