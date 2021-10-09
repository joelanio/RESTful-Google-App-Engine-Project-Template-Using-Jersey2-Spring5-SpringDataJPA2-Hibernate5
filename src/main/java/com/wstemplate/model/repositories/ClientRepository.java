package com.wstemplate.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wstemplate.model.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	//exemple of JPQL Query usage
	@Query("select c from Client c where c.email = :email")
	public Optional<Client> findByEmail(@Param("email")String email);
}
