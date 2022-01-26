/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HoaDon;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonDAO {

        public List<HoaDon> SelectAll() {
                String sql = "select * from HoaDon ORDER BY MaHoaDon ASC";
                return SelectBySQL(sql);
        }

        public void insert(HoaDon hd) {
                String sql = "insert into HoaDon(MaDatPhong,MaTaiKhoan,PT_ThanhToan,TongTienPhong,TongTienDichVu,ThanhTien, GhiChu) values (?,?,?,?,?,?,?)";
                JDBC.update(sql,
                        hd.getMaDatPhong(),
                        hd.getMaTaiKhoan(),
                        hd.getPtThanhToan(),
                        hd.getTongTienPhong(),
                        hd.getTongTienDichVu(),
                        hd.getThanhTien(),
                        hd.getGhiChu()
                );
        }

        public List<HoaDon> SelectBySQL(String sql, Object... args) {
                List<HoaDon> lstHoaDon = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        HoaDon hd = new HoaDon();
                                        hd.setMaDatPhong(rs.getString("MaDatPhong"));
                                        hd.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                                        hd.setPtThanhToan(rs.getString("PT_ThanhToan"));
                                        hd.setTongTienPhong(rs.getDouble("TongTienPhong"));
                                        hd.setTongTienDichVu(rs.getDouble("TongTienDichVu"));
                                        hd.setThanhTien(rs.getDouble("ThanhTien"));
                                        hd.setGhiChu(rs.getString("GhiChu"));
                                        lstHoaDon.add(hd);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstHoaDon;
        }

        public List<Object[]> getDanhSachHoaDon() {
                String sql = "{call sp_DanhSachCacHoaDon}";
                String[] cols = {
                        "TenKhachHang",
                        "TenPhong",
                        "NgayDatPhong",
                        "NgayTraPhong",
                        "TongTienPhong",
                        "TongTienDichVu",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols);
        }
}
