/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.DichVuDAO;
import Entity.KhoHang;
import DAO.KhoHangDAO;
import DAO.NhapXuatDAO;
import DAO.XuatHangDAO;
import Entity.DichVu;
import Entity.NhapXuat;
import Entity.XuatHang;
import Helper.MsgBox;
import java.util.List;
import Helper.xDate;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class KhoHangJdialog extends javax.swing.JDialog {

        /**
         * Creates new form KhoHang
         */
        public KhoHangJdialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                this.capNhatTable();
                init();

        }

        void init() {
                setLocationRelativeTo(null);
                setResizable(false);
                loadToTableDanhSachNhap();
                loadToTableNhapHang();
                loadToTableXuatHang();
        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToTableDanhSachNhap();
                        loadToTableNhapHang();
                        loadToTableXuatHang();
                }).start();
        }

        XuatHangDAO xhDAO = new XuatHangDAO();
        DichVuDAO dvDAO = new DichVuDAO();

        void loadToTableXuatHang() {
                DefaultTableModel model = (DefaultTableModel) tblXuatHang.getModel();
                model.setRowCount(0);
                List<XuatHang> lstXuatHang = xhDAO.SelectAll();
                for (XuatHang xh : lstXuatHang) {
                        String tinhTrang = xh.isTinhTrang() ? "Đặt" : "Huỷ";
                        Object[] row = {
                                xh.getMaDichVu(),
                                xh.getSoLuong(),
                                xh.getDonGia(),
                                xDate.dateToString(xh.getNgayXuatHang(), "dd/MM/yyyy"),
                                tinhTrang
                        };
                        model.addRow(row);
                }
        }

        void setFormNhapXuat(NhapXuat nx) {

                txtMaDichVu.setText(nx.getMaDichVu());
                txtDonGia.setText((nx.getDonGia().toString()));
                JspinerSoLuong.setValue(nx.getSoLuong());
                txtNgayNhap.setText(xDate.dateToString(nx.getNgayNhapXuat(), "dd/MM/yyyy"));
        }

        NhapXuat getFormNhapXuat() {
                NhapXuat nx = new NhapXuat();
                nx.setMaDichVu(txtMaDichVu.getText());
                nx.setSoLuong(Integer.parseInt(JspinerSoLuong.getValue().toString()));
                nx.setDonGia(Double.parseDouble(txtDonGia.getText()));
                nx.setNgayNhapXuat(xDate.stringToDate(txtNgayNhap.getText(), "dd/MM/yyyy"));
                return nx;
        }

        void insert() {
                if (!checkForm()) {
                        return;
                }
                NhapXuat nhap = getFormNhapXuat();
                try {
                        nxDAO.insert(nhap);
                        loadToTableNhapHang();
                        clearForm();
                        MsgBox.alert(this, "Nhập hàng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Nhập hàng thất bại !");
                }
        }

        void clearForm() {
                txtMaDichVu.setText("");
                txtTenDichVu.setText("");
                JspinerSoLuong.setValue(0);
                txtDonGia.setText("");
                txtNgayNhap.setText("");
                txtThanhTien.setText("");

        }

        KhoHangDAO khDAO = new KhoHangDAO();
        NhapXuatDAO nxDAO = new NhapXuatDAO();

        // Lịch Sử Nhập Hàng
        void loadToTableNhapHang() {
                DefaultTableModel model = (DefaultTableModel) tblLichSuNhap.getModel();
                model.setRowCount(0);
                List<Object[]> lstNhapHang = nxDAO.select_NhapHang();
                for (Object[] objects : lstNhapHang) {
                        model.addRow(objects);
                }
        }

        // Danh Sách Hàng Có Thể Nhập
        void loadToTableDanhSachNhap() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachNhap.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (txtTim.getText().trim().isEmpty()) {
                        keyword = "";
                } else {
                        keyword = txtTim.getText();
                }
                List<Object[]> lst = khDAO.SelectByKeyWord_KhoHang(keyword);
                for (Object[] objects : lst) {
                        model.addRow(objects);
                }
        }

        void ScanBarcode() {
                Scan_BarCode scan = new Scan_BarCode(new javax.swing.JFrame(), true);
                scan.setVisible(true);
                System.out.println(scan.str);
                txtTim.setText(scan.str);
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel4 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                txtTim = new javax.swing.JTextField();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblDanhSachNhap = new javax.swing.JTable();
                jPanel3 = new javax.swing.JPanel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                txtMaDichVu = new javax.swing.JTextField();
                txtTenDichVu = new javax.swing.JTextField();
                txtDonGia = new javax.swing.JTextField();
                JspinerSoLuong = new javax.swing.JSpinner();
                txtNgayNhap = new javax.swing.JTextField();
                txtThanhTien = new javax.swing.JTextField();
                btnNhapHang = new javax.swing.JButton();
                jScrollPane3 = new javax.swing.JScrollPane();
                tblLichSuNhap = new javax.swing.JTable();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                btnScanBarCode = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                jLabel11 = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblXuatHang = new javax.swing.JTable();
                background = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 51, 51));
                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/warehouse.png"))); // NOI18N
                jLabel1.setText("QUẢN LÝ KHO HÀNG");
                jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 1000, 40));

                jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
                jLabel2.setText("Tìm kiếm");

                txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKeyReleased(evt);
                        }
                });

                tblDanhSachNhap.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Dịch Vụ", "Tên Dịch Vụ", "Số Lượng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachNhap.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachNhapMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(tblDanhSachNhap);

                jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(204, 0, 0))); // NOI18N

                jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel3.setText("Mã Dịch Vụ");

                jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel4.setText("Tên Dịch Vụ");

                jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel5.setText("Số Lượng");

                jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel6.setText("Đơn Giá");

                jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                jLabel7.setText("Ngày Nhập");

                jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
                jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/money.png"))); // NOI18N
                jLabel8.setText("Thành Tiền");

                txtMaDichVu.setEditable(false);

                txtTenDichVu.setEditable(false);

                txtDonGia.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtDonGiaKeyReleased(evt);
                        }
                });

                JspinerSoLuong.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                JspinerSoLuongStateChanged(evt);
                        }
                });

                txtThanhTien.setEnabled(false);

                btnNhapHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnNhapHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/order.png"))); // NOI18N
                btnNhapHang.setText("Xác Nhận");
                btnNhapHang.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNhapHangActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(97, 97, 97)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel7))
                                                .addGap(23, 23, 23)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNgayNhap)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(JspinerSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel6)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(txtMaDichVu)
                                                        .addComponent(txtTenDichVu)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtMaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtTenDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(JspinerSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addComponent(btnNhapHang)
                                .addContainerGap())
                );

                tblLichSuNhap.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Dịch Vụ", "Tên Dịch Vụ", "Số Lượng", "Đơn Giá", "Ngày Nhập", "Tổng Tiền "
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane3.setViewportView(tblLichSuNhap);

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel9.setForeground(new java.awt.Color(204, 0, 51));
                jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stock.png"))); // NOI18N
                jLabel9.setText("Kho Hàng ");

                jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel10.setForeground(new java.awt.Color(204, 0, 51));
                jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
                jLabel10.setText("Lịch Sử Nhập Hàng");

                btnScanBarCode.setText("Scan Barcode");
                btnScanBarCode.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnScanBarCodeActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGap(14, 14, 14)
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(btnScanBarCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(txtTim)))
                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                .addGap(0, 20, Short.MAX_VALUE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap())
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(11, 11, 11)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(2, 2, 2)
                                .addComponent(btnScanBarCode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Nhập Hàng", jPanel4);

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel11.setForeground(new java.awt.Color(204, 0, 51));
                jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist.png"))); // NOI18N
                jLabel11.setText("Lịch Sử Xuất Hàng");

                tblXuatHang.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Dịch Vụ", "Số Lượng", "Đơn Giá", "Ngày Xuất", "TinhTrang"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane4.setViewportView(tblXuatHang);

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE))
                                .addContainerGap())
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(45, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("Xuất Hàng", jPanel2);

                jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1020, 650));

                background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 720));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void tblDanhSachNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachNhapMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int Vitri = tblDanhSachNhap.getSelectedRow();
                        txtMaDichVu.setText(tblDanhSachNhap.getValueAt(Vitri, 0).toString());
                        txtTenDichVu.setText(tblDanhSachNhap.getValueAt(Vitri, 1).toString());
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate localDate = LocalDate.now();
                        txtNgayNhap.setText(format.format(localDate));
                }
        }//GEN-LAST:event_tblDanhSachNhapMouseClicked

        private void txtDonGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaKeyReleased
                // TODO add your handling code here:
                if ((Integer.parseInt(JspinerSoLuong.getValue().toString()) <= 0)) {
                        return;
                }

                int SoLuong = Integer.parseInt(JspinerSoLuong.getValue().toString());
                double Dongia = Double.parseDouble(txtDonGia.getText());
                txtThanhTien.setText(String.valueOf(String.format("%.2f", (SoLuong * Dongia))));
        }//GEN-LAST:event_txtDonGiaKeyReleased

        private void JspinerSoLuongStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JspinerSoLuongStateChanged
                // TODO add your handling code here:
                if (Integer.parseInt(JspinerSoLuong.getValue().toString()) < 0) {
                        JspinerSoLuong.setValue(0);
                }
                if (txtDonGia.getText().trim().isEmpty()) {
                        return;
                }
                int So = Integer.parseInt(JspinerSoLuong.getValue().toString());
                double DonGia = Double.parseDouble(txtDonGia.getText());
                txtThanhTien.setText(String.valueOf(String.format("%.2f", (So * DonGia))));

        }//GEN-LAST:event_JspinerSoLuongStateChanged

        private void btnNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapHangActionPerformed
                // TODO add your handling code here:
                KhoHang kh = khDAO.SelectByID(txtMaDichVu.getText());
                khDAO.updateSoLuong(kh, Integer.parseInt(JspinerSoLuong.getValue().toString()));
                loadToTableDanhSachNhap();
                insert();
        }//GEN-LAST:event_btnNhapHangActionPerformed

        private void txtTimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKeyReleased
                // TODO add your handling code here:
                loadToTableDanhSachNhap();
        }//GEN-LAST:event_txtTimKeyReleased

    private void btnScanBarCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanBarCodeActionPerformed
            // TODO add your handling code here:
            ScanBarcode();

            for (int i = 0; i < tblDanhSachNhap.getRowCount(); i++) {
                    if (tblDanhSachNhap.getValueAt(i, 0).toString().equalsIgnoreCase(Helper.Auth.authMaDichVu)) {
                            tblDanhSachNhap.setRowSelectionInterval(i, i);
                            break;
                    }
            }
            DichVu dv = dvDAO.SelectByID(Helper.Auth.authMaDichVu);
            txtMaDichVu.setText(dv.getMaDichVu());
            txtTenDichVu.setText(dv.getTenLoaiDV());

    }//GEN-LAST:event_btnScanBarCodeActionPerformed

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
                        java.util.logging.Logger.getLogger(KhoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(KhoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(KhoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(KhoHangJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                KhoHangJdialog dialog = new KhoHangJdialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JSpinner JspinerSoLuong;
        private javax.swing.JLabel background;
        private javax.swing.JButton btnNhapHang;
        private javax.swing.JButton btnScanBarCode;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
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
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JTable tblDanhSachNhap;
        private javax.swing.JTable tblLichSuNhap;
        private javax.swing.JTable tblXuatHang;
        private javax.swing.JTextField txtDonGia;
        private javax.swing.JTextField txtMaDichVu;
        private javax.swing.JTextField txtNgayNhap;
        private javax.swing.JTextField txtTenDichVu;
        private javax.swing.JTextField txtThanhTien;
        private javax.swing.JTextField txtTim;
        // End of variables declaration//GEN-END:variables

        boolean checkForm() {
                // check trống mã dịch vụ
                if (!GenericValidator.isDate(txtNgayNhap.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày sinh sai định dạng (dd/MM/yyyy)!");
                        txtNgayNhap.requestFocus();
                        return false;
                }

                // trống đơn giá
                try {
                        if (Double.parseDouble(txtDonGia.getText().toString()) < 0) {
                                MsgBox.alert(this, "Nhập đơn giá!");
                                txtDonGia.requestFocus();
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Nhập đơn giá!");
                        txtDonGia.requestFocus();
                        return false;
                }

                // trống số lượng
                try {
                        if (Integer.parseInt(JspinerSoLuong.getValue().toString()) <= 0) {
                                MsgBox.alert(this, "Nhập số lượng!");
                                JspinerSoLuong.requestFocus();
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Nhập số lượng!");
                        JspinerSoLuong.requestFocus();
                        return false;
                }

                return true;
        }

}
