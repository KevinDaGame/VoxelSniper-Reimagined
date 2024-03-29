package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric

import com.github.kevdadev.voxelsniperfabric.voxelsniperfabric.world.FabricWorld
import com.github.kevindagame.snipe.Sniper
import com.github.kevindagame.voxelsniper.entity.IEntity
import com.github.kevindagame.voxelsniper.entity.entitytype.VoxelEntityType
import com.github.kevindagame.voxelsniper.entity.player.IPlayer
import com.github.kevindagame.voxelsniper.location.BaseLocation
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import com.github.kevindagame.voxelsniper.vector.VoxelVector
import com.github.kevindagame.voxelsniper.world.IWorld
import net.kyori.adventure.text.Component
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import java.util.*

class FabricPlayer(private val player: ServerPlayerEntity) : IPlayer {

    private val sniper: Sniper = Sniper(this)

    override fun getType(): VoxelEntityType {
        return VoxelEntityType.getEntityType(player.type.toString())
    }

    override fun remove() {
        throw IllegalCallerException("Cannot remove a player")
    }

    override fun getEntityId(): Int {
        return player.id
    }

    override fun getLocation(): BaseLocation {
        return BaseLocation(
            FabricWorld(player.serverWorld),
            player.x,
            player.y,
            player.z,
            player.yaw,
            player.pitch
        )
    }

    override fun getWorld(): IWorld {
        return FabricWorld(player.serverWorld)
    }

    override fun addPassenger(entity: IEntity?) {
        TODO("Not yet implemented")
    }

    override fun getPassengers(): MutableList<IEntity> {
        TODO("Not yet implemented")
    }

    override fun getEyeLocation(): BaseLocation {
        return BaseLocation(
            FabricWorld(player.serverWorld),
            player.x,
            player.y + player.getEyeHeight(player.pose),
            player.z,
            player.yaw,
            player.pitch
        )
    }

    override fun getNearbyEntities(x: Int, y: Int, z: Int): MutableList<IEntity> {
        TODO("Not yet implemented")
    }

    override fun eject() {
        TODO("Not yet implemented")
    }

    override fun teleport(other: IEntity?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): UUID {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: String?) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(message: Component) {
        TODO("Not yet implemented")
    }

    override fun hasPermission(permissionNode: String?): Boolean {
        return true
        TODO("Add implementation")
    }

    override fun isSneaking(): Boolean {
        return player.isSneaking
    }

    override fun getName(): String {
        return player.name.string
    }

    override fun teleport(location: BaseLocation) {
        val world = location.world as FabricWorld
        player.teleport(world.world, location.x, location.y, location.z, location.yaw, location.pitch)
    }

    override fun launchProjectile(type: VoxelEntityType?, velocity: VoxelVector?): IEntity {
        TODO("Not yet implemented")
    }

    override fun getItemInHand(): VoxelMaterial {
        return VoxelMaterial.getMaterial(player.inventory.mainHandStack.item.toString())
    }

    override fun getSniper(): Sniper {
        return sniper
    }

    companion object {
        fun toNative(component: Component): Text {
            return Text.empty()
            //TODO implement
        }
    }
}