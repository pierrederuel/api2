package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Agence;

public interface AgenceService {

    public Agence createAgence(Agence agence);
    public Agence updateAgence(Agence agence);
    public Agence getAgenceById(Long id);
    public Iterable<Agence> getAgences();
}
