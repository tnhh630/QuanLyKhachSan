/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.NhanVienDAO;
import DAO.TaiKhoanDAO;
import DAO.ThongKeDAO;
import Entity.ExcelExporter;
import Entity.NhanVien;
import Entity.TaiKhoan;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class ThongKeJDialog extends javax.swing.JDialog {

        int load;

        /**
         * Creates new form ThongKeJDialog
         */
        public ThongKeJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                setLocationRelativeTo(null);
                setResizable(false);

                initCboSapXep();
                fillTableNhanVien();

                fillComboNamDoanhThuThuePhong();
                fillTableDoanhThuThuePhong();

                fillComboNamDoanhThuDichVu();
                fillTableDoanhThuDichVu();

                fillComboKhachHang();
                load = Integer.parseInt(cboNamKhachHang.getSelectedItem().toString());
                fillTableKhachHang(load);

                fillComboDoThatLac();
                load = Integer.parseInt(cboNamDoThatLac.getSelectedItem().toString());
                fillTableDoThatLac(load);

                initJList();
                cboThangDoanhThuDichVu.setSelectedIndex(12);
                cboThangDoanhThuThuePhong.setSelectedIndex(12);

                this.capNhatTable();

        }

        void capNhatTable() {
                new Timer(500, (ActionEvent e) -> {
                        int layChiSoCboSort = cboSapXep.getSelectedIndex();
                        if (layChiSoCboSort == 0) {
                                fillTableNhanVien();
                        } else {
                                loadToTableSort(layChiSoCboSort, tang);
                        }
                        fillTableDoanhThuThuePhong();
                        fillTableDoanhThuDichVu();
                        fillTableDoThatLac(Integer.parseInt(cboNamDoThatLac.getSelectedItem().toString()));
                        fillTableKhachHang(Integer.parseInt(cboNamKhachHang.getSelectedItem().toString()));
                }).start();
        }

        void initJList() {
                DefaultListCellRenderer r = (DefaultListCellRenderer) lstSanPham.getCellRenderer();
                r.setHorizontalAlignment(SwingConstants.CENTER);
                int nam = Integer.parseInt(cboNamDoanhThuDichVu.getSelectedItem().toString());
                loadToJList(nam);
        }

        void loadToJList(int nam) {
                List<Object[]> lstTop3 = dao.GetTop3(nam);
                Iterator itr = lstTop3.iterator();
                String[] tensp = new String[lstTop3.size()];
                int cs = 0;
                while (itr.hasNext()) {
                        Object[] obj = (Object[]) itr.next();
                        String ten = String.valueOf(obj[0]);
                        tensp[cs] = ten;
                        cs++;
                }
                lstSanPham.setListData(tensp);
        }

        NhanVienDAO nvDao = new NhanVienDAO();
        ThongKeDAO dao = new ThongKeDAO();
        TaiKhoanDAO tkdao = new TaiKhoanDAO();

        // Bảng Doanh Thu Thuê Phòng
        void fillComboNamDoanhThuThuePhong() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboNamDoanhThuThuePhong.getModel();
                model.removeAllElements();
                List<Integer> list = dao.getNamCoDoanhThu();
                for (Integer nam : list) {
                        model.addElement(nam);
                }
                cboNamDoanhThuThuePhong.setSelectedIndex(0);
        }

        void fillTableDoanhThuThuePhong() {
                DefaultTableModel model = (DefaultTableModel) tblDoanhThuThuePhong.getModel();
                model.setRowCount(0);
                int thang = 0;
                if (cboThangDoanhThuThuePhong.getSelectedIndex() != 12) {
                        thang = Integer.parseInt(cboThangDoanhThuThuePhong.getSelectedItem().toString());
                }
                int nam = Integer.parseInt(cboNamDoanhThuThuePhong.getSelectedItem().toString());
                List<Object[]> lstDoanhThu = dao.GetDoanhThuThuePhong_Thang(thang, nam);
                txtSlHoaDon.setText(String.valueOf(lstDoanhThu.size()));
                for (Object[] x : lstDoanhThu) {
                        model.addRow(x);
                }
                double TongTien = 0;
                for (int i = 0; i < lstDoanhThu.size(); i++) {
                        TongTien += Double.parseDouble(tblDoanhThuThuePhong.getValueAt(i, 4).toString());
                }
                txtThuNhap.setText(String.valueOf(TongTien));
        }

        void fillTableDoanhThuThuePhong(int nam) {
                DefaultTableModel model = (DefaultTableModel) tblDoanhThuThuePhong.getModel();
                model.setRowCount(0);
                List<Object[]> lstDoanhThu = dao.getDoanhThuThuePhong(nam);
                txtSlHoaDon.setText(String.valueOf(lstDoanhThu.size()));
                for (Object[] x : lstDoanhThu) {
                        model.addRow(x);
                }
                double TongTien = 0;
                for (int i = 0; i < lstDoanhThu.size(); i++) {
                        TongTien += Double.parseDouble(tblDoanhThuThuePhong.getValueAt(i, 4).toString());
                }
                txtThuNhap.setText(String.valueOf(TongTien));
        }

        // Bảng Doanh Thu Dịch Vụ
        void fillComboNamDoanhThuDichVu() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboNamDoanhThuDichVu.getModel();
                model.removeAllElements();
                List<Integer> list = dao.getNamCoDoanhThu();
                for (Integer nam : list) {
                        model.addElement(nam);
                }
                cboNamDoanhThuDichVu.setSelectedIndex(0);
        }

        void fillTableDoanhThuDichVu() {
                DefaultTableModel model = (DefaultTableModel) tblDoanhThuDichVu.getModel();
                model.setRowCount(0);
                int thang = 0;
                if (cboThangDoanhThuDichVu.getSelectedIndex() != 12) {
                        thang = Integer.parseInt(cboThangDoanhThuDichVu.getSelectedItem().toString());
                }
                int nam = Integer.parseInt(cboNamDoanhThuDichVu.getSelectedItem().toString());
                List<Object[]> lstDoanhThu = dao.getDoanhThuDichVu(thang, nam);
                for (Object[] x : lstDoanhThu) {
                        model.addRow(x);
                }
        }

        // Bảng Khách Hàng
        void fillComboKhachHang() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboNamKhachHang.getModel();
                model.removeAllElements();
                List<Integer> list = dao.getNamCoDoanhThu();
                for (Integer nam : list) {
                        model.addElement(nam);
                }
                cboNamKhachHang.setSelectedIndex(0);
        }

        void fillTableKhachHang(int nam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachKhachHang.getModel();
                model.setRowCount(0);
                List<Object[]> lstKhachHang = dao.GetDanhSachKhachHang(nam);
                txtSoLuongKH.setText(String.valueOf(lstKhachHang.size()));
                for (Object[] x : lstKhachHang) {
                        model.addRow(x);

                }
        }

        // Bảng Đồ Thất lạc
        void fillComboDoThatLac() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboNamDoThatLac.getModel();
                model.removeAllElements();
                List<Integer> list = dao.GetNamCoDoThatLac();
                for (Integer nam : list) {
                        model.addElement(nam);
                }
                cboNamDoThatLac.setSelectedIndex(0);
        }

        void fillTableDoThatLac(int nam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachDoThatLac.getModel();
                model.setRowCount(0);
                List<Object[]> lstDoThatLac = dao.GetDanhSachDoThatLac(nam);
                txtSlDoThatLac.setText(String.valueOf(lstDoThatLac.size()));
                int datra = 0;
                int dalienhe = 0;
                int chuatra = 0;

                for (Object[] row : lstDoThatLac) {
                        String tinhtrang = "";
                        {
                                if (Integer.parseInt(row[4].toString()) == 0) {
                                        tinhtrang = "Chưa Trả";
                                        chuatra++;
                                } else if (Integer.parseInt(row[4].toString()) == 1) {
                                        tinhtrang = "Đã Trả";
                                        datra++;
                                } else if (Integer.parseInt(row[4].toString()) == 2) {
                                        tinhtrang = "Đã Liên Hệ";
                                        dalienhe++;
                                }
                        }

                        model.addRow(new Object[]{row[0], row[1], row[2], row[3], tinhtrang});

                }
                txtSLDaTra.setText(String.valueOf(datra));
                txtSLDaLienHe.setText(String.valueOf(dalienhe));
                txtSLChuaTra.setText(String.valueOf(chuatra));
        }

        void fillTableDoThatLac_xx(int nam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachDoThatLac.getModel();
                model.setRowCount(0);
                String keyword = txtTimDoThatLac.getText();
                List<Object[]> lstDoThatLac = dao.SelectByKeyWord_DoThatLac(keyword, nam);
                txtSlDoThatLac.setText(String.valueOf(lstDoThatLac.size()));
                int datra = 0;
                int dalienhe = 0;
                int chuatra = 0;

                for (Object[] row : lstDoThatLac) {
                        String tinhtrang = "";
                        {
                                if (Integer.parseInt(row[4].toString()) == 0) {
                                        tinhtrang = "Chưa Trả";
                                        chuatra++;
                                } else if (Integer.parseInt(row[4].toString()) == 1) {
                                        tinhtrang = "Đã Trả";
                                        datra++;
                                } else if (Integer.parseInt(row[4].toString()) == 2) {
                                        tinhtrang = "Đã Liên Hệ";
                                        dalienhe++;
                                }
                        }

                        model.addRow(new Object[]{row[0], row[1], row[2], row[3], tinhtrang});

                }
                txtSLDaTra.setText(String.valueOf(datra));
                txtSLDaLienHe.setText(String.valueOf(dalienhe));
                txtSLChuaTra.setText(String.valueOf(chuatra));
        }

        //Bảng Nhân Viên
        void fillTableNhanVien() {
                List<NhanVien> lstNhanVien = nvDao.SelectAll();
                txtSoLuongNv.setText(String.valueOf(lstNhanVien.size()));
                List<TaiKhoan> lstTaiKhoan = tkdao.SelectAll();
                txtCoTaiKhoan.setText(String.valueOf(lstTaiKhoan.size()));
                int chuacotk = lstNhanVien.size() - lstTaiKhoan.size();
                txtChuaCoTK.setText(String.valueOf(chuacotk));
                DefaultTableModel model = (DefaultTableModel) tblDanhSachNV.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (txtTimKiemNV.getText().trim().isEmpty()) {
                        keyword = "";
                } else {
                        keyword = txtTimKiemNV.getText();
                }
                List<Object[]> list = dao.SelectByKeyWord_NhanVien(keyword);
                for (Object[] row : list) {
                        String chucvu = "";
                        if (Integer.parseInt(row[2].toString()) == 1) {
                                chucvu = "Lễ tân";
                        } else if (Integer.parseInt(row[2].toString()) == 2) {
                                chucvu = "Quản lý khách sạn";
                        } else if (Integer.parseInt(row[2].toString()) == 3) {
                                chucvu = "Nhân Sự";
                        } else if (Integer.parseInt(row[2].toString()) == 4) {
                                chucvu = "Giám đốc";
                        }
                        model.addRow(new Object[]{row[0], row[1], chucvu, row[3], row[4]});
                }
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

        // sort Nhân Viên
        void loadToTableSort(int cond, boolean tangGiam) {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachNV.getModel();
                model.setRowCount(0);
                List<Object[]> list = dao.SelectAll_ThongKeSortNhanVien(cond, tangGiam);
                for (Object[] row : list) {
                        String chucvu = "";
                        if (Integer.parseInt(row[2].toString()) == 1) {
                                chucvu = "Lễ tân";
                        } else if (Integer.parseInt(row[2].toString()) == 2) {
                                chucvu = "Quản lý khách sạn";
                        } else if (Integer.parseInt(row[2].toString()) == 3) {
                                chucvu = "Nhân Sự";
                        } else if (Integer.parseInt(row[2].toString()) == 4) {
                                chucvu = "Giám đốc";
                        }
                        model.addRow(new Object[]{row[0], row[1], chucvu, row[3], row[4]});
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
                txtTimKiemNV = new javax.swing.JTextField();
                cboSapXep = new javax.swing.JComboBox<>();
                btnSapXep = new javax.swing.JButton();
                jScrollPane1 = new javax.swing.JScrollPane();
                tblDanhSachNV = new javax.swing.JTable();
                jLabel2 = new javax.swing.JLabel();
                txtSoLuongNv = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                txtCoTaiKhoan = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                txtChuaCoTK = new javax.swing.JTextField();
                btnExcelNhanVien = new javax.swing.JButton();
                jLabel18 = new javax.swing.JLabel();
                jPanel2 = new javax.swing.JPanel();
                cboNamKhachHang = new javax.swing.JComboBox<>();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                txtSoLuongKH = new javax.swing.JTextField();
                jScrollPane3 = new javax.swing.JScrollPane();
                tblDanhSachKhachHang = new javax.swing.JTable();
                btnExcelKhachHang = new javax.swing.JButton();
                jPanel3 = new javax.swing.JPanel();
                jLabel5 = new javax.swing.JLabel();
                cboNamDoanhThuThuePhong = new javax.swing.JComboBox<>();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblDoanhThuThuePhong = new javax.swing.JTable();
                jLabel12 = new javax.swing.JLabel();
                txtSlHoaDon = new javax.swing.JTextField();
                btnExcelDoanhThu = new javax.swing.JButton();
                jLabel13 = new javax.swing.JLabel();
                txtThuNhap = new javax.swing.JTextField();
                btnBarChart = new javax.swing.JButton();
                jLabel20 = new javax.swing.JLabel();
                cboThangDoanhThuThuePhong = new javax.swing.JComboBox<>();
                jPanel4 = new javax.swing.JPanel();
                jLabel9 = new javax.swing.JLabel();
                cboNamDoanhThuDichVu = new javax.swing.JComboBox<>();
                jScrollPane5 = new javax.swing.JScrollPane();
                tblDoanhThuDichVu = new javax.swing.JTable();
                btnDoanhThuDichVu = new javax.swing.JButton();
                jScrollPane6 = new javax.swing.JScrollPane();
                lstSanPham = new javax.swing.JList<>();
                jLabel19 = new javax.swing.JLabel();
                jLabel21 = new javax.swing.JLabel();
                cboThangDoanhThuDichVu = new javax.swing.JComboBox<>();
                btnCharDoanhThuDichVu = new javax.swing.JButton();
                jPanel6 = new javax.swing.JPanel();
                cboNamDoThatLac = new javax.swing.JComboBox<>();
                jLabel10 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblDanhSachDoThatLac = new javax.swing.JTable();
                btnExcelDoThatLac = new javax.swing.JButton();
                jLabel6 = new javax.swing.JLabel();
                txtTimDoThatLac = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();
                txtSLChuaTra = new javax.swing.JTextField();
                jLabel15 = new javax.swing.JLabel();
                txtSlDoThatLac = new javax.swing.JTextField();
                jLabel16 = new javax.swing.JLabel();
                txtSLDaTra = new javax.swing.JTextField();
                jLabel17 = new javax.swing.JLabel();
                txtSLDaLienHe = new javax.swing.JTextField();
                btnPieChart = new javax.swing.JButton();
                lblbackground = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("GodEdoc_Thống Kê");
                setResizable(false);

                jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 51, 51));
                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistical.png"))); // NOI18N
                jLabel1.setText("THỐNG KÊ");
                jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, 1040, 40));

                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                txtTimKiemNV.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemNVKeyReleased(evt);
                        }
                });
                jPanel1.add(txtTimKiemNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 61, 451, -1));

                cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                jPanel1.add(cboSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 61, 138, -1));

                btnSapXep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sort-columns-icon.png"))); // NOI18N
                btnSapXep.setText("Sắp Xếp");
                btnSapXep.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSapXepActionPerformed(evt);
                        }
                });
                jPanel1.add(btnSapXep, new org.netbeans.lib.awtextra.AbsoluteConstraints(851, 55, -1, -1));

                tblDanhSachNV.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Nhân Viên", "Tên Nhân Viên", "Chức Vụ", "Mã Tài Khoản", "Số Điện Thoại"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane1.setViewportView(tblDanhSachNV);

                jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 107, 995, 420));

                jLabel2.setText("Số Lượng Nhân Viên");
                jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 20, -1, -1));

                txtSoLuongNv.setEditable(false);
                txtSoLuongNv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel1.add(txtSoLuongNv, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 80, 30));

                jLabel3.setText("Đã Có Tài Khoản Đăng Nhập");
                jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(331, 20, 177, -1));

                txtCoTaiKhoan.setEditable(false);
                txtCoTaiKhoan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel1.add(txtCoTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 111, 30));

                jLabel4.setText("Chưa Có Tài Khoản");
                jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 20, -1, -1));

                txtChuaCoTK.setEditable(false);
                txtChuaCoTK.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel1.add(txtChuaCoTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 70, 30));

                btnExcelNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/xls.png"))); // NOI18N
                btnExcelNhanVien.setText("Xuất Excel");
                btnExcelNhanVien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnExcelNhanVienActionPerformed(evt);
                        }
                });
                jPanel1.add(btnExcelNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 540, -1, -1));

                jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistics.png"))); // NOI18N
                jLabel18.setText("Tìm");
                jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, -1, -1));

                jTabbedPane1.addTab("Nhân Viên", jPanel1);

                cboNamKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboNamKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                cboNamKhachHangMouseClicked(evt);
                        }
                });

                jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel7.setText("Năm");

                jLabel8.setText("Số Lượng Khách Hàng");

                txtSoLuongKH.setEditable(false);
                txtSoLuongKH.setHorizontalAlignment(javax.swing.JTextField.CENTER);

                tblDanhSachKhachHang.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Mã KH", "Họ Tên KH", "Email", "Số ĐT"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane3.setViewportView(tblDanhSachKhachHang);

                btnExcelKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/xls.png"))); // NOI18N
                btnExcelKhachHang.setText("Xuất Excel");
                btnExcelKhachHang.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnExcelKhachHangActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(205, 205, 205)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(cboNamKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtSoLuongKH, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnExcelKhachHang)))
                                .addContainerGap())
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cboNamKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8)
                                        .addComponent(txtSoLuongKH, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnExcelKhachHang)
                                .addContainerGap(16, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Khách Hàng", jPanel2);

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel5.setText("Năm");

                cboNamDoanhThuThuePhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboNamDoanhThuThuePhongActionPerformed(evt);
                        }
                });

                tblDoanhThuThuePhong.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Hoá Đơn", "Tên Khách Hàng", "Ngày Thanh Toán", "Số Ngày Thuê", "Tổng Tiền"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane2.setViewportView(tblDoanhThuThuePhong);

                jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel12.setText("Tổng Số Lượng Hoá Đơn ");

                txtSlHoaDon.setEditable(false);
                txtSlHoaDon.setHorizontalAlignment(javax.swing.JTextField.CENTER);

                btnExcelDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/xls.png"))); // NOI18N
                btnExcelDoanhThu.setText("Xuất Excel");
                btnExcelDoanhThu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnExcelDoanhThuActionPerformed(evt);
                        }
                });

                jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel13.setText("Tổng Doanh Thu Thuê Phòng");

                txtThuNhap.setEditable(false);
                txtThuNhap.setHorizontalAlignment(javax.swing.JTextField.CENTER);

                btnBarChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/diagram.png"))); // NOI18N
                btnBarChart.setText("Bar Chart");
                btnBarChart.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnBarChartActionPerformed(evt);
                        }
                });

                jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel20.setText("Tháng");

                cboThangDoanhThuThuePhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "Cả Năm" }));
                cboThangDoanhThuThuePhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboThangDoanhThuThuePhongActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(btnBarChart)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnExcelDoanhThu))
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel20))
                                                .addGap(26, 26, 26)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(cboNamDoanhThuThuePhong, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cboThangDoanhThuThuePhong, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(31, 31, 31)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtThuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                                        .addComponent(txtSlHoaDon))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cboNamDoanhThuThuePhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel13)
                                        .addComponent(txtThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(cboThangDoanhThuThuePhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12)
                                        .addComponent(txtSlHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBarChart)
                                        .addComponent(btnExcelDoanhThu))
                                .addGap(21, 21, 21))
                );

                jTabbedPane1.addTab("Doanh Thu Thuê Phòng", jPanel3);

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel9.setText("Năm");

                cboNamDoanhThuDichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboNamDoanhThuDichVuActionPerformed(evt);
                        }
                });

                tblDoanhThuDichVu.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Mã CTSDDV", "Tên Khách Hàng", "Tên Dịch Vụ", "Ngày Đặt", "Số Lượng", "Đơn Giá", "Thành Tiền"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane5.setViewportView(tblDoanhThuDichVu);

                btnDoanhThuDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/xls.png"))); // NOI18N
                btnDoanhThuDichVu.setText("Xuất Excel");
                btnDoanhThuDichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDoanhThuDichVuActionPerformed(evt);
                        }
                });

                lstSanPham.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = { "Item 1", "Item 2", "Item 3" };
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                });
                lstSanPham.setFocusable(false);
                lstSanPham.setVisibleRowCount(3);
                jScrollPane6.setViewportView(lstSanPham);

                jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel19.setForeground(new java.awt.Color(255, 51, 51));
                jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/TOP1.jpg"))); // NOI18N
                jLabel19.setText("Dịch vụ được đặt nhiều nhất trong năm");

                jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel21.setText("Tháng");

                cboThangDoanhThuDichVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "Cả Năm" }));
                cboThangDoanhThuDichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cboThangDoanhThuDichVuActionPerformed(evt);
                        }
                });

                btnCharDoanhThuDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/diagram.png"))); // NOI18N
                btnCharDoanhThuDichVu.setText("Bar Chart");
                btnCharDoanhThuDichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnCharDoanhThuDichVuActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(cboNamDoanhThuDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                                .addComponent(cboThangDoanhThuDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(253, 253, 253)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(154, 154, 154))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel9)
                                .addGap(158, 158, 158)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19)
                                .addGap(90, 90, 90))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane5)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnCharDoanhThuDichVu)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnDoanhThuDichVu)))
                                .addContainerGap())
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel21)))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(cboThangDoanhThuDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(cboNamDoanhThuDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnDoanhThuDichVu)
                                        .addComponent(btnCharDoanhThuDichVu))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Doanh Thu Dịch Vụ", jPanel4);

                jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                cboNamDoThatLac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cboNamDoThatLac.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                cboNamDoThatLacMouseClicked(evt);
                        }
                });
                jPanel6.add(cboNamDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 60, 130, 30));
                jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 106, -1, -1));

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calendar.png"))); // NOI18N
                jLabel11.setText("Năm");
                jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 70, 40));

                tblDanhSachDoThatLac.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Đồ Thất Lạc", "Tên Đồ Thất Lạc", "Thời Gian Tìm Thấy", "Nhân Viên Tìm Thấy", "Tình Trạng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane4.setViewportView(tblDanhSachDoThatLac);

                jPanel6.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 112, 995, 400));

                btnExcelDoThatLac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/xls.png"))); // NOI18N
                btnExcelDoThatLac.setText("Xuất Excel");
                btnExcelDoThatLac.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnExcelDoThatLacActionPerformed(evt);
                        }
                });
                jPanel6.add(btnExcelDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, -1, -1));

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistics.png"))); // NOI18N
                jLabel6.setText("Tìm Kiếm");
                jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

                txtTimDoThatLac.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimDoThatLacKeyReleased(evt);
                        }
                });
                jPanel6.add(txtTimDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 490, -1));

                jLabel14.setText("Số Lượng Đồ Thất Lạc");
                jPanel6.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

                txtSLChuaTra.setEditable(false);
                txtSLChuaTra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel6.add(txtSLChuaTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 50, 30));

                jLabel15.setText("Số Lượng Đã Trả");
                jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

                txtSlDoThatLac.setEditable(false);
                txtSlDoThatLac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel6.add(txtSlDoThatLac, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 50, 30));

                jLabel16.setText("Số Lượng Đồ Đã Liên Hệ");
                jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, -1));

                txtSLDaTra.setEditable(false);
                txtSLDaTra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel6.add(txtSLDaTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 50, 30));

                jLabel17.setText("Số Lượng Đồ Chưa Trả");
                jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, -1, -1));

                txtSLDaLienHe.setEditable(false);
                txtSLDaLienHe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                jPanel6.add(txtSLDaLienHe, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 50, 30));

                btnPieChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pie-chart.png"))); // NOI18N
                btnPieChart.setText("PieChart");
                btnPieChart.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnPieChartActionPerformed(evt);
                        }
                });
                jPanel6.add(btnPieChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 530, -1, -1));

                jTabbedPane1.addTab("Đồ Thất Lạc", jPanel6);

                jPanel5.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1020, 620));

                lblbackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel5.add(lblbackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1044, 680));

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
        boolean tang = true;
        private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
                // TODO add your handling code here:
                int layChiSoCboSort = cboSapXep.getSelectedIndex();
                if (layChiSoCboSort == 0) {
                        fillTableNhanVien();
                } else {
                        loadToTableSort(layChiSoCboSort, tang);
                        if (tang == true) {
                                tang = false;
                        } else {
                                tang = true;
                        }
                }
        }//GEN-LAST:event_btnSapXepActionPerformed

        private void txtTimKiemNVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemNVKeyReleased
                // TODO add your handling code here:
                fillTableNhanVien();
        }//GEN-LAST:event_txtTimKiemNVKeyReleased

        private void cboNamKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNamKhachHangMouseClicked
                // TODO add your handling code here:
                cboNamKhachHang.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int nam = Integer.parseInt(cboNamKhachHang.getSelectedItem().toString());
                                fillTableKhachHang(nam);
                        }
                });


        }//GEN-LAST:event_cboNamKhachHangMouseClicked

        private void cboNamDoThatLacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboNamDoThatLacMouseClicked
                // TODO add your handling code here:
                cboNamDoThatLac.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                int nam = Integer.parseInt(cboNamDoThatLac.getSelectedItem().toString());
                                fillTableDoThatLac(nam);
                        }
                });
        }//GEN-LAST:event_cboNamDoThatLacMouseClicked

        private void txtTimDoThatLacKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimDoThatLacKeyReleased
                // TODO add your handling code here:
                int nam = Integer.parseInt(cboNamDoThatLac.getSelectedItem().toString());
                fillTableDoThatLac_xx(nam);
        }//GEN-LAST:event_txtTimDoThatLacKeyReleased

    private void btnBarChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarChartActionPerformed
            // TODO add your handling code here:
            ChartJDialog dialog = new ChartJDialog(new javax.swing.JFrame(), true);
            dialog.setVisible(true);
    }//GEN-LAST:event_btnBarChartActionPerformed

    private void btnPieChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPieChartActionPerformed
            // TODO add your handling code here:
            PieChart dialog = new PieChart(new javax.swing.JFrame(), true);
            dialog.setVisible(true);
    }//GEN-LAST:event_btnPieChartActionPerformed

        private void btnExcelNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelNhanVienActionPerformed
                // TODO add your handling code here:
                try {
                        JFileChooser fileChooser = new JFileChooser();
                        int retval = fileChooser.showSaveDialog(btnExcelNhanVien);

                        if (retval == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                if (file != null) {
                                        if (!file.getName().toLowerCase().endsWith(".xls")) {
                                                file = new File(file.getParentFile(), file.getName() + ".xls");
                                        }
                                        try {
                                                ExcelExporter exp = new ExcelExporter();

                                                exp.exportTable(tblDanhSachNV, file, "Danh sách nhân viên", 5);

                                                Desktop.getDesktop().open(file);
                                        } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();

                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                System.out.println("not found");
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }//GEN-LAST:event_btnExcelNhanVienActionPerformed

        private void btnExcelKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelKhachHangActionPerformed
                // TODO add your handling code here:
                try {
                        JFileChooser fileChooser = new JFileChooser();
                        int retval = fileChooser.showSaveDialog(btnExcelKhachHang);

                        if (retval == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                if (file != null) {
                                        if (!file.getName().toLowerCase().endsWith(".xls")) {
                                                file = new File(file.getParentFile(), file.getName() + ".xls");
                                        }
                                        try {
                                                ExcelExporter exp = new ExcelExporter();

                                                exp.exportTable(tblDanhSachKhachHang, file, "Danh sách khách hàng", 4);

                                                Desktop.getDesktop().open(file);
                                                // MsgBox.alert(this, "Xut du lieu thanh cong!");
                                        } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();

                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                System.out.println("not found");
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                }

        }//GEN-LAST:event_btnExcelKhachHangActionPerformed

        private void btnExcelDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelDoanhThuActionPerformed
                // TODO add your handling code here:
                try {
                        JFileChooser fileChooser = new JFileChooser();
                        int retval = fileChooser.showSaveDialog(btnExcelDoanhThu);

                        if (retval == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                if (file != null) {
                                        if (!file.getName().toLowerCase().endsWith(".xls")) {
                                                file = new File(file.getParentFile(), file.getName() + ".xls");
                                        }
                                        try {
                                                ExcelExporter exp = new ExcelExporter();
                                                exp.exportTable(tblDoanhThuThuePhong, file, "Danh sách doanh thu thuê phòng", 5);

                                                Desktop.getDesktop().open(file);
                                                // MsgBox.alert(this, "Xut du lieu thanh cong!");
                                        } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();

                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                System.out.println("not found");
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                }
        }//GEN-LAST:event_btnExcelDoanhThuActionPerformed

        private void btnExcelDoThatLacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelDoThatLacActionPerformed
                // TODO add your handling code here:
                try {
                        JFileChooser fileChooser = new JFileChooser();
                        int retval = fileChooser.showSaveDialog(btnExcelDoThatLac);

                        if (retval == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                if (file != null) {
                                        if (!file.getName().toLowerCase().endsWith(".xls")) {
                                                file = new File(file.getParentFile(), file.getName() + ".xls");
                                        }
                                        try {
                                                ExcelExporter exp = new ExcelExporter();
                                                exp.exportTable(tblDanhSachDoThatLac, file, "Danh sách đồ thất lạc", 5);
                                                Desktop.getDesktop().open(file);
                                        } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                System.out.println("not found");
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                }
        }//GEN-LAST:event_btnExcelDoThatLacActionPerformed

        private void btnDoanhThuDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoanhThuDichVuActionPerformed
                // TODO add your handling code here:
                try {
                        JFileChooser fileChooser = new JFileChooser();
                        int retval = fileChooser.showSaveDialog(btnDoanhThuDichVu);

                        if (retval == JFileChooser.APPROVE_OPTION) {
                                File file = fileChooser.getSelectedFile();
                                if (file != null) {
                                        if (!file.getName().toLowerCase().endsWith(".xls")) {
                                                file = new File(file.getParentFile(), file.getName() + ".xls");
                                        }
                                        try {
                                                ExcelExporter exp = new ExcelExporter();
                                                exp.exportTable(tblDoanhThuDichVu, file, "Danh sách doanh thu dịch vụ", 6);
                                                Desktop.getDesktop().open(file);
                                        } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                System.out.println("not found");
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }

                } catch (Exception e) {
                }
        }//GEN-LAST:event_btnDoanhThuDichVuActionPerformed

        private void cboNamDoanhThuThuePhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamDoanhThuThuePhongActionPerformed
                // TODO add your handling code here:
                fillTableDoanhThuThuePhong();
        }//GEN-LAST:event_cboNamDoanhThuThuePhongActionPerformed

        private void cboThangDoanhThuThuePhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangDoanhThuThuePhongActionPerformed
                // TODO add your handling code here:
                fillTableDoanhThuThuePhong();
        }//GEN-LAST:event_cboThangDoanhThuThuePhongActionPerformed

        private void cboThangDoanhThuDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangDoanhThuDichVuActionPerformed
                // TODO add your handling code here:
                fillTableDoanhThuDichVu();
        }//GEN-LAST:event_cboThangDoanhThuDichVuActionPerformed

        private void cboNamDoanhThuDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamDoanhThuDichVuActionPerformed
                // TODO add your handling code here:
                fillTableDoanhThuDichVu();
                loadToJList(Integer.parseInt(cboNamDoanhThuDichVu.getSelectedItem().toString()));
        }//GEN-LAST:event_cboNamDoanhThuDichVuActionPerformed

        private void btnCharDoanhThuDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCharDoanhThuDichVuActionPerformed
                // TODO add your handling code here:
                ChartDVJDialog dialog = new ChartDVJDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
        }//GEN-LAST:event_btnCharDoanhThuDichVuActionPerformed

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
                        java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(ThongKeJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                ThongKeJDialog dialog = new ThongKeJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JButton btnBarChart;
        private javax.swing.JButton btnCharDoanhThuDichVu;
        private javax.swing.JButton btnDoanhThuDichVu;
        private javax.swing.JButton btnExcelDoThatLac;
        private javax.swing.JButton btnExcelDoanhThu;
        private javax.swing.JButton btnExcelKhachHang;
        private javax.swing.JButton btnExcelNhanVien;
        private javax.swing.JButton btnPieChart;
        private javax.swing.JButton btnSapXep;
        private javax.swing.JComboBox<String> cboNamDoThatLac;
        private javax.swing.JComboBox<String> cboNamDoanhThuDichVu;
        private javax.swing.JComboBox<String> cboNamDoanhThuThuePhong;
        private javax.swing.JComboBox<String> cboNamKhachHang;
        private javax.swing.JComboBox<String> cboSapXep;
        private javax.swing.JComboBox<String> cboThangDoanhThuDichVu;
        private javax.swing.JComboBox<String> cboThangDoanhThuThuePhong;
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
        private javax.swing.JLabel jLabel21;
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
        private javax.swing.JPanel jPanel6;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JScrollPane jScrollPane6;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JLabel lblbackground;
        private javax.swing.JList<String> lstSanPham;
        private javax.swing.JTable tblDanhSachDoThatLac;
        private javax.swing.JTable tblDanhSachKhachHang;
        private javax.swing.JTable tblDanhSachNV;
        private javax.swing.JTable tblDoanhThuDichVu;
        private javax.swing.JTable tblDoanhThuThuePhong;
        private javax.swing.JTextField txtChuaCoTK;
        private javax.swing.JTextField txtCoTaiKhoan;
        private javax.swing.JTextField txtSLChuaTra;
        private javax.swing.JTextField txtSLDaLienHe;
        private javax.swing.JTextField txtSLDaTra;
        private javax.swing.JTextField txtSlDoThatLac;
        private javax.swing.JTextField txtSlHoaDon;
        private javax.swing.JTextField txtSoLuongKH;
        private javax.swing.JTextField txtSoLuongNv;
        private javax.swing.JTextField txtThuNhap;
        private javax.swing.JTextField txtTimDoThatLac;
        private javax.swing.JTextField txtTimKiemNV;
        // End of variables declaration//GEN-END:variables
}
