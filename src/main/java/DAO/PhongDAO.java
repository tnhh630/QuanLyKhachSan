/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Phong;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class PhongDAO {

        public void insert(Phong p) {
                String sql = "insert into Phong(MaPhong,TenPhong,GiaLoaiPhong,MaLoaiPhong,MaTinhTrang) values (?,?,?,?,?)";
                JDBC.update(sql,
                        p.getMaPhong(),
                        p.getTenPhong(),
                        p.getGiaLoaiPhong(),
                        p.getMaLoaiPhong(),
                        p.getMaTinhTrang()
                );
        }

        public void update(Phong p) {
                String sql = " update Phong set TenPhong = ? , GiaLoaiPhong = ? , MaLoaiPhong = ? , MaTinhTrang = ? where  MaPhong = ?";
                JDBC.update(sql,
                        p.getTenPhong(),
                        p.getGiaLoaiPhong(),
                        p.getMaLoaiPhong(),
                        p.getMaTinhTrang(),
                        p.getMaPhong()
                );
        }

        public void delete(String maPhong) {
                String sql = "delete Phong where MaPhong = ?";
                JDBC.update(sql, maPhong);
        }

        public void capNhatTinhTrangPhong(int maTinhTrang, String maPhong) {
                String sql = "update Phong set MaTinhTrang = ? where MaPhong = ?";
                JDBC.update(sql, maTinhTrang, maPhong);
        }

        public List<Phong> SelectBySQL(String sql, Object... args) {
                List<Phong> lstPhong = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        Phong p = new Phong();
                                        p.setMaPhong(rs.getString("MaPhong"));
                                        p.setTenPhong(rs.getString("TenPhong"));
                                        p.setGiaLoaiPhong(rs.getDouble("GiaLoaiPhong"));
                                        p.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
                                        p.setMaTinhTrang(rs.getInt("MaTinhTrang"));
                                        lstPhong.add(p);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstPhong;
        }

        public List<Phong> SelectAll() {
                String sql = "select * from Phong";
                return SelectBySQL(sql);
        }

        public Phong SelectByID(String id) {
                String sql = "select * from Phong where MaPhong = ?";
                List<Phong> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<Object[]> getDanhSachPhong(String keyword) {
                String sql = "{call sp_tblDanhSachPhong(?)}";
                String[] cols = {"MaLoaiPhong", "TenLoaiPhong", "MaPhong", "TenPhong", "GiaLoaiPhong"};
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%");
        }

        public List<Phong> SelectPhongTrong(String idLoaiPhong) {
                String sql = "select * from Phong where  MaLoaiPhong = ? and( MaTinhTrang = 0 or MaTinhTrang = 1)";
                return SelectBySQL(sql, idLoaiPhong);
        }

        public int getSoLuongDatPhong(String maPhong) {
                String sql = "select Count(MaDatPhong) from DatPhong inner join Phong on DatPhong.MaPhong = Phong.MaPhong where TinhTrangDatPhong = 0 and DatPhong.MaPhong = ?";
                return (Integer) JDBC.value(sql, maPhong);
        }

}
