package fr.enelia.dashboardapi.services.impl;

import fr.enelia.dashboardapi.entities.Periode;
import fr.enelia.dashboardapi.repositories.PeriodeRepository;
import fr.enelia.dashboardapi.services.PeriodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service("periodeService")
public class PeriodeServiceImpl implements PeriodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodeServiceImpl.class);

    @Autowired
    private PeriodeRepository periodeRepository;

    @Override
    public Periode createPeriode(Periode periode) {
        LOGGER.info("createPeriode");
        periode = periodeRepository.save(periode);
        return periode;
    }

    @Override
    public Periode generatePeriode() {
        LOGGER.info("generatePeriode");

        Periode periode = new Periode();
        periode.setDateDebut(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        periode.setDateFin(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

        return createPeriode(periode);
    }

    @Override
    public Periode updatePeriode(Periode periode) {
        LOGGER.info("updatePeriode");
        periode = periodeRepository.save(periode);
        return periode;
    }

    @Override
    public Periode getPeriodeById(Long id) {
        LOGGER.info("getPeriodeById");
        return periodeRepository.findOne(id);
    }

    @Override
    public Periode getLatestPeriode() {
        LOGGER.info("getLatestPeriode");
        return periodeRepository.findOne(periodeRepository.getMaxId());
    }

    @Override
    public Periode getPeriodeBeforeLast() {
        LOGGER.info("getPeriodeBeforeLast");
        Periode result = null;
        if (periodeRepository.getMaxId() > 1) {
            result = periodeRepository.findOne(periodeRepository.getMaxId()-1);
        } else {
            result = periodeRepository.findOne(periodeRepository.getMaxId());
        }
        return result;
    }

    @Override
    public Iterable<Periode> getPeriodes() {
        LOGGER.info("getPeriodes");
        return periodeRepository.findAll();
    }
}
