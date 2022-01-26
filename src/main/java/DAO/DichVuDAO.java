/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.DichVu;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DichVuDAO {

        public void insert(DichVu dv) {
                String sql = "insert into DichVu (MaDichVu, TenLoaiDV, DonGia) values (?,?,?)";
                JDBC.update(sql,
                        dv.getMaDichVu(),
                        dv.getTenLoaiDV(),
                        dv.getDonGia()
                );
        }

        public void update(DichVu dv) {
                String sql = "update DichVu set TenLoaiDV = ?, DonGia = ? where MaDichVu = ?";
                JDBC.update(sql,
                        dv.getTenLoaiDV(),
                        dv.getDonGia(),
                        dv.getMaDichVu()
                );
        }

        public void delete(String id) {
                String sql = "delete DichVu where MaDichVu = ?";
                JDBC.update(sql, id);
        }

        public List<DichVu> SelectBySQL(String sql, Object... args) {
                List<DichVu> lstDichVu = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        DichVu dv = new DichVu();
                                        dv.setMaDichVu(rs.getString("MaDichVu"));
                                        dv.setTenLoaiDV(rs.getString("TenLoaiDV"));
                                        dv.setDonGia(rs.getDouble("DonGia"));
                                        lstDichVu.add(dv);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstDichVu;
        }

        public List<DichVu> SelectAll() {
                String sql = "select * from DichVu";
                return SelectBySQL(sql);
        }
          public List<DichVu> selectAll_notfree() {
                String sql = "select * from DichVu where MaDichVu <> 'dvb00' ";
                return SelectBySQL(sql);
        }
          
          public String SelectTenDV(String maDV){
                  String sql = "Select TenLoaiDV from DichVu where MaDichVu =?";
                  String tenDV = JDBC.value(sql, maDV).toString();
                  return tenDV;
          }
          
            public double SelectdonGia(String maDV){
                  String sql = "Select DonGia from DichVu where MaDichVu =?";
                  double DonGia = (double)JDBC.value(sql, maDV);
                  return DonGia;
          }
        public DichVu SelectByID(String id) {
                String sql = "select * from DichVu where MaDichVu = ?";
                List<DichVu> lstDichVu = SelectBySQL(sql, id);
                return lstDichVu.size() > 0 ? lstDichVu.get(0) : null;
        }

        public List<DichVu> SelectByKeyword(String keyword) {
                String sql = "select * from DichVu where MaDichVu like ? or TenLoaiDV like ?";
                String ans = "%" + keyword + "%";
                return SelectBySQL(sql, ans, ans);
        }
        
}
