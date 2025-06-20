// UserMapper.java
package com.liuao.game_card_sell.mapper;
import com.liuao.game_card_sell.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 插入用户并返回自增主键
     * @param user 用户实体
     * @return 影响行数（成功为1）
     */
    int insertUser(User user);

    // 根据用户名查询用户
    User selectByUsername(@Param("username") String username);

    // 检查用户名是否存在
    int checkUsernameExists(@Param("username") String username);

    // 检查邮箱是否存在
    int checkEmailExists(@Param("email") String email);

    User findByUsername(String username);

    // 分页查询用户列表
    List<User> selectUserList(@Param("offset") int offset, @Param("limit") int limit);

    // 查询用户总数
    int selectUserCount();

    List<User> selectPageWithRoles(@Param("offset") int offset, @Param("limit") int limit);

    int selectUserWithRolesCount();
}