package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Objectif;
import org.springframework.stereotype.Service;

@Service
public interface ObjectifService {
    public Objectif createObjectif(Objectif objectif);
    public Objectif updateObjectif(Objectif objectif);
    public Objectif getObjectifById(Long id);
    public Objectif getLatestObjectifOfEmployeById(Employe employe);
    public Objectif getLatestObjectifOfEmployeByIdAndPeriodeBeforeLast(Employe employe);
    public Iterable<Objectif> getObjectifs();
}
