package com.xunlei.framework.support.mybatis.handler;

import com.xunlei.framework.common.util.EnumUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 默认枚举转换器
 */
@MappedJdbcTypes(value = JdbcType.CHAR, includeNullJdbcType = true)
public class DefaultEnumTypeHandler extends BaseTypeHandler<EnumUtils.IDEnum> {

    private Class<EnumUtils.IDEnum> type;

    public DefaultEnumTypeHandler() {

    }

    public DefaultEnumTypeHandler(Class<EnumUtils.IDEnum> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, EnumUtils.IDEnum idEnum, JdbcType jdbcType) throws SQLException {
        if (idEnum == null) {
            preparedStatement.setString(i, null);
        } else {
            preparedStatement.setString(i, idEnum.getId());
        }
    }

    @Override
    public EnumUtils.IDEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return getValue(resultSet.getString(s));
    }

    @Override
    public EnumUtils.IDEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return getValue(resultSet.getString(i));
    }

    @Override
    public EnumUtils.IDEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return getValue(callableStatement.getString(i));
    }

    private EnumUtils.IDEnum getValue(String value) {
        if (value == null) {
            return null;
        }
        return EnumUtils.byId(value, type);
    }
}
