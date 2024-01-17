package net.joshb.deathmessages.listener;

import me.nahu.scheduler.wrapper.runnable.WrappedRunnable;
import net.joshb.deathmessages.DeathMessages;
import net.joshb.deathmessages.api.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        new WrappedRunnable(){
            @Override
            public void run() {
                if(PlayerManager.getPlayer(p) == null) new PlayerManager(p);
            }
        }.runTaskAsynchronously(DeathMessages.plugin);

        if (!DeathMessages.bungeeInit) return;
        new WrappedRunnable(){
            @Override
            public void run() {
                if(DeathMessages.bungeeServerNameRequest){
                    PluginMessaging.sendServerNameRequest(p);
                }
            }
        }.runTaskLater(DeathMessages.plugin, 5);
    }
}