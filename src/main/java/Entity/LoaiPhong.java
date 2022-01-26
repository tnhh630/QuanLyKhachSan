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
public class LoaiPhong {

        private String maLoaiPhong;
        private String tenLoaiPhong;

        public LoaiPhong() {
        }

        public LoaiPhong(String maLoaiPhong, String tenLoaiPhong) {
                this.maLoaiPhong = maLoaiPhong;
                this.tenLoaiPhong = tenLoaiPhong;
        }

        public String getMaLoaiPhong() {
                return maLoaiPhong;
        }

        public void setMaLoaiPhong(String maLoaiPhong) {
                this.maLoaiPhong = maLoaiPhong;
        }

        public String getTenLoaiPhong() {
                return tenLoaiPhong;
        }

        public void setTenLoaiPhong(String tenLoaiPhong) {
                this.tenLoaiPhong = tenLoaiPhong;
        }

}
