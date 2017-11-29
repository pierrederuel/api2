package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Role;
import fr.enelia.dashboardapi.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @PostMapping(value = "role")
    public Role createRole(@RequestBody Role role) {
        role = roleService.createRole(role);
        return role;
    }

    @PutMapping("role")
    public Role updateRole(@RequestBody Role role) {
        role = roleService.updateRole(role);
        return role;
    }

    @GetMapping(value = "role")
    public Role getRoleById(@RequestParam Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping(value = "roles")
    public Iterable<Role> getRoles() {
        return roleService.getRoles();
    }
}
