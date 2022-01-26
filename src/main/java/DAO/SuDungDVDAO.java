/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.SuDungDV;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class SuDungDVDAO {

        public List<SuDungDV> SelectBySQL(String sql, Object... args) {
                List<SuDungDV> lstSDDV = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        SuDungDV sddv = new SuDungDV();
                                        sddv.setMaSuDungDV(rs.getString("MaSuDungDV"));
                                        sddv.setMaDatPhong(rs.getString("MaDatPhong"));

                                        lstSDDV.add(sddv);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstSDDV;
        }

        public List<Object[]> getDanhSachSuDungDV() {
                String sql = "{call sp_DanhSachSuDungDV}";
                String[] cols = {
                        "MaSuDungDV",
                        "MaDatPhong",
                        "MaKhachHang"
                };
                return JDBC.getListOfArray(sql, cols);
        }

        public void deleteByMaSDDV(String maSuDungDV){
                String sql = "delete CT_SuDungDV where MaSuDungDV = ?";
                JDBC.update(sql, maSuDungDV);
        }
        
        public List<Object[]> getDanhSachChiTietSDDV(String maSuDungDV) {
                String sql = "{call sp_ChiTietSuDungDV(?)}";
                String[] cols = {
                        "MaDichVu",
                        "TenLoaiDV",
                        "SoLuong",
                        "DonGia",
                        "NgaySuDungDV"
                };
                return JDBC.getListOfArray(sql, cols, maSuDungDV);
        }

        public SuDungDV selectByMaPhong(String MaDatPhong) {
                String sql = "select * from SuDungDV where MaDatPhong = ?";
                List<SuDungDV> lstSDDV = SelectBySQL(sql, MaDatPhong);
                return lstSDDV.size() > 0 ? lstSDDV.get(0) : null;
        }

        public void insert(SuDungDV sddv) {
                String sql = "insert into SuDungDV (MaSuDungDV, MaDatPhong) values (?,?)";
                JDBC.update(sql,
                        sddv.getMaSuDungDV(),
                        sddv.getMaDatPhong()
                );
        }

}
