/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import GUI.UImain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                uimain.HienthiFormcon("nhacungcap");
            }
        });

        uimain.getBtnDVVC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uimain.HienthiFormcon("donvivanchuyen");
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
                uimain.HienthiFormcon("voucher");
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
                uimain.HienthiFormcon("nhanvien");
            }
        });
    }
}
