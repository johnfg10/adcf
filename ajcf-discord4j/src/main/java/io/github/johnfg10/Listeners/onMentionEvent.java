package io.github.johnfg10.Listeners;

import io.github.johnfg10.CommandBase;
import io.github.johnfg10.CommandHandler;
import io.github.johnfg10.Discord4JHandler;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by johnfg10 on 23/04/2017.
 */
public class onMentionEvent implements IListener<MentionEvent> {
    @Override
    public void handle(MentionEvent event) {
        String messageRaw = event.getMessage().getContent();
        List<Object> reply = new ArrayList<>();

        CommandHandler cmdHandler = Discord4JHandler.getSelfReference();
        TreeMap<String, CommandBase> commands = cmdHandler.getCommandBaseAliases();

        Set<String> cmds = commands.keySet();
        for (String str:cmds) {
            if(messageRaw.startsWith(str)){
                String msg = messageRaw.replaceFirst(str, "");
                String[] args = msg.split(" ");
                CommandBase command = commands.get(str);
                if (command.getCommandAnnotation().channelMention())
                {
                    try {
                        reply.add(command.getMethod().invoke(command.getExecutor(),
                                Discord4JHandler.getSelfReference().getParameters(
                                        str,
                                        msg,
                                        args,
                                        command,
                                        event.getMessage()
                                )));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        if (!reply.isEmpty()){
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
}
