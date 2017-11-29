package fr.enelia.dashboardapi.services;

import fr.enelia.dashboardapi.entities.Periode;
import org.springframework.stereotype.Service;

@Service
public interface PeriodeService {
    public Periode createPeriode(Periode periode);
    public Periode generatePeriode();
    public Periode updatePeriode(Periode periode);
    public Periode getPeriodeById(Long id);
    public Periode getLatestPeriode();
    public Periode getPeriodeBeforeLast();
    public Iterable<Periode> getPeriodes();
}
