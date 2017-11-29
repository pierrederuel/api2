package fr.enelia.dashboardapi.dto;

import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Commission;
import fr.enelia.dashboardapi.entities.Prospecteur;
import fr.enelia.dashboardapi.entities.Vente;

import java.io.Serializable;
import java.util.List;

public class InputVenteDTO implements Serializable {

    private Vente vente;
    private List<Commercial> commerciaux;

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public List<Commercial> getCommerciaux() {
        return commerciaux;
    }

    public void setComerciaux(List<Commercial> commerciaux) {
        this.commerciaux = commerciaux;
    }
}
