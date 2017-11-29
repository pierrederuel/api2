package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Employe;
import org.springframework.stereotype.Service;

@Service
public interface EmployeService {

    Employe updateEmploye(Employe employe);
    Iterable<Employe> getAllEmployes();
    Employe getEmployeById(Long id);
}
