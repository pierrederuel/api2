package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.entities.Prospecteur;
import org.springframework.stereotype.Service;

@Service
public interface ProspecteurService {
    public Prospecteur createProspecteur(Prospecteur prospecteur);
    public Prospecteur updateProspecteur(Prospecteur prospecteur);
    public Prospecteur getProspecteurById(Long id);
    public EmployeStats getProspecteurAvecStatsById(Long id);
    public Iterable<Prospecteur> getProspecteurs();
}
