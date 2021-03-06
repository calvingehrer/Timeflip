package at.qe.sepm.skeleton.ui.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * Basic request scoped bean to test bean initialization.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("request")
public class SecurityTestBean {

    private boolean showOkDialog = false;
    private String performedAction = "NONE";

    public boolean isShowOkDialog() {
        return showOkDialog;
    }

    public String getPerformedAction() {
        return performedAction;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void doAdminAction() {
        performedAction = "ADMIN";
        showOkDialog = true;
    }

    @PreAuthorize("hasAuthority('DEPARTMENTLEADER')")
    public void doManagerAction() {
        performedAction = "DEPARTMENTLEADER";
        showOkDialog = true;
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public void doEmployeeAction() {
        performedAction = "EMPLOYEE";
        showOkDialog = true;
    }

    public void doHideOkDialog() {
        showOkDialog = false;
    }

}
