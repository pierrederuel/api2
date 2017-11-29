package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Commercial;
import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.entities.Prospecteur;
import fr.enelia.dashboardapi.entities.Resultat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ResultatRepository extends CrudRepository<Resultat, Long> {

    Resultat findResultatByCommercialAndPeriode(Commercial commercial, Periode periode);
    Resultat findResultatByProspecteurAndPeriode(Prospecteur prospecteur, Periode periode);
    @Query("FROM Resultat r WHERE r.commercial != NULL AND r.periode.id = :idPeriode ORDER BY r.montantVendu ASC")
    Iterable<Resultat> findResultatCommerciauxByPeriodeId(@Param("idPeriode") Long idPeriode);
    @Query("FROM Resultat r  WHERE r.prospecteur != NULL AND r.periode.id = :idPeriode ORDER BY r.montantVendu ASC")
    Iterable<Resultat> findResultatProspecteursByPeriodeId(@Param("idPeriode") Long idPeriode);
}
