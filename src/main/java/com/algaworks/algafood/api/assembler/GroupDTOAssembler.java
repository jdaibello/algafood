package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.domain.model.Group;

@Component
public class GroupDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GroupDTO toModel(Group group) {
		return modelMapper.map(group, GroupDTO.class);
	}

	public List<GroupDTO> toCollectionModel(List<Group> groups) {
		return groups.stream().map(group -> toModel(group)).collect(Collectors.toList());
	}
}
