package at.qe.sepm.skeleton.utils.auditlog;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;

public class LogoutListener implements ApplicationListener<LogoutSuccessEvent> {

    @Autowired
    SessionInfoBean sessionInfoBean;
    @Autowired
    private Logger<String, User> logger;

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        logger.logLogout(event.getAuthentication().toString(), sessionInfoBean.getCurrentUser());
    }
}
