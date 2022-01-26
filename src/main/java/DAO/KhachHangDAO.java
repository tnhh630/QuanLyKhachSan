/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhachHang;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class KhachHangDAO {

        public void insert(KhachHang ttKh) {
                String sql = "insert Into KhachHang(MaKhachHang,TenKhachHang,CCCD,GioiTinh,NgaySinh,QuocTich,DiaChi,Email,SDT,LoaiKhach) 	values  (?,?,?,?,?,?,?,?,?,?)";
                JDBC.update(sql,
                        ttKh.getMaKhachHang(),
                        ttKh.getTenKhachHang(),
                        ttKh.getCCCD(),
                        ttKh.isGioiTinh(),
                        ttKh.getNgaySinh(),
                        ttKh.getQuocTich(),
                        ttKh.getDiaChi(),
                        ttKh.getEmail(),
                        ttKh.getSDT(),
                        ttKh.isLoaiKhach()
                );
        }

        public void update(KhachHang ttKh) {
                String sql = "update KhachHang set TenKhachHang = ?, GioiTinh = ?, NgaySinh = ?, QuocTich = ?, DiaChi = ?, Email = ?, SDT = ?, LoaiKhach = ? where MaKhachHang = ?";
                JDBC.update(sql,
                        ttKh.getTenKhachHang(),
                        ttKh.isGioiTinh(),
                        ttKh.getNgaySinh(),
                        ttKh.getQuocTich(),
                        ttKh.getDiaChi(),
                        ttKh.getEmail(),
                        ttKh.getSDT(),
                        ttKh.isLoaiKhach(),
                        ttKh.getMaKhachHang()
                );
        }

        public void delete(String maKhachHang) {
                String sql = "delete from KhachHang where MaKhachHang = ? ";
                JDBC.update(sql, maKhachHang);
        }

        public List<KhachHang> SelectBySQL(String sql, Object... args) {
                List<KhachHang> lstKhachHang = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        KhachHang ttKh = new KhachHang();
                                        ttKh.setMaKhachHang(rs.getString("MaKhachHang"));
                                        ttKh.setTenKhachHang(rs.getString("TenKhachHang"));
                                        ttKh.setCCCD(rs.getString("CCCD"));
                                        ttKh.setGioiTinh(rs.getBoolean("GioiTinh"));
                                        ttKh.setNgaySinh(rs.getDate("NgaySinh"));
                                        ttKh.setQuocTich(rs.getString("QuocTich"));
                                        ttKh.setDiaChi(rs.getString("DiaChi"));
                                        ttKh.setEmail(rs.getString("Email"));
                                        ttKh.setSDT(rs.getString("SDT"));
                                        ttKh.setLoaiKhach(rs.getBoolean("LoaiKhach"));
                                        lstKhachHang.add(ttKh);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstKhachHang;
        }

        public List<KhachHang> SelectAll() {
                String sql = "select * from KhachHang";
                return SelectBySQL(sql);
        }

        public KhachHang SelectByID(String maKhachHang) {
                String sql = "select * from KhachHang where MaKhachHang = ?";
                List<KhachHang> lstKhachHang = SelectBySQL(sql, maKhachHang);
                return lstKhachHang.size() > 0 ? lstKhachHang.get(0) : null;
        }

        public List<KhachHang> SelectByKeyword(String keyword) {
                String sql = "select * from KhachHang where MaKhachHang like ? or TenKhachHang like ?";
                return SelectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%");
        }

        public List<KhachHang> SelectAll_Sort(int cond, boolean tangGiam) {
                String sql = "";
                // mặc định -- 0
                if (cond == 0) {
                        sql = "select * from KhachHang";
                }

                // mã khách hàng -- 1
                if (cond == 1 && tangGiam == true) {
                        sql = "select * from KhachHang order by MaKhachHang"; // tăng
                } else if (cond == 1 && tangGiam == false) {
                        sql = "select * from KhachHang order by MaKhachHang desc"; //giảm
                }

                // loại khách hàng -- 2
                if (cond == 2 && tangGiam == true) {
                        sql = "select * from KhachHang order by LoaiKhach"; // tăng
                } else if (cond == 2 && tangGiam == false) {
                        sql = "select * from KhachHang order by LoaiKhach desc"; //giảm
                }

                return SelectBySQL(sql);
        }

        public List<KhachHang> SelectKhachChuaDatPhong() {
                String sql = "select * from KhachHang where MaKhachHang not in (select MaKhachHang from DatPhong)";
                return SelectBySQL(sql);
        }

        public List<KhachHang> SearchKhachHangCDP(String keyword) {
                if (keyword.trim().isEmpty()) {
                        return SelectKhachChuaDatPhong();
                }
                String sql = "select * from KhachHang where ( MaKhachHang like ?  or TenKhachHang like ? ) and MaKhachHang not in (select MaKhachHang from DatPhong) ";
                return SelectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%");
        }

}
