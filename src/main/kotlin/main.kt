import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.entity.EntityExplodeEvent

class WaterTNT():JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.registerEvents(TntExplosionListener(), this)
    }
}

class TntExplosionListener:Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.isCancelled) {
            return
        }

        val entity = event.entity
        if (entity.type != EntityType.PRIMED_TNT) {
            return
        }
        val location = entity.location
        val world = entity.world
        val block = world.getBlockAt(location)

        if(block.type != Material.WATER || block.type != Material.LAVA) {
            return
        }
        event.isCancelled = true
        block.type = Material.AIR

        val newTNT = world.spawnEntity(location,EntityType.PRIMED_TNT) as TNTPrimed
        newTNT.fuseTicks = 1
        newTNT.setGravity(false)
    }
}