package io.github.johnfg10;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public class CommandBase {
    private final Command annotation;
    private final Method method;
    private final CommandExecutor executor;
    private List<String> aliases = new ArrayList<>();
    private List<String> permissionsRequired = new ArrayList<>();

    public CommandBase(Command annotation, Method method, CommandExecutor executor) {
        this.annotation = annotation;
        this.method = method;
        this.executor = executor;
    }

    public Command getCommandAnnotation() {
        return annotation;
    }

    public Method getMethod() {
        return method;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }
}
