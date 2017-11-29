package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public interface UtilisateurService {
    public Utilisateur createUtilisateur(Utilisateur utilisateur);
    public Utilisateur updateUtilisateur(Utilisateur utilisateur);
    public Utilisateur getUtilisateurById(Long id);
    public Utilisateur getUtilisateurByUsername(String username);
    public Utilisateur getUtilisateurByUsernameAndActive(String username);
    public Iterable<Utilisateur> getUtilisateurs();
    public Iterable<Utilisateur> getUtilisateursActifs();
}
