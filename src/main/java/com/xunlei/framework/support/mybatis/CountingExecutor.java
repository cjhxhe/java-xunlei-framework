package com.xunlei.framework.support.mybatis;

import com.xunlei.framework.support.mybatis.util.JdbcUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;


public class CountingExecutor {

    private MappedStatement ms;
    private Dialect dialect;
    private BoundSql boundSql;

    public CountingExecutor(MappedStatement ms, Dialect dialect, BoundSql boundSql) {
        this.ms = ms;
        this.dialect = dialect;
        this.boundSql = boundSql;
    }

    public Integer execute() throws SQLException {
        String rawSql = boundSql.getSql().trim();
        String countingSql = dialect.getCountingSQL(rawSql);

        Connection conn = null;
        try {
            Environment evn = ms.getConfiguration().getEnvironment();
            conn = evn.getDataSource().getConnection();
            return new JdbcUtil().counting(conn, countingSql, this);
        } finally {
            JdbcUtil.close(conn);
        }
    }

    public MappedStatement getMs() {
        return ms;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

}
