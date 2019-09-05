package com.sayuri.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sayuri.cursomc.domain.Cliente;
import com.sayuri.cursomc.repositories.ClienteRepository;
import com.sayuri.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] =  randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um dígito (0 a 9 - códigos unicode: 48 a 57)
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiúscula (A a Z - códigos unicode: 65 a 90)
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minúscula (a a z - códigos unicode: 97 a 122)
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
}
