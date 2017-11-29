package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Utilisateur;
import fr.enelia.dashboardapi.repositories.UtilisateurRepository;
import fr.enelia.dashboardapi.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        LOGGER.info("createUtilisateur");
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        LOGGER.info("updateUtilisateur");
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        LOGGER.info("getUtilisateurById");
        return utilisateurRepository.findOne(id);
    }

    @Override
    public Utilisateur getUtilisateurByUsername(String username) {
        LOGGER.info("getUtilisateurById");
        return utilisateurRepository.findByUsername(username).get();
    }

    @Override
    public Utilisateur getUtilisateurByUsernameAndActive(String username) {
        LOGGER.info("getUtilisateurById");
        return utilisateurRepository.findByUsernameAndActive(username, true).get();//findByUsername(username).get();
    }

    @Override
    public Iterable<Utilisateur> getUtilisateurs() {
        LOGGER.info("getUtilisateurs");
        return utilisateurRepository.findAll();
    }

    @Override
    public Iterable<Utilisateur> getUtilisateursActifs() {
        LOGGER.info("getUtilisateursActifs");
        return utilisateurRepository.findUtilisateursActifs();
    }
}
