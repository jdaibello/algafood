package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.assembler.UserInputDisassembler;
import com.algaworks.algafood.api.controller.openapi.UserControllerOpenApi;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.dto.input.PasswordInput;
import com.algaworks.algafood.api.dto.input.UserInput;
import com.algaworks.algafood.api.dto.input.UserWithPasswordInput;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;
import com.algaworks.algafood.domain.service.UserService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService service;

	@Autowired
	private UserDTOAssembler userDTOAssembler;

	@Autowired
	private UserInputDisassembler userInputDisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDTO> fetchAll() {
		return userDTOAssembler.toCollectionModel(userRepository.findAll());
	}

	@Override
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO find(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		User user = service.findOrFail(userId);

		return userDTOAssembler.toModel(user);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO add(@RequestBody @Valid UserWithPasswordInput userInput) {
		User user = userInputDisassembler.toDomainObject(userInput);
		user = service.save(user);

		return userDTOAssembler.toModel(user);
	}

	@Override
	@PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO update(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@RequestBody @Valid UserInput userInput) {
		User currentUser = service.findOrFail(userId);
		userInputDisassembler.copyToDomainObject(userInput, currentUser);
		currentUser = service.save(currentUser);

		return userDTOAssembler.toModel(currentUser);
	}

	@Override
	@PatchMapping("/{userId}/reset-password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@RequestBody @Valid PasswordInput passwordInput) {
		service.updatePassword(userId, passwordInput.getCurrentPassword(), passwordInput.getNewPassword());
	}
}
