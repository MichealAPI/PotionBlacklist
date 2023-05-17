package it.mikeslab.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import it.mikeslab.Perms;
import it.mikeslab.util.BlacklistUtil;
import it.mikeslab.util.language.LangKey;
import it.mikeslab.util.language.Language;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@CommandAlias("potion")
public class PotionCommand extends BaseCommand {


    @Subcommand("blacklist")
    @Syntax("<subject>")
    @Description("Blacklist a player from using potions")
    @CommandPermission(Perms.BLACKLIST_COMMAND)
    public void onBlacklist(Player sender, OnlinePlayer subject) {
        UUID uuid = subject.getPlayer().getUniqueId();
        boolean result = BlacklistUtil.blacklist(uuid);

        if(!result) {
            sender.sendMessage(Language.getString(LangKey.ALREADY_BLACKLISTED,
                    Map.of("{player}", subject.getPlayer().getName())));
            return;
        }

        sender.sendMessage(Language.getString(LangKey.SUCCESSFULLY_BLACKLISTED,
                Map.of("{player}", subject.getPlayer().getName())));

        subject.getPlayer().sendMessage(Language.getString(LangKey.BEEN_BLACKLISTED));
    }




    @Subcommand("unblacklist")
    @Syntax("<subject>")
    @Description("Un-blacklist a player from using potions")
    @CommandPermission(Perms.UNBLACKLIST_COMMAND)
    public void onUnBlacklist(Player sender, OnlinePlayer subject) {
        UUID uuid = subject.getPlayer().getUniqueId();
        boolean result = BlacklistUtil.unBlacklist(uuid);

        if (!result) {
            sender.sendMessage(Language.getString(LangKey.NOT_BLACKLISTED,
                    Map.of("{player}", subject.getPlayer().getName())));
            return;
        }

        sender.sendMessage(Language.getString(LangKey.SUCCESSFULLY_UNBLACKLISTED,
                Map.of("{player}", subject.getPlayer().getName())));

        subject.getPlayer().sendMessage(Language.getString(LangKey.BEEN_UNBLACKLISTED));
    }






}
