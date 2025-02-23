package dlindustries.optimizer.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import dlindustries.optimizer.command.EnableOptimizerCommand;  // Import the EnableOptimizerCommand class

public class InventorySafetyHandler {
    private static boolean inventoryWasOpen = false;

    public static void handleInventoryTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player == null || !EnableOptimizerCommand.inventorySafetyEnabled) return;

        boolean inventoryOpen = client.currentScreen instanceof net.minecraft.client.gui.screen.ingame.InventoryScreen;

        // Check if inventory just opened
        if (inventoryOpen && !inventoryWasOpen) {
            switchToTotemSlot(player);
        }

        inventoryWasOpen = inventoryOpen;
    }

    private static void switchToTotemSlot(PlayerEntity player) {
        int totemSlot = findTotemInHotbar(player);
        if (totemSlot != -1 && player.getInventory().selectedSlot != totemSlot) {
            setSelectedSlot(totemSlot);
        }
    }

    private static int findTotemInHotbar(PlayerEntity player) {
        for (int i = 0; i < 9; i++) {
            if (isTotem(player.getInventory().getStack(i))) {
                return i;
            }
        }
        return -1; // No totem found in hotbar
    }

    private static boolean isTotem(ItemStack stack) {
        return stack.getItem() == Items.TOTEM_OF_UNDYING;
    }

    private static void setSelectedSlot(int slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.player.getInventory().selectedSlot = slot;
        client.getNetworkHandler().sendPacket(
                new net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket(slot)
        );
    }
}
