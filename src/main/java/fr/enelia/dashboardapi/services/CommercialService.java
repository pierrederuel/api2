package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.dto.EmployeStats;
import fr.enelia.dashboardapi.entities.Commercial;
import org.springframework.stereotype.Service;

@Service
public interface CommercialService {
    public Commercial createCommercial(Commercial commercial);
    public Commercial updateCommercial(Commercial commercial);
    public Commercial getCommercialById(Long id);
    public Commercial getCommercialByNomAndPrenom(String nom, String prenom);
    public EmployeStats getCommercialAvecStatsById(Long id);
    public Iterable<Commercial> getCommerciaux();
}
