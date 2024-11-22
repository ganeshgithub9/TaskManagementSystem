package com.example.taskmanagementsystem.repositories;

import com.example.taskmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByMail(String mail);

    @Query(value = "select r.name from roles r inner join users_roles ur on r.id=ur.roles_id and ur.user_id=:userId",nativeQuery = true)
    List<String> getRoleNames(@Param("userId") int id);
}
