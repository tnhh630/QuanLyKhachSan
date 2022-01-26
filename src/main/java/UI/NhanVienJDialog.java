/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import Helper.Auth;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class NhanVienJDialog extends javax.swing.JDialog {

        /**
         * Creates new form NhanVienJDalog
         */
        public NhanVienJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                pnlNhanVien.setBackground(new Color(120, 120, 120));
                pnlDanhSach.setBackground(new Color(120, 120, 120));
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                tblDanhSachNhanVien.setShowGrid(true);
                tblDanhSachNhanVien.getColumnModel().getColumn(0).setPreferredWidth(5);
                tblDanhSachNhanVien.getColumnModel().getColumn(1).setPreferredWidth(150);
                tblDanhSachNhanVien.getColumnModel().getColumn(2).setPreferredWidth(70);
                tblDanhSachNhanVien.getColumnModel().getColumn(3).setPreferredWidth(3);
                tblDanhSachNhanVien.getColumnModel().getColumn(4).setPreferredWidth(100);
                tblDanhSachNhanVien.getColumnModel().getColumn(5).setPreferredWidth(50);
                tblDanhSachNhanVien.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                this.capNhatTable();
                init();
        }

        void init() {
                setLocationRelativeTo(null);
                setResizable(false);

                initCboChucVu();
                initCboSapXep();
                loadToTable();

        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTable();
                }).start();
        }

        void initCboChucVu() {
                cboChucVu.removeAllItems();
                String[] data = {
                        "Lễ Tân",
                        "Quản Lý Khách Sạn",
                        "Quản Lý Nhân Sự",
                        "Giám đốc"
                };
                for (String str : data) {
                        cboChucVu.addItem(str);
                }
                cboChucVu.setRenderer(new DefaultListCellRenderer() {
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
                        "Mã Nhân Viên",
                        "Tên Nhân Viên",
                        "Chức Vụ"
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

        boolean checkForm() {
                // kiểm tra trống mã nhân viên
                if (txtMaNhanVien.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã nhân viên !");
                        txtMaNhanVien.requestFocus();
                        return false;
                }

                // kiểm tra trống tên
                if (txtTenNhanVien.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên nhân viên !");
                        txtTenNhanVien.requestFocus();
                        return false;
                }

                // kiểm tra trống CCCD
                if (txtCCCD.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập CCCD !");
                        txtCCCD.requestFocus();
                        return false;
                }

                // kiểm tra trống ngày sinh
                if (txtNgaySinh.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày sinh !");
                        txtNgaySinh.requestFocus();
                        return false;
                }

                // kiểm tra nhập định dạng ngày
                if (!GenericValidator.isDate(txtNgaySinh.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày sinh sai định dạng (dd/MM/yyyy)!");
                        txtNgaySinh.requestFocus();
                        return false;
                }

                // kiểm tra trống giới tính
                if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
                        MsgBox.alert(this, "Chọn giới tính !");
                        return false;
                }

                // kiểm tra trống  Email
                if (txtEmail.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập Email !");
                        txtEmail.requestFocus();
                        return false;
                }
                // kiểm tra định dạng email
//                String email = "\\w+@\\w+{\\.\\w+}{1,2}$";
                String email = "^[A-Za-z0-9+_.-]+@(.+)$";
                if (!txtEmail.getText().matches(email)) {
                        MsgBox.alert(this, "Sai định dạng email !");
                        txtEmail.requestFocus();
                        return false;
                }

                //Kiểm tra trống SĐT
                if (txtSoDienThoai.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập số điện thoại !");
                        txtSoDienThoai.requestFocus();
                        return false;
                }
                // kiểm tra định dạng SĐT
                if (!txtSoDienThoai.getText().matches("0\\d{9,10}")) {
                        MsgBox.alert(this, "Sai định dạng số điện thoại !");
                        txtSoDienThoai.requestFocus();
                        return false;
                }

                // không cần kiểm tra chức vụ (mặc định vào là admin)
                // kiểm tra trống địa chỉ
                if (txtDiaChi.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập địa chỉ !");
                        txtDiaChi.requestFocus();
                        return false;
                }

                // đúng tất cả định dạng
                return true;
        }

        NhanVienDAO nvDAO = new NhanVienDAO();
        int tableRow = -1;

        void loadToTable() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiem.getText();
                List<NhanVien> lstNhanVien = nvDAO.SelectByKeyWord(keyword);
                for (NhanVien nv : lstNhanVien) {
                        String gt = nv.isGioiTinh() ? "Nam" : "Nữ";
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
                                nv.getCCCD(),
                                gt,
                                nv.getEmail(),
                                nv.getSDT(),
                                chucVu
                        };
                        model.addRow(row);
                }
        }

        void loadToTableSort(int cond, boolean tangGiam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachNhanVien.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiem.getText();
                List<NhanVien> lstNhanVien = nvDAO.SelectAll_Sort(cond, tangGiam);
                for (NhanVien nv : lstNhanVien) {
                        String gt = nv.isGioiTinh() ? "Nam" : "Nữ";
                        String chucVu = "";
                        if (nv.getChucVu() == 1) {
                                chucVu = "Lễ Tân";
                        } else if (nv.getChucVu() == 2) {
                                chucVu = "Quản Lý Khách Sạn";
                        } else if (nv.getChucVu() == 3) {
                                chucVu = "Quản Lý Nhân Sự";
                        } else {
                                chucVu = "Giám đốc";
                        }
                        Object[] row = {
                                nv.getMaNhanVien(),
                                nv.getTenNhanVien(),
                                nv.getCCCD(),
                                gt,
                                nv.getEmail(),
                                nv.getSDT(),
                                chucVu
                        };
                        model.addRow(row);
                }
        }

        void edit() {
                String maNV = (String) tblDanhSachNhanVien.getValueAt(tableRow, 0);
                NhanVien nv = nvDAO.selectByID(maNV);
                setForm(nv);
        }

        void setForm(NhanVien nv) {
                txtMaNhanVien.setText(nv.getMaNhanVien());
                txtTenNhanVien.setText(nv.getTenNhanVien());
                txtCCCD.setText(nv.getCCCD());
                txtNgaySinh.setText(xDate.dateToString(nv.getNgaySinh(), "dd/MM/yyyy"));
                rdoNam.setSelected(nv.isGioiTinh());
                rdoNu.setSelected(!nv.isGioiTinh());
                txtEmail.setText(nv.getEmail());
                txtSoDienThoai.setText(nv.getSDT());
                cboChucVu.setSelectedIndex(nv.getChucVu() - 1);
                txtDiaChi.setText(nv.getDiaChi());
                if (nv.getHinhAnh() != null) {
                        lblHinhAnh.setIcon(new ImageIcon(new ImageIcon(nv.getHinhAnh()).getImage().getScaledInstance(196, 246, Image.SCALE_SMOOTH)));
                }

        }

        NhanVien getForm() {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(txtMaNhanVien.getText());
                nv.setTenNhanVien(txtTenNhanVien.getText());
                nv.setCCCD(txtCCCD.getText());
                nv.setNgaySinh(xDate.stringToDate(txtNgaySinh.getText(), "dd/MM/yyyy"));
                nv.setGioiTinh(rdoNam.isSelected());
                nv.setEmail(txtEmail.getText());
                nv.setSDT(txtSoDienThoai.getText());
                nv.setDiaChi(txtDiaChi.getText());
                nv.setChucVu(cboChucVu.getSelectedIndex() + 1);
                nv.setHinhAnh(p_image);
                return nv;
        }

        void clearForm() {
                NhanVien nv = new NhanVien();
                setForm(nv);
                txtNgaySinh.setText("");
                cboChucVu.setSelectedIndex(0);
                rdoGroupGioiTinh.clearSelection();
                lblHinhAnh.setIcon(null);
        }

        void insert() {
                if (!checkForm()) {
                        return;
                } else {
                        NhanVien nv = getForm();
                        try {
                                nvDAO.insert(nv);
                                loadToTable();
                                MsgBox.alert(this, "Thêm nhân viên thành công !");
                                String url = (txtMaNhanVien.getText() + ".jpg");
                                BufferedImage img = ImageIO.read(new File(path));
                                try ( Socket socket = new Socket("127.0.0.1", 6789)) {
                                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                                        out.println("picture");

                                        out.println(url);
                                        System.out.println(url);
                                        ImageIO.write(img, "jpg", socket.getOutputStream());
                                } catch (Exception e) {
                                }
                                clearForm();

                        } catch (Exception e) {
                                MsgBox.alert(this, "Thêm nhân viên thất bại !");
                        }
                }
        }

        void update() {
                if (!checkForm()) {
                        return;
                } else {
                        NhanVien nv = getForm();
                        try {
                                nvDAO.update(nv);
                                loadToTable();
                                clearForm();

                        } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.alert(this, "Cập nhật nhân viên thất bại !");
                        }
                }
        }

        void delete() {
                if (txtMaNhanVien.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Chọn nhân viên cần xoá !");
                        return;
                }
                String maNV = txtMaNhanVien.getText();

                if (maNV.equalsIgnoreCase(Auth.user.getMaNhanVien())) {
                        MsgBox.alert(this, "Không thể tự xoá !");
                        return;
                }

                if (MsgBox.confirm(this, "Xác nhận xoá !")) {
                        try {
                                nvDAO.delete(maNV);
                                loadToTable();
                                clearForm();
                                MsgBox.alert(this, "Xoá thành công !");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Xoá thất bại !");
                        }
                }
        }

        String path = null;
        byte[] p_image = null;

        void UploadImage() throws IOException {
                JFileChooser jFc = new JFileChooser();
                jFc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("png", "jpg", "jfif");
                jFc.addChoosableFileFilter(filter);

                int chooser = jFc.showOpenDialog(null);
                if (chooser == JFileChooser.APPROVE_OPTION) {
                        File file = jFc.getSelectedFile();
                        path = file.getAbsolutePath();
                        ImageIcon image = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(196, 246, Image.SCALE_SMOOTH));
                        lblHinhAnh.setIcon(image);
                        try {
                                File image_1 = new File(path);
                                FileInputStream fis = new FileInputStream(image_1);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] buf = new byte[1024];
                                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                                        bos.write(buf, 0, readNum);
                                }
                                p_image = bos.toByteArray();
                        } catch (Exception e) {

                        }
                }
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                rdoGroupGioiTinh = new javax.swing.ButtonGroup();
                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                pnlOut = new javax.swing.JTabbedPane();
                pnlNhanVien = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                txtMaNhanVien = new javax.swing.JTextField();
                lblHinhAnh = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                txtTenNhanVien = new javax.swing.JTextField();
                jLabel6 = new javax.swing.JLabel();
                txtCCCD = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                rdoNam = new javax.swing.JRadioButton();
                rdoNu = new javax.swing.JRadioButton();
                jLabel8 = new javax.swing.JLabel();
                txtNgaySinh = new javax.swing.JTextField();
                jLabel9 = new javax.swing.JLabel();
                txtSoDienThoai = new javax.swing.JTextField();
                jLabel10 = new javax.swing.JLabel();
                txtEmail = new javax.swing.JTextField();
                jLabel11 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                txtDiaChi = new javax.swing.JTextArea();
                jLabel12 = new javax.swing.JLabel();
                cboChucVu = new javax.swing.JComboBox<>();
                jPanel3 = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                jSeparator1 = new javax.swing.JSeparator();
                btnThem = new javax.swing.JButton();
                btnXoa = new javax.swing.JButton();
                btnSua = new javax.swing.JButton();
                btnMoi = new javax.swing.JButton();
                btnChonAnh = new javax.swing.JButton();
                btnXoaAnh = new javax.swing.JButton();
                pnlDanhSach = new javax.swing.JPanel();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblDanhSachNhanVien = new javax.swing.JTable();
                txtTimKiem = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                cboSapXep = new javax.swing.JComboBox<>();
                btnSapXep = new javax.swing.JButton();
                background = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("Quản Lý Nhân Viên");

                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 51, 51));
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/management.png"))); // NOI18N
                jLabel1.setText("QUẢN LÝ NHÂN VIÊN");
                jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, -1, -1));

                jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel2.setForeground(new java.awt.Color(51, 51, 255));
                jLabel2.setText("Mã nhân viên");

                txtMaNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(51, 51, 255));
                jLabel5.setText("Tên nhân viên");

                txtTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(51, 51, 255));
                jLabel6.setText("CCCD");

                txtCCCD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel7.setForeground(new java.awt.Color(51, 51, 255));
                jLabel7.setText("Giới tính");

                rdoGroupGioiTinh.add(rdoNam);
                rdoNam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                rdoNam.setForeground(new java.awt.Color(255, 51, 51));
                rdoNam.setText("Nam");

                rdoGroupGioiTinh.add(rdoNu);
                rdoNu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                rdoNu.setForeground(new java.awt.Color(51, 51, 255));
                rdoNu.setText("Nữ");

                jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel8.setForeground(new java.awt.Color(51, 51, 255));
                jLabel8.setText("Ngày sinh");

                txtNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtNgaySinh.setToolTipText("dd/MM/yyyy");

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel9.setForeground(new java.awt.Color(51, 51, 255));
                jLabel9.setText("Số điện thoại");

                txtSoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel10.setForeground(new java.awt.Color(51, 51, 255));
                jLabel10.setText("Email");

                txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel11.setForeground(new java.awt.Color(51, 51, 255));
                jLabel11.setText("Địa chỉ");

                txtDiaChi.setColumns(20);
                txtDiaChi.setRows(5);
                jScrollPane1.setViewportView(txtDiaChi);

                jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel12.setForeground(new java.awt.Color(51, 51, 255));
                jLabel12.setText("Chức vụ");

                cboChucVu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

                jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel13.setForeground(new java.awt.Color(255, 0, 0));
                jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/settings.png"))); // NOI18N
                jLabel13.setText("BẢNG CHỨC NĂNG");

                jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
                jSeparator1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

                btnThem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnThem.setForeground(new java.awt.Color(255, 0, 0));
                btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addd-user.png"))); // NOI18N
                btnThem.setText("THÊM");
                btnThem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemActionPerformed(evt);
                        }
                });

                btnXoa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnXoa.setForeground(new java.awt.Color(255, 0, 0));
                btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete user.png"))); // NOI18N
                btnXoa.setText("XOÁ");
                btnXoa.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaActionPerformed(evt);
                        }
                });

                btnSua.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnSua.setForeground(new java.awt.Color(255, 0, 0));
                btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
                btnSua.setText("SỬA");
                btnSua.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSuaActionPerformed(evt);
                        }
                });

                btnMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnMoi.setForeground(new java.awt.Color(255, 0, 0));
                btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-user.png"))); // NOI18N
                btnMoi.setText("MỚI");
                btnMoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnMoiActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel13)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                        .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnThem)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua)
                                .addGap(18, 18, 18)
                                .addComponent(btnMoi)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                btnChonAnh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnChonAnh.setForeground(new java.awt.Color(255, 0, 0));
                btnChonAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/photo-camera.png"))); // NOI18N
                btnChonAnh.setText("CHỌN ẢNH");
                btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnChonAnhActionPerformed(evt);
                        }
                });

                btnXoaAnh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnXoaAnh.setForeground(new java.awt.Color(255, 0, 0));
                btnXoaAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/photo.png"))); // NOI18N
                btnXoaAnh.setText("XÓA");
                btnXoaAnh.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaAnhActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout pnlNhanVienLayout = new javax.swing.GroupLayout(pnlNhanVien);
                pnlNhanVien.setLayout(pnlNhanVienLayout);
                pnlNhanVienLayout.setHorizontalGroup(
                        pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                .addGap(53, 53, 53)
                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnXoaAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                                        .addComponent(btnChonAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                .addGap(23, 23, 23)
                                                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40)
                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel8)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11))
                                                .addGap(30, 30, 30)
                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(cboChucVu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                                .addComponent(rdoNam)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(rdoNu))
                                                        .addComponent(txtTenNhanVien)
                                                        .addComponent(txtMaNhanVien)
                                                        .addComponent(txtCCCD)
                                                        .addComponent(txtNgaySinh)
                                                        .addComponent(txtSoDienThoai)
                                                        .addComponent(txtEmail))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(47, 47, 47))
                );
                pnlNhanVienLayout.setVerticalGroup(
                        pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel2))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel6)
                                                                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel7)
                                                                        .addComponent(rdoNam)
                                                                        .addComponent(rdoNu))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel8)
                                                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel9)
                                                                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel10)
                                                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(22, 22, 22)
                                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel11)
                                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(17, 17, 17)
                                                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlNhanVienLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnChonAnh)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnXoaAnh)))
                                .addContainerGap(82, Short.MAX_VALUE))
                );

                pnlOut.addTab("Đăng Ký", pnlNhanVien);

                pnlDanhSach.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                tblDanhSachNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                tblDanhSachNhanVien.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                                "Mã NV", "Tên NV", "CCCD", "Giới Tính", "Email", "SĐT", "Chức Vụ"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachNhanVienMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(tblDanhSachNhanVien);

                txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemKeyReleased(evt);
                        }
                });

                jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                jLabel4.setForeground(new java.awt.Color(255, 51, 51));
                jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/tim.png"))); // NOI18N
                jLabel4.setText("Tìm kiếm");

                cboSapXep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sort-columns-icon.png"))); // NOI18N
                btnSapXep.setText("Sắp Xếp");
                btnSapXep.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSapXepActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
                pnlDanhSach.setLayout(pnlDanhSachLayout);
                pnlDanhSachLayout.setHorizontalGroup(
                        pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlDanhSachLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                                        .addGroup(pnlDanhSachLayout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnSapXep)))
                                .addContainerGap())
                );
                pnlDanhSachLayout.setVerticalGroup(
                        pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSapXep))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                pnlOut.addTab("Danh Sách", pnlDanhSach);

                jPanel1.add(pnlOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1030, 530));

                background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 610));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
                // TODO add your handling code here:
                try {
                        UploadImage();
                } catch (IOException ex) {
                        Logger.getLogger(NhanVienJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }

        }//GEN-LAST:event_btnChonAnhActionPerformed

        private void tblDanhSachNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachNhanVienMouseClicked
                // TODO add your handling code here:
                try {
                        if (evt.getClickCount() == 2) {
                                lblHinhAnh.setIcon(null);
                                tableRow = tblDanhSachNhanVien.getSelectedRow();
                                edit();
                                pnlOut.setSelectedIndex(0);
                        }
                } catch (Exception e) {

                }
        }//GEN-LAST:event_tblDanhSachNhanVienMouseClicked

        private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
                // TODO add your handling code here:
                loadToTable();
        }//GEN-LAST:event_txtTimKiemKeyReleased

        boolean tang = true;
        private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
                // TODO add your handling code here:
                int layChiSoCboSort = cboSapXep.getSelectedIndex();
                if (layChiSoCboSort == 0) {
                        loadToTable();
                } else {
                        loadToTableSort(layChiSoCboSort, tang);
                        if (tang == true) {
                                tang = false;
                        } else {
                                tang = true;
                        }
                }
        }//GEN-LAST:event_btnSapXepActionPerformed

    private void btnXoaAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaAnhActionPerformed
            // TODO add your handling code here:
            lblHinhAnh.setIcon(null);
            path = null;
    }//GEN-LAST:event_btnXoaAnhActionPerformed

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
                        java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                NhanVienJDialog dialog = new NhanVienJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JButton btnChonAnh;
        private javax.swing.JButton btnMoi;
        private javax.swing.JButton btnSapXep;
        private javax.swing.JButton btnSua;
        private javax.swing.JButton btnThem;
        private javax.swing.JButton btnXoa;
        private javax.swing.JButton btnXoaAnh;
        private javax.swing.JComboBox<String> cboChucVu;
        private javax.swing.JComboBox<String> cboSapXep;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JLabel lblHinhAnh;
        private javax.swing.JPanel pnlDanhSach;
        private javax.swing.JPanel pnlNhanVien;
        private javax.swing.JTabbedPane pnlOut;
        private javax.swing.ButtonGroup rdoGroupGioiTinh;
        private javax.swing.JRadioButton rdoNam;
        private javax.swing.JRadioButton rdoNu;
        private javax.swing.JTable tblDanhSachNhanVien;
        private javax.swing.JTextField txtCCCD;
        private javax.swing.JTextArea txtDiaChi;
        private javax.swing.JTextField txtEmail;
        private javax.swing.JTextField txtMaNhanVien;
        private javax.swing.JTextField txtNgaySinh;
        private javax.swing.JTextField txtSoDienThoai;
        private javax.swing.JTextField txtTenNhanVien;
        private javax.swing.JTextField txtTimKiem;
        // End of variables declaration//GEN-END:variables

}
