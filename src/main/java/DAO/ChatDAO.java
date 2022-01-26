/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Chat;
import Helper.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trần Trung Nghĩa <PS14820>
 */
public class ChatDAO {

        public void insert(Chat chat) {
                String sql = "insert into chat (maNhanVienGui, maNhanVienNhan, noiDung, ngayChat) values (?,?,?,?)";
                JDBC.update(sql,
                        chat.getMaNhanVienGui(),
                        chat.getMaNhanVienNhan(),
                        chat.getNoiDung(),
                        chat.getNgayChat()
                );
        }

       

        public List<Chat> SelectBySQL(String sql, Object... args) {
                List<Chat> lst = new ArrayList<>();
                try {
                        ResultSet rs = null;
                        try {
                                rs = JDBC.query(sql, args);
                                while (rs.next()) {
                                        Chat ct = new Chat();
                                        ct.setMaNhanVienGui(rs.getString("maNhanVienGui"));
                                        ct.setMaNhanVienNhan(rs.getString("maNhanVienNhan"));
                                        ct.setNoiDung(rs.getString("noiDung"));
                                        ct.setNgayChat(rs.getDate("ngayChat"));
          
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

       public List<Chat> SelectAll(String nguoiGoi, String nguoiNhan) {
        String sql = "select * from Chat where (maNhanVienGui=? and maNhanVienNhan=?) or (maNhanVienGui=? and maNhanVienNhan=?) order by ngayChat asc";
        return SelectBySQL(sql, nguoiGoi, nguoiNhan, nguoiNhan, nguoiGoi);
    }
       
}
