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
public class SuDungDV {

        private String maSuDungDV;
        private String maDatPhong;

        public SuDungDV() {
        }

        public SuDungDV(String maSuDungDV, String maDatPhong) {
                this.maSuDungDV = maSuDungDV;
                this.maDatPhong = maDatPhong;
        }

        public String getMaSuDungDV() {
                return maSuDungDV;
        }

        public void setMaSuDungDV(String maSuDungDV) {
                this.maSuDungDV = maSuDungDV;
        }

        public String getMaDatPhong() {
                return maDatPhong;
        }

        public void setMaDatPhong(String maDatPhong) {
                this.maDatPhong = maDatPhong;
        }

}
