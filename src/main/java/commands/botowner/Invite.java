
package commands.botowner;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import commands.Command;

import java.awt.*;

public class Invite implements Command {




    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (Handler.get(event.getAuthor())) {
            String HALLO = event.getJDA().getGuildById(args[0]).getTextChannels().get(0).createInvite().complete().getURL();

            event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Invite " + HALLO)
                    .setTitle(":information_source: Invite").setColor(Color.MAGENTA).build()).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }

    @Override
    public String help() {
        return null;
    }
}