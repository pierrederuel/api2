package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Periode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PeriodeRepository extends CrudRepository<Periode, Long> {
    @Query("SELECT coalesce(max(p.id), 0) FROM Periode p")
    Long getMaxId();
}
