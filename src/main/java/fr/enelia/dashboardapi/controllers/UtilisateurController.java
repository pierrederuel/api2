package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.entities.Utilisateur;
import fr.enelia.dashboardapi.repositories.EmployeRepository;
import fr.enelia.dashboardapi.repositories.UtilisateurRepository;
import fr.enelia.dashboardapi.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
public class UtilisateurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EmployeRepository employeRepository;

    @PostMapping(value = "utilisateur")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @PutMapping(value = "utilisateur")
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.updateUtilisateur(utilisateur);
    }

    @GetMapping(value = "utilisateur/{id}")
    public Utilisateur getUtilisateur(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @GetMapping(value = "utilisateurs")
    public Iterable<Utilisateur> getUtilisateurs() {
        return utilisateurService.getUtilisateurs();
    }

    @GetMapping(value = "/me")
    @ResponseBody
    public Utilisateur currentUserName(Principal principal) {
        LOGGER.debug("currentUserName => " + principal.getName());
        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(principal.getName());
        utilisateur.getEmploye();
        for (int i = 0; i < utilisateur.getRoles().size(); i++) {
            utilisateur.getRoles().get(i).setUtilisateurs(null);
        }
        return utilisateur;
    }

    @Resource(name="tokenStore")
    TokenStore tokenStore;

    @RequestMapping(method = RequestMethod.GET, value = "/tokens")
    @ResponseBody
    public List<String> getTokens() {
        List<String> tokenValues = new ArrayList<String>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("eneliaAndroid");
        if (tokens!=null){
            for (OAuth2AccessToken token:tokens){
                tokenValues.add(token.getValue());
            }
        }
        return tokenValues;
    }

    @PostMapping(value = "/revoke")
    public void revokeUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findByEmploye(employeRepository.findOne(utilisateur.getId()));
        LOGGER.debug("revokeUtilisateur => " + user.getUsername());
        //On passe le statut de l'utilisateur à inactif
        utilisateurRepository.revokeUtilisateur(user.getId());
        //On supprime les tokens d'accès sur l'ensemble des environnements
        Collection<OAuth2AccessToken> tokens = new ArrayList<>();
        tokens.addAll(tokenStore.findTokensByClientIdAndUserName("eneliaAndroid", user.getUsername()));
        tokens.addAll(tokenStore.findTokensByClientIdAndUserName("eneliaIOS", user.getUsername()));
        tokens.addAll(tokenStore.findTokensByClientIdAndUserName("eneliaWeb", user.getUsername()));
        Iterator<OAuth2AccessToken> itToken = tokens.iterator();

        while (itToken.hasNext()) {
            OAuth2AccessToken current = itToken.next();
            LOGGER.debug("AccessToken => " + current.getValue());
            tokenStore.removeRefreshToken(current.getRefreshToken());
            tokenStore.removeAccessToken(current);
        }
    }
}
