package io.github.johnfg10;

import sx.blah.discord.handle.obj.IUser;

/**
 * Created by johnfg10 on 24/03/2017.
 */
public class ObjectMapper {
    public static adcfUser fromIUser(IUser user){
        adcfUser adcfUser1 = new adcfUser();
        adcfUser1.setUsername(user.getName());
        adcfUser1.setUuid(user.getID());
        return adcfUser1;
    }
}
