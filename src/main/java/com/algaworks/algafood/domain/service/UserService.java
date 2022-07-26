package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.UserNotFoundException;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User save(User user) {
		userRepository.detach(user);

		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser.isPresent() && !existingUser.get().equals(user)) {
			throw new BusinessException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
		}

		return userRepository.save(user);
	}

	@Transactional
	public void updatePassword(Long userId, String currentPassword, String newPassword) {
		User user = findOrFail(userId);

		if (user.passwordDoesntMatch(currentPassword)) {
			throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
		}

		user.setPassword(newPassword);
	}

	public User findOrFail(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
}
