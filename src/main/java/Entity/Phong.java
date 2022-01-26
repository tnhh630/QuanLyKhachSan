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
public class Phong {

        private String maPhong;
        private String tenPhong;
        private double giaLoaiPhong;
        private String maLoaiPhong;
        private int maTinhTrang;

        public Phong() {
        }

        public Phong(String maPhong, String tenPhong, double giaLoaiPhong, String maLoaiPhong, int maTinhTrang) {
                this.maPhong = maPhong;
                this.tenPhong = tenPhong;
                this.giaLoaiPhong = giaLoaiPhong;
                this.maLoaiPhong = maLoaiPhong;
                this.maTinhTrang = maTinhTrang;
        }

        public String getMaPhong() {
                return maPhong;
        }

        public void setMaPhong(String maPhong) {
                this.maPhong = maPhong;
        }

        public String getTenPhong() {
                return tenPhong;
        }

        public void setTenPhong(String tenPhong) {
                this.tenPhong = tenPhong;
        }

        public double getGiaLoaiPhong() {
                return giaLoaiPhong;
        }

        public void setGiaLoaiPhong(double giaLoaiPhong) {
                this.giaLoaiPhong = giaLoaiPhong;
        }

        public String getMaLoaiPhong() {
                return maLoaiPhong;
        }

        public void setMaLoaiPhong(String maLoaiPhong) {
                this.maLoaiPhong = maLoaiPhong;
        }

        public int getMaTinhTrang() {
                return maTinhTrang;
        }

        public void setMaTinhTrang(int maTinhTrang) {
                this.maTinhTrang = maTinhTrang;
        }

}
