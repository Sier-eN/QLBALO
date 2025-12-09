/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.ThongKeDAL;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Nghia
 */
public class ThongKeBLL {
    private ThongKeDAL dal = new ThongKeDAL();

    public HashMap<String, Integer> layDuLieuTonKho() {
        return dal.getTonKho();
    }

    public double[] layDuLieuTaiChinh(int thang, int nam) {
        return dal.getTaiChinhThang(thang, nam);
    }

    public LinkedHashMap<String, Integer> layTop5BanChay(int thang, int nam) {
        return dal.getTop5BanChay(thang, nam);
    }
}
