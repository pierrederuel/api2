package fr.enelia.dashboardapi;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.http.client.IFcmClient;
import fr.enelia.dashboardapi.config.FCMSettings;
import fr.enelia.dashboardapi.entities.EneliaUserDetails;
import fr.enelia.dashboardapi.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
public class DashboardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApiApplication.class, args);
	}

	@Bean
	public IFcmClient fcmClient(FCMSettings settings) {
		return new FcmClient(settings);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UtilisateurService utilisateurService) throws Exception {
		builder.userDetailsService(new UserDetailsService() {
            private final Logger LOGGER = LoggerFactory.getLogger(DashboardApiApplication.class);
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return new EneliaUserDetails(utilisateurService.getUtilisateurByUsernameAndActive(username.toLowerCase()));
			}
		});
	}

}
