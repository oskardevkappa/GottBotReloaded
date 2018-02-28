package listener;

import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stuff.SECRETS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static stuff.DATA.url;

public class Guildjoin extends ListenerAdapter {
    private static Logger logger = LoggerFactory.getLogger(commandListener.class);
    public void onGuildJoin(GuildJoinEvent event) {
        try {
            Connection con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", SECRETS.user, SECRETS.password);
            PreparedStatement pst = con.prepareStatement("Select * FROM `server` WHERE ID=" + event.getGuild().getId());
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", SECRETS.user, SECRETS.password);
                pst = con.prepareStatement("INSERT INTO `server` (`ID`) VALUES ('"+event.getGuild().getId()+"');");
                pst.execute();
                rs.close();
                logger.info("neuer Server: Name: "+event.getGuild().getName()+" ID: "+event.getGuild().getId()+" Member: "+event.getGuild().getMembers().size());

            }
            rs.close();





        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
