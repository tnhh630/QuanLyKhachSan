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
 * @author Admin
 */
public class NhapXuat {

        private String MaNhapXuat;
        private String MaDichVu;
        private int SoLuong;
        private Double DonGia;
        private Date NgayNhapXuat = xDate.addDays(new Date(), -365 * 20);

        public NhapXuat() {
        }

        public NhapXuat(String MaNhapXuat, String MaDichVu, int SoLuong, Double DonGia) {
                this.MaNhapXuat = MaNhapXuat;
                this.MaDichVu = MaDichVu;
                this.SoLuong = SoLuong;
                this.DonGia = DonGia;
        }

        public String getMaNhapXuat() {
                return MaNhapXuat;
        }

        public void setMaNhapXuat(String MaNhapXuat) {
                this.MaNhapXuat = MaNhapXuat;
        }

        public String getMaDichVu() {
                return MaDichVu;
        }

        public void setMaDichVu(String MaDichVu) {
                this.MaDichVu = MaDichVu;
        }

        public int getSoLuong() {
                return SoLuong;
        }

        public void setSoLuong(int SoLuong) {
                this.SoLuong = SoLuong;
        }

        public Double getDonGia() {
                return DonGia;
        }

        public void setDonGia(Double DonGia) {
                this.DonGia = DonGia;
        }

        public Date getNgayNhapXuat() {
                return NgayNhapXuat;
        }

        public void setNgayNhapXuat(Date NgayNhapXuat) {
                this.NgayNhapXuat = NgayNhapXuat;
        }

}
