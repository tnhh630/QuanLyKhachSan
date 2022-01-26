/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.CT_SuDungDV;
import Entity.DichVu;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 84384
 */
public class ChiTietSDDVDAO {

        public List<CT_SuDungDV> SelectBySQL(String sql, Object... args) {
                List<CT_SuDungDV> lstSDDV = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        CT_SuDungDV ctdv = new CT_SuDungDV();
                                        ctdv.setMaCTSDDV(rs.getInt("MaCTSDDV"));
                                        ctdv.setSoLuong(rs.getInt("SoLuong"));
                                        ctdv.setMaDichVu(rs.getString("MaDichVu"));
                                        ctdv.setMaSuDungDV(rs.getString("MaSuDungDV"));
                                        ctdv.setNgaySuDungDV(rs.getDate("NgaySuDungDV"));
                                        lstSDDV.add(ctdv);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstSDDV;
        }

        public List<CT_SuDungDV> SelectAll() {
                String sql = "select * from CT_SuDungDV";
                return SelectBySQL(sql);
        }

        public List<CT_SuDungDV> SelectAll(String MaSDDV) {
//                String sql = "select * from CT_SuDungDV where MaSuDungDV = ? and MaDichVu <> 'DVB00'";
                String sql = "select * from CT_SuDungDV where MaSuDungDV = ?";
                return SelectBySQL(sql, MaSDDV);
        }

        public CT_SuDungDV SelectByID(String id) {
                String sql = "select * from CT_SuDungDV where MaDichVu = ?";
                List<CT_SuDungDV> lstSDDV = SelectBySQL(sql, id);
                return lstSDDV.size() > 0 ? lstSDDV.get(0) : null;
        }

        public List<CT_SuDungDV> SelectByKeyword(String keyword) {
                String sql = "select * from CT_SuDungDV where MaCTSDDV like ? or MaDichVu like ?";
                String ans = "%" + keyword + "%";
                return SelectBySQL(sql, ans, ans);
        }

        public void insert(CT_SuDungDV ctdv) {
                String sql = "insert into CT_SuDungDV (SoLuong,MaDichVu,NgaySuDungDV,MaSuDungDV) values (?,?,?,?)";
                JDBC.update(sql,
                        ctdv.getSoLuong(),
                        ctdv.getMaDichVu(),
                        ctdv.getNgaySuDungDV(),
                        ctdv.getMaSuDungDV()
                );
        }

//    public void update(CT_SuDungDV ctdv) {
//        String sql = "update DichVu set SoLuong=?,MaDichVu=?,NgaySuDungDV=?,MaSuDungDV=? where MaCTSDDV=?";
//        JDBC.update(sql,
//                ctdv.getSoLuong(),
//                ctdv.getMaDichVu(),
//                ctdv.getNgaySuDungDV(),
//                ctdv.getMaSuDungDV(),
//                ctdv.getMaCTSDDV()
//        );
//    }
        public void SelectMaCTSDDV(int SoLuong, String MaDichVu, Date NgaySuDungDV, String MaSuDungDV) {
                String sql = "select MaCTSDDV from CT_SuDungDV where  MaDichVu =? and NgaySuDungDV =? and MaSuDungDV = ?";
                int maCTSDDV = (int) JDBC.value(sql, MaDichVu, NgaySuDungDV, MaSuDungDV);
                String sqlupdate = "update  CT_SuDungDV set SoLuong=? where MaCTSDDV=" + maCTSDDV;
                JDBC.update(sqlupdate, SoLuong);
        }

        public void delete(String MaDichVu, String MaSuDungDV, Date NgaySuDungDV, int soLuong) {
                String sql = "delete CT_SuDungDV where MaDichVu = ? and MaSuDungDV = ? and NgaySuDungDV = ? and SoLuong = ? ";
                JDBC.update(sql, MaDichVu, MaSuDungDV, NgaySuDungDV, soLuong);
        }

        public void delete(String MaSuDungDV) {
                String sql = "delete CT_SuDungDV where MaSuDungDV = ?";
                JDBC.update(sql, MaSuDungDV);
        }
}
