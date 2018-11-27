package me.dutchjelly.greenwalk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class GreenWalk extends JavaPlugin implements Listener{
	
	private Map<UUID, Boolean> enableData = new HashMap<UUID, Boolean>();
	private Logger log;
	
	@Override
	public void onEnable(){
		log = getLogger();
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("togglegreen").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
		if(sender instanceof Player){
			Player player = (Player)sender;
			UUID id = player.getUniqueId();
			if(enableData.containsKey(id)){
				enableData.replace(id, !enableData.get(id));
			} else{
				enableData.put(id, true);
			}
		} else{
			log.warning("That's a command for players only!");
		}
		return true;
	}
	
	@EventHandler
	public void onWalk(PlayerMoveEvent e){
		Player player = e.getPlayer();
		UUID id = player.getUniqueId();
		if(enableData.containsKey(id) && enableData.get(id)){
			Location blockLocation = player.getLocation().clone().add(new Vector(0,-1,0));
			Material blockType = blockLocation.getBlock().getType();
			if(blockType != Material.AIR && blockType != Material.WATER){
				blockLocation.getBlock().setType(Material.EMERALD_BLOCK);
			}
		}
	}
}
