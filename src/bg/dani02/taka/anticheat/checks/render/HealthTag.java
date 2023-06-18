package bg.dani02.taka.anticheat.checks.render;

import java.util.List;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;

import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import bg.dani02.taka.anticheat.ConfigsMessages;
import bg.dani02.taka.anticheat.Utils;
import bg.dani02.taka.anticheat.enums.HackType;

public class HealthTag {
	@SuppressWarnings("deprecation")
	public static void onCheck(com.comphenix.protocol.events.PacketEvent event) {
		if(!Utils.checkModule(Utils.getCheatPlayer(event.getPlayer().getUniqueId()), HackType.RENDER_HEALTHTAG))
			return;
		Player observer = event.getPlayer();
		StructureModifier<Entity> entityModifer = event.getPacket().getEntityModifier(observer.getWorld());

		try {
			Entity entity = entityModifer.read(0);

			if (entity != null && entity instanceof LivingEntity
					&& !(entity instanceof EnderDragon) && !(entity instanceof Wither)
					&& ((entity.getPassenger() == null) || (entity.getPassenger() != observer))
					&& (!ConfigsMessages.healthtag_player_only | entity instanceof Player)) {
				StructureModifier<List<WrappedWatchableObject>> watcher = event.getPacket()
						.getWatchableCollectionModifier();
				
				int index = 6;
				if (Utils.getServerVersion() >= 7) {
					index = 8;
				} else if (Utils.getServerVersion() >= 2) {
					index = 7;
				}

				for (WrappedWatchableObject watch : watcher.read(0)) {
					if (watch.getIndex() == index && (float) watch.getValue() >= 0.5F) {
						if(!observer.equals(entity))
							watch.setValue(0.5F);
						else watch.setValue((float) ((LivingEntity)entity).getHealth());
					}
				}
			}
		} catch (RuntimeException e) {
			// ProtocolLib version is incompatible with server version
			if (!(e instanceof IllegalArgumentException)) {
				e.printStackTrace();
			}
		}
	}
}