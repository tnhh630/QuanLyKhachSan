/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import Helper.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class ThongKeDAO {

        public List<Integer> getNamCoDoanhThu() {
                List<Integer> list = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query("select distinct year(NgayTraPhong) as nam from DatPhong order by year(NgayTraPhong) desc");
                                while (rs.next()) {
                                        int nam = rs.getInt(1);
                                        list.add(nam);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                }
                return list;
        }

        // năm đồ thất lạc
        public List<Integer> GetNamCoDoThatLac() {
                List<Integer> list = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query("select distinct year(ThoiGianTimThay) as nam from DoThatLac order by year(ThoiGianTimThay) desc");
                                while (rs.next()) {
                                        int nam = rs.getInt(1);
                                        list.add(nam);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                }
                return list;
        }

        public List<Object[]> getDoanhThuThuePhong(int Nam) {
                String sql = "{call sp_DoanhThuThuePhong (?)}";
                String[] cols = {
                        "MaHoaDon",
                        "TenKhachHang",
                        "NgayTraPhong",
                        "NgayThue",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols, Nam);
        }

        public List<Object[]> getDoanhThuDichVu(int Nam) {
                String sql = "{call sp_DoanhThuDichVu (?)}";
                String[] cols = {
                        "MaCTSDDV",
                        "TenKhachHang",
                        "TenLoaiDV",
                        "NgayTraPhong",
                        "SoLuong",
                        "DonGia",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols, Nam);
        }

        public List<Object[]> GetDanhSachKhachHang(int Nam) {
                String sql = "{call sp_KhachHang (?)}";
                String[] cols = {
                        "MaKhachHang",
                        "TenKhachHang",
                        "Email",
                        "SDT"
                };
                return JDBC.getListOfArray(sql, cols, Nam);
        }

        // Đồ Thất Lạc
        public List<Object[]> GetDanhSachDoThatLac(int Nam) {
                String sql = "{call sp_dothatlac (?)}";
                String[] cols = {
                        "MaDoThatLac",
                        "TenDoThatLac",
                        "ThoiGianTimThay",
                        "MaNVTimThay",
                        "TinhTrang"
                };
                return JDBC.getListOfArray(sql, cols, Nam);
        }

        // Top 3 Sản phẩm bán chạy
        public List<Object[]> GetTop3(int nam) {
                String sql = "{call sp_SanPhamBanChay(?)}";
                String[] cols = {
                        "TenLoaiDV"
                };
                return JDBC.getListOfArray(sql, cols, nam);
        }

        public List<Object[]> SelectByKeyWord_DoThatLac(String keyword, int nam) {
                String sql = "";
                if (keyword.length() == 0) {
                        return GetDanhSachDoThatLac(nam);
                }
                if (keyword.startsWith("DTL")) {
                        sql = " select MaDoThatLac, TenDoThatLac,ThoiGianTimThay, MaNVTimThay, TinhTrang from DoThatLac where MaNVTimThay like ?  and year(ThoiGianTimThay) = ?";
                } else {
                        sql = " select MaDoThatLac, TenDoThatLac,ThoiGianTimThay, MaNVTimThay, TinhTrang from DoThatLac where TenDoThatLac like ? and year(ThoiGianTimThay) = ?";
                }
                String[] cols = {
                        "MaDoThatLac",
                        "TenDoThatLac",
                        "ThoiGianTimThay",
                        "MaNVTimThay",
                        "TinhTrang"
                };
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%", nam);
        }

        // Danh sách Nhân viên 
        public List<Object[]> getDanhSachNhanVien() {
                String sql = " SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien";
                String[] cols = {"MaNhanVien", "TenNhanVien", "ChucVu", "MaTaiKhoan", "SDT"};
                return JDBC.getListOfArray(sql, cols);
        }

        public List<Object[]> SelectByKeyWord_NhanVien(String keyword) {
                String sql = "";
                if (keyword.length() == 0) {
                        return getDanhSachNhanVien();
                }
                if (keyword.startsWith("NV")) {
                        sql = " SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien where nv.MaNhanVien like ?";
                } else {
                        sql = " SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien where TenNhanVien like ?";
                }
                String[] cols = {"MaNhanVien", "TenNhanVien", "ChucVu", "MaTaiKhoan", "SDT"};
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%");
        }

        public List<Object[]> SelectAll_ThongKeSortNhanVien(int cond, boolean tangGiam) {
                String sql = "";
                // mặc định
                if (cond == 0) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien";
                }
                // Mã nhân viên
                if (cond == 1 && tangGiam == true) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien ORDER BY MaNhanVien"; // tăng 
                } else if (cond == 1 && tangGiam == false) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien ORDER BY MaNhanVien desc"; // giảm
                }
                // Tên Nhân Viên
                if (cond == 2 && tangGiam == true) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien order by TenNhanVien"; // tăng
                } else if (cond == 2 && tangGiam == false) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien order by TenNhanVien desc"; // giảm
                }

                // chức vụ
                if (cond == 3 && tangGiam == true) {
                        sql = " SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien order by ChucVu"; // tăng 
                } else if (cond == 3 && tangGiam == false) {
                        sql = "SELECT nv.MaNhanVien, TenNhanVien,ChucVu, tk.MaTaiKhoan, SDT  FROM NhanVien NV FULL OUTER JOIN TaiKhoan TK ON TK .MaNhanVien  = NV.MaNhanVien order by ChucVu desc"; // giảm
                }

                String[] cols = {"MaNhanVien", "TenNhanVien", "ChucVu", "MaTaiKhoan", "SDT"};
                return JDBC.getListOfArray(sql, cols);
        }

        public List<Integer> selectYears() {
                String sql = " SELECT YEAR(NgayTraPhong) as 'nam' from DatPhong group by YEAR(NgayTraPhong) order by YEAR(NgayTraPhong) asc ";
                List<Integer> list = new ArrayList<>();
                try {
                        ResultSet rs = JDBC.query(sql);
                        while (rs.next()) {
                                list.add(rs.getInt(1));
                        }
                        rs.getStatement().getConnection().close();
                        return list;
                } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                }
        }

        public List<Object[]> GetDoanhThuThuePhong_Thang(int thang, int nam) {
                String sql = "{call sp_DoanhThuThuePhong_NAM_Thang (?,?)}";
                String[] cols = {
                        "MaHoaDon",
                        "TenKhachHang",
                        "NgayTraPhong",
                        "NgayThue",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols, thang, nam);
        }

        public List<Object[]> getDoanhThuDichVu(int Thang, int Nam) {
                String sql = "{call sp_DoanhThuDichVu (?,?)}";
                String[] cols = {
                        "MaCTSDDV",
                        "TenKhachHang",
                        "TenLoaiDV",
                        "NgayTraPhong",
                        "SoLuong",
                        "DonGia",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols, Thang, Nam);
        }

}
