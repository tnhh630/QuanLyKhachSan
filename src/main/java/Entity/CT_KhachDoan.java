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
public class CT_KhachDoan {

        private String maKhachDoan;
        private String hoTenThanhVien;
        private Date ngaySinh = xDate.addDays(new Date(), -365 * 20);
        private String CCCD;
        private String maDoan;

        public CT_KhachDoan() {
        }

        public CT_KhachDoan(String maKhachDoan, String hoTenThanhVien, String CCCD, String maDoan) {
                this.maKhachDoan = maKhachDoan;
                this.hoTenThanhVien = hoTenThanhVien;
                this.CCCD = CCCD;
                this.maDoan = maDoan;
        }

        public String getMaKhachDoan() {
                return maKhachDoan;
        }

        public void setMaKhachDoan(String maKhachDoan) {
                this.maKhachDoan = maKhachDoan;
        }

        public String getHoTenThanhVien() {
                return hoTenThanhVien;
        }

        public void setHoTenThanhVien(String hoTenThanhVien) {
                this.hoTenThanhVien = hoTenThanhVien;
        }

        public Date getNgaySinh() {
                return ngaySinh;
        }

        public void setNgaySinh(Date ngaySinh) {
                this.ngaySinh = ngaySinh;
        }

        public String getCCCD() {
                return CCCD;
        }

        public void setCCCD(String CCCD) {
                this.CCCD = CCCD;
        }

        public String getMaDoan() {
                return maDoan;
        }

        public void setMaDoan(String maDoan) {
                this.maDoan = maDoan;
        }

}
