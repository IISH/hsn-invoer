package org.iish.hsn.invoer.util;

import org.iish.hsn.invoer.domain.invoer.Invoer;
import org.iish.hsn.invoer.domain.invoer.security.User;
import org.iish.hsn.invoer.domain.invoer.security.UserWorkOrder;
import org.iish.hsn.invoer.repository.invoer.security.UserRepository;
import org.iish.hsn.invoer.repository.invoer.security.UserWorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InputMetadataChecker {
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Value("${application.version}")
    private String version;

    @Autowired private Environment environment;
    @Autowired private UserRepository userRepository;
    @Autowired private UserWorkOrderRepository userWorkOrderRepository;

    public boolean isValid(InputMetadata inputMetadata) {
        boolean valid = ((inputMetadata.getOndrzk() != null) && (inputMetadata.getOndrzk().length() == 3)
                && (inputMetadata.getOpdrnr() != null) && (inputMetadata.getOpdrnr().length() == 3));

        if (valid && !environment.acceptsProfiles(Profiles.of("noCheck"))
                && environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            UserWorkOrder userWorkOrder =
                    userWorkOrderRepository.findByTripleAndWorkOrder(user.getTriple(), inputMetadata.getWorkOrder());
            valid = (userWorkOrder != null);
        }
        else if (valid && !environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            valid = ((inputMetadata.getInit() != null) && (inputMetadata.getInit().length() == 3));
        }

        return valid;
    }

    public boolean isAdmin() {
        if (environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            return user.getType().equalsIgnoreCase("ADMIN");
        }

        return false;
    }

    public void saveToEntity(Invoer invoer, InputMetadata inputMetadata) {
        invoer.setOpdrnr(inputMetadata.getOpdrnr());
        invoer.setInit(getInit(inputMetadata));
        invoer.setVersie(version);
        invoer.setDatum(DATE_TIME_FORMAT.format(new Date()));

        // Use the field 'opdrnro' to check if the original values should be set as well
        if ((invoer.getOpdrnro() == null) || invoer.getOpdrnro().trim().isEmpty()) {
            invoer.setWorkOrder(inputMetadata.getWorkOrder());
            invoer.setOpdrnro(inputMetadata.getOpdrnr());
            invoer.setInito(getInit(inputMetadata));
            invoer.setVersieo(version);
            invoer.setDatumo(DATE_TIME_FORMAT.format(new Date()));
        }
    }

    private String getInit(InputMetadata inputMetadata) {
        if (environment.acceptsProfiles(Profiles.of("ldapAuth", "dbAuth"))) {
            User user = getLoggedInUser();
            return user.getTriple();
        }
        return inputMetadata.getInit();
    }

    private User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByInlognaam(authentication.getName());
    }
}
