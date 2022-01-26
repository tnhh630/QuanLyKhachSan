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
public class TinhTrang {

        private int maTinhTrang;
        private String tenTinhTrang;

        public TinhTrang() {
        }

        public TinhTrang(int maTinhTrang, String tenTinhTrang) {
                this.maTinhTrang = maTinhTrang;
                this.tenTinhTrang = tenTinhTrang;
        }

        public int getMaTinhTrang() {
                return maTinhTrang;
        }

        public void setMaTinhTrang(int maTinhTrang) {
                this.maTinhTrang = maTinhTrang;
        }

        public String getTenTinhTrang() {
                return tenTinhTrang;
        }

        public void setTenTinhTrang(String tenTinhTrang) {
                this.tenTinhTrang = tenTinhTrang;
        }

}
