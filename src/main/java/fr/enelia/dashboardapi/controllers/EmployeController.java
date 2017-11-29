package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.dto.GestionEmploye;
import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Role;
import fr.enelia.dashboardapi.entities.Utilisateur;
import fr.enelia.dashboardapi.services.CommercialService;
import fr.enelia.dashboardapi.services.EmployeService;
import fr.enelia.dashboardapi.services.ProspecteurService;
import fr.enelia.dashboardapi.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class EmployeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeController.class);

    @Autowired
    EmployeService employeService;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    CommercialService commercialService;
    @Autowired
    ProspecteurService prospecteurService;

    @GetMapping(value="employes")
    public List<GestionEmploye> getAllEmployes() {
        List<GestionEmploye> results = new ArrayList<>();
        Iterable<Utilisateur> temp = utilisateurService.getUtilisateursActifs();
        Iterator<Utilisateur> itEmployes = temp.iterator();
        while (itEmployes.hasNext()) {
            Utilisateur utilisateur = itEmployes.next();
            if (!"arnaud.deschaume".equals(utilisateur.getUsername())
                    && !"pierre.deruel".equals(utilisateur.getUsername())
                    && !"pauline.moison".equals(utilisateur.getUsername())
                    && !"sylvain.legrand".equals(utilisateur.getUsername())
                    && !"salle.commerciaux".equals(utilisateur.getUsername())
                    && !"salle.prospecteurs".equals(utilisateur.getUsername())) {
                GestionEmploye emp = new GestionEmploye();
                emp.setId(utilisateur.getId());
                if (utilisateur.getEmploye() != null) {
                    emp.setNom(utilisateur.getEmploye().getNom());
                    emp.setIdEmploye(utilisateur.getEmploye().getId());
                    emp.setPrenom(utilisateur.getEmploye().getPrenom());
                    emp.setPhoto(utilisateur.getEmploye().getPhoto());
                }

                Iterator<Role> itRoles = utilisateur.getRoles().iterator();
                while (itRoles.hasNext()) {
                    Role role = itRoles.next();
                    role.setUtilisateurs(null);
                    emp.getRoles().add(role.getNom());
                }
                results.add(emp);

            } else {
                itEmployes.remove();
            }

        }
        return results;
    }

    @PutMapping(value = "update-photo")
    public EmployeStats updatePhoto(@RequestBody GestionEmploye employe) {
        Employe temp = employeService.getEmployeById(employe.getId());
        temp.setPhoto("https://enelia.ddns.net/img/users/" + employe.getPhoto());
        temp = employeService.updateEmploye(temp);

        EmployeStats result = null;
        try {
            result = commercialService.getCommercialAvecStatsById(temp.getId());
        } catch (Exception e) {
            result = prospecteurService.getProspecteurAvecStatsById(temp.getId());
        }

        return result;
    }

    @GetMapping(value = "employe-stats/{userType}/{userId}")
    public EmployeStats getEmployeAvecStatsById(@PathVariable("userType") String userType, @PathVariable("userId") Long userId) {
        EmployeStats results = null;
        if ("commercial".equals(userType)) {
            results = commercialService.getCommercialAvecStatsById(userId);
        } else {
            results = prospecteurService.getProspecteurAvecStatsById(userId);
        }

        return results;
    }
}
