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
public class DoThatLac {

        private String maDoThatLac;
        private String tenDoThatLac;
        private String maNVTimThay;
        private Date thoiGianTimThay = xDate.addDays(new Date(), -365 * 20);
        private String viTriTimThay;
        private int TinhTrang;
        private String ghiChu;

        public DoThatLac() {
        }

        public DoThatLac(String maDoThatLac, String tenDoThatLac, String maNVTimThay, String viTriTimThay, int TinhTrang, String ghiChu) {
                this.maDoThatLac = maDoThatLac;
                this.tenDoThatLac = tenDoThatLac;
                this.maNVTimThay = maNVTimThay;
                this.viTriTimThay = viTriTimThay;
                this.TinhTrang = TinhTrang;
                this.ghiChu = ghiChu;
        }

        public String getMaDoThatLac() {
                return maDoThatLac;
        }

        public void setMaDoThatLac(String maDoThatLac) {
                this.maDoThatLac = maDoThatLac;
        }

        public String getTenDoThatLac() {
                return tenDoThatLac;
        }

        public void setTenDoThatLac(String tenDoThatLac) {
                this.tenDoThatLac = tenDoThatLac;
        }

        public String getMaNVTimThay() {
                return maNVTimThay;
        }

        public void setMaNVTimThay(String maNVTimThay) {
                this.maNVTimThay = maNVTimThay;
        }

        public Date getThoiGianTimThay() {
                return thoiGianTimThay;
        }

        public void setThoiGianTimThay(Date thoiGianTimThay) {
                this.thoiGianTimThay = thoiGianTimThay;
        }

        public String getViTriTimThay() {
                return viTriTimThay;
        }

        public void setViTriTimThay(String viTriTimThay) {
                this.viTriTimThay = viTriTimThay;
        }

        public int getTinhTrang() {
                return TinhTrang;
        }

        public void setTinhTrang(int TinhTrang) {
                this.TinhTrang = TinhTrang;
        }

        public String getGhiChu() {
                return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
                this.ghiChu = ghiChu;
        }

}
