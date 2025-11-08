/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package App;

import GUI.UImain;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/**
 *
 * @author Nghia
 */
public class main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("Button.arc",16);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("TextField.arc",16);
        
        UImain frame = new UImain();
        frame.setVisible(true);
    }
}
