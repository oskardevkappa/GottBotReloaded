package commands.usercommands;

import commands.Command;
import core.MessageHandler;
import core.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandLevelMessage implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length<1) {
            event.getTextChannel().sendMessage(MessageHandler.getEmbed("usercommands.levelmessage.title", "usercommands.levelmessage.description","","normal", event)).queue();
        } else {
            if (args[0].equalsIgnoreCase("true")) {
                MySQL.update("user", "lvlmessage", "true", "id", event.getAuthor().getId());
                event.getTextChannel().sendMessage(MessageHandler.getEmbed("util.sucess","usercomamnds.levelmessage.true","", "sucess", event)).queue();
            } else if (args[0].equalsIgnoreCase("false")) {
                MySQL.update("user", "lvlmessage", "false", "id", event.getAuthor().getId());
                event.getTextChannel().sendMessage(MessageHandler.getEmbed("util.sucess", "usercomamnds.levelmessage.false","", "sucess", event)).queue();
            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    }
}
