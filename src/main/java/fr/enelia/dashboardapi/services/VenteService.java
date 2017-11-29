package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.dto.InputVenteDTO;
import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Prospecteur;
import fr.enelia.dashboardapi.entities.Vente;
import org.springframework.stereotype.Service;

@Service
public interface VenteService {
    public Vente createVente(InputVenteDTO vente);
    public Vente updateVente(Vente vente);
    public Vente updateVenteEmploye(Vente vente);
    public Vente updateVenteManager(Vente vente);
    public Vente getVenteById(Long id);
    public Iterable<Vente> getVentes();
    public Iterable<Vente> getVentesByProspecteur(Prospecteur prospcteur);
    public Iterable<Vente> getVentesByCommercial(Commercial commercial);
    public Vente cancelVente(Vente vente);
}
