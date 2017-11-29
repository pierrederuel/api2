package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VenteRepository extends CrudRepository<Vente, Long> {

    Iterable<Vente> findVentesByProspecteur(Prospecteur prospecteur);
    //@Query("FROM Vente v LEFT JOIN v.commisions c WHERE c.commercial = :commercial")
    //@Query("FROM Vente v WHERE v.commisions  in elements(:commissions)")
    @Query("FROM Vente v JOIN v.commisions c WHERE c.commercial = :commercial")
    Iterable<Vente> findVentesByCommercial(@Param("commercial")Commercial commercial);
    Vente findVenteByCommisions(Iterable<Commission> commissions);
}
