package io.github.johnfg10;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public abstract class CommandHandler {
    protected TreeMap<String, CommandBase> commandBaseAliases = new TreeMap<>();


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
                commandBaseAliases.put(alias, commandBase);
                System.out.println(alias);
            }
        }
    }

    public TreeMap<String, CommandBase> getCommandBaseAliases() {
        return commandBaseAliases;
    }


}
