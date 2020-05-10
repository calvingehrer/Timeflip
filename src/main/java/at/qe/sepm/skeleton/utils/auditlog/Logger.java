package at.qe.sepm.skeleton.utils.auditlog;

import at.qe.sepm.skeleton.model.User;

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

    void logCreation(String objectIdentifier, User changer);

    /**
     * Logs an error
     *
     * @param the     exception that was thrown
     * @param changer the executor of the operation which threw an error
     */
    void logError(Exception e, C executor);

    void logUpdate(String objectIdentifier, User changer);

    void logDeletion(String objectIdentifier, User changer);

    void logError(Exception e, User executor);
}
