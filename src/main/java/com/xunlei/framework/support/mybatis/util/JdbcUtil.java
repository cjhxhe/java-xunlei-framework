package com.xunlei.framework.support.mybatis.util;

import com.alibaba.fastjson.JSON;
import com.xunlei.framework.common.util.CglibUtils;
import com.xunlei.framework.common.util.EnumUtils;
import com.xunlei.framework.support.mybatis.CountingExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Jdbc Utils
 */
public final class JdbcUtil {

    Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String buildStatmentSql(String sql, CountingExecutor ce) {
        BoundSql boundSql = ce.getBoundSql();
        List<ParameterMapping> mappings = boundSql.getParameterMappings();
        if (mappings == null) {
            return sql;
        }
        Object paramObject = boundSql.getParameterObject();
        TypeHandlerRegistry typeHandlerRegistry = ce.getMs().getConfiguration().getTypeHandlerRegistry();
        for (ParameterMapping pm : mappings) {
            String propertyName = pm.getProperty();
            Object value = null;
            if (paramObject != null) {
                if (paramObject instanceof Map<?, ?>) {
                    value = ((Map<?, ?>) paramObject).get(propertyName);
                    if (value != null && value instanceof EnumUtils.IDEnum) {
                        value = ((EnumUtils.IDEnum) value).getId();
                    }
                } else if (typeHandlerRegistry.hasTypeHandler(paramObject.getClass())) {
                    value = paramObject;
                } else {
                    value = CglibUtils.getPropertyValue(paramObject, propertyName);
                }
            }
            if (value == null && boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            }

            String phString = "";
            if (value != null) {
                if (value instanceof String) {
                    phString = "'" + value.toString() + "'";
                } else if (value instanceof Date) {
                    phString = "'" + SDF.format((Date) value) + "'";
                } else {
                    phString = value.toString();
                }
            } else {
                logger.error(
                        "property value may be losted. sql: {}, currentPropertyName: {}, boundSql: {}",
                        sql, propertyName, JSON.toJSONString(boundSql));
                phString = null;
            }
            sql = sql.replaceFirst("\\?", phString);
        }
        return sql;
    }

    public Integer counting(Connection conn, String sql, CountingExecutor ce)
            throws SQLException {
        List<Object[]> dataList = query(conn, buildStatmentSql(sql, ce));
        if (dataList != null && dataList.size() > 0) {
            // Extract the first row and first column data
            Object[] rowData = dataList.get(0);
            if (rowData != null && rowData.length == 1) {
                return Integer.valueOf(String.valueOf(rowData[0]));
            }
        }
        return null;
    }

    public List<Object[]> query(Connection conn, String sql) throws SQLException {
        if (conn.isClosed()) {
            return null;
        }

        Statement state = null;
        ResultSet rs = null;

        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            return extractData(rsmd, rs);
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (state != null) {
                state.close();
            }
        }
    }


    private List<Object[]> extractData(ResultSetMetaData rsmd, ResultSet rs) throws SQLException {
        List<Object[]> data = new ArrayList<Object[]>();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            Object[] objData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                objData[i - 1] = rs.getObject(i);
            }

            data.add(objData);
        }

        return data;
    }


    public static void close(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

}
