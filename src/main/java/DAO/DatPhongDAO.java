/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.DatPhong;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DatPhongDAO {

        public void insert(DatPhong dp) {
                String sql = "insert into DatPhong(MaDatPhong, MaLoaiPhong, MaPhong, NgayDatPhong, NgayTraPhong, DatCocPhong, TinhTrangDatPhong, MaKhachHang, GhiChu) values (?,?,?,?,?,?,?,?,?) ";
                JDBC.update(sql,
                        dp.getMaDatPhong(),
                        dp.getMaLoaiPhong(),
                        dp.getMaPhong(),
                        dp.getNgayDatPhong(),
                        dp.getNgayTraPhong(),
                        dp.getDatCocPhong(),
                        dp.getTinhTrangDatPhong(),
                        dp.getMaKhachHang(),
                        dp.getGhiChu()
                );
        }

        public void cancel(String dp) {
                String sql = "update DatPhong set TinhTrangDatPhong = 2 where MaDatPhong = ?";
                JDBC.update(sql, dp);
        }

        public void update(DatPhong dp) {
                String sql = "update  DatPhong set MaLoaiPhong = ?, MaPhong = ?, NgayDatPhong = ?, NgayTraPhong = ?, DatCocPhong = ?, TinhTrangDatPhong = ?, MaKhachHang = ?, GhiChu = ? where MaDatPhong = ?";
                JDBC.update(sql,
                        dp.getMaLoaiPhong(),
                        dp.getMaPhong(),
                        dp.getNgayDatPhong(),
                        dp.getNgayTraPhong(),
                        dp.getDatCocPhong(),
                        dp.getTinhTrangDatPhong(),
                        dp.getMaKhachHang(),
                        dp.getGhiChu(),
                        dp.getMaDatPhong()
                );
        }

        public void nhanPhong(String dp) {
                String sql = "update DatPhong set TinhTrangDatPhong = 1 where MaDatPhong = ?";
                JDBC.update(sql, dp);
        }

        public void thanhToan(String dp) {
                String sql = "update DatPhong set TinhTrangDatPhong = 3 where MaDatPhong = ?";
                JDBC.update(sql, dp);
        }

        public List<DatPhong> SelectBySQL(String sql, Object... args) {
                List<DatPhong> lstDatPhong = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        DatPhong dp = new DatPhong();
                                        dp.setMaDatPhong(rs.getString("MaDatPhong"));
                                        dp.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
                                        dp.setMaPhong(rs.getString("MaPhong"));
                                        dp.setMaKhachHang(rs.getString("MaKhachHang"));
                                        dp.setNgayDatPhong(rs.getDate("NgayDatPhong"));
                                        dp.setNgayTraPhong(rs.getDate("NgayTraPhong"));
                                        dp.setTinhTrangDatPhong(rs.getInt("TinhTrangDatPhong"));
                                        dp.setDatCocPhong(rs.getDouble("DatCocPhong"));
                                        dp.setGhiChu(rs.getString("GhiChu"));
                                        lstDatPhong.add(dp);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstDatPhong;
        }

        public List<DatPhong> SelectAll() {
                String sql = "select * from DatPhong";
                return SelectBySQL(sql);
        }

        public DatPhong SelectByID(String id) {
                String sql = "select * from DatPhong where MaDatPhong = ?";
                List<DatPhong> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<DatPhong> SelectAllHuyPhong() {
                String sql = "select * from DatPhong where TinhTrangDatPhong = 2";
                return SelectBySQL(sql);
        }

        public List<Object[]> getSpDanhSachDatPhong(String keyword) {
                String sql = "{call sp_DanhSachDatPhong(?,?)}";
                String[] cols = {
                        "MaDatPhong",
                        "TenPhong",
                        "MaKhachHang",
                        "NgayDatPhong",
                        "NgayTraPhong"
                };
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%", "%" + keyword + "%");
        }

        public List<Object[]> getSpDanhSachPhongDangThue(String keyword) {
                String sql = "{call sp_DanhSachPhongDangThue(?,?,?,?)}";
                String[] cols = {
                        "MaDatPhong",
                        "MaPhong",
                        "TenPhong",
                        "MaKhachHang",
                        "NgayDatPhong",
                        "NgayTraPhong"
                };
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
        }

        public List<Object[]> getSpGoiYPhong(String maLoaiPhong, Date ngayDatPhong, Date ngayTraPhong) {
                String sql = "{call sp_GoiYPhong(?,?,?)}";
                String[] cols = {
                        "MaPhong",
                        "TenPhong",
                        "NgayTraPhong"
                };
                return JDBC.getListOfArray(sql, cols, maLoaiPhong, ngayDatPhong, ngayTraPhong);
        }

        public List<Object[]> getLichSuDungPhong(String maPhong, String maLoaiPhong, Date ngayDatPhong, Date ngayTraPhong) {
                String sql = "{call sp_LichSuDungPhong(?,?,?,?)}";
                String[] cols = {
                        "MaPhong",
                        "MaKhachHang",
                        "NgayDatPhong",
                        "NgayTraPhong",
                        "TinhTrangDatPhong"
                };
                return JDBC.getListOfArray(sql, cols, maPhong, maLoaiPhong, ngayDatPhong, ngayTraPhong);
        }

        // tìm kiếm phòng chưa đặt
        public List<DatPhong> SearchHuyPhong(String maDatPhong) {
                if (maDatPhong.trim().isEmpty()) {
                        return SelectAllHuyPhong();
                }
                String sql = "select * from DatPhong where TinhTrangDatPhong = 2 and ( MaDatPhong like ? or MaKhachHang like ? )";
                return SelectBySQL(sql, "%" + maDatPhong + "%", "%" + maDatPhong + "%");
        }
}
