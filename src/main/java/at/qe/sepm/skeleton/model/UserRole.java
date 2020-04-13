package at.qe.sepm.skeleton.model;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enumeration of available user roles.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
public enum UserRole {

    ADMIN("ADMIN"),
    DEPARTMENTLEADER("DEPARTMENTLEADER", "ADMIN"),
    TEAMLEADER("ADMIN", "TEAMLEADER"),
    EMPLOYEE("EMPLOYEE", "ADMIN");

    private final Set<String> accessibleBy;

    UserRole(String... accessibleBy) {
        this.accessibleBy = new HashSet<>(Arrays.asList(accessibleBy));
    }

    public static List<String> getAllRoles() {
        return Arrays.stream(UserRole.values()).map(Enum::name).collect(Collectors.toList());
    }

    /**
     * Returns a set of all roles that have access on a certain role
     *
     * @return the set of all roles that have access on operations for a given role
     */
    public Set<String> getRolesThatHaveAccess() {
        return accessibleBy;
    }

    /**
     * {@link #getRolesThatHaveAccess()}
     *
     * @return returns the result of link as string array
     */
    public String[] getRolesThatHaveAccessAsArray() {
        return StringUtils.toStringArray(accessibleBy);
    }

}
