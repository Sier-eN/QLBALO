/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import BLL.ThongKeBLL;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Nghia
 */
public class ChildformThongke extends javax.swing.JPanel {
    
    private ThongKeBLL bll = new ThongKeBLL();

    /**
     * Creates new form ChildformThongke
     */
    public ChildformThongke() {
        initComponents();
        
        pnl_hienthi.setLayout(new GridLayout(2, 2, 20, 20)); 
        
        taiDuLieuVaVeBieuDo();
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                System.out.println("Đang cập nhật biểu đồ thống kê...");
                taiDuLieuVaVeBieuDo();
            }
        });
        
    }
    
    private void taiDuLieuVaVeBieuDo() {
        // Bước A: Xóa sạch các biểu đồ cũ đang hiện
        pnl_hienthi.removeAll();
        
        // Bước B: Chuẩn bị thời gian (Tháng/Năm hiện tại)
        int thangHienTai = java.time.LocalDate.now().getMonthValue();
        int namHienTai = java.time.LocalDate.now().getYear();

        // Bước C: Tạo và thêm các biểu đồ mới
        // 1. Biểu đồ Tồn Kho (Pie Chart) 

        pnl_hienthi.add(taoBieuDoTonKho());

        // 2. Biểu đồ Tài Chính (Bar Chart) 
        pnl_hienthi.add(taoBieuDoTaiChinh(thangHienTai, namHienTai));

        // 3. Biểu đồ Top 5 Bán Chạy (Bar Chart Ngang)
        pnl_hienthi.add(taoBieuDoTop5(thangHienTai, namHienTai));
        
        // 4. Panel trống (để lấp đầy ô thứ 4 cho đẹp)
        JPanel pnlRong = new JPanel();
        pnlRong.setBackground(new Color(235, 238, 240));
        pnl_hienthi.add(pnlRong);

        // Bước D: Cập nhật lại giao diện để hiển thị thay đổi
        pnl_hienthi.revalidate();
        pnl_hienthi.repaint();
    }

    // --- CÁC HÀM VẼ BIỂU ĐỒ CHI TIẾT (Copy từ bài trước) ---

    private ChartPanel taoBieuDoTonKho() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Integer> data = bll.layDuLieuTonKho();
        
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "TỶ LỆ TỒN KHO", dataset, true, true, false);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        return new ChartPanel(chart);
    }

    private ChartPanel taoBieuDoTaiChinh(int thang, int nam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double[] data = bll.layDuLieuTaiChinh(thang, nam); // [0]Thu, [1]Chi, [2]LoiNhuan

        dataset.addValue(data[0], "VND", "Doanh Thu");
        dataset.addValue(data[1], "VND", "Chi Phí Nhập");
        dataset.addValue(data[2], "VND", "Lợi Nhuận");

        JFreeChart chart = ChartFactory.createBarChart(
                "TÀI CHÍNH THÁNG " + thang + "/" + nam, 
                "Hạng Mục", "Số Tiền (VND)", dataset, 
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        
        // Làm đẹp cột (bỏ bóng đổ, màu phẳng)
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter()); 
        renderer.setSeriesPaint(0, new Color(76, 175, 80)); // Màu xanh lá cho cột
        
        return new ChartPanel(chart);
    }

    private ChartPanel taoBieuDoTop5(int thang, int nam) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        LinkedHashMap<String, Integer> data = bll.layTop5BanChay(thang, nam);

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Số Lượng", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "TOP 5 BÁN CHẠY THÁNG " + thang, 
                "Sản Phẩm", "Đã Bán", dataset, 
                PlotOrientation.HORIZONTAL, false, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(33, 150, 243)); // Màu xanh dương
        
        return new ChartPanel(chart);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnl_hienthi = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1250, 900));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(50, 50, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(1250, 200));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Thống Kê");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(870, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 143));

        pnl_hienthi.setBackground(new java.awt.Color(235, 238, 240));
        pnl_hienthi.setPreferredSize(new java.awt.Dimension(100, 757));

        javax.swing.GroupLayout pnl_hienthiLayout = new javax.swing.GroupLayout(pnl_hienthi);
        pnl_hienthi.setLayout(pnl_hienthiLayout);
        pnl_hienthiLayout.setHorizontalGroup(
            pnl_hienthiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        pnl_hienthiLayout.setVerticalGroup(
            pnl_hienthiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );

        add(pnl_hienthi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 143, 1250, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnl_hienthi;
    // End of variables declaration//GEN-END:variables
}
