package fr.enelia.dashboardapi.controllers;

import fr.enelia.dashboardapi.dto.InputVenteDTO;
import fr.enelia.dashboardapi.entities.*;
import fr.enelia.dashboardapi.services.*;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
public class VenteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenteController.class);

    @Autowired
    private VenteService venteService;
    @Autowired
    private ResultatService resultatService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private CommercialService commercialService;
    @Autowired
    private ProspecteurService prospecteurService;
    @Autowired
    CommissionService commissionService;

    @PostMapping(value = "vente")
    public Vente createVente(@RequestBody InputVenteDTO vente) {
        JSONObject data = new JSONObject();
        LOGGER.error(vente.getVente().getClient());
        StringBuilder messageBody = new StringBuilder("Vente de ");
        //On créé la vente et les commissions associées à chaque commercial
        //On vérifie la vente ne contient pas de prospecteur (commercial ayant prospecté seul)
        if (vente.getVente().getProspecteur().getId() == 0) {
            vente.getVente().setProspecteur(null);
        }
        Vente resultVente = venteService.createVente(vente);
        messageBody.append(resultVente.getMontantTotal());
        messageBody.append(" € realisee par ");
        if (resultVente.getProspecteur() != null) {
            messageBody.append(resultVente.getProspecteur().getPrenom());
            messageBody.append(" ");
            messageBody.append(resultVente.getProspecteur().getNom());
        }
        //On met à jour les résultats en fonction de la vente
        resultatService.addVenteToResultat(resultVente);
        //On supprime la redondance entre vente et commission pour éviter une boucle infinie lors de la serialisation
        Iterator<Commission> itCommissions = resultVente.getCommisions().iterator();
        while (itCommissions.hasNext()) {
            Commission currentCommission = itCommissions.next();
            currentCommission.setVente(null);
            messageBody.append(" / ");
            messageBody.append(currentCommission.getCommercial().getPrenom());
            messageBody.append(" ");
            messageBody.append(currentCommission.getCommercial().getNom());
        }
        messageBody.append(" chez Mr/Mme ");
        messageBody.append(resultVente.getClient());
        //On envoie une notification à l'ensemble des utilisateurs actifs
        Iterable<Utilisateur> users = utilisateurService.getUtilisateursActifs();
        data.put("type", "vente");
        data.put("titre", messageBody.toString());
        data.put("commentaires", resultVente.getCommentaires());
        notificationService.sendNotification("Vente realisee", messageBody.toString(), users, data);
        return resultVente;
    }

    @PutMapping("vente-employe")
    public Vente updateVenteEmploye(@RequestBody Vente vente) {
        JSONObject data = new JSONObject();
        StringBuilder messageBody = new StringBuilder();
        if (vente.isAssise()) {
            messageBody.append("Assise effectuee");
        }
        vente = venteService.updateVenteEmploye(vente);
        messageBody.append(" sur la vente de ");
        messageBody.append(vente.getMontantTotal());
        messageBody.append(" € realisee par ");
        if (vente.getProspecteur() != null) {
            messageBody.append(vente.getProspecteur().getPrenom());
            messageBody.append(" ");
            messageBody.append(vente.getProspecteur().getNom());
        }
        Iterator<Commission> itCommissions = vente.getCommisions().iterator();
        while (itCommissions.hasNext()) {
            Commission currentCommission = itCommissions.next();
            currentCommission.setVente(null);
            messageBody.append(" / ");
            messageBody.append(currentCommission.getCommercial().getPrenom());
            messageBody.append(" ");
            messageBody.append(currentCommission.getCommercial().getNom());
        }
        messageBody.append(" chez Mr/Mme ");
        messageBody.append(vente.getClient());
        //On envoie une notification à l'ensemble des utilisateurs actifs
        Iterable<Utilisateur> users = utilisateurService.getUtilisateursActifs();
        data.put("type", "statut");
        data.put("titre", messageBody.toString());
        data.put("commentaires", vente.getCommentaires());
        notificationService.sendNotification("Changement de statut", messageBody.toString(), users, data);

        return vente;
    }

    @PutMapping("vente-manager")
    public Vente updateVenteManager(@RequestBody Vente vente) {
        JSONObject data = new JSONObject();
        StringBuilder messageBody = new StringBuilder();
        vente = venteService.updateVenteManager(vente);
        if (vente.isFinancement()) {
            messageBody.append("Financement obtenu ");
        } else if (vente.isVisiteTechnique()) {
            messageBody.append("Visite technique effectuee");
        } else if (vente.isEcoHabitant()) {
            messageBody.append("Eco Habitant effectue");
        } else if (vente.isAssise()) {
            messageBody.append("Assise effectuee");
        }
        messageBody.append(" sur la vente de ");
        messageBody.append(vente.getMontantTotal());
        messageBody.append(" € realisee par ");
        if (vente.getProspecteur() != null) {
            messageBody.append(vente.getProspecteur().getPrenom());
            messageBody.append(" ");
            messageBody.append(vente.getProspecteur().getNom());
        }
        Iterator<Commission> itCommissions = vente.getCommisions().iterator();
        while (itCommissions.hasNext()) {
            Commission currentCommission = itCommissions.next();
            currentCommission.setVente(null);
            messageBody.append(" / ");
            messageBody.append(currentCommission.getCommercial().getPrenom());
            messageBody.append(" ");
            messageBody.append(currentCommission.getCommercial().getNom());
        }
        messageBody.append(" chez Mr/Mme ");
        messageBody.append(vente.getClient());
        //On envoie une notification à l'ensemble des utilisateurs actifs
        Iterable<Utilisateur> users = utilisateurService.getUtilisateursActifs();
        data.put("type", "statut");
        data.put("titre", messageBody.toString());
        data.put("commentaires", vente.getCommentaires());
        notificationService.sendNotification("Changement de statut", messageBody.toString(), users, data);

        return vente;
    }

    @GetMapping(value = "vente/{venteId}")
    public Vente getVenteById(@PathVariable Long venteId) {
        Vente result = venteService.getVenteById(venteId);
        Iterator<Commission> itCommissions = result.getCommisions().iterator();
        while (itCommissions.hasNext()) {
            itCommissions.next().setVente(null);
        }
        return result;
    }

    @GetMapping(value = "ventes")
    public Iterable<Vente> getVentes() {
        Iterable<Vente> result = venteService.getVentes();
        Iterator<Vente> itVentes = result.iterator();
        while (itVentes.hasNext()) {
            Vente vente = itVentes.next();
            Iterator<Commission> itCommissions = vente.getCommisions().iterator();
            while (itCommissions.hasNext()) {
                itCommissions.next().setVente(null);
            }
        }
        return result;
    }

    @GetMapping(value = "ventes-en-cours")
    public Iterable<Vente> getVentesEnCours(Principal principal) {
        boolean isManager = false;
        Iterable<Vente> result = null;

        Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(principal.getName());
        utilisateur.getEmploye();
        for (int i = 0; i < utilisateur.getRoles().size(); i++) {
            if (utilisateur.getRoles().get(i).getNom().equals("ROLE_MANAGER")
                    || utilisateur.getRoles().get(i).getNom().equals("ROLE_ADMIN")
                    || utilisateur.getRoles().get(i).getNom().equals("ROLE_SUPERADMIN")) {
                isManager = true;
                break;
            }
        }

        if (isManager) {
            //On récupère l'ensemble des ventes
            result = venteService.getVentes();
        } else {
            //On récupère uniquement les ventes du commercial / prospecteur

            Commercial c = commercialService.getCommercialById(utilisateur.getEmploye().getId());
            ArrayList<Vente> ventes = new ArrayList<>();
            if (c != null) {
                Iterator<Commission> itCommissions = commissionService.getCommissionsByCommercial(c).iterator();
                while (itCommissions.hasNext()) {
                    ventes.add(itCommissions.next().getVente());
                }
            } else {
                Prospecteur p = prospecteurService.getProspecteurById(utilisateur.getEmploye().getId());
                Iterator<Vente> itVentes = venteService.getVentesByProspecteur(p).iterator();
                while (itVentes.hasNext()) {
                    ventes.add(itVentes.next());
                }
            }
            result = ventes;
        }

        if (result != null) {
            Iterator<Vente> itVentes = result.iterator();
            while (itVentes.hasNext()) {
                Vente vente = itVentes.next();
                Iterator<Commission> itCommissions = vente.getCommisions().iterator();
                while (itCommissions.hasNext()) {
                    itCommissions.next().setVente(null);
                }
            }
        }

        return result;
    }

    @PutMapping(value = "cancel-vente")
    public Vente cancelVente(@RequestBody Vente vente) {
        JSONObject data = new JSONObject();
        StringBuilder messageBody = new StringBuilder("Annulation de la vente de ");
        Vente result = venteService.cancelVente(vente);
        messageBody.append(result.getMontantTotal());
        messageBody.append(" € realisee par ");
        if (vente.getProspecteur() != null) {
            messageBody.append(result.getProspecteur().getPrenom());
            messageBody.append(" ");
            messageBody.append(result.getProspecteur().getNom());
        }
        Iterator<Commission> itCommissions = result.getCommisions().iterator();
        while (itCommissions.hasNext()) {
            Commission currentCommission = itCommissions.next();
            currentCommission.setVente(null);
            messageBody.append(" / ");
            messageBody.append(currentCommission.getCommercial().getPrenom());
            messageBody.append(" ");
            messageBody.append(currentCommission.getCommercial().getNom());
        }
        messageBody.append(" chez Mr/Mme ");
        messageBody.append(result.getClient());
        //On envoie une notification à l'ensemble des utilisateurs actifs
        Iterable<Utilisateur> users = utilisateurService.getUtilisateursActifs();

        data.put("type", "annulation");
        data.put("titre", messageBody.toString());
        data.put("commentaires", result.getCommentaires());
        notificationService.sendNotification("Vente annulee", messageBody.toString(), users, data);

        return result;
    }
}
