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
public class TaiKhoan {
        private String maTaiKhoan;
        private String matKhau;
        private int vaiTro;
        private String maNhanVien;

        public TaiKhoan() {
        }

        public TaiKhoan(String maTaiKhoan, String matKhau, int vaiTro, String maNhanVien) {
                this.maTaiKhoan = maTaiKhoan;
                this.matKhau = matKhau;
                this.vaiTro = vaiTro;
                this.maNhanVien = maNhanVien;
        }

        public String getMaTaiKhoan() {
                return maTaiKhoan;
        }

        public void setMaTaiKhoan(String maTaiKhoan) {
                this.maTaiKhoan = maTaiKhoan;
        }

        public String getMatKhau() {
                return matKhau;
        }

        public void setMatKhau(String matKhau) {
                this.matKhau = matKhau;
        }

        public int getVaiTro() {
                return vaiTro;
        }

        public void setVaiTro(int vaiTro) {
                this.vaiTro = vaiTro;
        }

        public String getMaNhanVien() {
                return maNhanVien;
        }

        public void setMaNhanVien(String maNhanVien) {
                this.maNhanVien = maNhanVien;
        }
        
        
}
