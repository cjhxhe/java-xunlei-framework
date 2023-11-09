package com.xunlei.framework.support.mybatis.util;

import com.xunlei.framework.support.mybatis.DBMS;
import com.xunlei.framework.support.mybatis.Dialect;
import com.xunlei.framework.support.mybatis.impl.MySQLDialect;
import com.xunlei.framework.support.mybatis.impl.OracleDialect;


public class DialectUtil {

    public Dialect switchDialect(String dbmsString) throws Exception {
        DBMS dbms = null;
        try {
            dbms = DBMS.valueOf(dbmsString);
        } catch (Exception e) {
            throw e;
        }
        switch (dbms) {
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
                return new OracleDialect();
            default:
                throw new UnsupportedOperationException("The database is not supported.");
        }
    }

}
