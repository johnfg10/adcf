package io.github.johnfg10.Listeners;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import io.github.johnfg10.CommandBase;
import io.github.johnfg10.JavacordHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnfg10 on 25/03/2017.
 */
public class MessageListener implements MessageCreateListener {

    List<CommandBase> commands = null;

    @Override
    public void onMessageCreate(DiscordAPI discordAPI, Message message) {

        String messageRaw = message.getContent();

        List<Object> reply = new ArrayList<>();
        for (CommandBase commandBase:commands) {
            for (String alias:commandBase.getAliases()) {
                if (messageRaw.startsWith(alias)){
                    String msg = messageRaw.replaceFirst(alias, "");
                    try {
                        reply.add(commandBase.getMethod().invoke(commandBase.getExecutor(),
                                JavacordHandler.getParameters(
                                        alias,
                                        msg,
                                        commandBase,
                                        message,
                                        discordAPI
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
                    message.getChannelReceiver().sendMessage(obj.toString());
                }
            }
        }
    }


    public void setCommands(List<CommandBase> commands) {
        this.commands = commands;
    }
}
