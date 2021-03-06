/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.TaiKhoanDAO;
import Entity.TaiKhoan;
import Helper.Auth;
import Helper.MsgBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.BorderFactory;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DoiMatKhauJDialog extends javax.swing.JDialog {

        /**
         * Creates new form DoiMatKhauJFrame
         */
        public DoiMatKhauJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setLocationRelativeTo(null);
                setResizable(false);
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                txtTenDangNhap.setText(Auth.user.getMaTaiKhoan());
        }
        TaiKhoanDAO tkDAO = new TaiKhoanDAO();

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLayeredPane1 = new javax.swing.JLayeredPane();
                jPanel2 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                txtTenDangNhap = new javax.swing.JTextField();
                txtMatKhauHienTai = new javax.swing.JPasswordField();
                txtMatKhauMoi = new javax.swing.JPasswordField();
                txtXacNhanMatKhauMoi = new javax.swing.JPasswordField();
                chkHienMatKhau = new javax.swing.JCheckBox();
                btnDongY = new javax.swing.JButton();
                btnHuy = new javax.swing.JButton();
                background = new javax.swing.JLabel();

                javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
                jLayeredPane1.setLayout(jLayeredPane1Layout);
                jLayeredPane1Layout.setHorizontalGroup(
                        jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                );
                jLayeredPane1Layout.setVerticalGroup(
                        jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                );

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("GodEdoc_Đổi mật khẩu");
                setBackground(new java.awt.Color(255, 255, 255));
                setResizable(false);

                jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
                jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ver2.png"))); // NOI18N
                jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 140, 130));

                jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(255, 255, 255));
                jLabel3.setText("Tên đăng nhập");
                jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

                jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel4.setForeground(new java.awt.Color(255, 255, 255));
                jLabel4.setText("Mật khẩu hiện tại");
                jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(255, 255, 255));
                jLabel5.setText("Mật khẩu mới");
                jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(255, 255, 255));
                jLabel6.setText("Xác nhận mật khẩu mới");
                jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

                txtTenDangNhap.setEditable(false);
                txtTenDangNhap.setBackground(new java.awt.Color(0, 0, 0));
                txtTenDangNhap.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtTenDangNhap.setForeground(new java.awt.Color(255, 255, 255));
                txtTenDangNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
                jPanel2.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 260, -1));

                txtMatKhauHienTai.setBackground(new java.awt.Color(0, 0, 0));
                txtMatKhauHienTai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtMatKhauHienTai.setForeground(new java.awt.Color(255, 255, 255));
                txtMatKhauHienTai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
                txtMatKhauHienTai.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMatKhauHienTaiActionPerformed(evt);
                        }
                });
                jPanel2.add(txtMatKhauHienTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 260, -1));

                txtMatKhauMoi.setBackground(new java.awt.Color(0, 0, 0));
                txtMatKhauMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtMatKhauMoi.setForeground(new java.awt.Color(255, 255, 255));
                txtMatKhauMoi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
                jPanel2.add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 260, 19));

                txtXacNhanMatKhauMoi.setBackground(new java.awt.Color(0, 0, 0));
                txtXacNhanMatKhauMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtXacNhanMatKhauMoi.setForeground(new java.awt.Color(255, 255, 255));
                txtXacNhanMatKhauMoi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
                jPanel2.add(txtXacNhanMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 260, -1));

                chkHienMatKhau.setBackground(new java.awt.Color(0, 0, 0));
                chkHienMatKhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                chkHienMatKhau.setForeground(new java.awt.Color(255, 255, 255));
                chkHienMatKhau.setText("Hiện mật khẩu");
                chkHienMatKhau.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                chkHienMatKhauStateChanged(evt);
                        }
                });
                chkHienMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                chkHienMatKhauMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                chkHienMatKhauMouseExited(evt);
                        }
                });
                chkHienMatKhau.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                chkHienMatKhauActionPerformed(evt);
                        }
                });
                jPanel2.add(chkHienMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, -1, -1));

                btnDongY.setBackground(new java.awt.Color(255, 51, 51));
                btnDongY.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checked (1).png"))); // NOI18N
                btnDongY.setText("Đồng ý");
                btnDongY.setBorderPainted(false);
                btnDongY.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                btnDongYMouseEntered(evt);
                        }
                });
                btnDongY.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDongYActionPerformed(evt);
                        }
                });
                jPanel2.add(btnDongY, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 110, 40));

                btnHuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/no-stopping.png"))); // NOI18N
                btnHuy.setText("Hủy");
                btnHuy.setBorderPainted(false);
                btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                btnHuyMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                btnHuyMouseExited(evt);
                        }
                });
                btnHuy.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnHuyActionPerformed(evt);
                        }
                });
                jPanel2.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 110, 40));

                background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/aa.jpg"))); // NOI18N
                background.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
                background.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                backgroundMouseExited(evt);
                        }
                });
                jPanel2.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 500));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

    private void txtMatKhauHienTaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauHienTaiActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauHienTaiActionPerformed

    private void chkHienMatKhauStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkHienMatKhauStateChanged
            // TODO add your handling code here:
            if (chkHienMatKhau.isSelected()) {
                    txtMatKhauHienTai.setEchoChar((char) 0);
                    txtMatKhauMoi.setEchoChar((char) 0);
                    txtXacNhanMatKhauMoi.setEchoChar((char) 0);
            } else {
                    txtMatKhauHienTai.setEchoChar('*');
                    txtMatKhauMoi.setEchoChar('*');
                    txtXacNhanMatKhauMoi.setEchoChar('*');
            }
    }//GEN-LAST:event_chkHienMatKhauStateChanged

    private void chkHienMatKhauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkHienMatKhauMouseEntered
            // TODO add your handling code here:
            chkHienMatKhau.setForeground(Color.RED);
            Font font = chkHienMatKhau.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            chkHienMatKhau.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_chkHienMatKhauMouseEntered

    private void chkHienMatKhauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkHienMatKhauMouseExited
            // TODO add your handling code here:
            chkHienMatKhau.setForeground(new Color(255, 255, 255));
            Font font = chkHienMatKhau.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, -1);
            chkHienMatKhau.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_chkHienMatKhauMouseExited

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
            // TODO add your handling code here:
            if (MsgBox.confirm(this, "Bạn có chắc muốn hủy ?")) {
                    this.dispose();
            }
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnHuyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseEntered
            // TODO add your handling code here:
            btnHuy.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
            btnHuy.setForeground(Color.RED);
            Font font = btnHuy.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            btnHuy.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_btnHuyMouseEntered

    private void btnHuyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseExited
            // TODO add your handling code here:
            btnHuy.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            btnHuy.setForeground(Color.WHITE);
            Font font = btnHuy.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, -1);
            btnHuy.setFont(font.deriveFont(attributes));
    }//GEN-LAST:event_btnHuyMouseExited
        void ok() {

                if (txtMatKhauHienTai.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mật khẩu hiện tại");
                        txtMatKhauHienTai.requestFocus();
                        return;
                }
                if (txtMatKhauMoi.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mật khẩu mới");
                        txtMatKhauMoi.requestFocus();
                        return;
                }
                if (txtXacNhanMatKhauMoi.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập xác nhận mật khẩu mới");
                        txtXacNhanMatKhauMoi.requestFocus();
                        return;
                }
                if (txtMatKhauMoi.getText().equalsIgnoreCase(txtXacNhanMatKhauMoi.getText()) == false) {
                        MsgBox.alert(this, "Xác nhận mật khẩu mới chưa trùng");
                        txtXacNhanMatKhauMoi.requestFocus();
                        return;
                }
                String tenDangNhap = Auth.user.getMaTaiKhoan();
                txtTenDangNhap.setText(tenDangNhap);
                String matKhauHienTai = new String(txtMatKhauHienTai.getPassword());
                String matKhauMoi = new String(txtMatKhauMoi.getPassword());
                String xacNhanMatKhauMoi = new String(txtXacNhanMatKhauMoi.getPassword());
                TaiKhoan tk = tkDAO.SelectByID(tenDangNhap);
                if (tk == null) {
                        MsgBox.alert(this, "Sai tên đăng nhập !");
                } else if (!matKhauHienTai.equals(tk.getMatKhau())) {
                        MsgBox.alert(this, "Sai mật khẩu hiện tại !");
                } else if (!matKhauMoi.equals(xacNhanMatKhauMoi)) {
                        MsgBox.alert(this, "Xác nhận mật khẩu mới chưa trùng !");
                } else {
                        Auth.user.setMatKhau(matKhauMoi);
                        tkDAO.update(Auth.user);
                        MsgBox.alert(this, "Đổi mật khẩu thành công !");
                        this.dispose();
                }

        }
    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
            // TODO add your handling code here:
            ok();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void btnDongYMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDongYMouseEntered
            // TODO add your handling code here:
            btnDongY.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            Font font = btnDongY.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            btnDongY.setFont(font.deriveFont(attributes));
            btnDongY.setBackground(new Color(255, 102, 102));
    }//GEN-LAST:event_btnDongYMouseEntered

    private void backgroundMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundMouseExited
            // TODO add your handling code here:
            btnDongY.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            Font font = btnDongY.getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, -1);
            btnDongY.setFont(font.deriveFont(attributes));
            btnDongY.setBackground(new Color(255, 0, 0));
    }//GEN-LAST:event_backgroundMouseExited

    private void chkHienMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHienMatKhauActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_chkHienMatKhauActionPerformed

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
                        java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                DoiMatKhauJDialog dialog = new DoiMatKhauJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JLabel background;
        private javax.swing.JButton btnDongY;
        private javax.swing.JButton btnHuy;
        private javax.swing.JCheckBox chkHienMatKhau;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLayeredPane jLayeredPane1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPasswordField txtMatKhauHienTai;
        private javax.swing.JPasswordField txtMatKhauMoi;
        private javax.swing.JTextField txtTenDangNhap;
        private javax.swing.JPasswordField txtXacNhanMatKhauMoi;
        // End of variables declaration//GEN-END:variables
}
