/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhachDoan;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class KhachDoanDAO {

        public void insert(KhachDoan kd) {
                String sql = "insert into KhachDoan (MaDoan, TenDoan, MaTruongDoan) values (?,?,?)";
                JDBC.update(sql,
                        kd.getMaDoan(),
                        kd.getTenDoan(),
                        kd.getMaTruongDoan()
                );
        }

        public void update(KhachDoan kd) {
                String sql = "update KhachDoan set TenDoan = ?, MaTruongDoan = ? where MaDoan = ?";
                JDBC.update(sql,
                        kd.getTenDoan(),
                        kd.getMaTruongDoan(),
                        kd.getMaDoan()
                );
        }

        public void delete(String maDoan) {
                String sql = "delete KhachDoan where MaDoan = ?";
                JDBC.update(sql, maDoan);
        }

        public List<KhachDoan> SelectBySQL(String sql, Object... args) {
                List<KhachDoan> lstKhachDoan = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        KhachDoan kd = new KhachDoan();
                                        kd.setMaDoan(rs.getString("MaDoan"));
                                        kd.setTenDoan(rs.getString("TenDoan"));
                                        kd.setMaTruongDoan(rs.getString("MaTruongDoan"));
                                        lstKhachDoan.add(kd);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstKhachDoan;
        }

        public List<KhachDoan> SelectAll() {
                String sql = "select * from KhachDoan";
                return SelectBySQL(sql);
        }

        public KhachDoan SelectByID_TruongDoan(String id) {
                String sql = "select * from KhachDoan where MaTruongDoan = ?";
                List<KhachDoan> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<KhachDoan> SelectAll_Sort(String keyword) {
                String sql = "select * from KhachDoan where MaTruongDoan like ? or TenDoan like ?";
                return SelectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%");
        }
}
