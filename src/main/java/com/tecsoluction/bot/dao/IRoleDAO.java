package com.tecsoluction.bot.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsoluction.bot.entidade.Role;



@Repository
public interface IRoleDAO extends JpaRepository<Role, UUID> {
}
