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
public class DatPhong {

        private String maDatPhong;
        private String maLoaiPhong;
        private String maPhong;
        private Date ngayDatPhong = xDate.addDays(new Date(), -365 * 20);
        private Date ngayTraPhong = xDate.addDays(new Date(), -365 * 20);
        private double datCocPhong;
        private int tinhTrangDatPhong;
        private String maKhachHang;
        private String ghiChu;

        public DatPhong() {
        }

        public DatPhong(String maDatPhong, String maLoaiPhong, String maPhong, double datCocPhong, int tinhTrangDatPhong, String maKhachHang, String ghiChu) {
                this.maDatPhong = maDatPhong;
                this.maLoaiPhong = maLoaiPhong;
                this.maPhong = maPhong;
                this.datCocPhong = datCocPhong;
                this.tinhTrangDatPhong = tinhTrangDatPhong;
                this.maKhachHang = maKhachHang;
                this.ghiChu = ghiChu;
        }

        public String getMaDatPhong() {
                return maDatPhong;
        }

        public void setMaDatPhong(String maDatPhong) {
                this.maDatPhong = maDatPhong;
        }

        public String getMaLoaiPhong() {
                return maLoaiPhong;
        }

        public void setMaLoaiPhong(String maLoaiPhong) {
                this.maLoaiPhong = maLoaiPhong;
        }

        public String getMaPhong() {
                return maPhong;
        }

        public void setMaPhong(String maPhong) {
                this.maPhong = maPhong;
        }

        public Date getNgayDatPhong() {
                return ngayDatPhong;
        }

        public void setNgayDatPhong(Date ngayDatPhong) {
                this.ngayDatPhong = ngayDatPhong;
        }

        public Date getNgayTraPhong() {
                return ngayTraPhong;
        }

        public void setNgayTraPhong(Date ngayTraPhong) {
                this.ngayTraPhong = ngayTraPhong;
        }

        public double getDatCocPhong() {
                return datCocPhong;
        }

        public void setDatCocPhong(double datCocPhong) {
                this.datCocPhong = datCocPhong;
        }

        public int getTinhTrangDatPhong() {
                return tinhTrangDatPhong;
        }

        public void setTinhTrangDatPhong(int tinhTrangDatPhong) {
                this.tinhTrangDatPhong = tinhTrangDatPhong;
        }

        public String getMaKhachHang() {
                return maKhachHang;
        }

        public void setMaKhachHang(String maKhachHang) {
                this.maKhachHang = maKhachHang;
        }

        public String getGhiChu() {
                return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
                this.ghiChu = ghiChu;
        }

}
