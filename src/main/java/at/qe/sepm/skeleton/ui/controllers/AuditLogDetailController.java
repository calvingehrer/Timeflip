package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("view")
public class AuditLogDetailController implements Serializable {

    @Autowired
    private AuditLogService auditLogService;

}
