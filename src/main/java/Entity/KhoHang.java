/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Admin
 */
public class KhoHang {
        private int maKho;
        private String MaDichVu;
        private int SoLuongTon;
        private String DonViTinh;

        public KhoHang() {
        }

        public KhoHang(int maKho, String MaDichVu, int SoLuongTon, String DonViTinh) {
                this.maKho = maKho;
                this.MaDichVu = MaDichVu;
                this.SoLuongTon = SoLuongTon;
                this.DonViTinh = DonViTinh;
        }

        public int getMaKho() {
                return maKho;
        }

        public void setMaKho(int maKho) {
                this.maKho = maKho;
        }

        public String getMaDichVu() {
                return MaDichVu;
        }

        public void setMaDichVu(String MaDichVu) {
                this.MaDichVu = MaDichVu;
        }

        public int getSoLuongTon() {
                return SoLuongTon;
        }

        public void setSoLuongTon(int SoLuongTon) {
                this.SoLuongTon = SoLuongTon;
        }

        public String getDonViTinh() {
                return DonViTinh;
        }

        public void setDonViTinh(String DonViTinh) {
                this.DonViTinh = DonViTinh;
        }

       
        
        
}
