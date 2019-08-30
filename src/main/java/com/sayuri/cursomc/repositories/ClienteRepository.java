package com.sayuri.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sayuri.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Transactional(readOnly = true)
	Cliente findByEmail(String email); // SpringData detecta que quer fazer busca por email e já implementa o método
	
}
