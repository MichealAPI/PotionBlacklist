package it.mikeslab.event;

import it.mikeslab.util.BlacklistUtil;
import it.mikeslab.util.language.LangKey;
import it.mikeslab.util.language.Language;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == null) return;

        if(event.getItem().getType() == Material.POTION) {

            if(BlacklistUtil.isBlacklisted(player.getUniqueId())) {
                player.sendMessage(Language.getString(LangKey.YOU_ARE_BLACKLISTED));
                event.setCancelled(true);
            }

        }
    }



}
