/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.KhoHang;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class KhoHangDAO {

        public List<KhoHang> SelectBySql(String sql, Object... args) {
                List<KhoHang> lstKhoHang = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        KhoHang kh = new KhoHang();
                                        kh.setMaDichVu(rs.getString("MaDichVu"));
                                        kh.setSoLuongTon(rs.getInt("SoLuongTon"));
                                        kh.setDonViTinh(rs.getString("DonViTinh"));
                                        lstKhoHang.add(kh);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstKhoHang;
        }
        
        public void insert(KhoHang kh){
                String sql = "insert into KhoHang(MaDichVu, SoluongTon, DonViTinh) values (?,?,?)";
                JDBC.update(sql,
                        kh.getMaDichVu(),
                        kh.getSoLuongTon(),
                        kh.getDonViTinh()
                        );
        }

        public void updateSoLuong(KhoHang kh, int soLuong) {
                String sql = "update KhoHang set SoLuongTon =(SoLuongTon + ?) where MaDichVu =? ";
                JDBC.update(sql,
                        soLuong,
                        kh.getMaDichVu());
        }

        public KhoHang SelectByID(String MaDichVu) {
                String sql = "select * from KhoHang where MaDichVu =?";
                List<KhoHang> lst = SelectBySql(sql, MaDichVu);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<Object[]> Select_KhoHang() {
                String sql = "select KhoHang.MaDichVu, TenLoaiDV, SoLuongTon,DonViTinh from KhoHang inner join DichVu on KhoHang.MaDichVu = DichVu.MaDichVu";
                String[] cols = {
                        "MaDichVu",
                        "TenLoaiDV",
                        "SoLuongTon",
                        "DonViTinh"
                };
                return JDBC.getListOfArray(sql, cols);
        }

        public List<Object[]> getDanhSachNhap() {
                String sql = "select KhoHang.MaDichVu, TenLoaiDV, SoLuongTon from KhoHang inner join DichVu on KhoHang.MaDichVu = DichVu.MaDichVu";
                String[] cols = {
                        "MaDichVu",
                        "TenLoaiDV",
                        "SoLuongTon"
                };
                return JDBC.getListOfArray(sql, cols);
        }

        public List<Object[]> SelectByKeyWord_KhoHang(String keyword) {
//                if (keyword.length() == 0) {
//                        return Select_KhoHang();
//                }
                String sql = "select KhoHang.MaDichVu, TenLoaiDV, SoLuongTon,DonViTinh from KhoHang inner join DichVu on KhoHang.MaDichVu = DichVu.MaDichVu where DichVu.MaDichVu like 'DVB%' and (KhoHang.MaDichVu like ? or DichVu.TenLoaiDV like ?)";
                String[] cols = {"MaDichVu", "TenLoaiDV", "SoLuongTon", "DonViTinh"};
                return JDBC.getListOfArray(sql, cols, "%" + keyword + "%", "%" + keyword + "%");
        }

        //---------------------------------------------------------------------------------------------
        public KhoHang SelectKhoHangByMaDV(String maDichVu) {
                String sql = "select * from KhoHang where MaDichVu = ? and MaDichVu like 'DVB%'";
                List<KhoHang> lst = SelectBySql(sql, maDichVu);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public void updateXuatKhoHang(int soLuongXuat, String maDichVu) {
                String sql = "update KhoHang set SoLuongTon = (SoLuongTon - ?) where MaDichVu = ?";
                JDBC.update(sql, soLuongXuat, maDichVu);
        }

        public void updateHuyXuatKhoHang(String maDichVu, int soLuongHuy) {
                String sql = "update KhoHang set SoLuongTon = (SoLuongTon + ?) where MaDichVu = ?";
                JDBC.update(sql, soLuongHuy, maDichVu);
        }
}
