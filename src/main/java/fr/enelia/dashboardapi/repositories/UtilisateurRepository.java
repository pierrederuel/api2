package fr.enelia.dashboardapi.repositories;

import fr.enelia.dashboardapi.entities.Employe;
import fr.enelia.dashboardapi.entities.Utilisateur;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByUsername(String username);
    @Query(value = "SELECT * FROM Utilisateur u WHERE u.active = 1", nativeQuery = true)
    Iterable<Utilisateur> findUtilisateursActifs();
    //@Query(value = "SELECT * FROM Utilisateur u WHERE u.active = 1", nativeQuery = true)
    Optional<Utilisateur> findByUsernameAndActive(String username, boolean active);
    Utilisateur findByEmploye(Employe employe);

    @Modifying
    @Transactional
    @Query("update Utilisateur u set u.active = false where u.id = ?1")
    void revokeUtilisateur(Long id);
}
