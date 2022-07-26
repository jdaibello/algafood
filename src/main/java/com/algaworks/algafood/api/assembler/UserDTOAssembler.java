package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.domain.model.User;

@Component
public class UserDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UserDTO toModel(User user) {
		return modelMapper.map(user, UserDTO.class);
	}

	public List<UserDTO> toCollectionModel(List<User> users) {
		return users.stream().map(user -> toModel(user)).collect(Collectors.toList());
	}
}
