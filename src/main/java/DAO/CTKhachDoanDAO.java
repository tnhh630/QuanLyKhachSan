/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.CT_KhachDoan;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class CTKhachDoanDAO {

        public void insert(CT_KhachDoan ctkd) {
                String sql = "insert into CT_KhachDoan (MaKhachDoan, HoTenThanhVien, NgaySinh, CCCD, MaDoan) values (?,?,?,?,?)";
                JDBC.update(sql,
                        ctkd.getMaKhachDoan(),
                        ctkd.getHoTenThanhVien(),
                        ctkd.getNgaySinh(),
                        ctkd.getCCCD(),
                        ctkd.getMaDoan()
                );
        }

        public void update(CT_KhachDoan ctkd) {
                String sql = "update CT_KhachDoan set HoTenThanhVien = ?, NgaySinh = ?, CCCD = ?, MaDoan = ? where MaKhachDoan = ?";
                JDBC.update(sql,
                        ctkd.getHoTenThanhVien(),
                        ctkd.getNgaySinh(),
                        ctkd.getCCCD(),
                        ctkd.getMaDoan(),
                        ctkd.getMaKhachDoan()
                );
        }

        public void delete(String maKhachDoan) {
                String sql = "delete CT_KhachDoan where MaKhachDoan = ?";
                JDBC.update(sql, maKhachDoan);
        }

        public List<CT_KhachDoan> SelectBySQL(String sql, Object... args) {
                List<CT_KhachDoan> lst = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        CT_KhachDoan ct = new CT_KhachDoan();
                                        ct.setMaKhachDoan(rs.getString("MaKhachDoan"));
                                        ct.setHoTenThanhVien(rs.getString("HoTenThanhVien"));
                                        ct.setNgaySinh(rs.getDate("NgaySinh"));
                                        ct.setCCCD(rs.getString("CCCD"));
                                        ct.setMaDoan(rs.getString("MaDoan"));
                                        lst.add(ct);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lst;
        }

        public List<CT_KhachDoan> SelectAll() {
                String sql = "select * from CT_KhachDoan";
                return SelectBySQL(sql);
        }

        public List<CT_KhachDoan> SelectAll_MaDoan(String maDoan) {
                String sql = "select * from CT_KhachDoan where MaDoan = ?";
                return SelectBySQL(sql, maDoan);
        }

        public CT_KhachDoan SelectByID(String id) {
                String sql = "select * from CT_KhachDoan where MaKhachDoan = ?";
                List<CT_KhachDoan> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

}
