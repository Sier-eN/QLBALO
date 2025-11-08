/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Nghia
 */
public class DateTimeBoGoc {
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    public static void lamBoGoc(DatePicker picker) {
        int radius = 10;

        JTextField dateField = picker.getComponentDateTextField();
        
        // Lấy màu border hiện tại
        Color borderColor = getBorderColor(dateField);

        RoundedBorder rounded = new RoundedBorder(radius, borderColor);
        // Tạo padding bên trong
        EmptyBorder padding = new EmptyBorder(3, 5, 3, 5);

        // Kết hợp RoundedBorder + padding
        dateField.setBorder(new CompoundBorder(rounded, padding));

        // Background trắng
        dateField.setBackground(Color.WHITE);
    }

    private static Color getBorderColor(JTextField field) {
        Border b = field.getBorder();
        if (b instanceof LineBorder) {
            return ((LineBorder) b).getLineColor();
        } else {
            // fallback màu xám nhạt giống Nimbus
            return new Color(169, 169, 169);
        }
    }
}
