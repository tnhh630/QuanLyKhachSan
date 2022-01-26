/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NhanVien;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class NhanVienDAO {

        public void insert(NhanVien nv) {
                String sql = "insert into NhanVien(MaNhanVien, TenNhanVien, CCCD, GioiTinh, NgaySinh, DiaChi, Email, SDT,HinhAnh, ChucVu) values  (?,?,?,?,?,?,?,?,?,?)";
                JDBC.update(sql,
                        nv.getMaNhanVien(),
                        nv.getTenNhanVien(),
                        nv.getCCCD(),
                        nv.isGioiTinh(),
                        nv.getNgaySinh(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.getSDT(),
                        nv.getHinhAnh(),
                        nv.getChucVu()
                );
        }

        public void update(NhanVien nv) {
                String sql = "update NhanVien set TenNhanVien = ?, CCCD = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Email = ?, SDT = ?, HinhAnh = ?, ChucVu = ? where MaNhanVien = ?";
                JDBC.update(sql,
                        nv.getTenNhanVien(),
                        nv.getCCCD(),
                        nv.isGioiTinh(),
                        nv.getNgaySinh(),
                        nv.getDiaChi(),
                        nv.getEmail(),
                        nv.getSDT(),
                        nv.getHinhAnh(),
                        nv.getChucVu(),
                        nv.getMaNhanVien()
                );
        }

        public void delete(String maNhanVien) {
                String sql = "delete from NhanVien where MaNhanVien = ?";
                JDBC.update(sql, maNhanVien);
        }

        public List<NhanVien> SelectBySQL(String sql, Object... args) {
                List<NhanVien> lstNhanVien = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        NhanVien nv = new NhanVien();
                                        nv.setMaNhanVien(rs.getString("MaNhanVien"));
                                        nv.setTenNhanVien(rs.getString("TenNhanVien"));
                                        nv.setCCCD(rs.getString("CCCD"));
                                        nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                                        nv.setNgaySinh(rs.getDate("NgaySinh"));
                                        nv.setDiaChi(rs.getString("DiaChi"));
                                        nv.setEmail(rs.getString("Email"));
                                        nv.setSDT(rs.getString("SDT"));
                                        nv.setHinhAnh(rs.getBytes("HinhAnh"));
                                        nv.setChucVu(rs.getInt("ChucVu"));
                                        lstNhanVien.add(nv);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstNhanVien;
        }

        public List<NhanVien> SelectAll() {
                String sql = "select * from NhanVien";
                return SelectBySQL(sql);
        }

        public NhanVien selectByID(String maNhanVien) {
                String sql = "select * from NhanVien where MaNhanVien = ?";
                List<NhanVien> lstNhanVien = SelectBySQL(sql, maNhanVien);
                return lstNhanVien.size() > 0 ? lstNhanVien.get(0) : null;
        }

        public List<NhanVien> SelectNVChuaCoTK() {
                String sql = "select * from NhanVien where MaNhanVien not in (select MaNhanVien from TaiKhoan)";
                return SelectBySQL(sql);
        }

        public List<NhanVien> SelectByKeyWord(String keyword) {
                String sql = "select * from NhanVien where MaNhanVien like ? or TenNhanVien like ? or CCCD like ?";
                return SelectBySQL(sql, "%" + keyword + "%","%" + keyword + "%","%" + keyword + "%");
        }

        public List<NhanVien> SelectByKeyWordNotTK(String keyword) {
                String sql = "";
                if (keyword.trim().startsWith("NV")) {
                        sql = "select * from NhanVien where MaNhanVien not in (select MaNhanVien from TaiKhoan) and MaNhanVien like ?";
                } else {
                        sql = "select * from NhanVien where MaNhanVien not in (select MaNhanVien from TaiKhoan) and TenNhanVien like ?";
                }
                return SelectBySQL(sql, "%" + keyword + "%");
        }

        public List<NhanVien> SelectAll_Sort(int cond, boolean tangGiam) {
                String sql = "";
                // mặc định
                if (cond == 0) {
                        sql = "select * from NhanVien";
                }
                // Mã nhân viên
                if (cond == 1 && tangGiam == true) {
                        sql = "select * from NhanVien order by MaNhanVien"; // tăng 
                } else if (cond == 1 && tangGiam == false) {
                        sql = "select * from NhanVien order by MaNhanVien desc"; // giảm
                }
                // Tên Nhân Viên
                if (cond == 2 && tangGiam == true) {
                        sql = "select * from NhanVien order by TenNhanVien"; // tăng
                } else if (cond == 2 && tangGiam == false) {
                        sql = "select * from NhanVien order by TenNhanVien desc"; // giảm
                }

                // chức vụ
                if (cond == 3 && tangGiam == true) {
                        sql = "select * from NhanVien order by ChucVu"; // tăng 
                } else if (cond == 3 && tangGiam == false) {
                        sql = "select * from NhanVien order by ChucVu desc"; // giảm
                }

                return SelectBySQL(sql);
        }

}
