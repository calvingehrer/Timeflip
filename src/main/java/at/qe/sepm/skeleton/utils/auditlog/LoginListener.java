package at.qe.sepm.skeleton.utils.auditlog;

import at.qe.sepm.skeleton.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * This class listen for an Login to occur. When someone logs in it is saved in the db
 */

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {


    @Autowired
    private Logger<String, User> logger;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        logger.logLogin(event.getAuthentication().getName());
    }
}
