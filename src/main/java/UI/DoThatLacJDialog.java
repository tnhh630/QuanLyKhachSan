/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.DoThatLacDAO;
import Entity.DatPhong;
import Entity.DoThatLac;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DoThatLacJDialog extends javax.swing.JDialog {

        /**
         * Creates new form DoThatLacJDialog
         */
        public DoThatLacJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                initComponents();
                tblDanhSach.getColumnModel().getColumn(0).setPreferredWidth(30);
                tblDanhSach.getColumnModel().getColumn(1).setPreferredWidth(150);
                tblDanhSach.getColumnModel().getColumn(2).setPreferredWidth(50);
                tblDanhSach.getColumnModel().getColumn(3).setPreferredWidth(150);
                tblDanhSach.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                this.capNhatTable();
                init();
        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTable();
                }).start();
        }

        DoThatLacDAO dtlDAO = new DoThatLacDAO();

        boolean checkForm() {
                // kiểm tra trống mã đồ thất lạc
                if (txtMaDoThatLac.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đồ thất lạc !");
                        txtMaDoThatLac.requestFocus();
                        return false;
                }
                // kiểm tra trống tên đồ thất lạc
                if (txtTenDoThatLac.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên đồ thất lạc !");
                        txtTenDoThatLac.requestFocus();
                        return false;
                }
                // kiểm tra trống mã nhân viên tìm thấy
                if (txtMaNhanVienTimThay.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã nhân viên tìm thấy !");
                        txtMaNhanVienTimThay.requestFocus();
                        return false;
                }
                // kiểm tra thời gian
                if (txtThoiGianTimThay.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập thời gian tìm thấy !");
                        txtThoiGianTimThay.requestFocus();
                        return false;
                }
                // kiểm tra định dạng ngày
                if (!GenericValidator.isDate(txtThoiGianTimThay.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày sai định dạng (dd/MM/yyyy)!");
                        txtThoiGianTimThay.requestFocus();
                        return false;
                }

                // kiểm tra trống vị trí tìm
                if (txtViTriTim.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập vị trí tìm thấy !");
                        txtViTriTim.requestFocus();
                        return false;
                }
                return true;
        }

        void init() {
                setLocationRelativeTo(null);
                setResizable(false);
                loadToTable();
                initCboTinhTrang();
                initCboSapXep();
                loadMaDoThatLac();
        }

        void loadMaDoThatLac() {
                List<DoThatLac> lst = dtlDAO.SelectAll();
                String mdp = lst.get(lst.size() - 1).getMaDoThatLac();
                char[] ten = new char[4];
                char[] so = new char[5];
                mdp.getChars(3, mdp.length(), so, 0);
                int so1 = Integer.parseInt(String.valueOf(so).trim());
                if (so1 < 9) {
                        mdp.getChars(0, 4, ten, 0);
                } else {
                        mdp.getChars(0, 3, ten, 0);
                }
                int somoi = so1 + 1;
                String maDatPhong = String.valueOf(ten).trim();
                maDatPhong += String.valueOf(somoi);
                txtMaDoThatLac.setText(maDatPhong);

        }

        void loadToTable() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiem.getText();
                List<DoThatLac> lstDoThatLac = dtlDAO.SelectByKeyword(keyword);
                for (DoThatLac dtl : lstDoThatLac) {
                        String tinhTrang = "";
                        if (dtl.getTinhTrang() == 0) {
                                tinhTrang = "Chưa Trả";
                        } else if (dtl.getTinhTrang() == 1) {
                                tinhTrang = "Đã Trả";
                        } else {
                                tinhTrang = "Đã Liên Hệ";
                        }
                        Object[] row = {
                                dtl.getMaDoThatLac(),
                                dtl.getTenDoThatLac(),
                                dtl.getMaNVTimThay(),
                                xDate.dateToString(dtl.getThoiGianTimThay(), "dd/MM/yyyy"),
                                tinhTrang
                        };
                        model.addRow(row);
                }
        }

        void loadToTable_Sort(int cond, boolean tangGiam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
                model.setRowCount(0);
                List<DoThatLac> lstDoThatLac = dtlDAO.SelectAll_Sort(cond, tangGiam);
                for (DoThatLac dtl : lstDoThatLac) {
                        String tinhTrang = "";
                        if (dtl.getTinhTrang() == 0) {
                                tinhTrang = "Chưa Trả";
                        } else if (dtl.getTinhTrang() == 1) {
                                tinhTrang = "Đã Trả";
                        } else {
                                tinhTrang = "Đã Liên Hệ";
                        }
                        Object[] row = {
                                dtl.getMaDoThatLac(),
                                dtl.getTenDoThatLac(),
                                dtl.getMaNVTimThay(),
                                //                                dtl.getThoiGianTimThay(),
                                xDate.dateToString(dtl.getThoiGianTimThay(), "dd/MM/yyyy"),
                                tinhTrang
                        };
                        model.addRow(row);
                }
        }

        void initCboTinhTrang() {
                cboTinhTrang.removeAllItems();
                String[] data = {
                        "Chưa Trả",
                        "Đã Trả",
                        "Đã Liên Hệ"
                };

                for (String str : data) {
                        cboTinhTrang.addItem(str);
                }
                cboTinhTrang.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void initCboSapXep() {
                cboSapXep.removeAllItems();
                String[] data = {
                        "Mặc Định",
                        "Tình Trạng",
                        "Mã Đồ Thất Lạc"
                };

                for (String str : data) {
                        cboSapXep.addItem(str);
                }
                cboSapXep.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void clearForm() {
                DoThatLac dtl = new DoThatLac();
                setForm(dtl);
                txtThoiGianTimThay.setText("");
                loadMaDoThatLac();
        }

        void setForm(DoThatLac dtl) {
                txtMaDoThatLac.setText(dtl.getMaDoThatLac());
                txtTenDoThatLac.setText(dtl.getTenDoThatLac());
                txtMaNhanVienTimThay.setText(dtl.getMaNVTimThay());
                txtThoiGianTimThay.setText(xDate.dateToString(dtl.getThoiGianTimThay(), "dd/MM/yyyy"));
                txtViTriTim.setText(dtl.getViTriTimThay());
                cboTinhTrang.setSelectedIndex(dtl.getTinhTrang());
                txtGhiChu.setText(dtl.getGhiChu());
        }

        DoThatLac getForm() {
                DoThatLac dtl = new DoThatLac();
                dtl.setMaDoThatLac(txtMaDoThatLac.getText());
                dtl.setTenDoThatLac(txtTenDoThatLac.getText());
                dtl.setMaNVTimThay(txtMaNhanVienTimThay.getText());
                dtl.setThoiGianTimThay(xDate.stringToDate(txtThoiGianTimThay.getText(), "dd/MM/yyyy"));
                dtl.setViTriTimThay(txtViTriTim.getText());
                dtl.setTinhTrang(cboTinhTrang.getSelectedIndex());
                dtl.setGhiChu(txtGhiChu.getText());
                return dtl;
        }

        void insert() {
                if (!checkForm()) {
                        return;
                }
                DoThatLac dtl = getForm();
                try {
                        dtlDAO.insert(dtl);
                        loadToTable();
                        clearForm();
                        MsgBox.alert(this, "Thêm thành công !");
                        loadMaDoThatLac();
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại !");
                }
        }

        void update() {
                if (!checkForm()) {
                        return;
                }
                DoThatLac dtl = getForm();
                try {
                        dtlDAO.update(dtl);
                        loadToTable();
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại !");
                }
        }

        void delete() {
                if (txtMaDoThatLac.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đồ thất lạc cần xoa !");
                        txtMaDoThatLac.requestFocus();
                        return;
                }
                String maDoThatLac = txtMaDoThatLac.getText();
                try {
                        dtlDAO.delete(maDoThatLac);
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

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        mniDaTra = new javax.swing.JMenuItem();
        mniDaLienHe = new javax.swing.JMenuItem();
        mniChuaTra = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaNhanVienTimThay = new javax.swing.JTextField();
        txtTenDoThatLac = new javax.swing.JTextField();
        txtMaDoThatLac = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtThoiGianTimThay = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtViTriTim = new javax.swing.JTextArea();
        cboSapXep = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSapXep = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

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

        mniDaTra.setText("Đã Trả");
        mniDaTra.setToolTipText("");
        mniDaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDaTraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mniDaTra);

        mniDaLienHe.setText("Đã liên hệ");
        mniDaLienHe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDaLienHeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mniDaLienHe);

        mniChuaTra.setText("Chưa Trả");
        mniChuaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniChuaTraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mniChuaTra);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Đồ Thất Lạc");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1suitcases.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ ĐỒ THẤT LẠC");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 260, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã Đồ Thất Lạc");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tên Đồ Thất Lạc");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 108, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mã Nhân Viên Tìm Thấy");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 146, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Vị Trí Tìm");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 146, -1, -1));

        txtMaNhanVienTimThay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienTimThayActionPerformed(evt);
            }
        });
        jPanel1.add(txtMaNhanVienTimThay, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 147, 222, -1));
        jPanel1.add(txtTenDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 109, 222, -1));

        txtMaDoThatLac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaDoThatLacActionPerformed(evt);
            }
        });
        jPanel1.add(txtMaDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 71, 220, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Ngày Tìm ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 70, -1, -1));
        jPanel1.add(txtThoiGianTimThay, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 71, 265, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Tình Trạng");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 108, -1, -1));

        cboTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cboTinhTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 109, 265, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Ghi Chú");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 184, -1, -1));

        txtGhiChu.setColumns(3);
        txtGhiChu.setRows(3);
        jScrollPane3.setViewportView(txtGhiChu);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 201, 222, 90));

        txtViTriTim.setColumns(20);
        txtViTriTim.setRows(5);
        jScrollPane4.setViewportView(txtViTriTim);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 146, 265, -1));

        cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cboSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 320, 264, 30));

        tblDanhSach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Đồ Thất Lạc", "Tên Đồ Thất Lạc", "Mã NV Tìm", "Thời Gian Tìm Thấy", "Tình Trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setComponentPopupMenu(jPopupMenu1);
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 371, 880, 226));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel1.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 95, -1));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1file.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 320, 95, -1));

        btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sort.png"))); // NOI18N
        btnSapXep.setText("Sắp Xếp");
        btnSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepActionPerformed(evt);
            }
        });
        jPanel1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 320, -1, -1));

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 320, 95, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lost-items.png"))); // NOI18N
        jLabel6.setText("Tìm kiếm");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(513, 252, -1, -1));

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        jPanel1.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(625, 261, 265, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 610));

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

        boolean tang = true;
        private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
                // TODO add your handling code here:
                int layChiSoCboSapXep = cboSapXep.getSelectedIndex();
                if (layChiSoCboSapXep == 0) {
                        loadToTable();
                } else {
                        loadToTable_Sort(layChiSoCboSapXep, tang);
                        if (tang == true) {
                                tang = false;
                        } else {
                                tang = true;
                        }
                }
        }//GEN-LAST:event_btnSapXepActionPerformed

        private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
                // TODO add your handling code here:
                loadToTable();
        }//GEN-LAST:event_txtTimKiemKeyReleased

        private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int row = tblDanhSach.getSelectedRow();
                        DoThatLac dtl = dtlDAO.SelectByID((String) tblDanhSach.getValueAt(row, 0));
                        setForm(dtl);
                }
        }//GEN-LAST:event_tblDanhSachMouseClicked

        private void mniDaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDaTraActionPerformed
                // TODO add your handling code here:
                //click chuột phải vào 1 dòng trong bảng chọn đã trả
                int viTri = tblDanhSach.getSelectedRow();
                if (viTri == -1) {
                        MsgBox.alert(this, "Vui lòng chọn dòng cần thay đổi trạng thái!");
                        return;
                }
                String maDoThatLac = tblDanhSach.getValueAt(viTri, 0).toString();
                List<DoThatLac> lstDTL = dtlDAO.SelectAll();
                DoThatLac dtl = new DoThatLac();

                //update vào database
                dtl.setMaDoThatLac(maDoThatLac);
                dtl.setTinhTrang(1);
                dtlDAO.updateTinhTrang(dtl);
                // load lại table

                loadToTable();


        }//GEN-LAST:event_mniDaTraActionPerformed

        private void mniDaLienHeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDaLienHeActionPerformed
                // TODO add your handling code here:
                int viTri = tblDanhSach.getSelectedRow();
                if (viTri == -1) {
                        MsgBox.alert(this, "Vui lòng chọn dòng cần thay đổi trạng thái!");
                        return;
                }
                String maDoThatLac = tblDanhSach.getValueAt(viTri, 0).toString();
                List<DoThatLac> lstDTL = dtlDAO.SelectAll();
                DoThatLac dtl = new DoThatLac();

                //update vào database
                dtl.setMaDoThatLac(maDoThatLac);
                dtl.setTinhTrang(2);
                dtlDAO.updateTinhTrang(dtl);
                // load lại table

                loadToTable();
        }//GEN-LAST:event_mniDaLienHeActionPerformed

        private void mniChuaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniChuaTraActionPerformed
                // TODO add your handling code here:
                int viTri = tblDanhSach.getSelectedRow();

                if (viTri == -1) {
                        MsgBox.alert(this, "Vui lòng chọn dòng cần thay đổi trạng thái!");
                        return;
                }
                String maDoThatLac = tblDanhSach.getValueAt(viTri, 0).toString();
                List<DoThatLac> lstDTL = dtlDAO.SelectAll();
                DoThatLac dtl = new DoThatLac();

                //update vào database
                dtl.setMaDoThatLac(maDoThatLac);
                dtl.setTinhTrang(0);
                dtlDAO.updateTinhTrang(dtl);
                // load lại table

                loadToTable();
        }//GEN-LAST:event_mniChuaTraActionPerformed

    private void txtMaNhanVienTimThayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienTimThayActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienTimThayActionPerformed

    private void txtMaDoThatLacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaDoThatLacActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_txtMaDoThatLacActionPerformed

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
                        java.util.logging.Logger.getLogger(DoThatLacJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(DoThatLacJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(DoThatLacJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(DoThatLacJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                DoThatLacJDialog dialog = new DoThatLacJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem mniChuaTra;
    private javax.swing.JMenuItem mniDaLienHe;
    private javax.swing.JMenuItem mniDaTra;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaDoThatLac;
    private javax.swing.JTextField txtMaNhanVienTimThay;
    private javax.swing.JTextField txtTenDoThatLac;
    private javax.swing.JTextField txtThoiGianTimThay;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextArea txtViTriTim;
    // End of variables declaration//GEN-END:variables
}
