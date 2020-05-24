package at.qe.sepm.skeleton.utils.auditlog;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

public class LoginAttemtsLogger {
    @Autowired
    CurrentUserBean currentUserBean;
    @Autowired
    private Logger<String, User> logger;

    /**
     * A Function to get the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    @EventListener
    public void auditEventHappened(
            AuditApplicationEvent auditApplicationEvent) {

        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
        logger.logLogin(auditEvent.getType(), currentUserBean.getCurrentUser());

    }
}
