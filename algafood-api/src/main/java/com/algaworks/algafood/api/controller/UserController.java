package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.assembler.UserInputDisassembler;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.dto.input.PasswordInput;
import com.algaworks.algafood.api.dto.input.UserInput;
import com.algaworks.algafood.api.dto.input.UserWithPasswordInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.openapi.controller.UserControllerOpenApi;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.UserRepository;
import com.algaworks.algafood.domain.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CollectionModel<UserDTO> fetchAll() {
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

        UserDTO userDTO = userDTOAssembler.toModel(user);
        ResourceUriHelper.addUriInResponseHeader(userDTO.getId());

        return userDTO;
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
