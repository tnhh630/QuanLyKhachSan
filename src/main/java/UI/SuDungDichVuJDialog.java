/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.ChiTietSDDVDAO;
import DAO.DatPhongDAO;
import DAO.DichVuDAO;
import DAO.KhoHangDAO;
import DAO.PhongDAO;
import DAO.SuDungDVDAO;
import DAO.XuatHangDAO;
import Entity.CT_SuDungDV;
import Entity.DatPhong;
import Entity.DichVu;
import Entity.GioHang;
import Entity.KhoHang;
import Entity.SuDungDV;
import Entity.XuatHang;
import Helper.Auth;
import Helper.MsgBox;
import Helper.xDate;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class SuDungDichVuJDialog extends javax.swing.JDialog {

        /**
         * Creates new form SuDungDichVuJDalog
         */
        public SuDungDichVuJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                setLocationRelativeTo(null);
                setResizable(false);
                this.capNhatTable();
                init();

        }

        void capNhatTable() {
                new Timer(1500, (ActionEvent e) -> {
                        loadToAllTable();
                        if (dongDuocChon != -1) {
                                tblLichSu.setRowSelectionInterval(dongDuocChon, dongDuocChon);
                        }
                }).start();
        }

        PhongDAO pDAO = new PhongDAO();
        DatPhongDAO dpDAO = new DatPhongDAO();
        DichVuDAO dvDAO = new DichVuDAO();
        SuDungDVDAO sddvDAO = new SuDungDVDAO();
        ChiTietSDDVDAO ctsddvDAO = new ChiTietSDDVDAO();
        KhoHangDAO khDAO = new KhoHangDAO();
        XuatHangDAO xhDAO = new XuatHangDAO();

        void init() {
                loadToAllTable();
                txtMaDichVu.setEditable(false);
                txtTenDichVu.setEditable(false);
                txtDonGia.setEditable(false);

                try {
                        if (dpDAO.SelectByID(Auth.dp.getMaDatPhong()) != null) {
                                DatPhong dp = dpDAO.SelectByID(Auth.dp.getMaDatPhong());
                                txtMaDatPhong.setText(dp.getMaDatPhong());
                                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate localDate = LocalDate.now();
                                txtNgayDat.setText(format.format(localDate));
                                if (sddvDAO.selectByMaPhong(Auth.dp.getMaDatPhong()) != null) {
                                        SuDungDV sd = sddvDAO.selectByMaPhong(Auth.dp.getMaDatPhong());
                                        txtMaSuDungDV.setText(sd.getMaSuDungDV());
                                        loadToDanhSachLichSuFromOrther();
                                        for (int i = 0; i < tblLichSu.getRowCount(); i++) {
                                                String maDichVu = tblLichSu.getValueAt(i, 0).toString();
                                                if (sd.getMaSuDungDV().equalsIgnoreCase(maDichVu)) {
                                                        tblLichSu.setRowSelectionInterval(i, i);
                                                        dongDuocChon = i;
                                                        break;
                                                }
                                        }
                                }
                        }
                } catch (Exception e) {

                }
        }

        void loadToAllTable() {
                loadToTableDichVu();
                loadToTableLichSu();
        }

        void loadToTableDichVu() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachCacDichVu.getModel();
                model.setRowCount(0);
                List<DichVu> lstDichVu = dvDAO.selectAll_notfree();
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

        void loadToTableLichSu() {
                DefaultTableModel model = (DefaultTableModel) tblLichSu.getModel();
                model.setRowCount(0);
                List<Object[]> lstDanhSach = sddvDAO.getDanhSachSuDungDV();
                for (Object[] row : lstDanhSach) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2]
                        });
                }
        }

        void loadToDanhSachLichSuFromOrther() {
                lstGH.clear();
                DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                model.setRowCount(0);
                int row = tblLichSu.getSelectedRow();
                String maSuDungDV = txtMaSuDungDV.getText();

                List<CT_SuDungDV> lstctdv = ctsddvDAO.SelectAll(maSuDungDV);
                double tongTien = 0;
                for (CT_SuDungDV ctdv : lstctdv) {
                        Object[] rows = {
                                ctdv.getMaDichVu(),
                                dvDAO.SelectTenDV(ctdv.getMaDichVu()),
                                dvDAO.SelectdonGia(ctdv.getMaDichVu()),
                                ctdv.getSoLuong(),
                                xDate.dateToString(ctdv.getNgaySuDungDV(), "dd/MM/yyyy"),
                                dvDAO.SelectdonGia(ctdv.getMaDichVu()) * ctdv.getSoLuong() /* thành tiền */};
                        GioHang gh = new GioHang();
                        gh.getMaDichVu();
                        gh.setTen(dvDAO.SelectTenDV(ctdv.getMaDichVu()));
                        gh.setGia(dvDAO.SelectdonGia(ctdv.getMaDichVu()));
                        gh.setSoluong(ctdv.getSoLuong());
                        gh.setMaDichVu(ctdv.getMaDichVu());
                        gh.setNgaySuDungDV(ctdv.getNgaySuDungDV());
                        gh.setMaSuDungDV(maSuDungDV);
                        gh.setMaDatPhong(maSuDungDV);
                        lstGH.add(gh);
                        model.addRow(rows);
                        tongTien += dvDAO.SelectdonGia(ctdv.getMaDichVu()) * ctdv.getSoLuong();

                }
                txtTongTien.setText(String.valueOf(tongTien));
                panel.setSelectedIndex(0);
        }

        boolean checkDV() {
                if (sddvDAO.selectByMaPhong(Auth.dp.getMaDatPhong()) != null) {
                        return true;
                }
                return false;
        }

        void setFormDV(SuDungDV sddv) {
                txtMaSuDungDV.setText(sddv.getMaSuDungDV());
                txtMaDatPhong.setText(sddv.getMaDatPhong());

        }

        SuDungDV getFormDV() {
                SuDungDV dv = new SuDungDV();
                dv.setMaSuDungDV(txtMaSuDungDV.getText());
                dv.setMaDatPhong(txtMaDatPhong.getText());
                return dv;
        }

        boolean checkFormCTSD() {

                return true;
        }

        void insertCTSDDV() {
                if (!checkFormCTSD()) {

                        return;
                }
                try {
                        CT_SuDungDV ctdv = getFormCTSDDV();
                        ctsddvDAO.insert(ctdv);

                } catch (Exception e) {
                }
        }

        void setFormCTSDDV(CT_SuDungDV ctdv) {
                jSpinner1.setValue(ctdv.getSoLuong());
                txtMaDichVu.setText(ctdv.getMaDichVu());
                txtNgayDat.setText(xDate.dateToString(ctdv.getNgaySuDungDV(), "dd/MM/yyyy"));
                txtMaSuDungDV.setText(ctdv.getMaSuDungDV());
        }

        CT_SuDungDV getFormCTSDDV() {
                CT_SuDungDV ctdv = new CT_SuDungDV();
                ctdv.setSoLuong(Integer.parseInt(jSpinner1.getValue().toString()));
                ctdv.setMaDichVu(txtMaDichVu.getText());
                ctdv.setNgaySuDungDV(xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                ctdv.setMaSuDungDV(txtMaSuDungDV.getText());

                return ctdv;
        }

        void insert() {
                insertCTSDDV();
        }

        void delete() {
                try {
                        int row = tblDanhSachChiTiet.getSelectedRow();
                        int rowLS = tblLichSu.getSelectedRow();
                        String maSDDV = tblLichSu.getValueAt(rowLS, 0).toString();
                        String maDV = tblDanhSachChiTiet.getValueAt(row, 0).toString();
                        int soLuong = Integer.parseInt(tblDanhSachChiTiet.getValueAt(row, 2).toString());
                        if (maDV.equalsIgnoreCase("DVB00")) {
                                MsgBox.alert(this, "Đây là sản phẩm miễn phí, không thể xóa!");
                                return;
                        }

                        khDAO.updateHuyXuatKhoHang(maDV, soLuong);
                        xhDAO.capNhapTinhTrangHuy(maDV, maSDDV, xDate.stringToDate(tblDanhSachChiTiet.getValueAt(row, 4).toString(), "dd/MM/yyyy"));

                        ctsddvDAO.delete(maDV, maSDDV, xDate.stringToDate(tblDanhSachChiTiet.getValueAt(row, 4).toString(), "dd/MM/yyyy"), soLuong);
                        int row2 = tblLichSu.getSelectedRow();
                        DefaultTableModel model = (DefaultTableModel) tblDanhSachChiTiet.getModel();
                        model.setRowCount(0);
                        String maSuDungDV = tblLichSu.getValueAt(rowLS, 0).toString();

                        List<Object[]> lstChiTiet = sddvDAO.getDanhSachChiTietSDDV(maSuDungDV);

                        for (Object[] row1 : lstChiTiet) {
                                Date date1 = (Date) row1[4];
                                model.addRow(new Object[]{
                                        row1[0],
                                        row1[1],
                                        row1[2],
                                        row1[3],
                                        xDate.dateToString(date1, "dd/MM/yyyy")
                                });
                        }
                        int vitri = 0;
                        for (int i = 0; i < lstGH.size(); i++) {
                                if (lstGH.get(i).getMaDichVu().equals(tblLichSu.getValueAt(rowLS, 0).toString()) && lstGH.get(i).getNgaySuDungDV().equals(tblLichSu.getValueAt(row2, 4).toString())) {
                                        vitri = i;
                                }
                        }
                        lstGH.remove(vitri);

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        void clearForm() {
                txtDonGia.setText("");
                txtMaDatPhong.setText("");
                txtMaDichVu.setText("");
                txtMaSuDungDV.setText("");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.now();
                txtNgayDat.setText(format.format(localDate));
                txtTenDichVu.setText("");
                jSpinner1.setValue(1);
                DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                model.setRowCount(0);
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                btnXoa1 = new javax.swing.JButton();
                jPanel5 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                panel = new javax.swing.JTabbedPane();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tblDanhSachCacDichVu = new javax.swing.JTable();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblGioHang = new javax.swing.JTable();
                jLabel3 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                jLabel6 = new javax.swing.JLabel();
                txtMaSuDungDV = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                txtMaDatPhong = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();
                txtTenDichVu = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                txtNgayDat = new javax.swing.JTextField();
                jLabel10 = new javax.swing.JLabel();
                txtMaDichVu = new javax.swing.JTextField();
                jLabel11 = new javax.swing.JLabel();
                jSpinner1 = new javax.swing.JSpinner();
                txtDonGia = new javax.swing.JTextField();
                jLabel13 = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                btnDat = new javax.swing.JButton();
                btnXoaDV = new javax.swing.JButton();
                btnXacNhanDichVu = new javax.swing.JButton();
                btnMoi = new javax.swing.JButton();
                txtTimKiem = new javax.swing.JTextField();
                jLabel12 = new javax.swing.JLabel();
                btnThemDVMoi = new javax.swing.JButton();
                jLabel14 = new javax.swing.JLabel();
                txtTongTien = new javax.swing.JTextField();
                jPanel3 = new javax.swing.JPanel();
                tblDanhSach = new javax.swing.JScrollPane();
                tblLichSu = new javax.swing.JTable();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblDanhSachChiTiet = new javax.swing.JTable();
                btnXoa = new javax.swing.JButton();
                jLabel15 = new javax.swing.JLabel();
                txtTongTien1 = new javax.swing.JTextField();
                jLabel16 = new javax.swing.JLabel();

                btnXoa1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnXoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove.png"))); // NOI18N
                btnXoa1.setText("XOÁ");
                btnXoa1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoa1ActionPerformed(evt);
                        }
                });

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("GodEdoc_Sử Dụng Dịch Vụ");

                jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 0, 0));
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/food-cart.png"))); // NOI18N
                jLabel1.setText("SỬ DỤNG DỊCH VỤ");
                jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

                tblDanhSachCacDichVu.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Dịch Vụ", "Tên Dịch Vụ", "Đơn Giá"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachCacDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachCacDichVuMouseClicked(evt);
                        }
                });
                jScrollPane1.setViewportView(tblDanhSachCacDichVu);

                tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                                "Mã DV", "Tên Dịch Vụ", "Đơn Giá", "Số Lượng", "Ngày Đặt", "Thành Tiền"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblGioHangMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(tblGioHang);

                jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(255, 0, 0));
                jLabel3.setText("SẢN PHẨM ĐƯỢC CHỌN");

                jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel2.setForeground(new java.awt.Color(255, 0, 51));
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
                jLabel2.setText("Danh Sách Các Dịch Vụ");

                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel6.setText("Mã Sử Dụng Dịch Vụ");

                jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel4.setText("Mã Đặt Phòng");

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel5.setText("Tên Dịch Vụ");

                jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel7.setText("Ngày Đặt");

                jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel10.setText("Mã Dịch Vụ");

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel11.setText("Số Lượng");

                jSpinner1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                jSpinner1.setValue(1);
                jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                jSpinner1StateChanged(evt);
                        }
                });

                jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel13.setText("Đơn Giá");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNgayDat)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtMaSuDungDV, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMaDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtMaDichVu)
                                        .addComponent(txtTenDichVu)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel13)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtMaSuDungDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtMaDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(txtMaDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtTenDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(txtNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(24, Short.MAX_VALUE))
                );

                jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức Năng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

                btnDat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnDat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/order.png"))); // NOI18N
                btnDat.setText("Đặt Dịch Vụ");
                btnDat.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDatActionPerformed(evt);
                        }
                });

                btnXoaDV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnXoaDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel.png"))); // NOI18N
                btnXoaDV.setText("Xóa Dịch Vụ Khỏi Giỏ Hàng");
                btnXoaDV.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaDVActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnXoaDV, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                                .addGap(51, 51, 51))
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnDat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(btnXoaDV)
                                .addContainerGap())
                );

                btnXacNhanDichVu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnXacNhanDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document.png"))); // NOI18N
                btnXacNhanDichVu.setText("Xác Nhận Chọn Dịch Vụ");
                btnXacNhanDichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXacNhanDichVuActionPerformed(evt);
                        }
                });

                btnMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
                btnMoi.setText("Mới");
                btnMoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnMoiActionPerformed(evt);
                        }
                });

                txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemKeyReleased(evt);
                        }
                });

                jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel12.setText("Tìm");

                btnThemDVMoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                btnThemDVMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new-item.png"))); // NOI18N
                btnThemDVMoi.setText("Thêm Dịch Vụ Mới");
                btnThemDVMoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemDVMoiActionPerformed(evt);
                        }
                });

                jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel14.setForeground(new java.awt.Color(255, 51, 51));
                jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/budget.png"))); // NOI18N
                jLabel14.setText("Tổng Tiền");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(0, 28, Short.MAX_VALUE)
                                                                .addComponent(jLabel12)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addGap(73, 73, 73))
                                                                        .addComponent(txtTimKiem)))
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addGap(18, 18, 18))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(100, 100, 100)
                                                .addComponent(btnThemDVMoi)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel14)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(btnXacNhanDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap())
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(234, 234, 234)
                                                .addComponent(jLabel3)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                                .addGap(5, 5, 5))
                                                        .addComponent(jLabel12))
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnThemDVMoi)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(77, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnMoi)
                                                        .addComponent(btnXacNhanDichVu))
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addGap(24, 24, 24)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel14))
                                                .addGap(21, 21, 21))))
                );

                panel.addTab("Lập Dịch Vụ", jPanel2);

                tblLichSu.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Sử Dụng", "Mã Đặt Phòng", "Mã Khách Hàng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblLichSu.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblLichSuMouseClicked(evt);
                        }
                });
                tblDanhSach.setViewportView(tblLichSu);

                jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist.png"))); // NOI18N
                jLabel8.setText("DANH SÁCH");

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/checklist (1).png"))); // NOI18N
                jLabel9.setText("Chi Tiết Các Dịch Vụ");

                tblDanhSachChiTiet.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Dịch Vụ", "Tên Dịch Vụ", "Số Lượng", "Đơn Giá", "Ngày Đặt Dịch Vụ"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane4.setViewportView(tblDanhSachChiTiet);

                btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/remove.png"))); // NOI18N
                btnXoa.setText("Xóa");
                btnXoa.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnXoaActionPerformed(evt);
                        }
                });

                jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                jLabel15.setForeground(new java.awt.Color(255, 51, 51));
                jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/budget.png"))); // NOI18N
                jLabel15.setText("Tổng Tiền");

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jLabel15)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(tblDanhSach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap())
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(426, 426, 426)
                                .addComponent(jLabel8)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tblDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                panel.addTab("Danh Sách Sử Dụng Dịch Vụ", jPanel3);

                jPanel5.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 1053, -1));

                jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 2, 1090, 700));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void tblDanhSachCacDichVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachCacDichVuMouseClicked
                // TODO add your handling code here:
                btnDat.setEnabled(true);
                btnXoaDV.setEnabled(true);
                btnXacNhanDichVu.setEnabled(true);
                if (evt.getClickCount() == 2) {
                        double tongTien = 0;
//                 DefaultTableModel model = (DefaultTableModel) tblDanhSachCacDichVu.getModel();
                        int viTri = tblDanhSachCacDichVu.getSelectedRow();

                        txtMaDichVu.setText(tblDanhSachCacDichVu.getValueAt(viTri, 0).toString());
                        txtTenDichVu.setText(tblDanhSachCacDichVu.getValueAt(viTri, 1).toString());
                        txtDonGia.setText(tblDanhSachCacDichVu.getValueAt(viTri, 2).toString());

                }
        }//GEN-LAST:event_tblDanhSachCacDichVuMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
            // TODO add your handling code here:
            loadTableDichVu();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnThemDVMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVMoiActionPerformed
            // TODO add your handling code here:
            QuanLyDichVuJDialog dialog = new QuanLyDichVuJDialog(new javax.swing.JFrame(), true);
            dialog.setVisible(true);
            loadToTableDichVu();
    }//GEN-LAST:event_btnThemDVMoiActionPerformed
        List<GioHang> lstGH = new ArrayList<>();

        boolean checkTrung = true;
    private void btnXacNhanDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanDichVuActionPerformed
            // TODO add your handling code here:
            if (!checkAllForm()) {
                    return;
            }

            try {
                    String maDichVu = txtMaDichVu.getText();
                    if (!dieuKienXuatHang(maDichVu)) {
                            return;
                    }
                    String ten = txtTenDichVu.getText();
                    double donGia = Double.parseDouble(txtDonGia.getText());
                    int soLuong = Integer.parseInt(jSpinner1.getValue().toString());

                    GioHang gh = new GioHang();
                    gh.getMaDichVu();
                    gh.setTen(ten);
                    gh.setGia(donGia);
                    gh.setSoluong(soLuong);
                    gh.setMaDichVu(txtMaDichVu.getText());
                    gh.setNgaySuDungDV(xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                    gh.setMaSuDungDV(txtMaSuDungDV.getText());
                    gh.setMaDatPhong(txtMaDatPhong.getText());
                    if (lstGH.isEmpty()) {
                            lstGH.add(gh);
                    } else {
                            for (int i = 0; i < lstGH.size(); i++) {
                                    if (lstGH.get(i).getTen().equals(ten) && lstGH.get(i).getNgaySuDungDV().equals(xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"))) {
                                            lstGH.get(i).setSoluong(lstGH.get(i).getSoluong() + Integer.parseInt(jSpinner1.getValue().toString()));
                                            checkTrung = false;
                                            break;
                                    } else {
                                            checkTrung = true;
                                    }
                            }
                            if (checkTrung) {
                                    lstGH.add(gh);
                            }

                    }
                    showDaTaGioHang();

            } catch (Exception e) {
            }

            double tongTien = 0;
            for (int i = 0; i < lstGH.size(); i++) {
                    tongTien += Double.parseDouble(tblGioHang.getValueAt(i, 5).toString());
            }
            txtTongTien.setText(tongTien + "");
    }//GEN-LAST:event_btnXacNhanDichVuActionPerformed

        void deleteToUpdate() {
                try {
                        for (int j = 0; j < tblDanhSachChiTiet.getRowCount(); j++) {
                                if (tblDanhSachChiTiet.getValueAt(j, 0).toString().startsWith("DVB")) {
                                        try {
                                                int rowLS = tblLichSu.getSelectedRow();
                                                String maSDDV = tblLichSu.getValueAt(rowLS, 0).toString();
                                                String maDV = tblDanhSachChiTiet.getValueAt(j, 0).toString();
                                                int soLuong = Integer.parseInt(tblDanhSachChiTiet.getValueAt(j, 2).toString());

                                                khDAO.updateHuyXuatKhoHang(maDV, soLuong);

                                        } catch (Exception e) {
                                        }
                                }
                        }
                } catch (Exception ex) {

                }

        }
    private void btnDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDatActionPerformed
            // TODO add your handling code here:
            if (!checkAllForm()) {
                    return;
            }
            if (lstGH.isEmpty()) {
                    MsgBox.alert(this, "Chọn mặt hàng cần đặt !");
                    return;
            }
            String maDichVu = txtMaDichVu.getText();
            if (!dieuKienXuatHang(maDichVu)) {
                    return;
            }
            try {
                    if (flagFromLichSu == true) {
                            flagFromLichSu = false;
                            khDAO.updateXuatKhoHang(2, "DVB00");
                    }
                    String maSDDV = txtMaSuDungDV.getText();

                    deleteToUpdate();

                    sddvDAO.deleteByMaSDDV(maSDDV); // xoá CT_SuDungDV
                    xhDAO.deleteDuLieuCu(txtMaSuDungDV.getText());
//                    ctsddvDAO.delete(txtMaDatPhong.getText());

//                    ------insert-----
//                    khDAO.updateXuatKhoHang(2, "DVB00");
                    for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                            String maDV = lstGH.get(i).getMaDichVu();
                            String tenDV = lstGH.get(i).getTen();
                            int soLuong = lstGH.get(i).getSoluong();
                            double donGia = lstGH.get(i).getGia();
                            Date ngayDat = lstGH.get(i).getNgaySuDungDV();

                            if (maDV.startsWith("DVB") && !maDV.equalsIgnoreCase("DVB00")) {
                                    khDAO.updateXuatKhoHang(soLuong, maDV);
                            }

                            CT_SuDungDV ctdv = new CT_SuDungDV();
                            ctdv.setMaSuDungDV(txtMaSuDungDV.getText());
                            ctdv.setMaDichVu(maDV);
                            ctdv.setNgaySuDungDV(ngayDat);
                            ctdv.setSoLuong(soLuong);
                            ctsddvDAO.insert(ctdv);

                            XuatHang xh = new XuatHang();
                            xh.setMaDichVu(maDV);
                            xh.setSoLuong(soLuong);
                            xh.setDonGia(donGia);
                            xh.setNgayXuatHang(ngayDat);
                            xh.setMaSuDungDV(maSDDV);
                            xhDAO.insert(xh);
                            xhDAO.capNhapDatHangThanhCong(xh.getMaDichVu(), txtMaSuDungDV.getText());

                    }
                    MsgBox.alert(this, "Thêm thành công!");
                    DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                    model.setRowCount(0);
                    txtTongTien.setText("");
                    loadToTableLichSu();
                    tblLichSu.clearSelection();

                    DefaultTableModel modelCT = (DefaultTableModel) tblDanhSachChiTiet.getModel();
                    modelCT.setRowCount(0);
                    txtMaSuDungDV.setText("");
                    txtMaDatPhong.setText("");
                    txtMaDichVu.setText("");
                    txtTenDichVu.setText("");
                    txtDonGia.setText("");
                    jSpinner1.setValue(1);
            } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Vui lòng xác nhận chọn dịch vụ !");
            }


    }//GEN-LAST:event_btnDatActionPerformed

        void hienThiCT() {
                tblDanhSachChiTiet.clearSelection();
                int row = tblLichSu.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) tblDanhSachChiTiet.getModel();
                model.setRowCount(0);
                String maSuDungDV = tblLichSu.getValueAt(row, 0).toString();
                List<Object[]> lstChiTiet = sddvDAO.getDanhSachChiTietSDDV(maSuDungDV);
                double tongTien = 0;
                for (Object[] row1 : lstChiTiet) {
                        Date date1 = (Date) row1[4];
                        model.addRow(new Object[]{
                                row1[0],
                                row1[1],
                                row1[2],
                                row1[3],
                                xDate.dateToString(date1, "dd/MM/yyyy")
                        });
                        tongTien += Double.parseDouble(row1[2].toString()) * Double.parseDouble(row1[3].toString());
                }
                txtTongTien1.setText(String.valueOf(String.format("%.2f", tongTien)));
        }
        int dongDuocChon = -1;
        boolean flagFromLichSu = false;
    private void tblLichSuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichSuMouseClicked
            // TODO add your handling code here:
            if (evt.getClickCount() == 1) {
                    tblDanhSachChiTiet.clearSelection();
                    int row = tblLichSu.getSelectedRow();
                    dongDuocChon = row;
                    DefaultTableModel model = (DefaultTableModel) tblDanhSachChiTiet.getModel();
                    model.setRowCount(0);
                    String maSuDungDV = tblLichSu.getValueAt(row, 0).toString();
                    List<Object[]> lstChiTiet = sddvDAO.getDanhSachChiTietSDDV(maSuDungDV);
                    double tongTien = 0;
                    for (Object[] row1 : lstChiTiet) {
                            Date date1 = (Date) row1[4];
                            model.addRow(new Object[]{
                                    row1[0],
                                    row1[1],
                                    row1[2],
                                    row1[3],
                                    xDate.dateToString(date1, "dd/MM/yyyy")
                            });
                            tongTien += Double.parseDouble(row1[2].toString()) * Double.parseDouble(row1[3].toString());
                    }
                    txtTongTien1.setText(String.valueOf(String.format("%.2f", tongTien)));

            } else if (evt.getClickCount() == 2) {
                    flagFromLichSu = true;
                    lstGH.clear();
                    hienThiCT();
                    DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                    model.setRowCount(0);
                    int row = tblLichSu.getSelectedRow();
                    String maSuDungDV = tblLichSu.getValueAt(row, 0).toString();

                    txtMaSuDungDV.setText(maSuDungDV);
                    txtMaDatPhong.setText(maSuDungDV);
                    List<CT_SuDungDV> lstctdv = ctsddvDAO.SelectAll(maSuDungDV);
                    double tongTien = 0;
                    for (CT_SuDungDV ctdv : lstctdv) {
                            Object[] rows = {
                                    ctdv.getMaDichVu(),
                                    dvDAO.SelectTenDV(ctdv.getMaDichVu()),
                                    dvDAO.SelectdonGia(ctdv.getMaDichVu()),
                                    ctdv.getSoLuong(),
                                    xDate.dateToString(ctdv.getNgaySuDungDV(), "dd/MM/yyyy"),
                                    dvDAO.SelectdonGia(ctdv.getMaDichVu()) * ctdv.getSoLuong() /* thành tiền */};
                            GioHang gh = new GioHang();
                            gh.getMaDichVu();
                            gh.setTen(dvDAO.SelectTenDV(ctdv.getMaDichVu()));
                            gh.setGia(dvDAO.SelectdonGia(ctdv.getMaDichVu()));
                            gh.setSoluong(ctdv.getSoLuong());
                            gh.setMaDichVu(ctdv.getMaDichVu());
                            gh.setNgaySuDungDV(ctdv.getNgaySuDungDV());
                            gh.setMaSuDungDV(maSuDungDV);
                            gh.setMaDatPhong(maSuDungDV);
                            lstGH.add(gh);
                            model.addRow(rows);
                            tongTien += dvDAO.SelectdonGia(ctdv.getMaDichVu()) * ctdv.getSoLuong();

                    }
                    txtTongTien.setText(String.valueOf(tongTien));
                    panel.setSelectedIndex(0);

            }
    }//GEN-LAST:event_tblLichSuMouseClicked

        int duocChon = -1;
        private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
                // TODO add your handling code here:

                if (evt.getClickCount() == 2) {
                        try {

                                if (tblGioHang.getRowCount() < 0) {
                                        return;
                                }
                                DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                                int viTri = tblGioHang.getSelectedRow();
                                duocChon = viTri;
                                txtMaDichVu.setText((model.getValueAt(viTri, 0)).toString());
                                txtTenDichVu.setText((model.getValueAt(viTri, 1)).toString());
                                txtDonGia.setText((model.getValueAt(viTri, 2)).toString());
                                jSpinner1.setValue(Integer.parseInt(model.getValueAt(viTri, 3).toString()));
                                txtNgayDat.setText(model.getValueAt(viTri, 4).toString());
                                if (txtMaDichVu.getText().equalsIgnoreCase("DVB00")) {
                                        btnDat.setEnabled(false);
                                        btnXoaDV.setEnabled(false);
                                        btnXacNhanDichVu.setEnabled(false);
                                } else {
                                        btnDat.setEnabled(true);
                                        btnXoaDV.setEnabled(true);
                                        btnXacNhanDichVu.setEnabled(true);
                                }
                        } catch (Exception e) {
                        }
        }//GEN-LAST:event_tblGioHangMouseClicked
        }
        private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
                // TODO add your handling code here:
                clearForm();

        }//GEN-LAST:event_btnMoiActionPerformed

        private void btnXoaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDVActionPerformed
                // TODO add your handling code here:
                int viTri = tblGioHang.getSelectedRow();
                if (tblGioHang.getValueAt(viTri, 0).toString().equalsIgnoreCase("DVB00")) {
                        MsgBox.alert(this, "Không thể xoá dịch vụ miễn phí !");
                        return;
                }
                if (viTri == -1) {
                        MsgBox.alert(this, "Vui lòng chọn dòng cần xóa !");

                } else if (lstGH.size() == 0) {
                        MsgBox.alert(this, "Vui lòng xác nhận chọn dịch vụ !");

                } else {
                        lstGH.remove(viTri);
                        showDaTaGioHang();
                }

        }//GEN-LAST:event_btnXoaDVActionPerformed

        private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_btnXoa1ActionPerformed

        private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
                // TODO add your handling code here:
                if (MsgBox.confirm(this, "Bạn có chắc xóa hay không?")) {
                        delete();
                        DefaultTableModel model = (DefaultTableModel) tblDanhSachChiTiet.getModel();
                        double tongTien = 0;
                        for (int i = 0; i < tblDanhSachChiTiet.getRowCount(); i++) {
                                double soLuong = Double.parseDouble(tblDanhSachChiTiet.getValueAt(i, 2).toString());
                                double donGia = Double.parseDouble(tblDanhSachChiTiet.getValueAt(i, 3).toString());
                                tongTien += soLuong * donGia;
                        }
                        txtTongTien1.setText(String.valueOf(String.format("%.2f", tongTien)));

                        txtMaSuDungDV.setText("");
                        txtMaDatPhong.setText("");
                        txtMaDichVu.setText("");
                        txtNgayDat.setText("");
                        txtTenDichVu.setText("");
                        txtDonGia.setText("");
                        jSpinner1.setValue(1);
                }

        }//GEN-LAST:event_btnXoaActionPerformed

        private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
                // TODO add your handling code here:
                if (Integer.parseInt(jSpinner1.getValue().toString()) < 1) {
                        jSpinner1.setValue(1);
                }
        }//GEN-LAST:event_jSpinner1StateChanged

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
                        java.util.logging.Logger.getLogger(SuDungDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(SuDungDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(SuDungDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(SuDungDichVuJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                SuDungDichVuJDialog dialog = new SuDungDichVuJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JButton btnDat;
        private javax.swing.JButton btnMoi;
        private javax.swing.JButton btnThemDVMoi;
        private javax.swing.JButton btnXacNhanDichVu;
        private javax.swing.JButton btnXoa;
        private javax.swing.JButton btnXoa1;
        private javax.swing.JButton btnXoaDV;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel16;
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
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JSpinner jSpinner1;
        private javax.swing.JTabbedPane panel;
        private javax.swing.JScrollPane tblDanhSach;
        private javax.swing.JTable tblDanhSachCacDichVu;
        private javax.swing.JTable tblDanhSachChiTiet;
        private javax.swing.JTable tblGioHang;
        public javax.swing.JTable tblLichSu;
        private javax.swing.JTextField txtDonGia;
        private javax.swing.JTextField txtMaDatPhong;
        private javax.swing.JTextField txtMaDichVu;
        private javax.swing.JTextField txtMaSuDungDV;
        private javax.swing.JTextField txtNgayDat;
        private javax.swing.JTextField txtTenDichVu;
        private javax.swing.JTextField txtTimKiem;
        private javax.swing.JTextField txtTongTien;
        private javax.swing.JTextField txtTongTien1;
        // End of variables declaration//GEN-END:variables
     void loadTableDichVu() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachCacDichVu.getModel();
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

        boolean checkAllForm() {
                // trống ngày đặt
                if (txtNgayDat.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày đặt !");
                        txtNgayDat.requestFocus();
                        return false;
                }
                // kiểm tra nhập định dạng ngày đặt
                if (!GenericValidator.isDate(txtNgayDat.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày định dạng (dd/MM/yyyy)!");
                        txtNgayDat.requestFocus();
                        return false;
                }

                // kiểm tra nhập ngày hiện tại
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.now();
                String ngayHienTai = format.format(localDate);
                if (xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy").compareTo(xDate.stringToDate(ngayHienTai, "dd/MM/yyyy")) < 0) {
                        MsgBox.alert(this, "Không thể đặt dịch vụ cho ngày đã qua !");
                        return false;
                }

                // trống ngày sử dụng dịch vụ
                if (txtMaSuDungDV.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã sử dụng!");
                        txtMaSuDungDV.requestFocus();
                        return false;
                }

                // trống mã đặt phòng
                if (txtMaDatPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đặt phòng!");
                        txtMaDatPhong.requestFocus();
                        return false;
                }

                // trống mã dịch vụ
                if (txtMaDichVu.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã dịch vụ!");
                        txtMaDichVu.requestFocus();
                        return false;
                }

                // trống tên dịch vụ
                if (txtTenDichVu.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập tên dịch vụ!");
                        return false;
                }
                try {
                        if (Integer.parseInt(jSpinner1.getValue().toString()) <= 0) {
                                MsgBox.alert(this, "Nhập số lượng!");
                                jSpinner1.requestFocus();
                                return false;
                        }
                } catch (Exception e) {
                        MsgBox.alert(this, "Nhập số lượng!");
                        jSpinner1.requestFocus();
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

                return true;
        }

        private void showDaTaGioHang() {
                DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
                model.setRowCount(0);
                for (GioHang x : lstGH) {
                        Object[] row = {
                                x.getMaDichVu(),
                                x.getTen(),
                                x.getGia(),
                                x.getSoluong(),
                                xDate.dateToString(x.getNgaySuDungDV(), "dd/MM/yyyy"),
                                x.getGia() * x.getSoluong()

                        };
                        model.addRow(row);
                }
        }

        boolean dieuKienXuatHang(String maDichVu) {

                // kiểm tra nếu số lượng hàng hoá = 0 thì không cho đặt hàng
                if (!txtMaDichVu.getText().startsWith("DVB")) {
                        return true;
                }
                KhoHang kh = khDAO.SelectKhoHangByMaDV(maDichVu);
                if (kh.getSoLuongTon() <= 0) {
                        MsgBox.alert(this, "Số lượng mặt hàng này không còn !");
                        return false;
                }

                // kiểm tra số lượng tồn của mặt hàng còn đủ với yêu cầu hay không 
                int soLuongDatHang = Integer.parseInt(jSpinner1.getValue().toString());
                String tenMatHang = dvDAO.SelectTenDV(maDichVu);
                if (soLuongDatHang > kh.getSoLuongTon()) {
                        MsgBox.alert(this, "Mặt hàng : " + tenMatHang + "\nHiện tại không đủ để đáp ứng yêu cầu ! \n Số lượng hiện tại : " + kh.getSoLuongTon());
                        return false;
                }

                return true;
        }

}
