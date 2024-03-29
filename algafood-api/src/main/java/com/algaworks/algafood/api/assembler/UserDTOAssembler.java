package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserDTOAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public UserDTOAssembler() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(User user) {
        UserDTO userDTO = createModelWithId(user.getId(), user);
        modelMapper.map(user, userDTO);

        if (algaSecurity.canQueryUsersGroupsPermissions()) {
            userDTO.add(algaLinks.linkToUsers("users"));
            userDTO.add(algaLinks.linkToUserGroups(user.getId(), "user-groups"));
        }

        return userDTO;
    }

    @Override
    public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToUsers());
    }
}
