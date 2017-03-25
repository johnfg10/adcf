package io.github.johnfg10;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public abstract class CommandHandler {
    protected final List<CommandBase> commands = new ArrayList<>();
    private final List<adcfUser> users = new ArrayList<>();

    public void registerCommandExecutor(CommandExecutor commandExecutor){
        List<Method> methods = Arrays.asList(commandExecutor.getClass().getMethods());

        for (Method method: methods){
            Command annotation = method.getAnnotation(Command.class);
            if (annotation == null) {
                continue;
            }
            if(annotation.aliases().length <= 0){
                throw new IllegalArgumentException("Aliases array cannot be empty!");
            }

            CommandBase commandBase = new CommandBase(annotation, method, commandExecutor);
            for (String alias:annotation.aliases()) {
                commandBase.addAlias(alias);
            }
            commands.add(commandBase);
        }
    }

    public boolean checkPermissions(adcfUser user, String perm){
        if(this.users.contains(user)){
            for (adcfUser aUser:this.users) {
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
