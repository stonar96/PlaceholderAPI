package me.clip.placeholderapi.commands.command.ecloud;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.commands.Command;
import me.clip.placeholderapi.expansion.cloud.CloudExpansion;
import me.clip.placeholderapi.util.Msg;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class EcloudVersionInfoCommand extends Command {
    public EcloudVersionInfoCommand() {
        super("ecloud versioninfo", options("&cIncorrect usage! &7/papi ecloud versioninfo <name> <version>", 2));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String input = args[0];
        CloudExpansion expansion = PlaceholderAPIPlugin.getInstance().getExpansionCloud().getCloudExpansion(input);
        if (expansion == null) {
            Msg.msg(sender, "&cNo expansion found by the name: &f" + input);
            return;
        }

        CloudExpansion.Version version = expansion.getVersion(args[1]);
        if (version == null) {
            Msg.msg(sender, "&cThe version specified does not exist for expansion: &f" + expansion.getName());
            return;
        }

        Msg.msg(sender, "&bExpansion: " + (expansion.shouldUpdate() ? "&e" : "&f") + expansion.getName(),
                "&bVersion: &f" + version.getVersion(),
                "&bVersion info: &f" + version.getReleaseNotes());

        if (!(sender instanceof Player)) {
            Msg.msg(sender, "&bDownload url: " + version.getUrl());
            return;
        }

        Player p = (Player) sender;
        JSONMessage download = JSONMessage.create(Msg.color("&7Click to download this version"));
        download.suggestCommand(
                "/papi ecloud download " + expansion.getName() + ' ' + version.getVersion());
        download.send(p);
    }
}
