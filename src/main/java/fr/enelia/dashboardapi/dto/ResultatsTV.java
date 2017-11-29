package fr.enelia.dashboardapi.dto;

import java.util.List;

public class ResultatsTV {

    private List<EmployeTV> resultatsCommerciaux;
    private List<EmployeTV> resultatsProspecteurs;
    private List<EmployeTV> resultatsCommerciauxPrecedent;
    private List<EmployeTV> resultatsProspecteursPrecedent;
    private double caEnCours;
    private double caPrecedent;

    public List<EmployeTV> getResultatsCommerciaux() {
        return resultatsCommerciaux;
    }

    public void setResultatsCommerciaux(List<EmployeTV> resultatsCommerciaux) {
        this.resultatsCommerciaux = resultatsCommerciaux;
    }

    public List<EmployeTV> getResultatsProspecteurs() {
        return resultatsProspecteurs;
    }

    public void setResultatsProspecteurs(List<EmployeTV> resultatsProspecteurs) {
        this.resultatsProspecteurs = resultatsProspecteurs;
    }

    public List<EmployeTV> getResultatsCommerciauxPrecedent() {
        return resultatsCommerciauxPrecedent;
    }

    public void setResultatsCommerciauxPrecedent(List<EmployeTV> resultatsCommerciauxPrecedent) {
        this.resultatsCommerciauxPrecedent = resultatsCommerciauxPrecedent;
    }

    public List<EmployeTV> getResultatsProspecteursPrecedent() {
        return resultatsProspecteursPrecedent;
    }

    public void setResultatsProspecteursPrecedent(List<EmployeTV> resultatsProspecteursPrecedent) {
        this.resultatsProspecteursPrecedent = resultatsProspecteursPrecedent;
    }

    public double getCaEnCours() {
        return caEnCours;
    }

    public void setCaEnCours(double caEnCours) {
        this.caEnCours = caEnCours;
    }

    public double getCaPrecedent() {
        return caPrecedent;
    }

    public void setCaPrecedent(double caPrecedent) {
        this.caPrecedent = caPrecedent;
    }
}
