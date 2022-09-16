package com.felixlaura.repository;

import com.felixlaura.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMgmtRepository extends JpaRepository<UserMaster, Integer> {

    UserMaster findByEmail(String email);
}
