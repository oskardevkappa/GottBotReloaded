package commands.moderation;

import commands.Command;
import commands.botowner.Owner;
import core.MessageHandler;
import core.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandPrefix implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getAuthor().getId()==event.getGuild().getOwner().getUser().getId() || event.getMember().hasPermission(Permission.MANAGE_SERVER) || Owner.get(event.getAuthor())) {
            if (args.length < 1) {
                try {
                    event.getTextChannel().sendMessage(MessageHandler.getEmbed("moderation.prefix.title", "moderation.prefix.text", "", "normal", event)).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    MySQL.update("server", "Prefix", args[0], "ID", event.getGuild().getId());
                    event.getTextChannel().sendMessage(MessageHandler.getEmbed("util.sucess", "moderation.prefix.changed", "", "sucess", event)).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            event.getTextChannel().sendMessage(MessageHandler.getEmbed("util.error","util.nopermissionuser", "", "error", event)).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }
}
