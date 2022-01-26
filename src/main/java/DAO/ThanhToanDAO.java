/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.DichVu;
import Entity.ThongTin;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThanhToanDAO {

        public List<ThongTin> SelectBySQL(String sql, Object... args) {
                List<ThongTin> lstThongTin = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        ThongTin tt = new ThongTin();
                                        tt.setHoten(rs.getString("TenKhachHang"));
                                        tt.setLoaiphong(rs.getString("MaLoaiPhong"));
                                        tt.setNgaydat(rs.getDate("NgayDatPhong"));
                                        tt.setNgaytra(rs.getDate("NgayTraPhong"));
                                        tt.setGiaphong(rs.getDouble("GiaLoaiPhong"));
                                        tt.setTienphong(rs.getDouble("TongTienPhong"));
                                        tt.setDatcoc(rs.getDouble("DatCocPhong"));
                                        lstThongTin.add(tt);
                                }
                        } finally {
                                rs.getStatement().getConnection().close();
                        }
                } catch (Exception e) {
                        throw new RuntimeException();
                }
                return lstThongTin;
        }

        public ThongTin getThongTinDatPhong(String madatphong) {
                String sql = "{call sp_ThongTinThuePhong (?)}";
                List<ThongTin> lst = SelectBySQL(sql, madatphong);
                return lst.size() > 0 ? lst.get(0) : null;
        }

        public List<Object[]> getThongTinDichVu(String madatphong) {
                String sql = "{CALL sp_ThongTinDichVu (?)}";
                String[] cols = {
                        "TenLoaiDV",
                        "SoLuong",
                        "DonGia",
                        "ThanhTien",
                        "NgaySuDungDV"
                };
                return JDBC.getListOfArray(sql, cols, madatphong);
        }
}
