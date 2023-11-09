package com.xunlei.framework.support.mybatis.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface ParameterSetter {

    void setParameters(PreparedStatement ps) throws SQLException;

}
