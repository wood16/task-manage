package com.example.taskmanage.repository;

import com.example.taskmanage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    List<UserEntity> findByUsernameContaining(String username);

    @Query(value = "SELECT u.* from tbl_user u where u.id in (" +
            "SELECT ur.user_id from tbl_user_role ur where ur.role_id in (" +
            "SELECT r.id from tbl_role r where r.name != ?1 ))" +
            "and u.username like %?2%", nativeQuery = true)
    List<UserEntity> findAllUserWithoutRole(String roleName, String search);

    @Query(value = "SELECT r.name from tbl_role r where r.id in (" +
            "SELECT ur.role_id from tbl_user_role ur where ur.user_id = ?1)", nativeQuery = true)
    List<String> findRoleByUser(long userId);
}
