package GB.commands.botowner;

import GB.Handler;
import GB.MessageHandler;
import commands.Command;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandDM implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (commands.botowner.Owner.get(event.getAuthor())) {
            Member User = event.getMessage().getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
            String Content = event.getMessage().getContentStripped().replaceFirst(MessageHandler.getprefix(event.getGuild()), "").replaceFirst("dm", "").replaceFirst(String.valueOf(User), "");

            PrivateChannel channel = User.getUser().openPrivateChannel().complete();
            channel.sendMessage(Content).queue();
            event.getTextChannel().sendMessage(new Handler().getMessageHandler().getEmbed("botowner.dm.text", "botowner.dm.title", "", "normal", event)).queue();
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
