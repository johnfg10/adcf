package io.github.johnfg10.Listeners;

import io.github.johnfg10.CommandBase;
import io.github.johnfg10.CommandHandler;
import io.github.johnfg10.Discord4JHandler;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnfg10 on 24/03/2017.
 */
public class MessageListener implements IListener<MessageReceivedEvent> {
    List<CommandBase> commands = null;

    @Override
    public void handle(MessageReceivedEvent event) {

        String messageRaw = event.getMessage().getContent();

        List<Object> reply = new ArrayList<>();
        for (CommandBase commandBase:commands) {
            for (String alias:commandBase.getAliases()) {
                if (messageRaw.startsWith(alias)){
                    String msg = messageRaw.replaceFirst(alias, "");
                    String[] args = msg.split(" ");
                    try {
                        reply.add(commandBase.getMethod().invoke(commandBase.getExecutor(),
                                Discord4JHandler.getParameters(
                                        alias,
                                        msg,
                                        args,
                                        commandBase,
                                        event.getMessage()
                                )));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (reply != null){
            for (Object obj:reply) {
                if (obj != null){
                    try {
                        event.getMessage().getChannel().sendMessage(obj.toString());
                    } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setCommands(List<CommandBase> commands) {
        this.commands = commands;
    }
}
