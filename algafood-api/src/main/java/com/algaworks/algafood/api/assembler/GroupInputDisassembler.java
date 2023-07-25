package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.GroupInput;
import com.algaworks.algafood.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Group toDomainObject(GroupInput groupInput) {
        return modelMapper.map(groupInput, Group.class);
    }

    public void copyToDomainObject(GroupInput groupInput, Group group) {
        modelMapper.map(groupInput, group);
    }
}
