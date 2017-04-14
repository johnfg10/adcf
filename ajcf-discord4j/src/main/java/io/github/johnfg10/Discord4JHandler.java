package io.github.johnfg10;

import io.github.johnfg10.Listeners.MessageListener;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Created by johnfg10 on 24/03/2017.
 */
public class Discord4JHandler extends CommandHandler {

    private static Discord4JHandler selfReference;

    public Discord4JHandler(IDiscordClient discordClient) {
        selfReference = this;
        MessageListener messageListener = new MessageListener();
        discordClient.getDispatcher().registerListener(messageListener);
    }

    public Object[] getParameters(String command, String message,String[] splitArgs, CommandBase commandBase, IMessage iMessage) {
        Parameter[] parameters = commandBase.getMethod().getParameters();
        List<Object> objects = new ArrayList<>();

        for (Parameter parameter:parameters) {
            Type aType = parameter.getParameterizedType();
            if (aType == IGuild.class){
                objects.add(iMessage.getGuild());
            }else if (aType == IChannel.class){
                objects.add(iMessage.getChannel());
            }else if (aType == IUser.class){
                objects.add(iMessage.getAuthor());
            }else if (aType == IDiscordClient.class){
                objects.add(iMessage.getClient());
            }else if (aType == IMessage.class){
                objects.add(iMessage);
            }else if (aType == String[].class){
                objects.add(new String[]{command, message});
            }else if(aType == CommandBase.class){
                objects.add(commandBase);
            }else if(aType == TreeMap.class){
                objects.add(selfReference.getCommandBaseAliases());
            }else {
                objects.add(null);
                System.out.println("Discord4JHandler.getParameters" + " " + "Unsupported Object detected: " + aType.getTypeName());
            }
        }

        return objects.toArray();
    }


    @Override
    public void registerCommandExecutor(CommandExecutor commandExecutor){
        super.registerCommandExecutor(commandExecutor);
    }

    public static Discord4JHandler getSelfReference(){
        return selfReference;
    }
}

