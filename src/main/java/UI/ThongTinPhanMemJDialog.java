/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author 84384
 */
public class ThongTinPhanMemJDialog extends javax.swing.JDialog {

        /**
         * Creates new form ThongTin
         */
        public ThongTinPhanMemJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                setLocationRelativeTo(null);
                setResizable(false); // không cho thay đổi kích thước
                pane.setContentType("text/html; charset=UTF-8");
                pane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                String data = "<p><font size = '5'><font color = 'red'><b>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Tổng quan</b></font color><br></font size>\n"
                        + "<hr>\n"
                        + "Phần mềm quản lý khách sạn <font color = 'green'>GODEDOC HOTEL</font color> được thiết kế dựa trên nền tảng công nghệ hiện đại.<br>\n"
                        + "Phát triển dành riêng dành riêng cho quy mô nhà nghỉ, khách sạn, homestay nhỏ.<br>\n"
                        + "Các chức năng của phần mềm đáp ứng yêu cầu nghiệp vụ chuyên biệt tới từng bộ phận: <font color = 'green'><i>chủ khách sạn, quản lý, lễ tân, buồng phòng, kế toán…</i></font color> <br>\n"
                        + "Nếu bạn đang tìm kiếm phần mềm hỗ trợ quản lý khách sạn dễ sử dụng, có thể tham khảo tài liệu hướng dẫn phần mềm quản lý khách sạn từ <font color = 'red'><a href=\"web/Buoi_10/FeedBack.html\">GODEDOC HOTEL.</a></font color></style><br>\n"
                        + "<font size = '4'><font color = 'red'><b> Giải pháp GODEDOC cung cấp</b></font color><br></font size>\n"
                        + " -  Quản lý khách sạn toàn diện.<br>\n"
                        + " -  Giao diện <b>thân thiện, trực quan, đơn giản.</b><br>\n"
                        + " -  <b>Tiết kiệm</b> thời gian và chi phí quản lý, điều hành.<br>\n"
                        + " -  Kiểm soát thất thoát hiệu quả.<br><br>\n"
                        + " Dự Án Quản Lý Khách Sạn bằng phần mềm <b><font color = 'rgb(255, 145, 25)'>GODEDOC HOTEL</font color></b> do team GodEdoc gồm các thành viên:<br>\n"
                        + "<strong><font color = 'red'>&emsp;1. Trần Trung Nghĩa<br>\n"
                        + "	&emsp;2 .Trần Nguyên Hải <br>\n"
                        + "	&emsp;3. Doãn Hoài Nam <br>\n"
                        + "	&emsp;4. Lê Quý Vương<br>\n"
                        + "	&emsp;5. Hồ Trung Tính<br>\n"
                        + "	&emsp;6. Nguyễn Ngọc Giàu </font color></strong><br><br>\n"
                        + " đồng hành vào tạo lập nên, mục đích tạo ra một phần mềm thân thiện dễ dùng và tiện nghi cho khách hàng.<br><br>\n"
                        + "<font size = '4'><font color = 'blue'><b>Yêu cầu về môi trường</b></font color><br></font size>\n"
                        + "<hr>\n"
                        + "	1. Hệ điều hành bất kỳ<br>\n"
                        + "	2. JDK 1.8 trở lên.<br>\n"
                        + "	3. SQL Server 2008 trở lên.\n"
                        + "\n"
                        + " </p>";
                pane.setText(data);
                pane.setEditable(false);

                pane.addHyperlinkListener(new HyperlinkListener() {
                        public void hyperlinkUpdate(HyperlinkEvent e) {
                                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                        try {
                                                Desktop.getDesktop().browse(new File("web/Buoi_10/FeedBack.html").toURI());
                                        } catch (IOException ex) {

                                        }
                                }
                        }
                });
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                pane = new javax.swing.JEditorPane();
                lblBackground = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                pane.setBackground(new java.awt.Color(204, 255, 204));
                pane.setBorder(new javax.swing.border.MatteBorder(null));
                pane.setContentType(" Dự Án Quản Lý Khách Sạn bằng phần mềm GodEdoc Hotel do team<html><font color = yellow> GodEdoc</font></html>  gồm các thành viên:\n" +
                        " 1. Trần Trung Nghĩa \n" +
                        " 2 .Trần Nguyên Hải\n" +
                        " 3.Doãn Hoài Nam\n" +
                        " 4. Lê Quý Vương\n" +
                        " 5. Hồ Trung Tính\n" +
                        " 6. Nguyễn Ngọc Giàu\n" +
                        " đồng hành vào tạo lập nên, mục đích tạo ra một phần mềm thân thiện dễ dùng\n" +
                        " và tiện nghi cho khách hàng.\n" +
                        " Yêu cầu về môi trường: \n" +
                        " 1. Hệ điều hành bất kỳ\n" +
                        " 2. JDK 1.8 trở lên.\n" +
                        " 3. SQL Server 2008 trở lên.");
                pane.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                pane.setText("Tổng quan\n________________________________________________________________\nPhần mềm quản lý khách sạn GODEDOC HOTEL được thiết kế dựa trên nền tảng công nghệ hiện đại.\n Phát triển dành riêng dành riêng cho quy mô nhà nghỉ, khách sạn, homestay nhỏ.\n Các chức năng của phần mềm đáp ứng yêu cầu nghiệp vụ chuyên biệt tới từng bộ phận: chủ khách sạn, quản lý, lễ tân, buồng phòng, kế toán… \nNếu bạn đang tìm kiếm phần mềm hỗ trợ quản lý khách sạn dễ sử dụng, có thể tham khảo tài liệu hướng dẫn phần mềm quản lý khách sạn từ GODEDOC HOTELL.\nGiải pháp GODEDOC HOTEL cung cấp:\n________________________________________________________________\n -  Quản lý khách sạn toàn diện.\n -  Giao diện thân thiện, trực quan, đơn giản.\n -  Tiết kiệm thời gian và chi phí quản lý, điều hành.\n -  Kiểm soát thất thoát hiệu quả.\n Dự Án Quản Lý Khách Sạn bằng phần mềm GODEDOC HOTEL do team GodEdoc gồm các thành viên:\n 1. Trần Trung Nghĩa \n 2 .Trần Nguyên Hải\n 3.Doãn Hoài Nam\n 4. Lê Quý Vương\n 5. Hồ Trung Tính\n 6. Nguyễn Ngọc Giàu\n đồng hành vào tạo lập nên, mục đích tạo ra một phần mềm thân thiện dễ dùng\nvà tiện nghi cho khách hàng.\n Yêu cầu về môi trường: \n 1. Hệ điều hành bất kỳ\n 2. JDK 1.8 trở lên.\n 3. SQL Server 2008 trở lên.");
                jScrollPane1.setViewportView(pane);

                lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/hotellogo1.jpg"))); // NOI18N

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(ThongTinPhanMemJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(ThongTinPhanMemJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(ThongTinPhanMemJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(ThongTinPhanMemJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                ThongTinPhanMemJDialog dialog = new ThongTinPhanMemJDialog(new javax.swing.JFrame(), true);
                                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                        @Override
                                        public void windowClosing(java.awt.event.WindowEvent e) {
                                                System.exit(0);
                                        }
                                });
                                dialog.setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JLabel lblBackground;
        private javax.swing.JEditorPane pane;
        // End of variables declaration//GEN-END:variables
}
