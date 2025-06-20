package com.liuao.game_card_sell.handler;

import com.liuao.game_card_sell.entity.CartridgePlatform;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CartridgePlatformTypeHandler extends BaseTypeHandler<List<CartridgePlatform>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<CartridgePlatform> parameter, JdbcType jdbcType) throws SQLException {
        // 一般查询场景用不到设置参数（这里是结果映射用），插入/更新时若要设置复杂类型，需额外处理，此处简单抛异常或空实现
        throw new UnsupportedOperationException("暂不支持设置 List<CartridgePlatform> 参数");
    }

    @Override
    public List<CartridgePlatform> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertStringToList(rs.getString(columnName));
    }

    @Override
    public List<CartridgePlatform> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertStringToList(rs.getString(columnIndex));
    }

    @Override
    public List<CartridgePlatform> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertStringToList(cs.getString(columnIndex));
    }

    // 辅助方法：将字符串转换为GamePlatform列表
    private List<CartridgePlatform> convertStringToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Stream.of(value.split(","))
                .map(item -> {
                    String[] parts = item.split(":");
                    if (parts.length == 2) {
                        CartridgePlatform platform = new CartridgePlatform();
                        platform.setId(Long.parseLong(parts[0]));
                        platform.setPlatformName(parts[1]);
                        // description 若数据库没存，可设为 null 或空字符串，根据需求调整
                        return platform;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}