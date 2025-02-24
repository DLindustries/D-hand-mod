package dlindustries.optimizer.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;


public class EnableOptimizerCommand {
    public static boolean fastCrystalEnabled = false;
    public static boolean inventorySafetyEnabled = true;

    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, environment) -> {
            dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("dhand")
                    .executes(context -> {
                        toggleAllFeatures();
                        return Command.SINGLE_SUCCESS;
                    })
            );
        });
    }

    private void toggleAllFeatures() {

        inventorySafetyEnabled = !inventorySafetyEnabled;
          // Toggle NoRender along with other features

        String status = !inventorySafetyEnabled ? "enabled" : "disabled";
        displayMessage("D-HandMod " + status);
    }

    public static void displayMessage(String message) {
        if (!isInGame()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        client.inGameHud.getChatHud().addMessage(Text.of(message));
    }

    public static boolean isInGame() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client.player != null && client.getNetworkHandler() != null;
    }
}
