/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.TaiKhoan;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class TaiKhoanDAO {

        public void insert(TaiKhoan tk) {
                String sql = "insert into TaiKhoan(MaTaiKhoan, MatKhau, VaiTro, MaNhanVien) values  (?,?,?,?)";
                JDBC.update(sql,
                        tk.getMaTaiKhoan(),
                        tk.getMatKhau(),
                        tk.getVaiTro(),
                        tk.getMaNhanVien()
                );
        }

        public void update(TaiKhoan tk) {
                String sql = "update TaiKhoan set MatKhau = ?, VaiTro = ? where MaTaiKhoan = ?";
                JDBC.update(sql,
                        tk.getMatKhau(),
                        tk.getVaiTro(),
                        tk.getMaTaiKhoan()
                );
        }

        public void delete(String maTaiKhoan) {
                String sql = "delete from TaiKhoan where MaTaiKhoan = ?";
                JDBC.update(sql, maTaiKhoan);
        }

        public List<TaiKhoan> SelectBySQL(String sql, Object... args) {
                List<TaiKhoan> lstTaiKhoan = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        TaiKhoan tk = new TaiKhoan();
                                        tk.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                                        tk.setMatKhau(rs.getString("MatKhau"));
                                        tk.setVaiTro(rs.getInt("VaiTro"));
                                        tk.setMaNhanVien(rs.getString("MaNhanVien"));
                                        lstTaiKhoan.add(tk);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstTaiKhoan;
        }

        public List<TaiKhoan> SelectAll() {
                String sql = "select * from TaiKhoan";
                return SelectBySQL(sql);
        }

        public TaiKhoan SelectByID(String maTaiKhoan) {
                String sql = "select * from TaiKhoan where MaTaiKhoan = ?";
                List<TaiKhoan> lstTaiKhoan = SelectBySQL(sql, maTaiKhoan);
                return lstTaiKhoan.size() > 0 ? lstTaiKhoan.get(0) : null;
        }

        public List<TaiKhoan> SelectAll_Sort(String keyword) {
                String sql = "";
                if (keyword.trim().startsWith("NV")) {
                        sql = "select * from TaiKhoan where MaNhanVien like ?";
                } else if (keyword.trim().startsWith("TK")) {
                        sql = "select * from TaiKhoan where MaTaiKhoan like ?";
                }else{
                        return SelectAll();
                }
                return SelectBySQL(sql, "%" + keyword + "%");
        }
}
