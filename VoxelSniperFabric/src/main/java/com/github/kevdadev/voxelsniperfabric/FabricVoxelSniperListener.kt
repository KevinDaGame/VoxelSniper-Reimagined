package com.github.kevdadev.voxelsniperfabric

import com.github.kevindagame.VoxelSniper
import com.github.kevindagame.snipe.Sniper
import com.github.kevindagame.voxelsniper.material.VoxelMaterial
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import java.util.*

class FabricVoxelSniperListener : UseItemCallback {
    private val cooldown: MutableList<UUID> = mutableListOf()
    override fun interact(player: PlayerEntity, world: World, hand: Hand): TypedActionResult<ItemStack> {
        if (hand != Hand.MAIN_HAND) return TypedActionResult.fail(player.getStackInHand(hand))
        val voxelPlayer = VoxelSniper.voxelsniper.getPlayer(player.uuid)!!
        //check permission

        if (cooldown.contains(player.uuid)) return TypedActionResult.fail(player.getStackInHand(hand))

        try {
            val sniper: Sniper = voxelPlayer.getSniper()
            val action: Sniper.Action = getAction()
            if (sniper.isEnabled && sniper.snipe(
                    action,
                    getItemStack(player, hand),
                    null,//TODO
                    null, //TODO
                )
            ) {
//                event.setCanceled(true) TODO
                cooldown.add(player.uuid)
                Thread {
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    cooldown.remove(player.uuid)
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return TypedActionResult.pass(player.getStackInHand(hand))
    }

    private fun getItemStack(player: PlayerEntity, hand: Hand): VoxelMaterial? {
        return VoxelSniper.voxelsniper.getMaterial("minecraft", "arrow")
    }

    private fun getAction(): Sniper.Action {
        return Sniper.Action.RIGHT_CLICK_AIR
    }


}