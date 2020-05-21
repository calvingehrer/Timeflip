package at.qe.sepm.skeleton.utils;


import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MessagesView {
    public static void warnMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
    }

    public static void errorMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
    }

    public static void successMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", message));
    }

    private static void addMessage(String target, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(target, message);
    }
}
