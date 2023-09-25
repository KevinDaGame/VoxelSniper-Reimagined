package com.github.kevdadev.voxelsniperfabric.voxelsniperfabric

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
import java.util.*

class FabricPlayer(private val player: ServerPlayerEntity) : IPlayer {

    private val sniper: Sniper = Sniper(this)

    override fun getType(): VoxelEntityType {
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun getEntityId(): Int {
        TODO("Not yet implemented")
    }

    override fun getLocation(): BaseLocation {
        TODO("Not yet implemented")
    }

    override fun getWorld(): IWorld {
        TODO("Not yet implemented")
    }

    override fun addPassenger(entity: IEntity?) {
        TODO("Not yet implemented")
    }

    override fun getPassengers(): MutableList<IEntity> {
        TODO("Not yet implemented")
    }

    override fun getEyeLocation(): BaseLocation {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun isSneaking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun teleport(location: BaseLocation?) {
        TODO("Not yet implemented")
    }

    override fun launchProjectile(type: VoxelEntityType?, velocity: VoxelVector?): IEntity {
        TODO("Not yet implemented")
    }

    override fun getItemInHand(): VoxelMaterial {
        TODO("Not yet implemented")
    }

    override fun getSniper(): Sniper {
        return sniper
    }
}