/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.DichVuDAO;
import DAO.KhoHangDAO;
import Entity.DichVu;
import Entity.KhoHang;
import Helper.MsgBox;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class QuanLyDichVuJDialog extends javax.swing.JDialog {

        /**
         * Creates new form DichVuJDialog
         */
        public QuanLyDichVuJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                tblDanhSachDichVu.setShowGrid(true);
                tblDanhSachDichVu.getColumnModel().getColumn(0).setPreferredWidth(60);
                tblDanhSachDichVu.getColumnModel().getColumn(1).setPreferredWidth(150);
                tblDanhSachDichVu.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                this.capNhatTable();
                init();
        }
        
        void init() {
                setLocationRelativeTo(null);
                setResizable(false);
                loadToTable();
        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTable();
                }).start();
        }

        DichVuDAO dvDAO = new DichVuDAO();
        KhoHangDAO khDAO = new KhoHangDAO();

        void loadToTable() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachDichVu.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiem.getText();
                List<DichVu> lstDichVu = dvDAO.SelectByKeyword(keyword);
                for (DichVu dv : lstDichVu) {
                        String donGia = String.valueOf(String.format("%.2f", dv.getDonGia()));
                        Object[] row = {
                                dv.getMaDichVu(),
                                dv.getTenLoaiDV(),
                                donGia
                        };
                        model.addRow(row);
                }
        }

        boolean checkForm() {
                // kiểm tra trống mã dịch vụ
                if (txtMaDichVu.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã dịch vụ !");
                        txtMaDichVu.requestFocus();
                        return false;
                }

                // kiểm tra trống tên dịch vụ
                if (txtTenDichVu.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên dịch vụ !");
                        txtTenDichVu.requestFocus();
                        return false;
                }

                // kiểm tra trống đơn giá
                if (txtDonGia.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập đơn giá dịch vụ !");
                        txtDonGia.requestFocus();
                        return false;
                }

                // kiểm tra định dạng đơn giá (phải là số / lớn hơn 0)
                try {
                        double donGia = Double.parseDouble(txtDonGia.getText());
                        if (donGia < 0) {
                                MsgBox.alert(this, "Đơn giá dịch vụ phải lớn hơn hoặc bằng 0 !");
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Đơn giá dịch vụ phải là số !");
                        return false;
                }

                return true;
        }

        void setForm(DichVu dv) {
                txtMaDichVu.setText(dv.getMaDichVu());
                txtTenDichVu.setText(dv.getTenLoaiDV());
                txtDonGia.setText(String.valueOf(String.format("%.2f", dv.getDonGia())));
        }

        DichVu getForm() {
                DichVu dv = new DichVu();
                dv.setMaDichVu(txtMaDichVu.getText());
                dv.setTenLoaiDV(txtTenDichVu.getText());
                dv.setDonGia(Double.parseDouble(txtDonGia.getText()));
                return dv;
        }

        void clearForm() {
                txtMaDichVu.setText("");
                txtTenDichVu.setText("");
                txtDonGia.setText("");
        }

        void insert() {
                if (!checkForm()) {
                        return;
                }
                DichVu dv = getForm();
                KhoHang kh = new KhoHang();
                kh.setSoLuongTon(0);
                kh.setDonViTinh("Thùng");
                kh.setMaDichVu(dv.getMaDichVu());
                try {
                        dvDAO.insert(dv);
                        khDAO.insert(kh);
                        loadToTable();
                        MsgBox.alert(this, "Thêm thành công !");
                        if (dv.getMaDichVu().contains("DVB")) {
                                GenBarCode(dv.getMaDichVu());
                        }
                        clearForm();

                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại !");
                }
        }

        void GenBarCode(String content) {
                try {
                        String path = "Barcode\\" + content + ".jpg";

                        Code128Writer write = new Code128Writer();
                        BitMatrix matrix = write.encode(content, BarcodeFormat.CODE_128, 500, 300);

                        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

                } catch (Exception e) {

                }
        }

        void update() {
                if (!checkForm()) {
                        return;
                }
                DichVu dv = getForm();
                if (dv.getMaDichVu().equalsIgnoreCase("DVB00")) {
                        MsgBox.alert(this, "Không thể thay đổi nước miễn phí !");
                        return;
                }
                try {
                        dvDAO.update(dv);
                        loadToTable();
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại !");
                }
        }

        void delete() {
                if (txtMaDichVu.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã dịch vụ cần xoá !");
                        txtMaDichVu.requestFocus();
                        return;
                }
                if (txtMaDichVu.getText().equalsIgnoreCase("DVB00")) {
                        MsgBox.alert(this, "Không thể xoá nước miễn phí !");
                        return;
                }
                try {
                        dvDAO.delete(txtMaDichVu.getText());
                        loadToTable();
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
        jLabel6 = new javax.swing.JLabel();
        txtMaDichVu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenDichVu = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachDichVu = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Quản Lý Dịch Vụ");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/room-service.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ DỊCH VỤ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Mã Dịch Vụ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));
        jPanel1.add(txtMaDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 61, 398, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Tên Dịch Vụ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 98, -1, -1));
        jPanel1.add(txtTenDichVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 99, 398, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Đơn Giá");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, -1, -1));
        jPanel1.add(txtDonGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 137, 398, -1));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1file.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 95, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, 95, -1));

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, 95, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
        jLabel2.setText("Tìm Kiếm");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        jPanel1.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 230, 400, 32));

        tblDanhSachDichVu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDanhSachDichVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Dịch Vụ", "Tên Dịch Vụ", "Đon Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachDichVuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachDichVu);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 500, 335));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 530, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
                // TODO add your handling code here:
                loadToTable();
        }//GEN-LAST:event_txtTimKiemKeyReleased

        private void tblDanhSachDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachDichVuMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int row = tblDanhSachDichVu.getSelectedRow();
                        DichVu dv = dvDAO.SelectByID(tblDanhSachDichVu.getValueAt(row, 0).toString());
                        setForm(dv);
                }
        }//GEN-LAST:event_tblDanhSachDichVuMouseClicked

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
                        java.util.logging.Logger.getLogger(QuanLyDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(QuanLyDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(QuanLyDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(QuanLyDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                QuanLyDichVuJDialog dialog = new QuanLyDichVuJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDanhSachDichVu;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaDichVu;
    private javax.swing.JTextField txtTenDichVu;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
