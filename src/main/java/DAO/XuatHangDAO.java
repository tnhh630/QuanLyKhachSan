/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.XuatHang;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class XuatHangDAO {

        public List<XuatHang> SelectBySQL(String sql, Object... args) {
                List<XuatHang> lstXuatHang = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        XuatHang xh = new XuatHang();
                                        xh.setMaDichVu(rs.getString("MaDichVu"));
                                        xh.setSoLuong(rs.getInt("SoLuongXuatHang"));
                                        xh.setDonGia(rs.getDouble("DonGia"));
                                        xh.setNgayXuatHang(rs.getDate("NgayXuatHang"));
                                        xh.setTinhTrang(rs.getBoolean("TinhTrang"));
                                        xh.setMaSuDungDV(rs.getString("MaSuDungDV"));
                                        lstXuatHang.add(xh);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstXuatHang;
        }

        public List<XuatHang> SelectAll() {
                String sql = "select * from XuatHang where MaDichVu like 'DVB%' ";
                return SelectBySQL(sql);
        }

        public void insert(XuatHang xh) {
                String sql = "insert into XuatHang (MaDichVu, SoLuongXuatHang, DonGia, NgayXuatHang, TinhTrang, MaSuDungDV) values (?,?,?,?,?,?)";
                JDBC.update(sql,
                        xh.getMaDichVu(),
                        xh.getSoLuong(),
                        xh.getDonGia(),
                        xh.getNgayXuatHang(),
                        xh.isTinhTrang(),
                        xh.getMaSuDungDV()
                );
        }

        public void capNhapTinhTrangHuy(String maDichVu, String maSuDungDV, Date ngay) {
                String sql = "update XuatHang set TinhTrang = 0 where MaDichVu = ? and maSuDungDV = ? and NgayXuatHang = ?";
                JDBC.update(sql, maDichVu, maSuDungDV, ngay);
        }

        public void capNhapDatHangThanhCong(String maDichVu, String maSuDungDV) {
                String sql = "update XuatHang set TinhTrang = 1 where MaDichVu = ? and maSuDungDV = ?";
                JDBC.update(sql, maDichVu, maSuDungDV);
        }
        
        public void deleteDuLieuCu(String maSuDungDV){
                String sql = "delete XuatHang where MaSuDungDV = ?";
                JDBC.update(sql, maSuDungDV);
        }
}
