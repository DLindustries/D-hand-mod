package vigillant.dlindustries.fabric.dhandmod.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import java.util.Random;

public class InventorySafetyHandler {
    private static final Random random = new Random();
    private static long scheduledOpenTime = -1;
    private static boolean shouldOpenInventory = false;

    public static void onInventoryKeyPressed() {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null || client.world == null) return;

        // Attempt totem switch if needed
        int totemSlot = findTotemInHotbar(player);
        if (totemSlot != -1 && totemSlot != player.getInventory().selectedSlot) {
            player.getInventory().selectedSlot = totemSlot;
            if (client.getNetworkHandler() != null) {
                client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(totemSlot));
            }
        }

        // Always schedule inventory opening
        scheduleInventoryOpen();
    }

    private static void scheduleInventoryOpen() {
        long delay = Math.max(10, Math.min(35, (long) (random.nextGaussian() * 20 + 50)));
        scheduledOpenTime = System.currentTimeMillis() + delay;
        shouldOpenInventory = true;
    }



    public static void onClientTick() {
        if (shouldOpenInventory && System.currentTimeMillis() >= scheduledOpenTime) {
            MinecraftClient client = MinecraftClient.getInstance();

            // Verify valid state
            if (client.player == null || client.world == null || client.currentScreen != null) {
                resetState();
                return;
            }

            // Open inventory unconditionally
            client.execute(() -> {
                client.setScreen(new InventoryScreen(client.player));
                resetState();
            });
        }
    }

    public static void resetState() {
        scheduledOpenTime = -1;
        shouldOpenInventory = false;
    }

    private static int findTotemInHotbar(PlayerEntity player) {
        // Random scan direction for anticheat
        if (random.nextBoolean()) {
            for (int i = 0; i < 9; i++) {
                if (isTotem(player.getInventory().getStack(i))) return i;
            }
        } else {
            for (int i = 8; i >= 0; i--) {
                if (isTotem(player.getInventory().getStack(i))) return i;
            }
        }
        return -1;
    }

    private static boolean isTotem(ItemStack stack) {
        return stack.getItem() == Items.TOTEM_OF_UNDYING;
    }
}