package io.github.johnfg10;

        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;

/**
 * Created by johnfg10 on 23/03/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] aliases();

    String usage();

    String[] requiredPermmisions();

    boolean async() default false;

    boolean showInHelp() default true;

    boolean enabled() default true;

    boolean isTestCommand() default false;

    boolean privateMessage() default true;

    boolean channelMessage() default true;

    boolean channelMention() default true;
}
