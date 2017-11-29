package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    public Role createRole(Role role);
    public Role updateRole(Role role);
    public Role getRoleById(Long id);
    public Iterable<Role> getRoles();
}
