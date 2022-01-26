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
 * @author Tran Trung Nghia <PS14820>
 */
public class NhanVien {

        private String maNhanVien;
        private String tenNhanVien;
        private String CCCD;
        private boolean gioiTinh;
        private Date ngaySinh = xDate.addDays(new Date(), -365 * 20);
        private String diaChi;
        private String Email;
        private String SDT;
        private byte[] hinhAnh;
        private int chucVu;

        public NhanVien() {
        }

        public NhanVien(String maNhanVien, String tenNhanVien, String CCCD, boolean gioiTinh, String diaChi, String Email, String SDT, byte[] hinhAnh, int chucVu) {
                this.maNhanVien = maNhanVien;
                this.tenNhanVien = tenNhanVien;
                this.CCCD = CCCD;
                this.gioiTinh = gioiTinh;
                this.diaChi = diaChi;
                this.Email = Email;
                this.SDT = SDT;
                this.hinhAnh = hinhAnh;
                this.chucVu = chucVu;
        }

        public String getMaNhanVien() {
                return maNhanVien;
        }

        public void setMaNhanVien(String maNhanVien) {
                this.maNhanVien = maNhanVien;
        }

        public String getTenNhanVien() {
                return tenNhanVien;
        }

        public void setTenNhanVien(String tenNhanVien) {
                this.tenNhanVien = tenNhanVien;
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

        public String getDiaChi() {
                return diaChi;
        }

        public void setDiaChi(String diaChi) {
                this.diaChi = diaChi;
        }

        public String getEmail() {
                return Email;
        }

        public void setEmail(String Email) {
                this.Email = Email;
        }

        public String getSDT() {
                return SDT;
        }

        public void setSDT(String SDT) {
                this.SDT = SDT;
        }

        public byte[] getHinhAnh() {
                return hinhAnh;
        }

        public void setHinhAnh(byte[] hinhAnh) {
                this.hinhAnh = hinhAnh;
        }

        public int getChucVu() {
                return chucVu;
        }

        public void setChucVu(int chucVu) {
                this.chucVu = chucVu;
        }

        @Override
        public String toString() {
                return tenNhanVien;
        }

}
