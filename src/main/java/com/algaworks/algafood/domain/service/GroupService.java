package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.GroupNotFoundException;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.repository.GroupRepository;

@Service
public class GroupService {
	private static final String GROUP_IN_USE_MSG = "Grupo de código %d não pode ser removido, pois está em uso";

	@Autowired
	private GroupRepository groupRepository;

	@Transactional
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Transactional
	public void delete(Long groupId) {
		try {
			groupRepository.deleteById(groupId);
			groupRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new GroupNotFoundException(groupId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(GROUP_IN_USE_MSG, groupId));
		}
	}

	public Group findOrFail(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
	}
}
