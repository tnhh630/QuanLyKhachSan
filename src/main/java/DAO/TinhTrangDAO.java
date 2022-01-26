/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.TinhTrang;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class TinhTrangDAO {

        public List<TinhTrang> SelectBySQL(String sql, Object... args) {
                List<TinhTrang> lstTinhTrang = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        TinhTrang tt = new TinhTrang();
                                        tt.setMaTinhTrang(rs.getInt("MaTinhTrang"));
                                        tt.setTenTinhTrang(rs.getString("TenTinhTrang"));
                                        lstTinhTrang.add(tt);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstTinhTrang;
        }

        public List<TinhTrang> SelectAll() {
                String sql = "select * from TinhTrang";
                return SelectBySQL(sql);
        }

        public List<String> SelectAll_TenTinhTrang() {
                String sql = "select TenTinhTrang from TinhTrang";
                List<String> lst = new ArrayList<>();
                try {
                        ResultSet rs = JDBC.query(sql);
                        while (rs.next()) {
                                lst.add(rs.getString("TenTinhTrang"));
                        }
                        rs.getStatement().getConnection().close();
                        return lst;
                } catch (Exception e) {
                        throw new RuntimeException();
                }
        }

}
