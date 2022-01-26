/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.LoaiPhong;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class LoaiPhongDAO {

        public void insert(LoaiPhong lp) {
                String sql = "insert into LoaiPhong (MaLoaiPhong, TenLoaiPhong) values (?,?)";
                JDBC.update(sql,
                        lp.getMaLoaiPhong(),
                        lp.getTenLoaiPhong()
                );
        }

        public void update(LoaiPhong lp) {
                String sql = "update LoaiPhong set TenLoaiPhong = ? where MaLoaiPhong = ?";
                JDBC.update(sql,
                        lp.getTenLoaiPhong(),
                        lp.getMaLoaiPhong()
                );
        }

        public void delete(String maLoaiPhong) {
                String sql = "delete LoaiPhong where MaLoaiPhong = ?";
                JDBC.update(sql, maLoaiPhong);
        }

        public List<LoaiPhong> SelectBySQL(String sql, Object... args) {
                List<LoaiPhong> lstLoaiPhong = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        LoaiPhong lp = new LoaiPhong();
                                        lp.setMaLoaiPhong(rs.getString("MaLoaiPhong"));
                                        lp.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                                        lstLoaiPhong.add(lp);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstLoaiPhong;
        }

        public List<LoaiPhong> SelectAll() {
                String sql = "select * from LoaiPhong";
                return SelectBySQL(sql);
        }

        public LoaiPhong SelectByID(String id) {
                String sql = "select * from LoaiPhong where MaLoaiPhong = ?";
                List<LoaiPhong> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<String> SelectAll_MaLoaiPhong() {
                String sql = "select MaLoaiPhong from LoaiPhong";
                List<String> lst = new ArrayList<>();
                try {
                        ResultSet rs = JDBC.query(sql);
                        while (rs.next()) {
                                lst.add(rs.getString(1));
                        }
                        rs.getStatement().getConnection().close();
                        return lst;
                } catch (Exception e) {
                        throw new RuntimeException();
                }
        }

}
