/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Helper.xDate;
import java.util.Date;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class KhachHang {

        private String maKhachHang;
        private String tenKhachHang;
        private String CCCD;
        private boolean gioiTinh;
        private Date ngaySinh = xDate.addDays(new Date(), -365 * 20);
        private String quocTich;
        private String diaChi;
        private String email;
        private String SDT;
        private boolean LoaiKhach;

        public KhachHang() {
        }

        public KhachHang(String maKhachHang, String tenKhachHang, String CCCD, boolean gioiTinh, String quocTich, String diaChi, String email, String SDT, boolean LoaiKhach) {
                this.maKhachHang = maKhachHang;
                this.tenKhachHang = tenKhachHang;
                this.CCCD = CCCD;
                this.gioiTinh = gioiTinh;
                this.quocTich = quocTich;
                this.diaChi = diaChi;
                this.email = email;
                this.SDT = SDT;
                this.LoaiKhach = LoaiKhach;
        }

        public String getMaKhachHang() {
                return maKhachHang;
        }

        public void setMaKhachHang(String maKhachHang) {
                this.maKhachHang = maKhachHang;
        }

        public String getTenKhachHang() {
                return tenKhachHang;
        }

        public void setTenKhachHang(String tenKhachHang) {
                this.tenKhachHang = tenKhachHang;
        }

        public String getCCCD() {
                return CCCD;
        }

        public void setCCCD(String CCCD) {
                this.CCCD = CCCD;
        }

        public boolean isGioiTinh() {
                return gioiTinh;
        }

        public void setGioiTinh(boolean gioiTinh) {
                this.gioiTinh = gioiTinh;
        }

        public Date getNgaySinh() {
                return ngaySinh;
        }

        public void setNgaySinh(Date ngaySinh) {
                this.ngaySinh = ngaySinh;
        }

        public String getQuocTich() {
                return quocTich;
        }

        public void setQuocTich(String quocTich) {
                this.quocTich = quocTich;
        }

        public String getDiaChi() {
                return diaChi;
        }

        public void setDiaChi(String diaChi) {
                this.diaChi = diaChi;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getSDT() {
                return SDT;
        }

        public void setSDT(String SDT) {
                this.SDT = SDT;
        }

        public boolean isLoaiKhach() {
                return LoaiKhach;
        }

        public void setLoaiKhach(boolean LoaiKhach) {
                this.LoaiKhach = LoaiKhach;
        }

}
