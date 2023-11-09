package com.xunlei.framework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DBUtils {

    private static Logger log = LoggerFactory.getLogger(DBUtils.class);

    public static Connection getConnection(String url, String driverClass, String username, String password) {
        try {
            Class.forName(driverClass);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.error("获取数据库连接出错，原因：", e);
            return null;
        }
    }

    public static void release(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException se) {
            log.error("释放数据库连接出错，原因：", se);
        }
    }

    public static void release(Connection con, ResultSet rs) {
        DBUtils.release(con, null, rs);
    }

    public static void release(Connection con, PreparedStatement ps) {
        DBUtils.release(con, ps, null);
    }

    public static void release(Connection con) {
        DBUtils.release(con, null, null);
    }

    public static void release(PreparedStatement ps, ResultSet rs) {
        DBUtils.release(null, ps, rs);
    }
}
