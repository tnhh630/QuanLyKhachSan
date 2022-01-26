/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Trung Nghia <PS14820>
 */
public class JDBC {

        private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        private static String url = "jdbc:sqlserver://42.117.36.10:1433;database=QuanLyKhachSan";
        private static String url = "jdbc:sqlserver://localhost;database=QuanLyKhachSan";
        private static String user = "sa";
//        private static String password = "Admin123@";
        private static String password = "";
        public static Connection conn = null;

        static {
                try {
                        Class.forName(driver);

                        Connection conn = DriverManager.getConnection(url, user, password);

                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        public JDBC() {

        }

        public JDBC(boolean a) {
                try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        conn = DriverManager.getConnection(url, user, password);
                } catch (Exception ex) {

                }

        }

        public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
                conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt;
                if (sql.trim().startsWith("{")) {
                        stmt = conn.prepareCall(sql);
                } else {
                        stmt = conn.prepareStatement(sql);
                }
                for (int i = 0; i < args.length; i++) {
                        stmt.setObject(i + 1, args[i]);
                }
                return stmt;
        }

        public static void update(String sql, Object... args) {
                try {
                        PreparedStatement stmt = getStmt(sql, args);
                        try {
                                stmt.executeUpdate();
                        } finally {
                                stmt.getConnection().close();
                        }
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        public static ResultSet query(String sql, Object... args) throws SQLException {
                PreparedStatement stmt = getStmt(sql, args);
                return stmt.executeQuery();
        }

        public static Object value(String sql, Object... args) {
                try {
                        ResultSet rs = JDBC.query(sql, args);
                        if (rs.next()) {
                                return rs.getObject(1);
                        }
                        rs.getStatement().getConnection().close();
                        return null;
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        public static List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
                try {
                        List<Object[]> list = new ArrayList<>();
                        ResultSet rs = JDBC.query(sql, args);
                        while (rs.next()) {
                                Object[] vals = new Object[cols.length];
                                for (int i = 0; i < cols.length; i++) {
                                        vals[i] = rs.getObject(cols[i]);
                                }
                                list.add(vals);
                        }
                        rs.getStatement().getConnection().close();
                        return list;
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
}
