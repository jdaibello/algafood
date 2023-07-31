package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.GroupController;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GroupDTOAssembler extends RepresentationModelAssemblerSupport<Group, GroupDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public GroupDTOAssembler() {
        super(GroupController.class, GroupDTO.class);
    }

    @Override
    public GroupDTO toModel(Group group) {
        GroupDTO groupDTO = createModelWithId(group.getId(), group);
        modelMapper.map(group, groupDTO);

        groupDTO.add(algaLinks.linkToGroups("groups"));
        groupDTO.add(algaLinks.linkToGroupPermissions(group.getId(), "permissions"));

        return groupDTO;
    }

    @Override
    public CollectionModel<GroupDTO> toCollectionModel(Iterable<? extends Group> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToGroups());
    }
}
