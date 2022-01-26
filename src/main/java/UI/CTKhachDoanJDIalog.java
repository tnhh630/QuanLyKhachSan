/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.CTKhachDoanDAO;
import Entity.CT_KhachDoan;
import Helper.Auth;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class CTKhachDoanJDIalog extends javax.swing.JDialog {

        /**
         * Creates new form CTKhachDoanJDIalog
         */
        public CTKhachDoanJDIalog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                init();
        }

        void init() {
                txtMaDoan.setText(Auth.authKhachHang.getMaKhachHang());
                setLocationRelativeTo(null);
                setResizable(false);
                if (Auth.ctKD != null) {
                        txtCCCD.setFocusable(false);
                        txtCCCD.setEditable(false);
                        txtCCCD.setText(Auth.ctKD.getCCCD());
                        txtMaKhachDoan.setText(Auth.ctKD.getMaKhachDoan());
                        txtTenKhachDoan.setText(Auth.ctKD.getHoTenThanhVien());
                        txtNgaySinh.setText(xDate.dateToString(Auth.ctKD.getNgaySinh(), "dd/MM/yyyy"));
                }
        }

        CTKhachDoanDAO ctDAO = new CTKhachDoanDAO();

        CT_KhachDoan getForm() {
                CT_KhachDoan ct = new CT_KhachDoan();
                ct.setMaKhachDoan(txtMaKhachDoan.getText());
                ct.setHoTenThanhVien(txtTenKhachDoan.getText());
                ct.setCCCD(txtCCCD.getText());
                ct.setNgaySinh(xDate.stringToDate(txtNgaySinh.getText(), "dd/MM/yyyy"));
                ct.setMaDoan(txtMaDoan.getText());
                return ct;
        }

        boolean checkForm() {
                // trống mã đoàn
                if (txtMaDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đoàn !");
                        txtMaDoan.requestFocus();
                        return false;
                }

                // trống mã khách đoàn
                if (txtMaKhachDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã khách đoàn !");
                        txtMaKhachDoan.requestFocus();
                        return false;
                }

                // trống tên khách đoàn
                if (txtTenKhachDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên khách đoàn !");
                        txtTenKhachDoan.requestFocus();
                        return false;
                }

                // trống ngày sinh
                if (txtNgaySinh.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày sinh khách đoàn !");
                        txtNgaySinh.requestFocus();
                        return false;
                }

                // kiểm tra nhập định dạng ngày
                if (!GenericValidator.isDate(txtNgaySinh.getText(), "dd/MM/yyyy", true)) {
                        return false;
                }

                return true;
        }

        void clearForm() {
//                txtMaDoan.setText("");
                txtCCCD.setFocusable(true);
                txtCCCD.setEditable(true);
                txtTenKhachDoan.setText("");
                txtNgaySinh.setText("");
                txtCCCD.setText("");
                txtMaKhachDoan.setText("");
        }

        void insert() {
                if (!checkForm()) {
                        return;
                }
                try {
                        CT_KhachDoan ct = getForm();
                        ctDAO.insert(ct);
                        clearForm();
                        MsgBox.alert(this, "Thêm thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại !");
                }
        }

        void update() {
                if (!checkForm()) {
                        return;
                }
                try {
                        CT_KhachDoan ct = getForm();
                        ctDAO.update(ct);
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại !");
                }
        }

        void delete() {
                if (txtMaKhachDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã khách đoàn cần xoá !");
                        txtMaKhachDoan.requestFocus();
                        return;
                }

                try {
                        String maKhachDoan = txtMaKhachDoan.getText();
                        ctDAO.delete(maKhachDoan);
                        clearForm();
                        MsgBox.alert(this, "Xoá thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Xoá thất bại !");
                }

        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenKhachDoan = new javax.swing.JTextField();
        txtMaKhachDoan = new javax.swing.JTextField();
        txtMaDoan = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();
        txtCCCD = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Thêm Thành Viên");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Add.png"))); // NOI18N
        jLabel1.setText("THÊM THÀNH VIÊN ĐOÀN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã Đoàn:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("CCCD:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 98, 60, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Tên Khách Đoàn:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Ngày Sinh:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 174, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Mã Khách Đoàn:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        txtTenKhachDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachDoanActionPerformed(evt);
            }
        });
        jPanel1.add(txtTenKhachDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 270, -1));

        txtMaKhachDoan.setEditable(false);
        jPanel1.add(txtMaKhachDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 270, -1));
        jPanel1.add(txtMaDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 270, -1));

        txtNgaySinh.setToolTipText("dd/MM/yyyy");
        jPanel1.add(txtNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 270, -1));

        txtCCCD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCCCDKeyReleased(evt);
            }
        });
        jPanel1.add(txtCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 270, -1));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-group.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 262, -1, -1));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/group.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 262, 95, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 262, 95, -1));

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 262, 95, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 330));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
                // TODO add your handling code here:
                insert();
        }//GEN-LAST:event_btnThemActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
                // TODO add your handling code here:
                delete();
        }//GEN-LAST:event_btnXoaActionPerformed

        private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                // TODO add your handling code here:
                update();
        }//GEN-LAST:event_btnSuaActionPerformed

        private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
                // TODO add your handling code here:
                clearForm();
        }//GEN-LAST:event_btnMoiActionPerformed

        private void txtCCCDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCCCDKeyReleased
                // TODO add your handling code here:
                DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyy");
                LocalDate localDate = LocalDate.now();
                if (txtCCCD.getText().trim().isEmpty()) {
                        txtMaKhachDoan.setText("");
                } else {
                        txtMaKhachDoan.setText(txtCCCD.getText() + format.format(localDate));
                }
        }//GEN-LAST:event_txtCCCDKeyReleased

    private void txtTenKhachDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachDoanActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachDoanActionPerformed

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
                        java.util.logging.Logger.getLogger(CTKhachDoanJDIalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(CTKhachDoanJDIalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(CTKhachDoanJDIalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(CTKhachDoanJDIalog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                CTKhachDoanJDIalog dialog = new CTKhachDoanJDIalog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtMaDoan;
    private javax.swing.JTextField txtMaKhachDoan;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtTenKhachDoan;
    // End of variables declaration//GEN-END:variables
}
