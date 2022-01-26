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
public class KhachDoan {

        private String maDoan;
        private String tenDoan;
        private String maTruongDoan;

        public KhachDoan() {
        }

        public KhachDoan(String maDoan, String tenDoan, String maTruongDoan) {
                this.maDoan = maDoan;
                this.tenDoan = tenDoan;
                this.maTruongDoan = maTruongDoan;
        }

        public String getMaDoan() {
                return maDoan;
        }

        public void setMaDoan(String maDoan) {
                this.maDoan = maDoan;
        }

        public String getTenDoan() {
                return tenDoan;
        }

        public void setTenDoan(String tenDoan) {
                this.tenDoan = tenDoan;
        }

        public String getMaTruongDoan() {
                return maTruongDoan;
        }

        public void setMaTruongDoan(String maTruongDoan) {
                this.maTruongDoan = maTruongDoan;
        }

}
