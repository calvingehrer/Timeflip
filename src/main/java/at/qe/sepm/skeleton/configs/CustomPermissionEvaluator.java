package at.qe.sepm.skeleton.configs;


import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("application")

public class CustomPermissionEvaluator {
    /**
     * Specialized method to determine if the current {@link User} has the permission of a certain role.
     * E.g. the administrator even if not given the {@link UserRole}.MANAGER role has permission on all fields
     * a manager also has permission on
     *
     * @param permission to check
     * @return true if the permission is given, false if not
     */
    public boolean hasPermission(String permission) {
        Set<String> permissions;
        try {
            permissions = UserRole.valueOf(permission).getRolesThatHaveAccess();
        } catch (IllegalArgumentException e) {
            return false;
        }
        return !Collections.disjoint(SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()), permissions);
    }

    /**
     * {@link CustomPermissionEvaluator}.hasPermission is called for the current user.
     * <p>
     * This method is the equivalent for a user which is given as a parameter
     *
     * @param permission the permission to check
     * @param user       the user on which the method checks the permission
     * @return true if the permission is granted, false else
     */
    public boolean hasPermission(String permission, User user) {
        Set<String> grantedPermissions = new HashSet<>(), testPermissions;
        try {
            testPermissions = UserRole.valueOf(permission).getRolesThatHaveAccess();
            user.getRoles().forEach(r -> grantedPermissions.addAll(r.getRolesThatHaveAccess()));
        } catch (IllegalArgumentException e) {
            return false;
        }

        return !Collections.disjoint(grantedPermissions, testPermissions);

    }
}