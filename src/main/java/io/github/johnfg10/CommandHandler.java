package io.github.johnfg10;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public abstract class CommandHandler {
    protected final Map<String, CommandBase> commands = new TreeMap<>();
    private final List<adcfUser> users = new ArrayList<>();

    public void registerCommandExecutor(CommandExecutor commandExecutor){
        for (Method method: commandExecutor.getClass().getMethods()){
            Command annotation = method.getAnnotation(Command.class);
            if (annotation == null) {
                continue;
            }else if(annotation.aliases().length >= 0){
                throw new IllegalArgumentException("Aliases array cannot be empty!");
            }
            CommandBase commandBase = new CommandBase(annotation, method, commandExecutor);
            for (String alias:annotation.aliases()) {
                commands.put(alias, commandBase);
            }
        }
    }

    public Map<String, CommandBase> getCommands(){
        return this.getCommands();
    }

    public List<adcfUser> getUsers() {
        return this.getUsers();
    }

    public boolean checkPermissions(adcfUser user, String perm){
        if(this.getUsers().contains(user)){
            for (adcfUser aUser:this.getUsers()) {
                if(aUser.equals(user)){
                    if (aUser.checkPermission(perm)){
                        return true;
                    }
                }
            }
        }else {
            throw new IllegalArgumentException("User does not exist in list");
        }
        return false;
    }
}
