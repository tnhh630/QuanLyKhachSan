/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class HoaDon {

        private String maDatPhong;
        private String maTaiKhoan;
        private String ptThanhToan;
        private double tongTienPhong;
        private double tongTienDichVu;
        private double thanhTien;
        private String ghiChu;

        public HoaDon() {
        }

        public HoaDon(String maDatPhong, String maTaiKhoan, String ptThanhToan, double tongTienPhong, double tongTienDichVu, double thanhTien, String ghiChu) {
                this.maDatPhong = maDatPhong;
                this.maTaiKhoan = maTaiKhoan;
                this.ptThanhToan = ptThanhToan;
                this.tongTienPhong = tongTienPhong;
                this.tongTienDichVu = tongTienDichVu;
                this.thanhTien = thanhTien;
                this.ghiChu = ghiChu;
        }


        public String getMaDatPhong() {
                return maDatPhong;
        }

        public void setMaDatPhong(String maDatPhong) {
                this.maDatPhong = maDatPhong;
        }

        public String getMaTaiKhoan() {
                return maTaiKhoan;
        }

        public void setMaTaiKhoan(String maTaiKhoan) {
                this.maTaiKhoan = maTaiKhoan;
        }

        public String getPtThanhToan() {
                return ptThanhToan;
        }

        public void setPtThanhToan(String ptThanhToan) {
                this.ptThanhToan = ptThanhToan;
        }

        public double getTongTienPhong() {
                return tongTienPhong;
        }

        public void setTongTienPhong(double tongTienPhong) {
                this.tongTienPhong = tongTienPhong;
        }

        public double getTongTienDichVu() {
                return tongTienDichVu;
        }

        public void setTongTienDichVu(double tongTienDichVu) {
                this.tongTienDichVu = tongTienDichVu;
        }

        public double getThanhTien() {
                return thanhTien;
        }

        public void setThanhTien(double thanhTien) {
                this.thanhTien = thanhTien;
        }

        public String getGhiChu() {
                return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
                this.ghiChu = ghiChu;
        }

}
