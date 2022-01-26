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
 * @author 84384
 */
public class GioHang {

        private String ten;
        private double gia;
        private int soluong;
        private String MaDichVu ;
        private Date NgaySuDungDV = xDate.addDays(new Date(), -365 * 20);
        private String MaSuDungDV;
        private String maDatPhong;

        public GioHang() {
        }

        public GioHang(String ten, double gia, int soluong, String MaDichVu, String MaSuDungDV, String maDatPhong) {
                this.ten = ten;
                this.gia = gia;
                this.soluong = soluong;
                this.MaDichVu = MaDichVu;
                this.MaSuDungDV = MaSuDungDV;
                this.maDatPhong = maDatPhong;
        }

        public String getTen() {
                return ten;
        }

        public void setTen(String ten) {
                this.ten = ten;
        }

        public double getGia() {
                return gia;
        }

        public void setGia(double gia) {
                this.gia = gia;
        }

        public int getSoluong() {
                return soluong;
        }

        public void setSoluong(int soluong) {
                this.soluong = soluong;
        }

        public String getMaDichVu() {
                return MaDichVu;
        }

        public void setMaDichVu(String MaDichVu) {
                this.MaDichVu = MaDichVu;
        }

        public Date getNgaySuDungDV() {
                return NgaySuDungDV;
        }

        public void setNgaySuDungDV(Date NgaySuDungDV) {
                this.NgaySuDungDV = NgaySuDungDV;
        }

        public String getMaSuDungDV() {
                return MaSuDungDV;
        }

        public void setMaSuDungDV(String MaSuDungDV) {
                this.MaSuDungDV = MaSuDungDV;
        }

        public String getMaDatPhong() {
                return maDatPhong;
        }

        public void setMaDatPhong(String maDatPhong) {
                this.maDatPhong = maDatPhong;
        }
      
       
}
