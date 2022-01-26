/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.LoaiPhongDAO;
import DAO.PhongDAO;
import DAO.TinhTrangDAO;
import Entity.LoaiPhong;
import Entity.Phong;
import Helper.MsgBox;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class PhongJDialog extends javax.swing.JDialog {

        /**
         * Creates new form Phong
         */
        public PhongJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                this.capNhatTable();
                init();
        }
        PhongDAO pDAO = new PhongDAO();
        LoaiPhongDAO lpDAO = new LoaiPhongDAO();
        TinhTrangDAO ttDAO = new TinhTrangDAO();

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTablePhong();
                        loadToTableLoaiPhong();
                }).start();
        }

        void init() {
                setLocationRelativeTo(null);
                setResizable(false);
                loadToTablePhong();
                loadToTableLoaiPhong();
                initCboLoaiPhong();
                initCboTinhTrang();
        }

        void initCboLoaiPhong() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaLoaiPhong.getModel();
                model.removeAllElements();
                List<String> lst = lpDAO.SelectAll_MaLoaiPhong();
                for (String str : lst) {
                        model.addElement(str);
                }
                cboMaLoaiPhong.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void initCboTinhTrang() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboTinhTrang.getModel();
                model.removeAllElements();
                List<String> lst = ttDAO.SelectAll_TenTinhTrang();
                for (String str : lst) {
                        model.addElement(str);
                }
                cboTinhTrang.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void loadToTablePhong() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachPhong.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiemPhong.getText();
                List<Object[]> lstPhong = pDAO.getDanhSachPhong(keyword);
                for (Object[] row : lstPhong) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4]
                        });
                }
        }

        void loadToTableLoaiPhong() {
                DefaultTableModel model = (DefaultTableModel) tblLoaiPhong.getModel();
                model.setRowCount(0);
                List<LoaiPhong> lstLoaiPhong = lpDAO.SelectAll();
                for (LoaiPhong lp : lstLoaiPhong) {
                        Object[] row = {
                                lp.getMaLoaiPhong(),
                                lp.getTenLoaiPhong()
                        };
                        model.addRow(row);
                }
        }

        boolean checkFormPhong() {
                // kiểm tra trống mã phòng
                if (txtMaPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã phòng !");
                        txtMaPhong.requestFocus();
                        return false;
                }

                if (txtMaPhong.getText().trim().length() > 5) {
                        MsgBox.alert(this, "Mã phòng tối đa 5 kí tự !");
                        txtMaPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống tên phòng
                if (txtTenPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên phòng");
                        txtTenPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống giá phòng
                if (txtGiaPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập giá phòng");
                        txtGiaPhong.requestFocus();
                        return false;
                }

                // kiểm tra định dạng giá phòng
                try {
                        double giaPhong = Double.parseDouble(txtGiaPhong.getText());
                        if (giaPhong <= 0) {
                                MsgBox.alert(this, "Giá phòng phải lớn hơn 0 !");
                                txtGiaPhong.requestFocus();
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Giá phòng phải là số !");
                        txtGiaPhong.requestFocus();
                        return false;
                }

                return true;
        }

        boolean checkFormLoaiPhong() {
                // kiểm tra trống mã loại phòng
                if (txtMaLoaiPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã loại phòng !");
                        txtMaLoaiPhong.requestFocus();
                        return false;
                }

                if (txtMaLoaiPhong.getText().trim().length() > 5) {
                        MsgBox.alert(this, "Mã loại phòng tối đa 5 kí tự !");
                        txtMaLoaiPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống tên loại phòng
                if (txtTenLoaiPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên loại phòng !");
                        txtTenLoaiPhong.requestFocus();
                        return false;
                }
                return true;
        }

        //------- PHÒNG ------
        void setFormPhong(Phong p) {
                txtMaPhong.setText(p.getMaPhong());
                txtTenPhong.setText(p.getTenPhong());
                txtGiaPhong.setText(String.valueOf(String.format("%.2f", p.getGiaLoaiPhong())));
                cboMaLoaiPhong.setSelectedItem(p.getMaLoaiPhong());
                cboTinhTrang.setSelectedIndex(p.getMaTinhTrang());
        }

        Phong getForm() {
                Phong p = new Phong();
                p.setMaPhong(txtMaPhong.getText());
                p.setTenPhong(txtTenPhong.getText());
                p.setGiaLoaiPhong(Double.parseDouble(txtGiaPhong.getText()));
                p.setMaLoaiPhong(cboMaLoaiPhong.getSelectedItem().toString());
                p.setMaTinhTrang(cboTinhTrang.getSelectedIndex());
                return p;
        }

        void clearFormPhong() {
                Phong p = new Phong();
                setFormPhong(p);
                txtGiaPhong.setText("");
                cboMaLoaiPhong.setSelectedIndex(0);
        }

        void insertPhong() {
                if (!checkFormPhong()) {
                        return;
                }
                Phong p = getForm();
                try {
                        pDAO.insert(p);
                        loadToTablePhong();
                        clearFormPhong();
                        MsgBox.alert(this, "Thêm phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm phòng thất bại !");
                }
        }

        void updatePhong() {
                if (!checkFormPhong()) {
                        return;
                }
                Phong p = getForm();
                try {
                        pDAO.update(p);
                        loadToTablePhong();
                        clearFormPhong();
                        MsgBox.alert(this, "Cập nhật phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật phòng thất bại !");
                }
        }

        void deletePhong() {
                if (txtMaPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã phòng cần xoá !");
                        txtMaPhong.requestFocus();
                        return;
                }
                try {
                        Phong p = pDAO.SelectByID(txtMaPhong.getText());
                        if (p == null) {
                                MsgBox.alert(this, "Không tìm thấy phòng !");
                                return;
                        }
                        pDAO.delete(txtMaPhong.getText());
                        loadToTablePhong();
                        clearFormPhong();
                        MsgBox.alert(this, "Xoá phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Xoá phòng thất bại !");
                }
        }

        //------- LOẠI PHÒNG ------
        void setFormLoaiPhong(LoaiPhong lp) {
                txtMaLoaiPhong.setText(lp.getMaLoaiPhong());
                txtTenLoaiPhong.setText(lp.getTenLoaiPhong());
        }

        LoaiPhong getFormLoaiPhong() {
                LoaiPhong lp = new LoaiPhong();
                lp.setMaLoaiPhong(txtMaLoaiPhong.getText());
                lp.setTenLoaiPhong(txtTenLoaiPhong.getText());
                return lp;
        }

        void clearFormLoaiPhong() {
                txtMaLoaiPhong.setText("");
                txtTenLoaiPhong.setText("");
                tblLoaiPhong.clearSelection();
        }

        void insertLoaiPhong() {
                if (!checkFormLoaiPhong()) {
                        return;
                }
                LoaiPhong lp = getFormLoaiPhong();
                try {
                        lpDAO.insert(lp);
                        loadToTableLoaiPhong();
                        loadToTablePhong();
                        initCboLoaiPhong();
                        clearFormLoaiPhong();
                        MsgBox.alert(this, "Thêm loại phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm loại phòng thất bại !");
                }
        }

        void updateLoaiPhong() {
                if (!checkFormLoaiPhong()) {
                        return;
                }
                LoaiPhong lp = getFormLoaiPhong();
                try {
                        lpDAO.update(lp);
                        loadToTableLoaiPhong();
                        loadToTablePhong();
                        clearFormLoaiPhong();
                        MsgBox.alert(this, "Cập nhật loại phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật loại phòng thất bại !");
                }
        }

        void deleteLoaiPhong() {
                if (txtMaLoaiPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã loại phòng cần xoá !");
                        txtMaLoaiPhong.requestFocus();
                        return;
                }
                String p = "<html><p style='color : red; font-weight:bold; '> Xoá LOẠI PHÒNG đồng nghĩa với việc xoá các phòng</p> <hr>Bạn có chắc muốn xoá ?</html>";
                if (MsgBox.confirm(this, p)) {
                        String maLoaiPhong = txtMaLoaiPhong.getText();
                        try {
                                lpDAO.delete(maLoaiPhong);
                                loadToTableLoaiPhong();
                                loadToTablePhong();
                                initCboLoaiPhong();
                                clearFormLoaiPhong();
                                MsgBox.alert(this, "Xoá loại phòng thành công !");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Xoá loại phòng thất bại !");
                        }
                }
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        txtGiaPhong = new javax.swing.JTextField();
        txtTenPhong = new javax.swing.JTextField();
        txtMaPhong = new javax.swing.JTextField();
        cboMaLoaiPhong = new javax.swing.JComboBox<>();
        btnThemPhong = new javax.swing.JButton();
        btnXoaPhong = new javax.swing.JButton();
        btnMoiPhong = new javax.swing.JButton();
        btnSuaPhong = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaLoaiPhong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTenLoaiPhong = new javax.swing.JTextField();
        btnThemLoaiPhong = new javax.swing.JButton();
        btnXoaLoaiPhong = new javax.swing.JButton();
        btnSuaLoaiPhong = new javax.swing.JButton();
        btnMoiLoaiPhong = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoaiPhong = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachPhong = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiemPhong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Quản Lý Phòng");

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/meeting-room.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ PHÒNG");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(306, 10, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Phòng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã Phòng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tên Phòng");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Giá Phòng");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Mã Loại Phòng");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Tình Trạng");

        cboTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboMaLoaiPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThemPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1add.png"))); // NOI18N
        btnThemPhong.setText("Thêm");
        btnThemPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhongActionPerformed(evt);
            }
        });

        btnXoaPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1file.png"))); // NOI18N
        btnXoaPhong.setText("Xoá");
        btnXoaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhongActionPerformed(evt);
            }
        });

        btnMoiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnMoiPhong.setText("Mới");
        btnMoiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiPhongActionPerformed(evt);
            }
        });

        btnSuaPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSuaPhong.setText("Sửa");
        btnSuaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGiaPhong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenPhong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaPhong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboMaLoaiPhong, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThemPhong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSuaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboMaLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemPhong)
                    .addComponent(btnXoaPhong)
                    .addComponent(btnMoiPhong)
                    .addComponent(btnSuaPhong))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Loại Phòng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Mã Loại Phòng");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Tên Loại Phòng");

        btnThemLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1add.png"))); // NOI18N
        btnThemLoaiPhong.setText("Thêm");
        btnThemLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiPhongActionPerformed(evt);
            }
        });

        btnXoaLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1file.png"))); // NOI18N
        btnXoaLoaiPhong.setText("Xoá");
        btnXoaLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLoaiPhongActionPerformed(evt);
            }
        });

        btnSuaLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSuaLoaiPhong.setText("Sửa");
        btnSuaLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLoaiPhongActionPerformed(evt);
            }
        });

        btnMoiLoaiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnMoiLoaiPhong.setText("Mới");
        btnMoiLoaiPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiLoaiPhongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLoaiPhong)
                            .addComponent(txtTenLoaiPhong)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThemLoaiPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(btnMoiLoaiPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSuaLoaiPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(btnXoaLoaiPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemLoaiPhong)
                    .addComponent(btnXoaLoaiPhong))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuaLoaiPhong)
                    .addComponent(btnMoiLoaiPhong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblLoaiPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã Loại Phòng", "Tên Loại Phòng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiPhongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoaiPhong);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chỉnh Sửa Thông Tin", jPanel1);

        tblDanhSachPhong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDanhSachPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Loại Phòng", "Tên Loại Phòng", "Mã Phòng", "Tên Phòng", "Đơn Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachPhong);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/tim.png"))); // NOI18N
        jLabel9.setText("Tìm kiếm");

        txtTimKiemPhong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemPhongKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTimKiemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh Sách Các Phòng", jPanel2);

        jPanel5.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 800, 430));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void txtTimKiemPhongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemPhongKeyReleased
                // TODO add your handling code here:
                loadToTablePhong();
        }//GEN-LAST:event_txtTimKiemPhongKeyReleased

        private void tblDanhSachPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachPhongMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int row = tblDanhSachPhong.getSelectedRow();
                        String maPhong = tblDanhSachPhong.getValueAt(row, 2).toString();
                        Phong p = pDAO.SelectByID(maPhong);
                        setFormPhong(p);
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_tblDanhSachPhongMouseClicked

        private void btnThemPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhongActionPerformed
                // TODO add your handling code here:
                insertPhong();
        }//GEN-LAST:event_btnThemPhongActionPerformed

        private void btnXoaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhongActionPerformed
                // TODO add your handling code here:
                deletePhong();
        }//GEN-LAST:event_btnXoaPhongActionPerformed

        private void btnSuaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhongActionPerformed
                // TODO add your handling code here:
                updatePhong();
        }//GEN-LAST:event_btnSuaPhongActionPerformed

        private void btnMoiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiPhongActionPerformed
                // TODO add your handling code here:
                clearFormPhong();
        }//GEN-LAST:event_btnMoiPhongActionPerformed

        private void btnThemLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiPhongActionPerformed
                // TODO add your handling code here:
                insertLoaiPhong();
        }//GEN-LAST:event_btnThemLoaiPhongActionPerformed

        private void btnXoaLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLoaiPhongActionPerformed
                // TODO add your handling code here:
                deleteLoaiPhong();
        }//GEN-LAST:event_btnXoaLoaiPhongActionPerformed

        private void btnMoiLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiLoaiPhongActionPerformed
                // TODO add your handling code here:
                clearFormLoaiPhong();
        }//GEN-LAST:event_btnMoiLoaiPhongActionPerformed

        private void btnSuaLoaiPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLoaiPhongActionPerformed
                // TODO add your handling code here:
                updateLoaiPhong();
        }//GEN-LAST:event_btnSuaLoaiPhongActionPerformed

        private void tblLoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiPhongMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int row = tblLoaiPhong.getSelectedRow();
                        String maLoaiPhong = tblLoaiPhong.getValueAt(row, 0).toString();
                        LoaiPhong lp = lpDAO.SelectByID(maLoaiPhong);
                        setFormLoaiPhong(lp);
                }
        }//GEN-LAST:event_tblLoaiPhongMouseClicked

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
                        java.util.logging.Logger.getLogger(PhongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(PhongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(PhongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(PhongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                PhongJDialog dialog = new PhongJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnMoiLoaiPhong;
    private javax.swing.JButton btnMoiPhong;
    private javax.swing.JButton btnSuaLoaiPhong;
    private javax.swing.JButton btnSuaPhong;
    private javax.swing.JButton btnThemLoaiPhong;
    private javax.swing.JButton btnThemPhong;
    private javax.swing.JButton btnXoaLoaiPhong;
    private javax.swing.JButton btnXoaPhong;
    private javax.swing.JComboBox<String> cboMaLoaiPhong;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblDanhSachPhong;
    private javax.swing.JTable tblLoaiPhong;
    private javax.swing.JTextField txtGiaPhong;
    private javax.swing.JTextField txtMaLoaiPhong;
    private javax.swing.JTextField txtMaPhong;
    private javax.swing.JTextField txtTenLoaiPhong;
    private javax.swing.JTextField txtTenPhong;
    private javax.swing.JTextField txtTimKiemPhong;
    // End of variables declaration//GEN-END:variables
}
