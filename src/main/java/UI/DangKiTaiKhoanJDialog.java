/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import CHAT.MainFrame;
import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import Entity.NhanVien;
import Entity.TaiKhoan;
import Helper.Auth;
import Helper.MsgBox;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DangKiTaiKhoanJDialog extends javax.swing.JDialog {

        /**
         * Creates new form DangKiTaiKhoanJFrame
         */
        public DangKiTaiKhoanJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                cboVaiTro.setEnabled(false);
                tblDanhSachTaiKhoan.setShowGrid(true);
                tblDanhSachTaiKhoan.getColumnModel().getColumn(0).setPreferredWidth(30);
                tblDanhSachTaiKhoan.getColumnModel().getColumn(1).setPreferredWidth(30);
                tblDanhSachTaiKhoan.getColumnModel().getColumn(2).setPreferredWidth(30);
                tblDanhSachTaiKhoan.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                init();
                this.capNhatTable();
        }

        void init() {
                setLocationRelativeTo(null);
                setResizable(false);
                initCbo();
                loadToTableTaiKhoan();
                loadToTableDanhSachNV();
        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTableTaiKhoan();
                        loadToTableDanhSachNV();
                }).start();
        }

        void initCbo() {
                cboVaiTro.removeAllItems();
                String[] data = {
                        "Lễ Tân",
                        "Quản Lý Khách Sạn",
                        "Quản Lý Nhân Sự",
                        "Giám đốc"
                };
                for (String str : data) {
                        cboVaiTro.addItem(str);
                }
                cboVaiTro.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }
        TaiKhoanDAO tkDAO = new TaiKhoanDAO();
        NhanVienDAO nvDAO = new NhanVienDAO();
        String vaiTro = "";

        void clearForm() {
                txtMaNhanVien.setEditable(true);
                txtMaNhanVien.setFocusable(true);
                txtMaTaiKhoan.setEditable(true);
                txtMaTaiKhoan.setFocusable(true);
                txtMaNhanVien.setText("");
                txtMaTaiKhoan.setText("");
                txtMatKhau.setText("");
                cboVaiTro.setSelectedIndex(0);
        }

        void loadToTableTaiKhoan() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachTaiKhoan.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiemTK.getText();
                List<TaiKhoan> lstTaiKhoan = tkDAO.SelectAll_Sort(keyword);
                for (TaiKhoan tk : lstTaiKhoan) {
                        String vaiTro = "";
                        switch(tk.getVaiTro()){
                                case 1 : vaiTro = "Lễ Tân"; break;
                                case 2 : vaiTro =  "Quản Lý Khách Sạn"; break;
                                case 3 : vaiTro = "Quản Lý Nhân Sự"; break;
                                case 4 : vaiTro = "Giám Đốc"; break;
                        }
                        Object[] row = {
                                tk.getMaNhanVien(),
                                tk.getMaTaiKhoan(),
                                tk.getMatKhau(),
                                vaiTro
                        };
                        model.addRow(row);
                }
        }

        int taiKhoanRow = -1;

        void editFormTaiKhoan() {
                tblNVChuaCoTK.clearSelection();
                String maTaiKhoan = (String) tblDanhSachTaiKhoan.getValueAt(taiKhoanRow, 1);
                TaiKhoan tk = tkDAO.SelectByID(maTaiKhoan);
                setFormTaiKhoan(tk);
        }

        void setFormTaiKhoan(TaiKhoan tk) {
//                clearForm();
                txtMaNhanVien.setText(tk.getMaNhanVien());
                txtMaTaiKhoan.setText(tk.getMaTaiKhoan());
                txtMatKhau.setText(tk.getMatKhau());
                cboVaiTro.setSelectedIndex(tk.getVaiTro() - 1);
        }

        TaiKhoan getForm() {
                TaiKhoan tk = new TaiKhoan();
                tk.setMaNhanVien(txtMaNhanVien.getText());
                tk.setMaTaiKhoan(txtMaTaiKhoan.getText());
                tk.setMatKhau(txtMatKhau.getText());
                tk.setVaiTro(cboVaiTro.getSelectedIndex() + 1);
                return tk;
        }

        // kiểm tra form nhập liệu
        boolean checkForm() {
                // kiểm tra trống mã nhân viên
                if (txtMaNhanVien.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã nhân viên !");
                        txtMaNhanVien.requestFocus();
                        return false;
                }

                // kiểm tra trống mã tài khoản
                if (txtMaTaiKhoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tài khoản !");
                        txtMaTaiKhoan.requestFocus();
                        return false;
                }

                // kiểm tra trống mật khẩu
                if (txtMatKhau.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mật khẩu !");
                        txtMatKhau.requestFocus();
                        return false;
                }
                return true;
        }

        void insert() {
                if (!checkForm()) {
                        return;
                } else {
                        TaiKhoan tk = getForm();
                        try {
                                tkDAO.insert(tk);
                                // QR code
                                String data = tk.getMaTaiKhoan() + "-" + tk.getMatKhau();
                                String path = "AnhQRCode\\" + tk.getMaNhanVien() + ".jpg";
                                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 300, 300);
                                MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
                                clearForm();
                                loadToTableDanhSachNV();
                                loadToTableTaiKhoan();
                                MsgBox.alert(this, "Thêm tài khoản thành công !");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Thêm tài khoản thất bại !");
                        }
                }
        }

        void update() {
                if (!checkForm()) {
                        return;
                } else {
                        TaiKhoan tk = getForm();
                        try {
                                tkDAO.update(tk);
                                clearForm();
                                loadToTableDanhSachNV();
                                loadToTableTaiKhoan();
                                MsgBox.alert(this, "Cập nhật tài khoản thành công !");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Cập nhật tài khoản thất bại !");
                        }
                }
        }

        void delete() {
                // trống mã tài khoản
                if (txtMaTaiKhoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên tài khoản cần xoá !");
                        txtMaTaiKhoan.requestFocus();
                        return;
                }

                // không xoá chính tài khoản đang sử dụng
                if (txtMaTaiKhoan.getText().equalsIgnoreCase(Auth.user.getMaTaiKhoan())) {
                        MsgBox.alert(this, "Không thể xoá chính bạn");
                        txtMaTaiKhoan.requestFocus();
                        return;
                }

                TaiKhoan tk = tkDAO.SelectByID(txtMaTaiKhoan.getText());
                try {
                        if (tk != null) {
                                if (MsgBox.confirm(this, "Bạn có chắc muốn xoá")) {
                                        tkDAO.delete(txtMaTaiKhoan.getText());
                                        clearForm();
                                        loadToTableTaiKhoan();
                                        loadToTableDanhSachNV();
                                        MsgBox.alert(this, "Xoá thành công");
                                }
                        } else {
                                MsgBox.alert(this, "Không tìm thấy tài khoản : " + txtMaTaiKhoan.getText());
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Lỗi xoá tài khoản !");
                }
        }

        // TABS nhân viên chưa có tài khoản
        int danhSachRow = -1;

        void loadToTableDanhSachNV() {
                DefaultTableModel model = (DefaultTableModel) tblNVChuaCoTK.getModel();
                model.setRowCount(0);
                try {
                        String keyword = txtTimKiem.getText();
                        List<NhanVien> lstNhanVien = nvDAO.SelectByKeyWordNotTK(keyword);
//                        List<NhanVien> lstNhanVien = nvDAO.SelectNVChuaCoTK();
                        for (NhanVien nv : lstNhanVien) {
                                String chucVu = "";
                                if (nv.getChucVu() == 1) {
                                        chucVu = "Lễ Tân";
                                } else if (nv.getChucVu() == 2) {
                                        chucVu = "Quản Lý Khách Sạn";
                                } else if (nv.getChucVu() == 3) {
                                        chucVu = "Quản Lý Nhân Sự";
                                } else {
                                        chucVu = "Giám Đốc";
                                }
                                Object[] row = {
                                        nv.getMaNhanVien(),
                                        nv.getTenNhanVien(),
                                        nv.getSDT(),
                                        chucVu
                                };
                                model.addRow(row);
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Lỗi hiển thị danh sách nhân viên chưa có tài khoản !");
                }
        }

        void editFormDanhSachNV() {
                clearForm();
                tblDanhSachTaiKhoan.clearSelection();
                danhSachRow = tblNVChuaCoTK.getSelectedRow();
                String maNV = tblNVChuaCoTK.getValueAt(danhSachRow, 0).toString();
                NhanVien nv = nvDAO.selectByID(maNV);
                txtMaNhanVien.setText(nv.getMaNhanVien());
                cboVaiTro.setSelectedIndex(nv.getChucVu() - 1);
                tabs.setSelectedIndex(0);
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachTaiKhoan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtMaTaiKhoan = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        cboVaiTro = new javax.swing.JComboBox<>();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTimKiemTK = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNVChuaCoTK = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Tài Khoản");
        setFocusCycleRoot(false);
        setResizable(false);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDanhSachTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Mã tài khoản", "Mật khẩu", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachTaiKhoan);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Mã NV");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Mã tài khoản");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Mật khẩu");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Chức vụ");

        cboVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addd-user.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete user.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
        btnNew.setText("Làm mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        jLabel6.setText("Tìm kiếm");

        txtTimKiemTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemTKKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboVaiTro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMatKhau)
                    .addComponent(txtMaTaiKhoan)
                    .addComponent(txtMaNhanVien))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiemTK, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimKiemTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNew))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Đăng Ký", jPanel1);

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        tblNVChuaCoTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Chức vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNVChuaCoTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVChuaCoTKMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNVChuaCoTK);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
        jLabel5.setText("Tìm kiếm ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Nhân viên chưa có tài khoản", jPanel2);

        jPanel3.add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 920, 340));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 360));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        private void tblDanhSachTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachTaiKhoanMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        txtMaNhanVien.setEditable(false);
                        txtMaNhanVien.setFocusable(false);
                        txtMaTaiKhoan.setEditable(false);
                        txtMaTaiKhoan.setFocusable(false);
                        taiKhoanRow = tblDanhSachTaiKhoan.getSelectedRow();
                        editFormTaiKhoan();
                }
        }//GEN-LAST:event_tblDanhSachTaiKhoanMouseClicked

        private void tblNVChuaCoTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVChuaCoTKMouseClicked
                // TODO add your handling code here:           
                if (evt.getClickCount() == 2) {
                        editFormDanhSachNV();
                }
        }//GEN-LAST:event_tblNVChuaCoTKMouseClicked

        private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
                // TODO add your handling code here:
                clearForm();
        }//GEN-LAST:event_btnNewActionPerformed

        private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
                // TODO add your handling code here:
                loadToTableDanhSachNV();
        }//GEN-LAST:event_txtTimKiemKeyReleased

        private void txtTimKiemTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTKKeyReleased
                // TODO add your handling code here:
                loadToTableTaiKhoan();
        }//GEN-LAST:event_txtTimKiemTKKeyReleased

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
                        java.util.logging.Logger.getLogger(DangKiTaiKhoanJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(DangKiTaiKhoanJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(DangKiTaiKhoanJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(DangKiTaiKhoanJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                DangKiTaiKhoanJDialog dialog = new DangKiTaiKhoanJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDanhSachTaiKhoan;
    private javax.swing.JTable tblNVChuaCoTK;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtMaTaiKhoan;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemTK;
    // End of variables declaration//GEN-END:variables
}
