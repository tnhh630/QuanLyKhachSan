/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.CTKhachDoanDAO;
import DAO.KhachDoanDAO;
import DAO.KhachHangDAO;
import Entity.CT_KhachDoan;
import Entity.KhachDoan;
import Entity.KhachHang;
import Helper.Auth;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
public class KhachHangJDialog extends javax.swing.JDialog {

        /**
         * Creates new form KhachHangJDialog
         */
        public KhachHangJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                tblDanhSachKhachHang.setShowGrid(true);
                tblDanhSachKhachHang.getColumnModel().getColumn(0).setPreferredWidth(150);
                tblDanhSachKhachHang.getColumnModel().getColumn(1).setPreferredWidth(120);
                tblDanhSachKhachHang.getColumnModel().getColumn(2).setPreferredWidth(5);
                tblDanhSachKhachHang.getColumnModel().getColumn(3).setPreferredWidth(150);
                tblDanhSachKhachHang.getColumnModel().getColumn(4).setPreferredWidth(70);
                tblDanhSachKhachHang.getColumnModel().getColumn(5).setPreferredWidth(50);
                tblDanhSachKhachHang.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                init();
                this.capNhatTable();
                cboQuocTich.setSelectedItem("Vietnam");
        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTableDanhSachKhachHang();
                        loadToTableKhachDoan();
                }).start();
        }

        KhachHangDAO khdao = new KhachHangDAO();
        int tblRowKh = -1;

        KhachDoanDAO kdDAO = new KhachDoanDAO();
        int tblKhachDoanRow = -1;

        CTKhachDoanDAO ctDAO = new CTKhachDoanDAO();

        void init() {
                btnThemThanhVien.setEnabled(false);
                setLocationRelativeTo(null);
                setResizable(false);
                loadToComboBox();
                pnlKhachDoan.setVisible(false);
                loadToTableDanhSachKhachHang();
                loadToTableKhachDoan();
                initCboSapXepKhach();
        }

        void initCboSapXepKhach() {
                cboSapXepKhach.removeAllItems();
                String[] data = {
                        "Mặc Định",
                        "Mã Khách Hàng",
                        "Loại Khách Hàng"
                };
                for (String str : data) {
                        cboSapXepKhach.addItem(str);
                }
                cboSapXepKhach.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void loadToTableDanhSachKhachHang() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachKhachHang.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiemKhach.getText();
                List<KhachHang> lstKhachHang = khdao.SelectByKeyword(keyword);
                for (KhachHang kh : lstKhachHang) {
                        String gt = kh.isGioiTinh() ? "Nam" : "Nữ";
                        String loaiKhach = kh.isLoaiKhach() ? "Đoàn" : "Đơn";
                        Object[] row = {
                                kh.getMaKhachHang(),
                                kh.getTenKhachHang(),
                                gt,
                                kh.getEmail(),
                                kh.getSDT(),
                                kh.getQuocTich(),
                                loaiKhach
                        };
                        model.addRow(row);
                }
        }

        void loadToTableDanhSachKhachHangSort(int cond, boolean tangGiam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachKhachHang.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiemKhach.getText();
                List<KhachHang> lstKhachHang = khdao.SelectAll_Sort(cond, tangGiam);
                for (KhachHang kh : lstKhachHang) {
                        String gt = kh.isGioiTinh() ? "Nam" : "Nữ";
                        String loaiKhach = kh.isLoaiKhach() ? "Đoàn" : "Đơn";
                        Object[] row = {
                                kh.getMaKhachHang(),
                                kh.getTenKhachHang(),
                                gt,
                                kh.getEmail(),
                                kh.getSDT(),
                                kh.getQuocTich(),
                                loaiKhach
                        };
                        model.addRow(row);
                }
        }

        void loadToTableKhachDoan() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachDoan.getModel();
                model.setRowCount(0);
                String keyword = txtTimKiemDoan.getText();
                List<KhachDoan> lstKhachDoan = kdDAO.SelectAll_Sort(keyword);
                for (KhachDoan kd : lstKhachDoan) {
                        Object[] row = {
                                kd.getMaDoan(),
                                kd.getTenDoan(),
                                kd.getMaTruongDoan()
                        };
                        model.addRow(row);
                }
        }

        void loadToComboBox() {
                cboQuocTich.removeAllItems();
                String[] countryCodeData = Locale.getISOCountries();
                for (String code : countryCodeData) {
                        Locale obj = new Locale("", code);
                        cboQuocTich.addItem(obj.getDisplayCountry());
                }
                cboQuocTich.setRenderer(new DefaultListCellRenderer() {
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        //---------------------------------------------------------------------------------------------------------------
        void editTableDanhSachKhachHang() {
                String maKH = (String) tblDanhSachKhachHang.getValueAt(tblRowKh, 0);
                KhachHang kh = khdao.SelectByID(maKH);
                setFormKhachHang(kh);
        }

        void setFormKhachHang(KhachHang ttkh) {
                txtMaKhachHang.setText(ttkh.getMaKhachHang());
                txtTenKhachHang.setText(ttkh.getTenKhachHang());
                txtCCCD.setText(ttkh.getCCCD());
                if (ttkh.isGioiTinh()) {
                        rdoNam.setSelected(true);
                } else {
                        rdoNu.setSelected(true);
                }
                txtNgaySinh.setText(xDate.dateToString(ttkh.getNgaySinh(), "dd/MM/yyyy"));
                cboQuocTich.setSelectedItem(ttkh.getQuocTich());
                txtDiaChi.setText(ttkh.getDiaChi());
                txtSDT.setText(ttkh.getSDT());
                txtEmail.setText(ttkh.getEmail());
                if (ttkh.isLoaiKhach()) {
                        rdoKhachDoan.setSelected(true);
                } else {
                        rdoKhachDon.setSelected(true);
                }
        }

        KhachHang getFormKhachHang() {
                KhachHang ttkh = new KhachHang();
                ttkh.setMaKhachHang(txtMaKhachHang.getText());
                ttkh.setTenKhachHang(txtTenKhachHang.getText());
                ttkh.setCCCD(txtCCCD.getText());
                ttkh.setGioiTinh(rdoNam.isSelected());
                ttkh.setNgaySinh(xDate.stringToDate(txtNgaySinh.getText(), "dd/MM/yyyy"));
                ttkh.setQuocTich(cboQuocTich.getSelectedItem().toString());
                ttkh.setDiaChi(txtDiaChi.getText());
                ttkh.setEmail(txtEmail.getText());
                if (rdoKhachDon.isSelected()) {
                        ttkh.setLoaiKhach(false);
                } else {
                        ttkh.setLoaiKhach(true);
                }
                ttkh.setSDT(txtSDT.getText());
                return ttkh;
        }

        void setFormKhachDoan(KhachDoan kd) {
                txtMaDoan.setText(kd.getMaDoan());
                txtTenDoan.setText(kd.getTenDoan());
                txtMaTruongDoan.setText(kd.getMaTruongDoan());
        }

        KhachDoan getFormKhachDoan() {
                KhachDoan kd = new KhachDoan();
//                kd.setMaDoan(txtMaDoan.getText());
                kd.setMaDoan(txtMaTruongDoan.getText());
                kd.setTenDoan(txtTenDoan.getText());
                kd.setMaTruongDoan(txtMaTruongDoan.getText());
                return kd;
        }

        void clearForm() {
                txtCCCD.setFocusable(true);
                txtCCCD.setEditable(true);
                txtMaTruongDoan.setFocusable(true);
                txtMaTruongDoan.setEditable(true);
                txtMaDoan.setFocusable(true);
                txtMaDoan.setEditable(true);
                KhachHang kh = new KhachHang();
                setFormKhachHang(kh);
                KhachDoan kd = new KhachDoan();
                setFormKhachDoan(kd);
                cboQuocTich.setSelectedIndex(0);
                txtMaKhachHang.setText("");
                txtNgaySinh.setText("");
                rdoGroupGioiTinh.clearSelection();
                rdoGroupLoaiKhach.clearSelection();
        }

        //----------------------------------------------------------------------------------------------------------------------------
        boolean checkFormKhachHang() {
                // kiểm tra trống mã khách hàng
                if (txtMaKhachHang.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã khách hàng !");
                        txtMaKhachHang.requestFocus();
                        return false;
                }

                // kt trống tên khách hàng
                if (txtTenKhachHang.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên khách hàng !");
                        txtTenKhachHang.requestFocus();
                        return false;
                }

                // kiểm tra trống cccd
                if (txtCCCD.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập CCCD khách hàng !");
                        txtCCCD.requestFocus();
                        return false;
                }

                //kiểm tra định dạng cccd
                try {
                        long cccd = Long.parseLong(txtCCCD.getText());
                        if (cccd < 0) {
                                MsgBox.alert(this, "Sai định dạng CCCD!");
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "CCCD phải là số!");
                        return false;
                }

                // kiểm tra chọn giới tính
                if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
                        MsgBox.alert(this, "Chọn giới tính !");
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
                        MsgBox.alert(this, "Nhập ngày sinh theo đinh dạng dd/MM/yyyy !");
                        txtNgaySinh.requestFocus();
                        return false;
                }

                //Kiểm tra trống SĐT
                if (txtSDT.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập số điện thoại !");
                        txtSDT.requestFocus();
                        return false;
                }
                // kiểm tra định dạng SĐT
                if (!txtSDT.getText().matches("0\\d{9,10}")) {
                        MsgBox.alert(this, "Sai định dạng số điện thoại !");
                        txtSDT.requestFocus();
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

                // kiểm tra trống địa chỉ
                if (txtDiaChi.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập địa chỉ khách hàng !");
                        txtDiaChi.requestFocus();
                        return false;
                }

                // kiểm tra chọn loại khách
                if (!rdoKhachDon.isSelected() && !rdoKhachDoan.isSelected()) {
                        MsgBox.alert(this, "Chọn loại khách hàng !");
                        return false;
                }

                return true;
        }

        boolean checkFormKhachDoan() {
                // kiểm tra trống mã đoàn
                if (txtMaDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đoàn !");
                        txtMaDoan.requestFocus();
                        return false;
                }

                // kiểm tra trống tên đoàn
                if (txtTenDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên đoàn !");
                        txtTenDoan.requestFocus();
                        return false;
                }

                // kiểm tra trống mã trưởng đoàn
                if (txtMaDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã trưởng đoàn !");
                        txtMaDoan.requestFocus();
                        return false;
                }

                return true;
        }

        //----------------------------------------------------------------------------------------------------------------------------
        void insertKhachHang() {
                if (!checkFormKhachHang()) {
                        return;
                }
                KhachHang kh = getFormKhachHang();
                try {
                        khdao.insert(kh);
                        loadToTableDanhSachKhachHang();
                        clearForm();
                        MsgBox.alert(this, "Thêm thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại");
                }
        }

        void updateKhachHang() {
                if (!checkFormKhachHang()) {
                        return;
                }
                KhachHang kh = getFormKhachHang();
                try {
                        khdao.update(kh);
                        loadToTableDanhSachKhachHang();
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");
                }
        }

        void deleteKhachHang() {
                if (txtMaKhachHang.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã khách hàng cần xoá !");
                        txtMaKhachHang.requestFocus();
                        return;
                }
                try {
                        khdao.delete(txtMaKhachHang.getText());
                        loadToTableDanhSachKhachHang();
                        clearForm();
                        MsgBox.alert(this, "Xoá thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Xoá thất bại");
                }
        }

        void insertKhachDoan() {
                if (!checkFormKhachDoan()) {
                        return;
                }
                KhachDoan kd = getFormKhachDoan();
                try {
                        kdDAO.insert(kd);
                        loadToTableKhachDoan();
                        clearForm();
                        MsgBox.alert(this, "Thêm thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại");
                }
        }

        void updateKhachDoan() {
                if (!checkFormKhachDoan()) {
                        return;
                }
                KhachDoan kd = getFormKhachDoan();
                try {
                        kdDAO.update(kd);
                        loadToTableKhachDoan();
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");
                }
        }

        void deleteKhachDoan() {
                if (txtMaDoan.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đoàn cần xoá !");
                        txtMaDoan.requestFocus();
                        return;
                }
                try {
                        kdDAO.delete(txtMaDoan.getText());
                        loadToTableKhachDoan();
                        clearForm();
                        MsgBox.alert(this, "Xoá thành công");
                } catch (Exception e) {
                        MsgBox.alert(this, "Xoá thất bại");
                }
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                rdoGroupGioiTinh = new javax.swing.ButtonGroup();
                rdoGroupLoaiKhach = new javax.swing.ButtonGroup();
                jLabel12 = new javax.swing.JLabel();
                jpopupDanhSachThanhVien = new javax.swing.JPopupMenu();
                mniXoaThanhVien = new javax.swing.JMenuItem();
                jPanel3 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel1 = new javax.swing.JPanel();
                pnlThongTinKhachHang = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                txtTenKhachHang = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                txtMaKhachHang = new javax.swing.JTextField();
                txtCCCD = new javax.swing.JTextField();
                txtNgaySinh = new javax.swing.JTextField();
                cboQuocTich = new javax.swing.JComboBox<>();
                txtSDT = new javax.swing.JTextField();
                txtEmail = new javax.swing.JTextField();
                jScrollPane1 = new javax.swing.JScrollPane();
                txtDiaChi = new javax.swing.JTextArea();
                jLabel11 = new javax.swing.JLabel();
                rdoNam = new javax.swing.JRadioButton();
                rdoNu = new javax.swing.JRadioButton();
                rdoKhachDon = new javax.swing.JRadioButton();
                rdoKhachDoan = new javax.swing.JRadioButton();
                pnlChucNang = new javax.swing.JPanel();
                btnThem = new javax.swing.JButton();
                btnXoa = new javax.swing.JButton();
                btnSua = new javax.swing.JButton();
                btnMoi = new javax.swing.JButton();
                btnDanhSachKhach = new javax.swing.JButton();
                pnlKhachDoan = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                txtMaDoan = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();
                txtTenDoan = new javax.swing.JTextField();
                jLabel15 = new javax.swing.JLabel();
                txtMaTruongDoan = new javax.swing.JTextField();
                btnDanhSachKhachDoan = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane5 = new javax.swing.JScrollPane();
                tblDanhSachKhachHang = new javax.swing.JTable();
                jLabel16 = new javax.swing.JLabel();
                txtTimKiemKhach = new javax.swing.JTextField();
                cboSapXepKhach = new javax.swing.JComboBox<>();
                btnSapXepKhach = new javax.swing.JButton();
                btnDatPhong = new javax.swing.JButton();
                cboSapXepDoan = new javax.swing.JPanel();
                jScrollPane3 = new javax.swing.JScrollPane();
                tblDanhSachDoan = new javax.swing.JTable();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblDanhSachThanhVienDoan = new javax.swing.JTable();
                jLabel17 = new javax.swing.JLabel();
                txtTimKiemDoan = new javax.swing.JTextField();
                jLabel18 = new javax.swing.JLabel();
                jSeparator1 = new javax.swing.JSeparator();
                jLabel19 = new javax.swing.JLabel();
                lblMaDoan = new javax.swing.JLabel();
                btnThemThanhVien = new javax.swing.JButton();
                jLabel20 = new javax.swing.JLabel();

                jLabel12.setText("jLabel12");

                mniXoaThanhVien.setText("Tuỳ Chỉnh Thông Tin");
                mniXoaThanhVien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mniXoaThanhVienActionPerformed(evt);
                        }
                });
                jpopupDanhSachThanhVien.add(mniXoaThanhVien);

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("GodEdoc_Khách Hàng");

                jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 51, 51));
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/customer-satisfaction.png"))); // NOI18N
                jLabel1.setText("QUẢN LÝ KHÁCH HÀNG");
                jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 20, -1, -1));

                pnlThongTinKhachHang.setBackground(new java.awt.Color(250, 250, 250));
                pnlThongTinKhachHang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel2.setText("Mã Khách Hàng");

                jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel3.setText("Tên Khách Hàng");

                jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel4.setText("CCCD");

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel5.setText("Giới Tính");

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel6.setText("Ngày Sinh");

                jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel7.setText("Quốc Tịch");

                jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel8.setText("SĐT");

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel9.setText("Email");

                jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel10.setText("Địa Chỉ");

                txtMaKhachHang.setEditable(false);

                txtCCCD.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtCCCDKeyReleased(evt);
                        }
                });

                cboQuocTich.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                txtDiaChi.setColumns(20);
                txtDiaChi.setRows(5);
                jScrollPane1.setViewportView(txtDiaChi);

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel11.setText("Loại Khách");

                rdoGroupGioiTinh.add(rdoNam);
                rdoNam.setText("Nam");

                rdoGroupGioiTinh.add(rdoNu);
                rdoNu.setText("Nữ");

                rdoGroupLoaiKhach.add(rdoKhachDon);
                rdoKhachDon.setText("Khách Đơn");
                rdoKhachDon.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                rdoKhachDonActionPerformed(evt);
                        }
                });

                rdoGroupLoaiKhach.add(rdoKhachDoan);
                rdoKhachDoan.setText("Khách Đoàn");
                rdoKhachDoan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                rdoKhachDoanActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout pnlThongTinKhachHangLayout = new javax.swing.GroupLayout(pnlThongTinKhachHang);
                pnlThongTinKhachHang.setLayout(pnlThongTinKhachHangLayout);
                pnlThongTinKhachHangLayout.setHorizontalGroup(
                        pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel11)
                                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                                .addComponent(rdoKhachDon)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoKhachDoan))
                                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtNgaySinh)
                                                .addComponent(txtTenKhachHang)
                                                .addComponent(txtMaKhachHang)
                                                .addComponent(txtCCCD)
                                                .addComponent(cboQuocTich, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtSDT)
                                                .addComponent(txtEmail)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                                .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                                        .addComponent(rdoNam)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(rdoNu))))
                                .addContainerGap(32, Short.MAX_VALUE))
                );
                pnlThongTinKhachHangLayout.setVerticalGroup(
                        pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinKhachHangLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(rdoNam)
                                        .addComponent(rdoNu))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(cboQuocTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addGroup(pnlThongTinKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(rdoKhachDon)
                                                .addComponent(rdoKhachDoan)))
                                .addContainerGap(43, Short.MAX_VALUE))
                );

                pnlChucNang.setBackground(new java.awt.Color(250, 250, 250));
                pnlChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chức Năng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 51, 51))); // NOI18N

                btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/addd-user.png"))); // NOI18N
                btnThem.setText("Thêm");
                btnThem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemActionPerformed(evt);
                        }
                });

                btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete user.png"))); // NOI18N
                btnXoa.setText("Xoá");
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

                btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-user.png"))); // NOI18N
                btnMoi.setText("Mới");
                btnMoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnMoiActionPerformed(evt);
                        }
                });

                btnDanhSachKhach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
                btnDanhSachKhach.setText("Xem Danh Sách Khách Hàng");
                btnDanhSachKhach.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDanhSachKhachActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout pnlChucNangLayout = new javax.swing.GroupLayout(pnlChucNang);
                pnlChucNang.setLayout(pnlChucNangLayout);
                pnlChucNangLayout.setHorizontalGroup(
                        pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlChucNangLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChucNangLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDanhSachKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84))
                );
                pnlChucNangLayout.setVerticalGroup(
                        pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlChucNangLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnThem)
                                        .addComponent(btnMoi)
                                        .addComponent(btnXoa)
                                        .addComponent(btnSua))
                                .addGap(18, 18, 18)
                                .addComponent(btnDanhSachKhach)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pnlKhachDoan.setBackground(new java.awt.Color(250, 250, 250));
                pnlKhachDoan.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Khách Đoàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

                jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel13.setText("Mã Đoàn");

                jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel14.setText("Tên Đoàn");

                jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel15.setText("Mã Trưởng Đoàn");

                btnDanhSachKhachDoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist.png"))); // NOI18N
                btnDanhSachKhachDoan.setText("Danh Sách Thành Viên");
                btnDanhSachKhachDoan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDanhSachKhachDoanActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout pnlKhachDoanLayout = new javax.swing.GroupLayout(pnlKhachDoan);
                pnlKhachDoan.setLayout(pnlKhachDoanLayout);
                pnlKhachDoanLayout.setHorizontalGroup(
                        pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlKhachDoanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlKhachDoanLayout.createSequentialGroup()
                                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel13)
                                                        .addComponent(jLabel14))
                                                .addGap(58, 58, 58)
                                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtTenDoan)
                                                        .addComponent(txtMaDoan)))
                                        .addGroup(pnlKhachDoanLayout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtMaTruongDoan)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachDoanLayout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(btnDanhSachKhachDoan)
                                                                .addGap(81, 81, 81)))))
                                .addContainerGap())
                );
                pnlKhachDoanLayout.setVerticalGroup(
                        pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlKhachDoanLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(txtMaDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel14)
                                        .addComponent(txtTenDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlKhachDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(txtMaTruongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(btnDanhSachKhachDoan)
                                .addContainerGap())
                );

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlThongTinKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pnlChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlKhachDoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(16, Short.MAX_VALUE))
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(53, 53, 53)
                                                .addComponent(pnlKhachDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(pnlThongTinKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(85, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Thêm Khách Hàng", jPanel1);

                tblDanhSachKhachHang.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Mã KH", "Tên KH", "Giới Tính", "Email", "Số ĐT", "Quốc Tịch", "Loại Khách"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachKhachHangMouseClicked(evt);
                        }
                });
                jScrollPane5.setViewportView(tblDanhSachKhachHang);

                jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel16.setText("Tìm kiếm");

                txtTimKiemKhach.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemKhachKeyReleased(evt);
                        }
                });

                cboSapXepKhach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                cboSapXepKhach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                btnSapXepKhach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sort.png"))); // NOI18N
                btnSapXepKhach.setText("Sắp xếp");
                btnSapXepKhach.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSapXepKhachActionPerformed(evt);
                        }
                });

                btnDatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/online-booking.png"))); // NOI18N
                btnDatPhong.setText("ĐẶT PHÒNG");
                btnDatPhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDatPhongActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane5)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnDatPhong)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTimKiemKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cboSapXepKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSapXepKhach)))
                                .addContainerGap())
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel16)
                                                .addComponent(txtTimKiemKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cboSapXepKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(btnSapXepKhach))
                                        .addComponent(btnDatPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Danh Sách Khách Hàng", jPanel2);

                tblDanhSachDoan.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Đoàn", "Tên Đoàn", "Mã Trưởng Đoàn"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachDoan.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachDoanMouseClicked(evt);
                        }
                });
                jScrollPane3.setViewportView(tblDanhSachDoan);

                tblDanhSachThanhVienDoan.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Mã Khách Đoàn", "Tên Thành Viên", "Ngày Sinh", "CCCD"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachThanhVienDoan.setComponentPopupMenu(jpopupDanhSachThanhVien);
                jScrollPane4.setViewportView(tblDanhSachThanhVienDoan);

                jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel17.setText("Tìm kiếm");

                txtTimKiemDoan.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemDoanKeyReleased(evt);
                        }
                });

                jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel18.setForeground(new java.awt.Color(255, 0, 0));
                jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
                jLabel18.setText("DANH SÁCH CÁC THÀNH VIÊN");

                jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/code.png"))); // NOI18N
                jLabel19.setText("Mã đoàn :");

                lblMaDoan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                lblMaDoan.setForeground(new java.awt.Color(255, 0, 51));
                lblMaDoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblMaDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                btnThemThanhVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-user (1).png"))); // NOI18N
                btnThemThanhVien.setText("Thêm Thành Viên");
                btnThemThanhVien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemThanhVienActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout cboSapXepDoanLayout = new javax.swing.GroupLayout(cboSapXepDoan);
                cboSapXepDoan.setLayout(cboSapXepDoanLayout);
                cboSapXepDoanLayout.setHorizontalGroup(
                        cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cboSapXepDoanLayout.createSequentialGroup()
                                .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jSeparator1)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cboSapXepDoanLayout.createSequentialGroup()
                                                .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cboSapXepDoanLayout.createSequentialGroup()
                                                                .addGap(205, 205, 205)
                                                                .addComponent(jLabel17)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtTimKiemDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, cboSapXepDoanLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel18)
                                                                .addGap(73, 73, 73)
                                                                .addComponent(jLabel19)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblMaDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(btnThemThanhVien)))
                                                .addGap(0, 13, Short.MAX_VALUE))
                                        .addGroup(cboSapXepDoanLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane4)
                                                        .addComponent(jScrollPane3))))
                                .addContainerGap())
                );
                cboSapXepDoanLayout.setVerticalGroup(
                        cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cboSapXepDoanLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel17)
                                        .addComponent(txtTimKiemDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel19))
                                        .addGroup(cboSapXepDoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btnThemThanhVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addComponent(lblMaDoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                jTabbedPane1.addTab("Danh Sách Khách Đoàn", cboSapXepDoan);

                jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 870, 650));

                jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 740));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        boolean loaiKhach = false; // khách đơn
        private void rdoKhachDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhachDoanActionPerformed
                // TODO add your handling code here:
                if (rdoKhachDoan.isSelected()) {
                        pnlKhachDoan.setVisible(true);
//                        txtMaTruongDoan.setEditable(false);
                        String maKhachHang = txtMaKhachHang.getText();
                        txtMaTruongDoan.setText(maKhachHang);
                        txtMaDoan.setText(maKhachHang);
                        loaiKhach = true;
                }
        }//GEN-LAST:event_rdoKhachDoanActionPerformed

        private void rdoKhachDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhachDonActionPerformed
                // TODO add your handling code here:
                if (rdoKhachDon.isSelected()) {
                        pnlKhachDoan.setVisible(false);
                        loaiKhach = false;
                }

        }//GEN-LAST:event_rdoKhachDonActionPerformed

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
                // TODO add your handling code here:
                if (loaiKhach) {
                        if (!checkFormKhachHang() || !checkFormKhachDoan()) {
                                return;
                        }
                        KhachHang kh = getFormKhachHang();
                        KhachDoan kd = getFormKhachDoan();
                        try {
                                khdao.insert(kh);
                                kdDAO.insert(kd);
                                loadToTableDanhSachKhachHang();
                                loadToTableKhachDoan();
                                clearForm();
                                MsgBox.alert(this, "Thêm thành công");
                        } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.alert(this, "Thêm thất bại");
                        }
                } else {
                        if (!checkFormKhachHang()) {
                                return;
                        }
                        KhachHang kh = getFormKhachHang();
                        try {
                                khdao.insert(kh);
                                loadToTableDanhSachKhachHang();
                                clearForm();
                                MsgBox.alert(this, "Thêm thành công");
                        } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.alert(this, "Thêm thất bại");
                        }
                }
        }//GEN-LAST:event_btnThemActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
                // TODO add your handling code here:
                if (loaiKhach) {
                        if (!checkFormKhachHang() || !checkFormKhachDoan()) {
                                return;
                        }
                        if (txtMaDoan.getText().trim().isEmpty() || txtMaKhachHang.getText().trim().isEmpty()) {
                                MsgBox.alert(this, "Chọn mã khách hàng cần xoá !");
                                txtMaDoan.requestFocus();
                                return;
                        }
                        try {
                                khdao.delete(txtMaKhachHang.getText());
                                kdDAO.delete(txtMaDoan.getText());
                                loadToTableDanhSachKhachHang();
                                loadToTableKhachDoan();
                                clearForm();
                                MsgBox.alert(this, "Xoá thành công");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Xoá thất bại");
                        }
                } else {
                        if (txtMaKhachHang.getText().trim().isEmpty()) {
                                MsgBox.alert(this, "Nhập mã khách hàng cần xoá !");
                                txtMaKhachHang.requestFocus();
                                return;
                        }
                        try {
                                khdao.delete(txtMaKhachHang.getText());
                                loadToTableDanhSachKhachHang();
                                clearForm();
                                MsgBox.alert(this, "Xoá thành công");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Xoá thất bại");
                        }
                }
        }//GEN-LAST:event_btnXoaActionPerformed

        private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                // TODO add your handling code here:
                if (rdoKhachDoan.isSelected()) {
                        if (!checkFormKhachHang() || !checkFormKhachDoan()) {
                                return;
                        }
                        KhachHang kh = getFormKhachHang();
                        KhachDoan kd = getFormKhachDoan();
                        try {
                                khdao.update(kh);
                                kdDAO.update(kd);
                                loadToTableDanhSachKhachHang();
                                loadToTableKhachDoan();
                                clearForm();
                                MsgBox.alert(this, "Cập nhật thành công");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Cập nhật thất bại");
                        }
                } else {
                        if (!checkFormKhachHang()) {
                                return;
                        }
                        KhachHang kh = getFormKhachHang();
                        try {
                                khdao.update(kh);
                                loadToTableDanhSachKhachHang();
                                clearForm();
                                MsgBox.alert(this, "Cập nhật thành công");
                        } catch (Exception e) {
                                MsgBox.alert(this, "Cập nhật thất bại");
                        }
                }
        }//GEN-LAST:event_btnSuaActionPerformed

        private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
                // TODO add your handling code here:
                cboQuocTich.setSelectedItem("Vietnam");
                clearForm();
                txtCCCD.setEditable(true);
        }//GEN-LAST:event_btnMoiActionPerformed

        private void btnDanhSachKhachDoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachKhachDoanActionPerformed
                // TODO add your handling code here:
                jTabbedPane1.setSelectedIndex(2);
        }//GEN-LAST:event_btnDanhSachKhachDoanActionPerformed

        private void txtTimKiemKhachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKhachKeyReleased
                // TODO add your handling code here:
                loadToTableDanhSachKhachHang();
        }//GEN-LAST:event_txtTimKiemKhachKeyReleased

        private void tblDanhSachKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachKhachHangMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        lblMaDoan.setText("");
                        txtCCCD.setFocusable(false);
                        txtCCCD.setEditable(false);
                        tblRowKh = tblDanhSachKhachHang.getSelectedRow();
                        editTableDanhSachKhachHang();
                        String loaiKhach = tblDanhSachKhachHang.getValueAt(tblRowKh, 6).toString();
                        if (loaiKhach.equalsIgnoreCase("Đoàn")) {
                                pnlKhachDoan.setVisible(true);
                                KhachDoan kd = kdDAO.SelectByID_TruongDoan(txtMaKhachHang.getText());
                                txtMaDoan.setText(kd.getMaDoan());
                                txtTenDoan.setText(kd.getTenDoan());
                                txtMaTruongDoan.setText(txtMaKhachHang.getText());
                        } else {
                                pnlKhachDoan.setVisible(false);
                        }
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_tblDanhSachKhachHangMouseClicked

        boolean tang = true;
        private void btnSapXepKhachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepKhachActionPerformed
                // TODO add your handling code here:
                int layChiSoCboSort = cboSapXepKhach.getSelectedIndex();
                if (layChiSoCboSort == 0) {
                        loadToTableDanhSachKhachHang();
                } else {
                        loadToTableDanhSachKhachHangSort(layChiSoCboSort, tang);
                        if (tang == true) {
                                tang = false;
                        } else {
                                tang = true;
                        }
                }
        }//GEN-LAST:event_btnSapXepKhachActionPerformed

        private void tblDanhSachDoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachDoanMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 1) {
                        DefaultTableModel model = (DefaultTableModel) tblDanhSachThanhVienDoan.getModel();
                        model.setRowCount(0);
                        String maDoan = tblDanhSachDoan.getValueAt(tblDanhSachDoan.getSelectedRow(), 0).toString();
                        lblMaDoan.setText(maDoan);
                        List<CT_KhachDoan> lstCTKhachDoan = ctDAO.SelectAll_MaDoan(maDoan);
                        Auth.authKhachHang = khdao.SelectByID(maDoan);
                        for (CT_KhachDoan ct : lstCTKhachDoan) {
                                Object[] row = {
                                        ct.getMaKhachDoan(),
                                        ct.getHoTenThanhVien(),
                                        xDate.dateToString(ct.getNgaySinh(), "dd/MM/yyyy"),
                                        ct.getCCCD()
                                };
                                model.addRow(row);
                        }
                        btnThemThanhVien.setEnabled(true);
                } else if (evt.getClickCount() == 2) {
                        lblMaDoan.setText("");
                        txtMaTruongDoan.setFocusable(false);
                        txtMaTruongDoan.setEditable(false);
                        txtMaDoan.setFocusable(false);
                        txtMaDoan.setEditable(false);
                        pnlKhachDoan.setVisible(true);
                        String maKhachHang = tblDanhSachDoan.getValueAt(tblDanhSachDoan.getSelectedRow(), 2).toString();
                        KhachHang kh = khdao.SelectByID(maKhachHang);
                        setFormKhachHang(kh);
                        txtMaDoan.setText(tblDanhSachDoan.getValueAt(tblDanhSachDoan.getSelectedRow(), 0).toString());
                        txtTenDoan.setText(tblDanhSachDoan.getValueAt(tblDanhSachDoan.getSelectedRow(), 1).toString());
                        txtMaTruongDoan.setText(tblDanhSachDoan.getValueAt(tblDanhSachDoan.getSelectedRow(), 2).toString());
                        tblDanhSachDoan.clearSelection();
                        DefaultTableModel model = (DefaultTableModel) tblDanhSachThanhVienDoan.getModel();
                        model.setRowCount(0);
                        btnThemThanhVien.setEnabled(false);
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_tblDanhSachDoanMouseClicked

        private void btnDanhSachKhachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachKhachActionPerformed
                // TODO add your handling code here:
                jTabbedPane1.setSelectedIndex(1);
        }//GEN-LAST:event_btnDanhSachKhachActionPerformed

        private void txtTimKiemDoanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemDoanKeyReleased
                // TODO add your handling code here:
                loadToTableKhachDoan();
        }//GEN-LAST:event_txtTimKiemDoanKeyReleased
        CTKhachDoanDAO ctkdDAO = new CTKhachDoanDAO();
        private void mniXoaThanhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniXoaThanhVienActionPerformed
                // TODO add your handling code here:
                int row = tblDanhSachThanhVienDoan.getSelectedRow();
                String maKhachDoan = tblDanhSachThanhVienDoan.getValueAt(row, 0).toString();
                Helper.Auth.ctKD = ctkdDAO.SelectByID(maKhachDoan);
                CTKhachDoanJDIalog dialog = new CTKhachDoanJDIalog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
        }//GEN-LAST:event_mniXoaThanhVienActionPerformed

        private void txtCCCDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCCCDKeyReleased

                DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyy");
                LocalDate localDate = LocalDate.now();
                if (txtCCCD.getText().trim().isEmpty()) {
                        txtMaKhachHang.setText("");
                } else {
                        txtMaKhachHang.setText(txtCCCD.getText() + format.format(localDate));
                }
        }//GEN-LAST:event_txtCCCDKeyReleased

        private void btnDatPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatPhongActionPerformed
                // TODO add your handling code here:
                DatPhongJDialog dialog = new DatPhongJDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
        }//GEN-LAST:event_btnDatPhongActionPerformed

        private void btnThemThanhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThanhVienActionPerformed
                // TODO add your handling code here:
                CTKhachDoanJDIalog dialog = new CTKhachDoanJDIalog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
                tblDanhSachDoan.clearSelection();
                DefaultTableModel model = (DefaultTableModel) tblDanhSachThanhVienDoan.getModel();
                model.setRowCount(0);
        }//GEN-LAST:event_btnThemThanhVienActionPerformed

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
                        java.util.logging.Logger.getLogger(KhachHangJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(KhachHangJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(KhachHangJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(KhachHangJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                KhachHangJDialog dialog = new KhachHangJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JButton btnDanhSachKhach;
        private javax.swing.JButton btnDanhSachKhachDoan;
        private javax.swing.JButton btnDatPhong;
        private javax.swing.JButton btnMoi;
        private javax.swing.JButton btnSapXepKhach;
        private javax.swing.JButton btnSua;
        private javax.swing.JButton btnThem;
        private javax.swing.JButton btnThemThanhVien;
        private javax.swing.JButton btnXoa;
        private javax.swing.JComboBox<String> cboQuocTich;
        private javax.swing.JPanel cboSapXepDoan;
        private javax.swing.JComboBox<String> cboSapXepKhach;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
        private javax.swing.JLabel jLabel17;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel20;
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
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JPopupMenu jpopupDanhSachThanhVien;
        private javax.swing.JLabel lblMaDoan;
        private javax.swing.JMenuItem mniXoaThanhVien;
        private javax.swing.JPanel pnlChucNang;
        private javax.swing.JPanel pnlKhachDoan;
        private javax.swing.JPanel pnlThongTinKhachHang;
        private javax.swing.ButtonGroup rdoGroupGioiTinh;
        private javax.swing.ButtonGroup rdoGroupLoaiKhach;
        private javax.swing.JRadioButton rdoKhachDoan;
        private javax.swing.JRadioButton rdoKhachDon;
        private javax.swing.JRadioButton rdoNam;
        private javax.swing.JRadioButton rdoNu;
        private javax.swing.JTable tblDanhSachDoan;
        private javax.swing.JTable tblDanhSachKhachHang;
        private javax.swing.JTable tblDanhSachThanhVienDoan;
        private javax.swing.JTextField txtCCCD;
        private javax.swing.JTextArea txtDiaChi;
        private javax.swing.JTextField txtEmail;
        private javax.swing.JTextField txtMaDoan;
        private javax.swing.JTextField txtMaKhachHang;
        private javax.swing.JTextField txtMaTruongDoan;
        private javax.swing.JTextField txtNgaySinh;
        private javax.swing.JTextField txtSDT;
        private javax.swing.JTextField txtTenDoan;
        private javax.swing.JTextField txtTenKhachHang;
        private javax.swing.JTextField txtTimKiemDoan;
        private javax.swing.JTextField txtTimKiemKhach;
        // End of variables declaration//GEN-END:variables
}
