/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NhapXuat;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhapXuatDAO {

        public List<NhapXuat> SelectBySql(String sql, Object... args) {
                List<NhapXuat> lstNhapXuat = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        NhapXuat nx = new NhapXuat();
                                        nx.setMaNhapXuat(rs.getString("MaNhapXuat"));
                                        nx.setMaDichVu(rs.getString("MaDichVu"));
                                        nx.setSoLuong(rs.getInt("SoLuong"));
                                        nx.setDonGia(rs.getDouble("DonGia"));
                                        nx.setNgayNhapXuat(rs.getDate("NgayNhapXuat"));
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
                return lstNhapXuat;
        }

        public void insert(NhapXuat nx) {
                String sql = "insert into NhapXuat(MaDichVu,SoLuong,DonGia,NgayNhapXuat) values(?,?,?,?)";
                JDBC.update(sql,
                        nx.getMaDichVu(),
                        nx.getSoLuong(),
                        nx.getDonGia(),
                        nx.getNgayNhapXuat()
                );
        }

        public void update(NhapXuat nx) {
                String sql = "update NhapXuat SoLuong = ?, DonGia = ?,NgayNhapXuat = ? where MaDichVu = ?";
                JDBC.update(sql,
                        nx.getSoLuong(),
                        nx.getDonGia(),
                        nx.getNgayNhapXuat(),
                        nx.getMaDichVu()
                );
        }

        public List<NhapXuat> SelectAll() {
                String sql = "select * from NhapXuat";
                return SelectBySql(sql);
        }

        public List<Object[]> select_NhapHang() {
                String sql = "select NhapXuat.MaDichVu,TenLoaiDV,SoLuong,NhapXuat.DonGia,NgayNhapXuat,(SoLuong*NhapXuat.DonGia) as ThanhTien from NhapXuat inner join DichVu on DichVu.MaDichVu = NhapXuat.MaDichVu";
                String[] cols = {
                        "MaDichVu",
                        "TenLoaiDV",
                        "SoLuong",
                        "DonGia",
                        "NgayNhapXuat",
                        "ThanhTien"
                };
                return JDBC.getListOfArray(sql, cols);
        }
        
        // ------------------------------------------------------------------------------------------------------------------
}
