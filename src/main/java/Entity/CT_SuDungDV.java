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
public class CT_SuDungDV {

        private int maCTSDDV;
        private int soLuong;
        private String maDichVu;
        private String maSuDungDV;
        private Date ngaySuDungDV = xDate.addDays(new Date(), -365 * 20);

        public CT_SuDungDV() {
        }

        public CT_SuDungDV(int maCTSDDV, int soLuong, String maDichVu, String maSuDungDV) {
                this.maCTSDDV = maCTSDDV;
                this.soLuong = soLuong;
                this.maDichVu = maDichVu;
                this.maSuDungDV = maSuDungDV;
        }

        public int getMaCTSDDV() {
                return maCTSDDV;
        }

        public void setMaCTSDDV(int maCTSDDV) {
                this.maCTSDDV = maCTSDDV;
        }

        public int getSoLuong() {
                return soLuong;
        }

        public void setSoLuong(int soLuong) {
                this.soLuong = soLuong;
        }

        public String getMaDichVu() {
                return maDichVu;
        }

        public void setMaDichVu(String maDichVu) {
                this.maDichVu = maDichVu;
        }

        public String getMaSuDungDV() {
                return maSuDungDV;
        }

        public void setMaSuDungDV(String maSuDungDV) {
                this.maSuDungDV = maSuDungDV;
        }

        public Date getNgaySuDungDV() {
                return ngaySuDungDV;
        }

        public void setNgaySuDungDV(Date ngaySuDungDV) {
                this.ngaySuDungDV = ngaySuDungDV;
        }

}
