/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class image {

        public static Image getAppIcon() {
                String url = "/resources/icons/logo1.png";
                return new ImageIcon(image.class.getResource(url)).getImage();
        }

}
