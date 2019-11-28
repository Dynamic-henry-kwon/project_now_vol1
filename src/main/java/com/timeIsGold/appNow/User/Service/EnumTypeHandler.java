package com.timeIsGold.appNow.User.Service;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.timeIsGold.appNow.User.Domain.Level;

public class EnumTypeHandler  extends BaseTypeHandler<Level>{
	public EnumTypeHandler() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Level parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.initValue());
	}
	
	@Override
	public Level getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("rsResult" + cs.getInt(columnIndex));
		return Level.valueOf(cs.getInt(columnIndex));
	}
	
	@Override
	public Level getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return Level.valueOf(rs.getInt(columnIndex));
	}
	
	@Override
	public Level getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return Level.valueOf(rs.getInt(columnName));
	}

	
}
