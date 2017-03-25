package io.github.johnfg10;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import io.github.johnfg10.Listeners.MessageListener;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnfg10 on 25/03/2017.
 */
public class JavacordHandler extends CommandHandler {
    public JavacordHandler(DiscordAPI discordAPI){
        MessageListener messageListener = new MessageListener();
        messageListener.setCommands(this.commands);
        discordAPI.registerListener(messageListener);
    }

    public static Object[] getParameters(String command, String message, CommandBase commandBase, Message iMessage, DiscordAPI discordAPI) {
        Parameter[] parameters = commandBase.getMethod().getParameters();
        List<Object> objects = new ArrayList<>();

        for (Parameter parameter:parameters) {
            Type aType = parameter.getParameterizedType();
            if (aType == Server.class){
                objects.add(iMessage.getChannelReceiver().getServer());
            }else if (aType == Channel.class){
                objects.add(iMessage.getChannelReceiver());
            }else if (aType == User.class){
                objects.add(iMessage.getAuthor());
            }else if (aType == DiscordAPI.class){
                objects.add(discordAPI);
            }else if (aType == Message.class){
                objects.add(iMessage);
            }else if (aType == String[].class){
                objects.add(new String[]{command, message});
            }else{
                objects.add(null);
                System.out.println("Discord4JHandler.getParameters" + " " + "Unsupported Object detected: " + aType.getTypeName());
            }
        }

        return objects.toArray();
    }
}
