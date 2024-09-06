package com.hhhh.dodream.domain.user.repository;

import com.hhhh.dodream.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
