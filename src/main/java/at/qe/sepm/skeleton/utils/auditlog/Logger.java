package at.qe.sepm.skeleton.utils.auditlog;


/**
 * Handles Logging for managing objects, like updating, deleting, etc.
 *
 * @param <T> The type which is used to identify the managed object when evaluating
 *            E.g. String, the name of the object
 *            Use a meaningful name, as an auto generated ID cannot be traced when the connected
 *            object was deleted
 * @param <C> The changing object, which should be included in the log
 */
public interface Logger<T, C> {

    /**
     * Logs a creation of an object with
     *
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     */
    void logCreation(T objectIdentifier, C changer);

    /**
     * Logs an update of an object with
     *
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     */
    void logUpdate(T objectIdentifier, C changer);

    /**
     * Logs the deletion of the object with
     *
     * @param objectIdentifier the name of the object
     * @param changer          the changer of the object
     */
    void logDeletion(T objectIdentifier, C changer);


    /**
     * Logs an error
     *
     * @param e        exception that was thrown
     * @param executor the executor of the operation which threw an error
     */
    void logError(Exception e, C executor);

    /**
     * Logs Login of User
     *
     * @param objectIdentifier the name of the object
     */
    void logLogin(T objectIdentifier);

    /**
     * Logs Logout of User
     *
     * @param objectIdentifier the name of the object
     *
     */
    void logLogout(T objectIdentifier);

}
