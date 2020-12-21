package com.tecsoluction.bot.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tecsoluction.bot.entidade.Usuario;



@Repository
public interface IUsuarioDAO extends JpaRepository<Usuario, UUID> {


//	@Query("SELECT p FROM Usuario p where p.id=")
//    Usuario findById(Long id);
//	@Query("SELECT p FROM Usuario p where p.iduser= :iduser")
//    Usuario findByIdUser(@Param("iduser") String iduser);
	 Usuario findByIduser(String iduser);

}
