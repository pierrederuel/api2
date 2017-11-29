package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Role;
import fr.enelia.dashboardapi.repositories.RoleRepository;
import fr.enelia.dashboardapi.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        LOGGER.info("createRole");
        role = roleRepository.save(role);
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        LOGGER.info("updateRole");
        role = roleRepository.save(role);
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        LOGGER.info("getRoleById");
        return roleRepository.findOne(id);
    }

    @Override
    public Iterable<Role> getRoles() {
        LOGGER.info("getRoles");
        return roleRepository.findAll();
    }
}
