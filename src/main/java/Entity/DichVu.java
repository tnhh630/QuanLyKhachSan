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
public class DichVu {

        private String maDichVu;
        private String tenLoaiDV;
        private double donGia;

        public DichVu() {
        }

        public DichVu(String maDichVu, String tenLoaiDV, double donGia) {
                this.maDichVu = maDichVu;
                this.tenLoaiDV = tenLoaiDV;
                this.donGia = donGia;
        }

        public String getMaDichVu() {
                return maDichVu;
        }

        public void setMaDichVu(String maDichVu) {
                this.maDichVu = maDichVu;
        }

        public String getTenLoaiDV() {
                return tenLoaiDV;
        }

        public void setTenLoaiDV(String tenLoaiDV) {
                this.tenLoaiDV = tenLoaiDV;
        }

        public double getDonGia() {
                return donGia;
        }

        public void setDonGia(double donGia) {
                this.donGia = donGia;
        }

}
