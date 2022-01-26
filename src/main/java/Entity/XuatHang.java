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
public class XuatHang {

        private String maXuatHang;
        private String maDichVu;
        private int soLuong;
        private double donGia;
        private Date ngayXuatHang = xDate.addDays(new Date(), -365 * 20);
        private boolean tinhTrang;
        private String maSuDungDV;

        public XuatHang() {
        }

        public XuatHang(String maXuatHang, String maDichVu, int soLuong, double donGia, boolean tinhTrang, String maSuDungDV) {
                this.maXuatHang = maXuatHang;
                this.maDichVu = maDichVu;
                this.soLuong = soLuong;
                this.donGia = donGia;
                this.tinhTrang = tinhTrang;
                this.maSuDungDV = maSuDungDV;
        }

        public String getMaXuatHang() {
                return maXuatHang;
        }

        public void setMaXuatHang(String maXuatHang) {
                this.maXuatHang = maXuatHang;
        }

        public String getMaDichVu() {
                return maDichVu;
        }

        public void setMaDichVu(String maDichVu) {
                this.maDichVu = maDichVu;
        }

        public int getSoLuong() {
                return soLuong;
        }

        public void setSoLuong(int soLuong) {
                this.soLuong = soLuong;
        }

        public double getDonGia() {
                return donGia;
        }

        public void setDonGia(double donGia) {
                this.donGia = donGia;
        }

        public Date getNgayXuatHang() {
                return ngayXuatHang;
        }

        public void setNgayXuatHang(Date ngayXuatHang) {
                this.ngayXuatHang = ngayXuatHang;
        }

        public boolean isTinhTrang() {
                return tinhTrang;
        }

        public void setTinhTrang(boolean tinhTrang) {
                this.tinhTrang = tinhTrang;
        }

        public String getMaSuDungDV() {
                return maSuDungDV;
        }

        public void setMaSuDungDV(String maSuDungDV) {
                this.maSuDungDV = maSuDungDV;
        }

}
