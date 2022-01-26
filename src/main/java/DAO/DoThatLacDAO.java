/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.DoThatLac;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class DoThatLacDAO {

        public void insert(DoThatLac dtl) {
                String sql = "insert into DoThatLac(MaDoThatLac,TenDoThatLac,MaNVTimThay,ThoiGianTimThay,ViTriTim,TinhTrang,GhiChu) values (?,?,?,?,?,?,?)";
                JDBC.update(sql,
                        dtl.getMaDoThatLac(),
                        dtl.getTenDoThatLac(),
                        dtl.getMaNVTimThay(),
                        dtl.getThoiGianTimThay(),
                        dtl.getViTriTimThay(),
                        dtl.getTinhTrang(),
                        dtl.getGhiChu()
                );
        }

        public void update(DoThatLac dtl) {
                String sql = "update DoThatLac set TenDoThatLac = ?,MaNVTimThay = ?,ThoiGianTimThay = ?,ViTriTim = ?,TinhTrang = ?,GhiChu = ? where MaDoThatLac = ?";
                JDBC.update(sql,
                        dtl.getTenDoThatLac(),
                        dtl.getMaNVTimThay(),
                        dtl.getThoiGianTimThay(),
                        dtl.getViTriTimThay(),
                        dtl.getTinhTrang(),
                        dtl.getGhiChu(),
                        dtl.getMaDoThatLac()
                );

        }
         public void updateTinhTrang(DoThatLac dtl) {
          String sql = "update DoThatLac set TinhTrang = ? where MaDoThatLac=?";
                JDBC.update(sql,                  
                        dtl.getTinhTrang(),                   
                        dtl.getMaDoThatLac()
                );
        }

        public void delete(String maDoThatLac) {
                String sql = "delete DoThatLac where MaDoThatLac = ?";
                JDBC.update(sql, maDoThatLac);
        }

        public List<DoThatLac> SelectBySQL(String sql, Object... args) {
                List<DoThatLac> lstDoThatLac = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        DoThatLac dtl = new DoThatLac();
                                        dtl.setMaDoThatLac(rs.getString("MaDoThatLac"));
                                        dtl.setTenDoThatLac(rs.getString("TenDoThatLac"));
                                        dtl.setMaNVTimThay(rs.getString("MaNVTimThay"));
                                        dtl.setThoiGianTimThay(rs.getDate("ThoiGianTimThay"));
                                        dtl.setViTriTimThay(rs.getString("ViTriTim"));
                                        dtl.setTinhTrang(rs.getInt("TinhTrang"));
                                        dtl.setGhiChu(rs.getString("GhiChu"));
                                        lstDoThatLac.add(dtl);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstDoThatLac;
        }

        public List<DoThatLac> SelectAll() {
                String sql = "select * from DoThatLac";
                return SelectBySQL(sql);
        }

        public DoThatLac SelectByID(String id) {
                String sql = "select * from DoThatLac where MaDoThatLac = ?";
                List<DoThatLac> lst = SelectBySQL(sql, id);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<DoThatLac> SelectByKeyword(String keyword) {
                String sql = "select * from DoThatLac where MaDoThatLac like ? or TenDoThatLac like ? or MaNVTimThay like ?";
                String ans = "%" + keyword + "%";
                return SelectBySQL(sql, ans, ans, ans);
        }

        public List<DoThatLac> SelectAll_Sort(int cond, boolean tangGiam) {
                String sql = "";
                if (cond == 0) {
                        return SelectAll();
                }

                if (cond == 1 && tangGiam == true) {
                        sql = "select * from DoThatLac order by TinhTrang"; // tăng
                } else if (cond == 1 && tangGiam == false) {
                        sql = "select * from DoThatLac order by TinhTrang desc"; // giảm
                }

                if (cond == 2 && tangGiam == true) {
                        sql = "select * from DoThatLac order by MaDoThatLac"; // tăng
                } else if (cond == 2 && tangGiam == false) {
                        sql = "select * from DoThatLac order by MaDoThatLac desc"; // giảm
                }
                
                return SelectBySQL(sql);
        }
}
