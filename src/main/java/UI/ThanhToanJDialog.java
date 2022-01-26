/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.DatPhongDAO;
import DAO.HoaDonDAO;
import DAO.PhongDAO;
import DAO.ThanhToanDAO;
import Entity.DatPhong;
import Entity.ThongTin;
import Entity.HoaDon;
import Entity.Phong;
import Helper.Auth;
import static Helper.Auth.dp;
import Helper.JDBC;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Toolkit;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class ThanhToanJDialog extends javax.swing.JDialog {

        public static JDBC conn;

        /**
         * Creates new form ThanhToanJDialog
         */
        public ThanhToanJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                setResizable(false);
                fillTableDanhSachHoaDon();
                setLocationRelativeTo(null);
                if (Auth.dp != null) {
                        fillPhieuThanhToan();
                }
        }

        void Clear() {
                txtTenKH.setText("");
                txtLoaiPhong.setText("");
                txtNgayNhan.setText("");
                txtNgayTra.setText("");
                txtGiaPhong.setText("");
                txtTienPhong.setText("");
                txtTienDichVu.setText("");
                txtTongTienThanhToan.setText("");
                txtDatCoc.setText("");
                txtConLai.setText("");
                tblCTSDDV.removeAll();
        }

        ThanhToanDAO ttDAO = new ThanhToanDAO();
        DatPhongDAO dpDAO = new DatPhongDAO();

        void fillPhieuThanhToan() {
                if (dpDAO.SelectByID(Auth.dp.getMaDatPhong()) != null) {
                        DatPhong dp = dpDAO.SelectByID(Auth.dp.getMaDatPhong());
                        String maDatPhong = dp.getMaDatPhong();
                        ThongTin tt = ttDAO.getThongTinDatPhong(maDatPhong);
                        txtTenKH.setText(tt.getHoten());
                        txtLoaiPhong.setText(tt.getLoaiphong());
                        txtNgayNhan.setText(Helper.xDate.dateToString(tt.getNgaydat(), "dd/MM/yyyy"));
                        txtNgayTra.setText(Helper.xDate.dateToString(tt.getNgaytra(), "dd/MM/yyyy"));
                        txtGiaPhong.setText(String.valueOf(tt.getGiaphong()));
                        txtTienPhong.setText(String.valueOf(tt.getTienphong()));
                        txtDatCoc.setText(String.valueOf(tt.getDatcoc()));

                        List<Object[]> lst = ttDAO.getThongTinDichVu(maDatPhong);
                        DefaultTableModel model = (DefaultTableModel) tblCTSDDV.getModel();
                        model.setRowCount(0);
                        for (Object[] row : lst) {
                                model.addRow(new Object[]{
                                        row[0],
                                        row[1],
                                        row[2],
                                        row[3],
                                        row[4]
                                });
                        }
                        double tongtienDichVu = 0;
                        for (int i = 0; i < lst.size(); i++) {
                                tongtienDichVu += Double.parseDouble(tblCTSDDV.getValueAt(i, 3).toString());
                        }
                        txtTienDichVu.setText(String.valueOf(tongtienDichVu));
                }
                double a = Double.parseDouble(txtTienPhong.getText());
                double b = Double.parseDouble(txtTienDichVu.getText());
                double total = a + b;
                double conlai = total - Double.parseDouble(txtDatCoc.getText());

                txtTongTienThanhToan.setText(String.valueOf(total));
                txtConLai.setText(String.valueOf(conlai));

        }

        void fillTableDanhSachHoaDon() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachHoaDon.getModel();
                model.setRowCount(0);
                List<Object[]> lst = hdDAO.getDanhSachHoaDon();
                for (Object[] row : lst) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4],
                                row[5],
                                row[6]
                        });
                }
        }

        HoaDonDAO hdDAO = new HoaDonDAO();

        boolean checkAllForm() {
                // trống ngày đặt
                if (txtTenKH.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Tên Khách Hàng trống !");
                        txtTenKH.requestFocus();
                        return false;
                }
                // kiểm tra nhập định dạng ngày đặt
                if (!GenericValidator.isDate(txtNgayNhan.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày định dạng (dd/MM/yyyy)!");
                        txtNgayNhan.requestFocus();
                        return false;
                }

                // trống ngày trả
                if (txtNgayTra.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày trả  !");
                        txtNgayTra.requestFocus();
                        return false;
                }
                // kiểm tra nhập định dạng ngày trả
                if (!GenericValidator.isDate(txtNgayTra.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày định dạng (dd/MM/yyyy)!");
                        txtNgayTra.requestFocus();
                        return false;
                }
                // kiểm tra ngày đặt bé hơn ngày trả
                Date ngayDat = (xDate.stringToDate(txtNgayTra.getText(), "dd/MM/yyyy"));
                Date ngayTra = (xDate.stringToDate(txtNgayNhan.getText(), "dd/MM/yyyy"));
                if (ngayDat.compareTo(ngayTra) <= 0) {
                        MsgBox.alert(this, "Ngày đặt không thể trước ngày trả phòng !");
                        return false;
                }

                // kiểm tra trống mã đặt phòng
                if (txtLoaiPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Tên phòng không được trống !");
                        txtLoaiPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống mã phòng
                if (txtGiaPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Giá phòng không được trống !");
                        txtGiaPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống mã khách hàng
                if (txtTienPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Tiền thuê phòng không được trống !");
                        txtTienPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống đặt cọc,
                if (txtDatCoc.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Đặt cọc không được trống !");
                        txtDatCoc.requestFocus();
                        return false;
                }

                // kiểm tra định dạng đặt cọc
                try {
                        Double datCoc = Double.parseDouble(txtDatCoc.getText());
                        if (datCoc < 0) {
                                MsgBox.alert(this, "Đặt cọc phải lớn hơn hoặc bằng 0 !");
                                txtDatCoc.requestFocus();
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Đặt cọc phải là số !");
                        txtDatCoc.requestFocus();
                        return false;
                }
                return true;
        }
        String test = "";

        HoaDon getForm() {
                HoaDon hd = new HoaDon();
                hd.setMaDatPhong(Auth.dp.getMaDatPhong());
                hd.setMaTaiKhoan(Auth.user.getMaTaiKhoan());
                hd.setPtThanhToan(cboPTThanhToan.getSelectedItem().toString());
                hd.setTongTienPhong(Double.parseDouble(txtTienPhong.getText()));
                hd.setTongTienDichVu(Double.parseDouble(txtTienDichVu.getText()));
                hd.setThanhTien(Double.parseDouble(txtTongTienThanhToan.getText()));
                hd.setGhiChu("");
                return hd;
        }

        PhongDAO pDAO = new PhongDAO();

        void ThanhToan() {
                if (!checkAllForm()) {
                        return;
                }
                try {
                        //Thêm đặt phòng
                        HoaDon hd = getForm();
                        hdDAO.insert(hd);
                        Clear();

                        XuatHoaDon(hd.getMaDatPhong());
                        MsgBox.alert(this, "Thanh toán thành công !");
                        dpDAO.thanhToan(dp.getMaDatPhong());

                        Phong p = pDAO.SelectByID(dp.getMaPhong());
                        int soLuongDatPhong = pDAO.getSoLuongDatPhong(dp.getMaPhong());
                        if (p.getMaTinhTrang() == 3 && soLuongDatPhong > 1) {
                                pDAO.capNhatTinhTrangPhong(1, dp.getMaPhong());
                        }
                        if (p.getMaTinhTrang() == 2) {
                                pDAO.capNhatTinhTrangPhong(0, dp.getMaPhong());
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Thanh toán thất bại !");
                }
        }

        void XuatHoaDon(String mdp) {
                conn = new JDBC(true);
                try {
                        Hashtable map = new Hashtable();
                        JasperReport report = JasperCompileManager.compileReport("src/main/java/UI/rptXuatHoaDon.jrxml");

                        map.put("MaDatPhong", mdp);

                        JasperPrint p = JasperFillManager.fillReport(report, map, JDBC.conn);
                        JasperViewer.viewReport(p, false);
                        String maDatPhong = Auth.dp.getMaDatPhong();
                        JasperExportManager.exportReportToPdfFile(p, "HoaDon/" + maDatPhong + "_HoaDon.pdf");

                } catch (Exception ex) {

                }
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCTSDDV = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTienPhong = new javax.swing.JTextField();
        txtGiaPhong = new javax.swing.JTextField();
        txtNgayTra = new javax.swing.JTextField();
        txtNgayNhan = new javax.swing.JTextField();
        txtLoaiPhong = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        txtTienDichVu = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTongTienThanhToan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDatCoc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtConLai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cboPTThanhToan = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnDong = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDanhSachHoaDon = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GodEdoc_Quản Lý Hóa Đơn");

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice (1).png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ HOÁ ĐƠN");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Tên Khách Hàng");

        tblCTSDDV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên Dịch Vụ", "Số Lượng", "Đơn Giá", "Tổng Tiền", "Ngày Sử Dụng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCTSDDV);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist.png"))); // NOI18N
        jLabel3.setText("Chi Tiết Sử Dụng Dịch Vụ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Ngày Nhận Phòng");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Phòng Đã Thuê");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Ngày Trả Phòng");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Giá Phòng");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Tổng Tiền Phòng");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/money.png"))); // NOI18N
        jLabel12.setText("Tổng Tiền Dịch Vụ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Tổng Tiền Khách Cần Thanh Toán");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Số Tiền Khách Hàng Đã Cọc");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Số Tiền Cần Thanh Toán");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
        jLabel10.setText("Chi Tiết Thuê Phòng");

        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/budget.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán & In HĐ");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Phương Thức Thanh Toán");

        cboPTThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Thẻ tín dụng" }));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo.png"))); // NOI18N

        btnDong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1cancel.png"))); // NOI18N
        btnDong.setText("Đóng");
        btnDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTienPhong, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                                    .addComponent(txtGiaPhong)
                                    .addComponent(txtNgayTra)
                                    .addComponent(txtNgayNhan)
                                    .addComponent(txtLoaiPhong)
                                    .addComponent(txtTenKH)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addGap(52, 52, 52)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtConLai, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtDatCoc)
                                        .addComponent(cboPTThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 20, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTienDichVu, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                        .addComponent(txtTongTienThanhToan)))))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(238, 238, 238))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNgayNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 52, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtTienDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtTongTienThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtDatCoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtConLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(cboPTThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnDong)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Thanh Toán", jPanel1);

        tblDanhSachHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên Khách Hàng", "Tên Phòng", "Ngày Đặt Phòng", "Ngày Trả Phòng", "Tổng Tiền Phòng", "Tổng Tiền Dịch  Vụ", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblDanhSachHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh Sách Các Hoá Đơn", jPanel2);

        jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 1010, 560));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
            // TODO add your handling code here:
            ThanhToan();
            fillTableDanhSachHoaDon();
    }//GEN-LAST:event_btnThanhToanActionPerformed

        private void btnDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongActionPerformed
                // TODO add your handling code here:
                this.dispose();
        }//GEN-LAST:event_btnDongActionPerformed

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
                        java.util.logging.Logger.getLogger(ThanhToanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(ThanhToanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(ThanhToanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(ThanhToanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                ThanhToanJDialog dialog = new ThanhToanJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDong;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JComboBox<String> cboPTThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblCTSDDV;
    private javax.swing.JTable tblDanhSachHoaDon;
    private javax.swing.JTextField txtConLai;
    private javax.swing.JTextField txtDatCoc;
    private javax.swing.JTextField txtGiaPhong;
    private javax.swing.JTextField txtLoaiPhong;
    private javax.swing.JTextField txtNgayNhan;
    private javax.swing.JTextField txtNgayTra;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienDichVu;
    private javax.swing.JTextField txtTienPhong;
    private javax.swing.JTextField txtTongTienThanhToan;
    // End of variables declaration//GEN-END:variables
}
