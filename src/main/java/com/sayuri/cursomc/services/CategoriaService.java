package com.sayuri.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sayuri.cursomc.domain.Categoria;
import com.sayuri.cursomc.repositories.CategoriaRepository;
import com.sayuri.cursomc.services.exceptions.DataIntegrityException;
import com.sayuri.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // Se não colocar null, o save vai achar que é atualização
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId()); // Faz busca para ver se existe esse id
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
