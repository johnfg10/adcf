package io.github.johnfg10;

import java.util.List;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public class adcfUser {
    private String uuid;

    private String username;

    private List<String> permissions;

    public void addPermission(String permission){
        permissions.add(permission);
    }

    public boolean checkPermission(String permission){
        return permissions.contains(permission);
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        int intNumber;
        intNumber = getUuid().hashCode() + getUsername().hashCode() + getPermissions().hashCode();
        return intNumber;
    }
}
