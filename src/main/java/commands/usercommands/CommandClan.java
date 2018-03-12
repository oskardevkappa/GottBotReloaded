package commands.usercommands;

import commands.Command;
import core.MessageHandler;
import core.MySQL;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandClan implements Command {
    public static List<String> List = new ArrayList<>();
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            if (args.length < 1) {
                event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(MessageHandler.get(event.getAuthor()).getString("clantext").replaceAll("gb.", MessageHandler.getprefix(event.getGuild())))
                        .setTitle(MessageHandler.get(event.getAuthor()).getString("clantitel")).build()).queue();
            } else {
                switch (args[0].toLowerCase()) {
                    case "list":
                        int i = 0;
                        String out = MessageHandler.get(event.getAuthor()).getString("clanlisttext");
                        List<String> List = MySQL.getall("clan", "guildid", event.getGuild().getId(), "name");
                        while (List.size() - 1 >= i) {
                            if (List.size() - 1 > i) {
                                out += List.get(i) + ", ";
                            } else out += List.get(i);
                            i++;
                        }
                        event.getTextChannel().sendMessage(new EmbedBuilder().setDescription(out).setTitle(MessageHandler.get(event.getAuthor()).getString("clanlisttitel")).build()).queue();
                        out = "";
                        List.clear();
                        break;

                    case "create":

                        if (MySQL.get("user", "ID", event.getAuthor().getId(), "clan").equals("none")) {
                            if (args.length != 2) {
                                System.out.println("debug 1");
                                System.out.println(args.length);
                            } else {
                                if (Long.parseLong(MySQL.get("user", "id", event.getAuthor().getId(), "miner")) > 1023) {
                                    String name = args[1];
                                    if (MySQL.get("server", "id", event.getGuild().getId(), "clancategory").equals("none")) {
                                        MySQL.update("server", "clancategory", event.getGuild().getController().createCategory("clan").addPermissionOverride(event.getGuild().getPublicRole(), 0, Permission.ALL_VOICE_PERMISSIONS).complete().getId(), "id", event.getGuild().getId());
                                    }
                                    Long TextChannel = event.getGuild().getCategoryById(MySQL.get("server", "id", event.getGuild().getId(), "clancategory"))
                                            .createTextChannel(name)
                                            .addPermissionOverride(event.getGuild().getSelfMember(), 3072, 0)
                                            .addPermissionOverride(event.getMember(), 3072, 0)
                                            .addPermissionOverride(event.getGuild().getPublicRole(), 0, Permission.ALL_VOICE_PERMISSIONS)
                                            .complete().getIdLong();
                                    int id = 0;
                                    List = MySQL.getallwithoutwhere("clan", "id");
                                    while (List.size() >= id) {
                                        id++;
                                        System.out.println(id);
                                    }
                                    System.out.println(id);
                                    MySQL.insert("clan", "name`, `id`, `textchannel`,`guildid", name + "', '" + id + "', '" + TextChannel + "', '" + event.getGuild().getId());
                                    MySQL.update("user", "clan", String.valueOf(TextChannel), "id", event.getAuthor().getId());
                                    event.getTextChannel().sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("clancreatesucesstitel"))
                                            .setDescription(MessageHandler.get(event.getAuthor()).getString("clancreatesucesstext")).build()).queue();
                                    event.getJDA().getTextChannelById(MySQL.get("clan", "name", name, "textchannel")).sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("clanwelcometitel"))
                                            .setDescription(MessageHandler.get(event.getAuthor()).getString("clanwelcometext")).build()).queue();
                                    List.clear();
                                } else {
                                    event.getTextChannel().sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("minertitel"))
                                            .setDescription(MessageHandler.get(event.getAuthor()).getString("minertext")
                                                    .replaceAll("gb.", MessageHandler.getprefix(event.getGuild()))).build()).queue();
                                }
                            }
                        } else {
                            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("alreadyclantitel"))
                                    .setDescription(MessageHandler.get(event.getAuthor()).getString("alreadyclantext").replaceAll("gb.", MessageHandler.getprefix(event.getGuild()))).build()).queue();
                        }

                        break;

                    case "join":
                        if (MySQL.get("user", "ID", event.getAuthor().getId(), "clan").equals("none")) {
                            if (args.length != 2) {
                                event.getTextChannel().sendMessage(
                                        new EmbedBuilder()
                                                .setDescription(MessageHandler.get(event.getAuthor()).getString("clanjointext").replaceAll("gb.", MessageHandler.getprefix(event.getGuild())))
                                                .setTitle(MessageHandler.get(event.getAuthor()).getString("clanjointitel")).build()).queue();
                            } else {


                                if (MySQL.get("clan", "name", args[1], "open").equals("false")) {
                                    MySQL.insert("requests", "name`,`user", MySQL.get("clan", "name", args[1], "textchannel") + "','" + event.getAuthor().getId());
                                    String Channelid = MySQL.get("clan", "name", args[1], "id");
                                    event.getGuild().getTextChannelById(Channelid).sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("clanjoinrequesttitel"))
                                            .setDescription(MessageHandler.get(event.getAuthor()).getString("clanjoinrequesttext").replaceAll("User", event.getAuthor().getAsMention()).replaceAll("gb.", MessageHandler.getprefix(event.getGuild()))).build()).queue();
                                } else if (MySQL.get("clan", "name", args[1], "open").equals("true")) {
                                    String Channelid = MySQL.get("clan", "name", args[1], "id");
                                    MySQL.update("user", "clan", Channelid, "ID", event.getAuthor().getId());
                                    event.getGuild().getTextChannelById(Channelid).sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("clanjoineventtitel"))
                                            .setDescription(MessageHandler.get(event.getAuthor()).getString("clanjoineventtext")).build()).queue();
                                }
                            }
                        } else {
                            event.getTextChannel().sendMessage(new EmbedBuilder().setTitle(MessageHandler.get(event.getAuthor()).getString("alreadyclantitel"))
                                    .setDescription(MessageHandler.get(event.getAuthor()).getString("alreadyclantext").replaceAll("gb.", MessageHandler.getprefix(event.getGuild()))).build()).queue();
                        }
                        break;

                    case "accept":

                        if (!MySQL.get("user", "ID", event.getAuthor().getId(), "clan").equals("none")) {


                        }
                        break;

                    case "requests":


                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
