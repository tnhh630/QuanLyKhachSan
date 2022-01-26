/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Entity.CT_KhachDoan;
import Entity.DatPhong;
import Entity.KhachDoan;
import Entity.KhachHang;
import Entity.TaiKhoan;

/**
 *
 * @author Tran Trung Nghia <PS14820>
 */
public class Auth {

        // Đối tượng chứa thông tin người sử dụng sau khi đăng nhập
        public static TaiKhoan user = null;
        public static DatPhong dp = null;
        public static KhachHang authKhachHang = null;
        public static String authMaDichVu = null;
        public static CT_KhachDoan ctKD = null;

        // Xoá thông tin người dùng khi đăng xuất
        public static void clear() {
                Auth.user = null;
        }

        // Kiểm tra có đăng nhập hay không
        public static boolean isLogin() {
                return Auth.user != null;
        }

        /*
        // Kiểm tra role
        public static boolean isManager() {
                return Auth.isLogin() && user.isVaiTro();
        }
        */
        public static int vaiTro(){
                return  user.getVaiTro();
        }
}
