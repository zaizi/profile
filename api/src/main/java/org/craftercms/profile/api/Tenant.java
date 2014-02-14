package org.craftercms.profile.api;

import java.util.ArrayList;
import java.util.List;

/**
 * A tenant is normally an application that shares common configuration.
 *
 * @author avasquez
 */
public class Tenant {

    private String name;
    private boolean verifyNewAccounts;
    private List<String> roles;
    private List<Group> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVerifyNewAccounts() {
        return verifyNewAccounts;
    }

    public void setVerifyNewAccounts(boolean verifyNewAccounts) {
        this.verifyNewAccounts = verifyNewAccounts;
    }

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<String>();
        }

        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Group> getGroups() {
        if (groups == null) {
            return new ArrayList<Group>();
        }

        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}