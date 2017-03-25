package io.github.johnfg10;

import java.lang.reflect.Method;

/**
 * Created by johnfg10 on 23/03/2017.
 */
public class CommandBase {
    private final Command annotation;
    private final Method method;
    private final CommandExecutor executor;

    /**
     * Class constructor.
     *
     * @param annotation The annotation of the executor's method.
     * @param method The method which listens to the commands.
     * @param executor The executor of the method.
     */
    protected CommandBase(Command annotation, Method method, CommandExecutor executor) {
        this.annotation = annotation;
        this.method = method;
        this.executor = executor;
    }

    /**
     * The command annotation of the method.
     *
     * @return The command annotation of the method.
     */
    public Command getCommandAnnotation() {
        return annotation;
    }

    /**
     * Gets the method which listens to the commands.
     *
     * @return The method which listens to the commands.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the executor of the method.
     *
     * @return The executor of the method.
     */
    public CommandExecutor getExecutor() {
        return executor;
    }
}
