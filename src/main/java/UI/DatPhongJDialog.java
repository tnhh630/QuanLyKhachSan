/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.ChiTietSDDVDAO;
import DAO.DatPhongDAO;
import DAO.KhachHangDAO;
import DAO.KhoHangDAO;
import DAO.LoaiPhongDAO;
import DAO.PhongDAO;
import DAO.SuDungDVDAO;
import Entity.CT_SuDungDV;
import Entity.DatPhong;
import Entity.KhachHang;
import Entity.Phong;
import Entity.SuDungDV;
import Helper.Auth;
import Helper.MsgBox;
import Helper.xDate;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DatPhongJDialog extends javax.swing.JDialog {

        /**
         * Creates new form DatPhongJDiolog
         */
        public DatPhongJDialog(java.awt.Frame parent, boolean modal) {
                super(parent, modal);
                initComponents();
                setLocationRelativeTo(null);
                setResizable(false);
                setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo1.png")));
                this.capNhatTable();
                init();
                loadMaDatPhong();
        }
        PhongDAO pDAO = new PhongDAO();
        DatPhongDAO dpDAO = new DatPhongDAO();
        LoaiPhongDAO lpDAO = new LoaiPhongDAO();
        SuDungDVDAO sdDAO = new SuDungDVDAO();
        KhachHangDAO khDAO = new KhachHangDAO();

        void init() {
                initCboLoaiPhong();
                initCboTinhTrang();
                loadAllTable();
        }

        void loadAllTable() {
                loadToTableDSDatPhong();
                loadToTableDSPhongSuDung();
                loadToTableHuyPhong();
                loadToTableKhachHang();
        }

        void capNhatTable() {
                new Timer(5000, (ActionEvent e) -> {
                        loadAllTable();
                        if (chonDongDangThue != -1) {
                                tblDanhSachPhongDangThue.setRowSelectionInterval(chonDongDangThue, chonDongDangThue);
                        }
                        if (dongDatPhong != -1) {
                                tblDanhSachDatPhong.setRowSelectionInterval(dongDatPhong, dongDatPhong);
                        }
                        if (chonKhachChoDatPhong != -1) {
                                tblKhachHang.setRowSelectionInterval(chonKhachChoDatPhong, chonKhachChoDatPhong);
                        }
                }).start();
        }

        void loadToTableKhachHang() {
                DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (!txtTimKiemKhachHang.getText().trim().isEmpty()) {
                        keyword = txtTimKiemKhachHang.getText();
                }
                List<KhachHang> lstKhachHang = khDAO.SearchKhachHangCDP(keyword);
                for (KhachHang khachHang : lstKhachHang) {
                        Object[] row = {
                                khachHang.getMaKhachHang(),
                                khachHang.getTenKhachHang(),
                                khachHang.getSDT()
                        };
                        model.addRow(row);
                }
        }

        void loadMaDatPhong() {
                List<DatPhong> lst = dpDAO.SelectAll();
                String mdp = lst.get(lst.size() - 1).getMaDatPhong();
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
                txtMaDatPhong.setText(maDatPhong);
                txtDatCoc.setText("0");
        }

        void initCboTinhTrang() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboTinhTrang.getModel();
                model.removeAllElements();
                String[] data = new String[]{
                        "Đặt Trước", // 0
                        "Đã Nhân Phòng", // 1 
                        "Huỷ Đặt Phòng" // 2
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

        void initCboLoaiPhong() {
                DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiPhong.getModel();
                model.removeAllElements();
                List<String> lst = lpDAO.SelectAll_MaLoaiPhong();
                for (String str : lst) {
                        model.addElement(str);
                }
                cboLoaiPhong.setRenderer(new DefaultListCellRenderer() { // format nằm giữa 
                        public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean b, boolean b1) {
                                JLabel rendrlbl = (JLabel) super.getListCellRendererComponent(jlist, o, i, b, b1);
                                rendrlbl.setHorizontalAlignment(SwingConstants.CENTER);
                                return rendrlbl;
                        }
                });
        }

        void loadToTablePhongTrong() {
                DefaultTableModel model = (DefaultTableModel) tblPhongTrong.getModel();
                model.setRowCount(0);
                List<Phong> lstPhong = pDAO.SelectPhongTrong(cboLoaiPhong.getSelectedItem().toString());
                for (Phong p : lstPhong) {
                        Object[] row = {
                                p.getMaPhong(),
                                p.getTenPhong()
                        };
                        model.addRow(row);
                }
        }

        void loadToTableDSDatPhong() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachDatPhong.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (!txtTimKiemDatPhong.getText().trim().isEmpty()) {
                        keyword = txtTimKiemDatPhong.getText();
                }
                List<Object[]> lst = dpDAO.getSpDanhSachDatPhong(keyword);
                for (Object[] row : lst) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4]
                        });
                }
        }

        void loadToTableDSPhongSuDung() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachPhongDangThue.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (!txtTimKiemPhongDangThue.getText().trim().isEmpty()) {
                        keyword = txtTimKiemPhongDangThue.getText();
                }
                List<Object[]> lst = dpDAO.getSpDanhSachPhongDangThue(keyword);
                for (Object[] row : lst) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4],
                                row[5]
                        });
                }
        }

        boolean checkFormTimPhong() {
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

                // trống ngày trả
                if (txtNgayTraPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày trả  !");
                        txtNgayTraPhong.requestFocus();
                        return false;
                }
                // kiểm tra nhập định dạng ngày trả
                if (!GenericValidator.isDate(txtNgayTraPhong.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày định dạng (dd/MM/yyyy)!");
                        txtNgayTraPhong.requestFocus();
                        return false;
                }
                // kiểm tra ngày đặt bé hơn ngày trả
                Date ngayDat = (xDate.stringToDate(txtNgayTraPhong.getText(), "dd/MM/yyyy"));
                Date ngayTra = (xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                if (ngayDat.compareTo(ngayTra) <= 0) {
                        MsgBox.alert(this, "Ngày đặt không thể trước ngày tra phòng !");
                        return false;
                }
                return true;
        }

        void loadToTableGoiY() {
                DefaultTableModel model = (DefaultTableModel) tblPhongSapTra.getModel();
                model.setRowCount(0);
                String maLoaiPhong = cboLoaiPhong.getSelectedItem().toString();
                Date ngayDat = (xDate.stringToDate(txtNgayTraPhong.getText(), "dd/MM/yyyy"));
                Date ngayTra = (xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                List<Object[]> lst = dpDAO.getSpGoiYPhong(maLoaiPhong, ngayDat, ngayTra);
                for (Object[] row : lst) {
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2]
                        });
                }
        }

        void loadToTableLichSu(int viTri, String maPhong) {
                DefaultTableModel model = (DefaultTableModel) tblLichSuDung.getModel();
                model.setRowCount(0);
                String maLoaiPhong = cboLoaiPhong.getSelectedItem().toString();
                Date ngayDat = (xDate.stringToDate(txtNgayTraPhong.getText(), "dd/MM/yyyy"));
                Date ngayTra = (xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                List<Object[]> lst = dpDAO.getLichSuDungPhong(maPhong, maLoaiPhong, ngayDat, ngayTra);
                for (Object[] row : lst) {
                        String tinhTrang = "";
                        if (Integer.parseInt(row[4].toString()) == 1) {
                                tinhTrang = "Đang thuê";
                        } else if (Integer.parseInt(row[4].toString()) == 0) {
                                tinhTrang = "Đã đặt trước";

                        }
                        model.addRow(new Object[]{
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                tinhTrang
                        });
                }
        }

        void loadToTableHuyPhong() {
                DefaultTableModel model = (DefaultTableModel) tblDanhSachHuyPhong.getModel();
                model.setRowCount(0);
                String keyword = "";
                if (!txtTimKiemHuyDatPhong.getText().trim().isEmpty()) {
                        keyword = txtTimKiemHuyDatPhong.getText();
                }
                List<DatPhong> lstDatPhong = dpDAO.SearchHuyPhong(keyword);
                for (DatPhong dp : lstDatPhong) {
                        Object[] row = new Object[]{
                                dp.getMaDatPhong(),
                                dp.getMaPhong(),
                                dp.getMaKhachHang(),
                                xDate.dateToString(dp.getNgayDatPhong(), "dd/MM/yyyy"),
                                xDate.dateToString(dp.getNgayTraPhong(), "dd/MM/yyyy"),
                                dp.getTinhTrangDatPhong()
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

                // trống ngày trả
                if (txtNgayTraPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập ngày trả  !");
                        txtNgayTraPhong.requestFocus();
                        return false;
                }
                // kiểm tra nhập định dạng ngày trả
                if (!GenericValidator.isDate(txtNgayTraPhong.getText(), "dd/MM/yyyy", true)) {
                        MsgBox.alert(this, "Nhập ngày định dạng (dd/MM/yyyy)!");
                        txtNgayTraPhong.requestFocus();
                        return false;
                }
                // kiểm tra ngày đặt bé hơn ngày trả
                Date ngayDat = (xDate.stringToDate(txtNgayTraPhong.getText(), "dd/MM/yyyy"));
                Date ngayTra = (xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                if (ngayDat.compareTo(ngayTra) <= 0) {
                        MsgBox.alert(this, "Ngày đặt không thể trước ngày tra phòng !");
                        return false;
                }

                // kiểm tra trống mã đặt phòng
                if (txtMaDatPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đặt phòng !");
                        txtMaDatPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống mã phòng
                if (txtMaPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã phòng !");
                        txtMaPhong.requestFocus();
                        return false;
                }

                // kiểm tra trống mã khách hàng
                if (txtMaKhachHang.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã khách hàng !");
                        txtMaKhachHang.requestFocus();
                        return false;
                }

                // kiểm tra trống đặt cọc,
                if (txtDatCoc.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập đặt cọc !");
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

        void clearForm() {
                cboLoaiPhong.setSelectedItem(0);
                txtNgayDat.setText("");
                txtNgayTraPhong.setText("");
                txtMaDatPhong.setText("");
                txtMaPhong.setText("");
                txtMaKhachHang.setText("");
                txtDatCoc.setText("");
                cboTinhTrang.setSelectedIndex(0);
                txtGhiChu.setText("");
                DefaultTableModel model = (DefaultTableModel) tblLichSuDung.getModel();
                model.setRowCount(0);
                DefaultTableModel model1 = (DefaultTableModel) tblPhongSapTra.getModel();
                model1.setRowCount(0);
                DefaultTableModel model2 = (DefaultTableModel) tblPhongTrong.getModel();
                model2.setRowCount(0);
        }

        void setForm(DatPhong dp) {
                cboLoaiPhong.setSelectedItem(dp.getMaLoaiPhong());
                txtNgayDat.setText(xDate.dateToString(dp.getNgayDatPhong(), "dd/MM/yyyy"));
                txtNgayTraPhong.setText(xDate.dateToString(dp.getNgayTraPhong(), "dd/MM/yyyy"));
                txtMaDatPhong.setText(dp.getMaDatPhong());
                txtMaPhong.setText(dp.getMaPhong());
                txtMaKhachHang.setText(dp.getMaKhachHang());
                txtDatCoc.setText(String.valueOf(String.format("%.2f", dp.getDatCocPhong())));
                cboTinhTrang.setSelectedIndex(dp.getTinhTrangDatPhong());
                txtGhiChu.setText(dp.getGhiChu());
        }

        DatPhong getForm() {
                DatPhong dp = new DatPhong();
                dp.setMaDatPhong(txtMaDatPhong.getText());
                dp.setMaLoaiPhong(cboLoaiPhong.getSelectedItem().toString());
                dp.setMaPhong(txtMaPhong.getText());
                dp.setNgayDatPhong(xDate.stringToDate(txtNgayDat.getText(), "dd/MM/yyyy"));
                dp.setNgayTraPhong(xDate.stringToDate(txtNgayTraPhong.getText(), "dd/MM/yyyy"));
                dp.setDatCocPhong(Double.parseDouble(txtDatCoc.getText()));
                dp.setTinhTrangDatPhong(cboTinhTrang.getSelectedIndex());
                dp.setMaKhachHang(txtMaKhachHang.getText());
                dp.setGhiChu(dp.getGhiChu());
                return dp;
        }

        void insert() {
                if (!checkAllForm()) {
                        return;
                }
                try {
                        // Thêm đặt phòng
                        DatPhong dp = getForm();
                        dpDAO.insert(dp);

                        Phong p = pDAO.SelectByID(dp.getMaPhong());
                        if (p.getMaTinhTrang() == 0 || p.getMaTinhTrang() == 1) {
                                pDAO.capNhatTinhTrangPhong(1, p.getMaPhong());
                        }
                        if (p.getMaTinhTrang() == 2 || p.getMaTinhTrang() == 3) {
                                pDAO.capNhatTinhTrangPhong(3, p.getMaPhong());
                        }
                        loadAllTable();
                        clearForm();
                        MsgBox.alert(this, "Đặt phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Đặt phòng thất bại !");
                }
        }

        void update() {
                if (!checkAllForm()) {
                        return;
                }
                try {
                        DatPhong dp = getForm();
                        dpDAO.update(dp);
                        if (cboTinhTrang.getSelectedIndex() == 1) {
                                try {
                                        String maDatPhong = txtMaDatPhong.getText();
                                        dpDAO.nhanPhong(maDatPhong); // DatPhong.MaTinhTrang = 1
                                        dp = dpDAO.SelectByID(maDatPhong);
                                        Phong p = pDAO.SelectByID(dp.getMaPhong());
                                        int soLuongDatPhong = pDAO.getSoLuongDatPhong(dp.getMaPhong());
                                        if (soLuongDatPhong < 1) {
                                                pDAO.capNhatTinhTrangPhong(2, dp.getMaPhong());
                                        } else if (soLuongDatPhong >= 1) {
                                                pDAO.capNhatTinhTrangPhong(3, dp.getMaPhong());
                                        }
                                        MsgBox.alert(this, "Nhận phòng thành công !");
                                } catch (Exception e) {
                                        MsgBox.alert(this, "Nhận phòng thất bại !");
                                }
                        } else if (cboTinhTrang.getSelectedIndex() == 2) {
                                cancel();
                        }
                        loadAllTable();
                        clearForm();
                        MsgBox.alert(this, "Cập nhật thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại !");
                }
        }

        void cancel() {
                if (txtMaDatPhong.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Nhập mã đặt phòng cần huỷ !");
                        txtMaDatPhong.requestFocus();
                        return;
                }
                if (MsgBox.confirm(this, "Bạn có chắc muốn huỷ đặt phòng ?")) {
                        try {
                                String maDatPhong = txtMaDatPhong.getText();
                                DatPhong dp = dpDAO.SelectByID(maDatPhong);
                                dpDAO.cancel(maDatPhong);
                                Phong p = pDAO.SelectByID(dp.getMaPhong());
                                int soLuongDatPhong = pDAO.getSoLuongDatPhong(dp.getMaPhong());
                                if (p.getMaTinhTrang() == 1 && soLuongDatPhong < 1) {
                                        pDAO.capNhatTinhTrangPhong(0, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 1 && soLuongDatPhong >= 1) {
                                        pDAO.capNhatTinhTrangPhong(1, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 3 && soLuongDatPhong < 1) {
                                        pDAO.capNhatTinhTrangPhong(2, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 3 && soLuongDatPhong != 0) {
                                        pDAO.capNhatTinhTrangPhong(3, dp.getMaPhong());
                                }
                                loadAllTable();
                                clearForm();
                                MsgBox.alert(this, "Huỷ thành công !");
                        } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.alert(this, "Huỷ thất bại !");
                        }
                }
        }

        void TimPhong() {
                if (!checkFormTimPhong()) {
                        return;
                }
                loadToTablePhongTrong();
                loadToTableGoiY();
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                popupDanhSachDatPhong = new javax.swing.JPopupMenu();
                mni_NhanPhong = new javax.swing.JMenuItem();
                mni_HuyPhong = new javax.swing.JMenuItem();
                mni_DatThemPhong_DSDP = new javax.swing.JMenuItem();
                popupDanhSachPhongDangThue = new javax.swing.JPopupMenu();
                mni_ThanhToan = new javax.swing.JMenuItem();
                mni_DichVu = new javax.swing.JMenuItem();
                mni_DatThemmPhong_PDT = new javax.swing.JMenuItem();
                jPanel5 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel1 = new javax.swing.JPanel();
                pnlTimPhong = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                cboLoaiPhong = new javax.swing.JComboBox<>();
                txtNgayDat = new javax.swing.JTextField();
                btnTimPhong = new javax.swing.JButton();
                jLabel7 = new javax.swing.JLabel();
                txtNgayTraPhong = new javax.swing.JTextField();
                pnlThongTin = new javax.swing.JPanel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                txtMaPhong = new javax.swing.JTextField();
                txtMaDatPhong = new javax.swing.JTextField();
                txtMaKhachHang = new javax.swing.JTextField();
                jLabel8 = new javax.swing.JLabel();
                txtDatCoc = new javax.swing.JTextField();
                jLabel10 = new javax.swing.JLabel();
                btnThem = new javax.swing.JButton();
                btnHuy = new javax.swing.JButton();
                btnSua = new javax.swing.JButton();
                btnMoi = new javax.swing.JButton();
                jScrollPane7 = new javax.swing.JScrollPane();
                txtGhiChu = new javax.swing.JTextArea();
                jLabel9 = new javax.swing.JLabel();
                cboTinhTrang = new javax.swing.JComboBox<>();
                pnlPhong = new javax.swing.JPanel();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblPhongSapTra = new javax.swing.JTable();
                jScrollPane3 = new javax.swing.JScrollPane();
                tblPhongTrong = new javax.swing.JTable();
                jLabel11 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                pnlLichSu = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                jScrollPane4 = new javax.swing.JScrollPane();
                tblLichSuDung = new javax.swing.JTable();
                jPanel6 = new javax.swing.JPanel();
                jScrollPane8 = new javax.swing.JScrollPane();
                tblKhachHang = new javax.swing.JTable();
                jLabel15 = new javax.swing.JLabel();
                txtTimKiemKhachHang = new javax.swing.JTextField();
                jPanel2 = new javax.swing.JPanel();
                jScrollPane5 = new javax.swing.JScrollPane();
                tblDanhSachDatPhong = new javax.swing.JTable();
                jLabel16 = new javax.swing.JLabel();
                txtTimKiemDatPhong = new javax.swing.JTextField();
                jPanel3 = new javax.swing.JPanel();
                jScrollPane6 = new javax.swing.JScrollPane();
                tblDanhSachPhongDangThue = new javax.swing.JTable();
                jLabel17 = new javax.swing.JLabel();
                txtTimKiemPhongDangThue = new javax.swing.JTextField();
                jPanel4 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tblDanhSachHuyPhong = new javax.swing.JTable();
                jLabel18 = new javax.swing.JLabel();
                txtTimKiemHuyDatPhong = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();

                mni_NhanPhong.setText("Nhận Phòng");
                mni_NhanPhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_NhanPhongActionPerformed(evt);
                        }
                });
                popupDanhSachDatPhong.add(mni_NhanPhong);

                mni_HuyPhong.setText("Huỷ Phòng");
                mni_HuyPhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_HuyPhongActionPerformed(evt);
                        }
                });
                popupDanhSachDatPhong.add(mni_HuyPhong);

                mni_DatThemPhong_DSDP.setText("Đặt Thêm Phòng");
                mni_DatThemPhong_DSDP.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_DatThemPhong_DSDPActionPerformed(evt);
                        }
                });
                popupDanhSachDatPhong.add(mni_DatThemPhong_DSDP);

                mni_ThanhToan.setText("Thanh Toán");
                mni_ThanhToan.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_ThanhToanActionPerformed(evt);
                        }
                });
                popupDanhSachPhongDangThue.add(mni_ThanhToan);

                mni_DichVu.setText("Thêm Dịch Vụ");
                mni_DichVu.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_DichVuActionPerformed(evt);
                        }
                });
                popupDanhSachPhongDangThue.add(mni_DichVu);

                mni_DatThemmPhong_PDT.setText("Đặt Thêm Phòng");
                mni_DatThemmPhong_PDT.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                mni_DatThemmPhong_PDTActionPerformed(evt);
                        }
                });
                popupDanhSachPhongDangThue.add(mni_DatThemmPhong_PDT);

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("GodEdoc_Đặt Phòng");

                jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(255, 0, 51));
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/online-booking.png"))); // NOI18N
                jLabel1.setText("QUẢN LÝ ĐẶT PHÒNG");
                jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 250, -1));

                pnlTimPhong.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

                jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel2.setText("Loại Phòng");

                jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel3.setText("Ngày Đặt");

                cboLoaiPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                btnTimPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                btnTimPhong.setForeground(new java.awt.Color(255, 0, 51));
                btnTimPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/tim.png"))); // NOI18N
                btnTimPhong.setText("Tìm Phòng");
                btnTimPhong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                btnTimPhong.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                btnTimPhong.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnTimPhongActionPerformed(evt);
                        }
                });

                jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel7.setText("Ngày Trả");

                javax.swing.GroupLayout pnlTimPhongLayout = new javax.swing.GroupLayout(pnlTimPhong);
                pnlTimPhong.setLayout(pnlTimPhongLayout);
                pnlTimPhongLayout.setHorizontalGroup(
                        pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTimPhongLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cboLoaiPhong, 0, 157, Short.MAX_VALUE)
                                        .addComponent(txtNgayDat)
                                        .addComponent(txtNgayTraPhong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTimPhong)
                                .addContainerGap())
                );
                pnlTimPhongLayout.setVerticalGroup(
                        pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTimPhongLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTimPhongLayout.createSequentialGroup()
                                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(cboLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlTimPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(txtNgayTraPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(btnTimPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                );

                pnlThongTin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

                jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel4.setText("Mã Đặt Phòng");

                jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel5.setText("Mã Phòng");

                jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel6.setText("Mã Khách Hàng");

                txtMaDatPhong.setEditable(false);

                jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel8.setText("Đặt Cọc");

                jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                jLabel10.setText("Ghi Chú");

                btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1add.png"))); // NOI18N
                btnThem.setText("Thêm");
                btnThem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnThemActionPerformed(evt);
                        }
                });

                btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/1file.png"))); // NOI18N
                btnHuy.setText("Huỷ");
                btnHuy.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnHuyActionPerformed(evt);
                        }
                });

                btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/repair.png"))); // NOI18N
                btnSua.setText("Sửa");
                btnSua.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSuaActionPerformed(evt);
                        }
                });

                btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add-file.png"))); // NOI18N
                btnMoi.setText("Mới");
                btnMoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnMoiActionPerformed(evt);
                        }
                });

                txtGhiChu.setColumns(3);
                txtGhiChu.setRows(3);
                jScrollPane7.setViewportView(txtGhiChu);

                jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel9.setText("Tình Trạng");

                cboTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
                pnlThongTin.setLayout(pnlThongTinLayout);
                pnlThongTinLayout.setHorizontalGroup(
                        pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane7))
                                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel4))
                                                .addGap(26, 26, 26)
                                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtMaPhong)
                                                        .addComponent(txtMaDatPhong)))
                                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                                .addComponent(btnThem)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnHuy)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSua)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnMoi)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(60, 60, 60)
                                                .addComponent(txtDatCoc))
                                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cboTinhTrang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtMaKhachHang))))
                                .addContainerGap())
                );
                pnlThongTinLayout.setVerticalGroup(
                        pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtMaDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtDatCoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnThem)
                                        .addComponent(btnHuy)
                                        .addComponent(btnSua)
                                        .addComponent(btnMoi))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pnlPhong.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

                tblPhongSapTra.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Phòng", "Tên Phòng", "Ngày Trả"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblPhongSapTra.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblPhongSapTraMouseClicked(evt);
                        }
                });
                jScrollPane2.setViewportView(tblPhongSapTra);

                tblPhongTrong.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null},
                                {null, null},
                                {null, null},
                                {null, null}
                        },
                        new String [] {
                                "Mã Phòng", "Tên Phòng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblPhongTrong.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblPhongTrongMouseClicked(evt);
                        }
                });
                jScrollPane3.setViewportView(tblPhongTrong);

                jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel11.setForeground(new java.awt.Color(0, 0, 255));
                jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/beds.png"))); // NOI18N
                jLabel11.setText("PHÒNG TRỐNG HIỆN TẠI");

                jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel12.setForeground(new java.awt.Color(0, 0, 255));
                jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/beds.png"))); // NOI18N
                jLabel12.setText("PHÒNG SẮP TRẢ");

                javax.swing.GroupLayout pnlPhongLayout = new javax.swing.GroupLayout(pnlPhong);
                pnlPhong.setLayout(pnlPhongLayout);
                pnlPhongLayout.setHorizontalGroup(
                        pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPhongLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                                .addGroup(pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlPhongLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                                        .addGroup(pnlPhongLayout.createSequentialGroup()
                                                .addGap(110, 110, 110)
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                );
                pnlPhongLayout.setVerticalGroup(
                        pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPhongLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap())
                );

                pnlLichSu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

                jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel13.setForeground(new java.awt.Color(255, 0, 51));
                jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/papyrus.png"))); // NOI18N
                jLabel13.setText("LỊCH SỬ DỤNG VÀ ĐẶT PHÒNG");

                tblLichSuDung.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Phòng", "Mã Khách", "Ngày Đặt", "Ngày Trả", "Tình Trạng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                jScrollPane4.setViewportView(tblLichSuDung);

                javax.swing.GroupLayout pnlLichSuLayout = new javax.swing.GroupLayout(pnlLichSu);
                pnlLichSu.setLayout(pnlLichSuLayout);
                pnlLichSuLayout.setHorizontalGroup(
                        pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlLichSuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLichSuLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(151, 151, 151))
                );
                pnlLichSuLayout.setVerticalGroup(
                        pnlLichSuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlLichSuLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
                );

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlTimPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlLichSu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(pnlTimPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(pnlPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pnlLichSu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(54, Short.MAX_VALUE))
                );

                jTabbedPane1.addTab("ĐẶT PHÒNG", jPanel1);

                tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null},
                                {null, null, null},
                                {null, null, null},
                                {null, null, null}
                        },
                        new String [] {
                                "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblKhachHang.setShowGrid(true);
                tblKhachHang.setUpdateSelectionOnSort(false);
                tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblKhachHangMouseClicked(evt);
                        }
                });
                jScrollPane8.setViewportView(tblKhachHang);

                jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel15.setText("Tìm kiếm");

                txtTimKiemKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemKhachHangKeyReleased(evt);
                        }
                });

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(277, 277, 277)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel6Layout.setVerticalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                                .addContainerGap())
                );

                jTabbedPane1.addTab("KHÁCH CHỜ ĐẶT PHÒNG", jPanel6);

                tblDanhSachDatPhong.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null},
                                {null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Đặt Phòng", "Tên Phòng", "Mã Khách Hàng", "Ngày Đặt", "Ngày Trả"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachDatPhong.setComponentPopupMenu(popupDanhSachDatPhong);
                tblDanhSachDatPhong.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachDatPhongMouseClicked(evt);
                        }
                });
                jScrollPane5.setViewportView(tblDanhSachDatPhong);

                jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel16.setText("Tìm kiếm");

                txtTimKiemDatPhong.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemDatPhongKeyReleased(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(273, 273, 273)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiemDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(txtTimKiemDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                jTabbedPane1.addTab("DANH SÁCH ĐẶT PHÒNG", jPanel2);

                tblDanhSachPhongDangThue.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Đặt Phòng", "Mã Phòng", "Tên Phòng", "Mã Khách Hàng", "Ngày Đặt", "Ngày Trả"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachPhongDangThue.setComponentPopupMenu(popupDanhSachPhongDangThue);
                tblDanhSachPhongDangThue.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachPhongDangThueMouseClicked(evt);
                        }
                });
                jScrollPane6.setViewportView(tblDanhSachPhongDangThue);

                jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel17.setText("Tìm kiếm");

                txtTimKiemPhongDangThue.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemPhongDangThueKeyReleased(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(274, 274, 274)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiemPhongDangThue, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel17)
                                        .addComponent(txtTimKiemPhongDangThue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                jTabbedPane1.addTab("DANH SÁCH PHÒNG ĐANG THUÊ", jPanel3);

                tblDanhSachHuyPhong.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null},
                                {null, null, null, null, null, null}
                        },
                        new String [] {
                                "Mã Đặt Phòng", "Mã Phòng", "Mã Khách Hàng", "Ngày Đặt", "Ngày Trả", "Tình Trạng"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                tblDanhSachHuyPhong.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachHuyPhongMouseClicked(evt);
                        }
                });
                jScrollPane1.setViewportView(tblDanhSachHuyPhong);

                jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ticket.png"))); // NOI18N
                jLabel18.setText("Tìm kiếm");

                txtTimKiemHuyDatPhong.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                txtTimKiemHuyDatPhongKeyReleased(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(274, 274, 274)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimKiemHuyDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18)
                                        .addComponent(txtTimKiemHuyDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                                .addContainerGap())
                );

                jTabbedPane1.addTab("DANH SÁCH HỦY ĐẶT PHÒNG", jPanel4);

                jPanel5.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1010, 590));

                jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bk.jpg"))); // NOI18N
                jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 680));

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

        private void btnTimPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimPhongActionPerformed
                // TODO add your handling code here:
                TimPhong();
        }//GEN-LAST:event_btnTimPhongActionPerformed

        private void tblPhongSapTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongSapTraMouseClicked
                // TODO add your handling code here:
                tblPhongTrong.clearSelection();
                if (evt.getClickCount() == 2) {
                        try {
                                int viTri = tblPhongSapTra.getSelectedRow();
                                String maPhong = tblPhongSapTra.getValueAt(viTri, 0).toString();
                                loadToTableLichSu(viTri, maPhong);
                        } catch (Exception e) {
                        }

                }
        }//GEN-LAST:event_tblPhongSapTraMouseClicked

        private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
                // TODO add your handling code here:
                insert();
        }//GEN-LAST:event_btnThemActionPerformed

        private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
                // TODO add your handling code here:
                cancel();
        }//GEN-LAST:event_btnHuyActionPerformed

        private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
                // TODO add your handling code here:
                update();
        }//GEN-LAST:event_btnSuaActionPerformed

        private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
                // TODO add your handling code here:
                clearForm();
                loadMaDatPhong();
        }//GEN-LAST:event_btnMoiActionPerformed

        int dongDatPhong = -1;
        private void tblDanhSachDatPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachDatPhongMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int n = tblDanhSachDatPhong.getSelectedRow();
                        dongDatPhong = n;
                        String maDatPhong = tblDanhSachDatPhong.getValueAt(n, 0).toString();
                        DatPhong dp = dpDAO.SelectByID(maDatPhong);
                        setForm(dp);
                        jTabbedPane1.setSelectedIndex(0);
                } else {
                        int n = tblDanhSachDatPhong.getSelectedRow();
                        dongDatPhong = n;
                }
        }//GEN-LAST:event_tblDanhSachDatPhongMouseClicked

        int chonDongDangThue = -1;
        private void tblDanhSachPhongDangThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachPhongDangThueMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int n = tblDanhSachPhongDangThue.getSelectedRow();
                        chonDongDangThue = n;
                        String maDatPhong = tblDanhSachPhongDangThue.getValueAt(n, 0).toString();
                        DatPhong dp = dpDAO.SelectByID(maDatPhong);
                        setForm(dp);
                        jTabbedPane1.setSelectedIndex(0);
                } else {
                        int n = tblDanhSachPhongDangThue.getSelectedRow();
                        chonDongDangThue = n;
                }
        }//GEN-LAST:event_tblDanhSachPhongDangThueMouseClicked

        private void tblDanhSachHuyPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachHuyPhongMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        int n = tblDanhSachHuyPhong.getSelectedRow();
                        String maDatPhong = tblDanhSachHuyPhong.getValueAt(n, 0).toString();
                        DatPhong dp = dpDAO.SelectByID(maDatPhong);
                        setForm(dp);
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_tblDanhSachHuyPhongMouseClicked

        ChiTietSDDVDAO ctDAO = new ChiTietSDDVDAO();
        KhoHangDAO khoDAO = new KhoHangDAO();
        private void mni_NhanPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_NhanPhongActionPerformed
                // TODO add your handling code here:
                try {

                        int rowSelected = tblDanhSachDatPhong.getSelectedRow();
                        if (rowSelected == -1) {
                                MsgBox.alert(this, "Vui lòng chọn phòng cần nhận phòng !");
                                return;
                        }
                        String maDatPhong = tblDanhSachDatPhong.getValueAt(rowSelected, 0).toString();
                        DatPhong dp1 = dpDAO.SelectByID(maDatPhong);
                        Phong p1 = pDAO.SelectByID(dp1.getMaPhong());
                        if (p1.getMaTinhTrang() == 3 || p1.getMaTinhTrang() == 2) {
                                MsgBox.alert(this, "Phòng đang sử dụng không thể nhận phòng !");
                                return;
                        }

                        dpDAO.nhanPhong(maDatPhong); // DatPhong.MaTinhTrang = 1
                        DatPhong dp = dpDAO.SelectByID(maDatPhong);
                        Phong p = pDAO.SelectByID(dp.getMaPhong());
                        // thêm phòng => thêm mã sử dụng dịch vụ
                        SuDungDV sddv = new SuDungDV();
                        sddv.setMaSuDungDV(maDatPhong);
                        sddv.setMaDatPhong(maDatPhong);
                        sdDAO.insert(sddv);
                        // Dịch vụ cơ bản - 2 nước suối free
                        CT_SuDungDV ct = new CT_SuDungDV();
                        ct.setSoLuong(2);
                        ct.setMaDichVu("DVB00");
                        ct.setNgaySuDungDV(dp.getNgayDatPhong());
                        ct.setMaSuDungDV(maDatPhong);
                        ctDAO.insert(ct);
                        khoDAO.updateXuatKhoHang(2, "DVB00");

                        //--------------------------
                        int soLuongDatPhong = pDAO.getSoLuongDatPhong(dp.getMaPhong());
                        if (soLuongDatPhong < 1) {
                                pDAO.capNhatTinhTrangPhong(2, dp.getMaPhong());
                        } else if (soLuongDatPhong >= 1) {
                                pDAO.capNhatTinhTrangPhong(3, dp.getMaPhong());
                        }
                        loadAllTable();
                        MsgBox.alert(this, "Nhận phòng thành công !");
                } catch (Exception e) {
                        MsgBox.alert(this, "Nhận phòng thất bại !");
                }
        }//GEN-LAST:event_mni_NhanPhongActionPerformed

        private void mni_HuyPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_HuyPhongActionPerformed
                // TODO add your handling code here:
                int rowSelected = tblDanhSachDatPhong.getSelectedRow();
                if (rowSelected == -1) {
                        MsgBox.alert(this, "Vui lòng chọn phòng cần hủy !");
                        return;
                }
                String maDatPhong = tblDanhSachDatPhong.getValueAt(rowSelected, 0).toString();

                if (MsgBox.confirm(this, "Bạn có chắc muốn huỷ đặt phòng ?")) {
                        try {
                                DatPhong dp = dpDAO.SelectByID(maDatPhong);
                                dpDAO.cancel(maDatPhong);
                                Phong p = pDAO.SelectByID(dp.getMaPhong());
                                int soLuongDatPhong = pDAO.getSoLuongDatPhong(dp.getMaPhong());
                                if (p.getMaTinhTrang() == 1 && soLuongDatPhong < 1) {
                                        pDAO.capNhatTinhTrangPhong(0, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 1 && soLuongDatPhong >= 1) {
                                        pDAO.capNhatTinhTrangPhong(1, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 3 && soLuongDatPhong < 1) {
                                        pDAO.capNhatTinhTrangPhong(2, dp.getMaPhong());
                                } else if (p.getMaTinhTrang() == 3 && soLuongDatPhong != 0) {
                                        pDAO.capNhatTinhTrangPhong(3, dp.getMaPhong());
                                }

                                loadAllTable();
                                clearForm();
                                MsgBox.alert(this, "Huỷ thành công !");
                        } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.alert(this, "Huỷ thất bại !");
                        }
                }

        }//GEN-LAST:event_mni_HuyPhongActionPerformed

        private void mni_ThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_ThanhToanActionPerformed
                // TODO add your handling code here:
                int row = tblDanhSachPhongDangThue.getSelectedRow();
                if (row == -1) {
                        MsgBox.alert(this, "Vui lòng chọn phòng cần thanh toán !");
                        return;
                }
                String maDatPhong = tblDanhSachPhongDangThue.getValueAt(row, 0).toString();
                DatPhong datphong = dpDAO.SelectByID(maDatPhong);
                Auth.dp = datphong;
                chonDongDangThue = -1;
                ThanhToanJDialog dialog = new ThanhToanJDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
                loadAllTable();
        }//GEN-LAST:event_mni_ThanhToanActionPerformed

        private void tblPhongTrongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongTrongMouseClicked
                // TODO add your handling code here:
                tblPhongSapTra.clearSelection();
                if (evt.getClickCount() == 2) {
                        try {
                                int viTri = tblPhongTrong.getSelectedRow();
                                String maPhong = tblPhongTrong.getValueAt(viTri, 0).toString();
                                loadToTableLichSu(viTri, maPhong);
                                txtMaPhong.setText(maPhong);
                        } catch (Exception e) {
                        }
                }

        }//GEN-LAST:event_tblPhongTrongMouseClicked

        private void mni_DichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_DichVuActionPerformed
                // TODO add your handling code here:
                int row = tblDanhSachPhongDangThue.getSelectedRow();
                if (row == -1) {
                        MsgBox.alert(this, "Vui lòng chọn phòng cần thanh toán !");
                        return;
                }
                String maDatPhong = tblDanhSachPhongDangThue.getValueAt(row, 0).toString();
                DatPhong datphong = dpDAO.SelectByID(maDatPhong);
                Auth.dp = datphong;
                chonDongDangThue = -1;
                SuDungDichVuJDialog dialog = new SuDungDichVuJDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
        }//GEN-LAST:event_mni_DichVuActionPerformed

        private void txtTimKiemKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKhachHangKeyReleased
                // TODO add your handling code here:
                loadToTableKhachHang();
        }//GEN-LAST:event_txtTimKiemKhachHangKeyReleased

        int chonKhachChoDatPhong = -1;
        private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
                // TODO add your handling code here:
                if (evt.getClickCount() == 2) {
                        loadMaDatPhong();
                        int viTri = tblKhachHang.getSelectedRow();
                        chonKhachChoDatPhong = viTri;
                        String maKhachHang = tblKhachHang.getValueAt(viTri, 0).toString();
                        txtMaKhachHang.setText(maKhachHang);
                        jTabbedPane1.setSelectedIndex(0);
                        loadAllTable();
                } else {
                        loadMaDatPhong();
                        int viTri = tblKhachHang.getSelectedRow();
                }
        }//GEN-LAST:event_tblKhachHangMouseClicked

        private void txtTimKiemHuyDatPhongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemHuyDatPhongKeyReleased
                // TODO add your handling code here:
                loadToTableHuyPhong();
        }//GEN-LAST:event_txtTimKiemHuyDatPhongKeyReleased

        private void txtTimKiemPhongDangThueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemPhongDangThueKeyReleased
                // TODO add your handling code here:
                loadToTableDSPhongSuDung();
        }//GEN-LAST:event_txtTimKiemPhongDangThueKeyReleased

        private void txtTimKiemDatPhongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemDatPhongKeyReleased
                // TODO add your handling code here:
                loadToTableDSDatPhong();
        }//GEN-LAST:event_txtTimKiemDatPhongKeyReleased

        private void mni_DatThemPhong_DSDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_DatThemPhong_DSDPActionPerformed
                // TODO add your handling code here:
                loadMaDatPhong();
                int row = tblDanhSachDatPhong.getSelectedRow();
                if (row == -1) {
                        MsgBox.alert(this, "Vui lòng chọn khách hàng !");
                } else {
                        String maKhachHang = tblDanhSachDatPhong.getValueAt(row, 2).toString();
                        txtMaKhachHang.setText(maKhachHang);
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_mni_DatThemPhong_DSDPActionPerformed

        private void mni_DatThemmPhong_PDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mni_DatThemmPhong_PDTActionPerformed
                // TODO add your handling code here:
                loadMaDatPhong();
                int row = tblDanhSachPhongDangThue.getSelectedRow();
                if (row == -1) {
                        MsgBox.alert(this, "Vui lòng chọn khách hàng !");
                } else {
                        String maKhachHang = tblDanhSachPhongDangThue.getValueAt(row, 3).toString();
                        txtMaKhachHang.setText(maKhachHang);
                        chonDongDangThue = -1;
                        jTabbedPane1.setSelectedIndex(0);
                }
        }//GEN-LAST:event_mni_DatThemmPhong_PDTActionPerformed

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
                        java.util.logging.Logger.getLogger(DatPhongJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(DatPhongJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(DatPhongJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);

                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(DatPhongJDialog.class
                                .getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>
                //</editor-fold>

                /* Create and display the dialog */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                DatPhongJDialog dialog = new DatPhongJDialog(new javax.swing.JFrame(), true);
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
        private javax.swing.JButton btnHuy;
        private javax.swing.JButton btnMoi;
        private javax.swing.JButton btnSua;
        private javax.swing.JButton btnThem;
        private javax.swing.JButton btnTimPhong;
        private javax.swing.JComboBox<String> cboLoaiPhong;
        private javax.swing.JComboBox<String> cboTinhTrang;
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
        private javax.swing.JPanel jPanel6;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JScrollPane jScrollPane6;
        private javax.swing.JScrollPane jScrollPane7;
        private javax.swing.JScrollPane jScrollPane8;
        public javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JMenuItem mni_DatThemPhong_DSDP;
        private javax.swing.JMenuItem mni_DatThemmPhong_PDT;
        private javax.swing.JMenuItem mni_DichVu;
        private javax.swing.JMenuItem mni_HuyPhong;
        private javax.swing.JMenuItem mni_NhanPhong;
        private javax.swing.JMenuItem mni_ThanhToan;
        private javax.swing.JPanel pnlLichSu;
        private javax.swing.JPanel pnlPhong;
        private javax.swing.JPanel pnlThongTin;
        private javax.swing.JPanel pnlTimPhong;
        private javax.swing.JPopupMenu popupDanhSachDatPhong;
        private javax.swing.JPopupMenu popupDanhSachPhongDangThue;
        private javax.swing.JTable tblDanhSachDatPhong;
        private javax.swing.JTable tblDanhSachHuyPhong;
        private javax.swing.JTable tblDanhSachPhongDangThue;
        private javax.swing.JTable tblKhachHang;
        private javax.swing.JTable tblLichSuDung;
        private javax.swing.JTable tblPhongSapTra;
        private javax.swing.JTable tblPhongTrong;
        private javax.swing.JTextField txtDatCoc;
        private javax.swing.JTextArea txtGhiChu;
        private javax.swing.JTextField txtMaDatPhong;
        private javax.swing.JTextField txtMaKhachHang;
        private javax.swing.JTextField txtMaPhong;
        private javax.swing.JTextField txtNgayDat;
        private javax.swing.JTextField txtNgayTraPhong;
        private javax.swing.JTextField txtTimKiemDatPhong;
        private javax.swing.JTextField txtTimKiemHuyDatPhong;
        private javax.swing.JTextField txtTimKiemKhachHang;
        private javax.swing.JTextField txtTimKiemPhongDangThue;
        // End of variables declaration//GEN-END:variables
}
