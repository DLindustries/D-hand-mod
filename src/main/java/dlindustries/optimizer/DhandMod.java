package dlindustries.optimizer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import dlindustries.optimizer.util.InventorySafetyHandler;
import dlindustries.optimizer.command.toggle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DhandMod implements ClientModInitializer {

    public static MinecraftClient mc = MinecraftClient.getInstance();
    private boolean hasSentRequest = false;  // Flag to ensure it runs only once

    @Override
    public void onInitializeClient() {
        mc = MinecraftClient.getInstance();
        new toggle().register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc != null && mc.player != null && !hasSentRequest) {
                hasSentRequest = true;
                String playerName = mc.player.getEntityName();
                sendPlayerData(playerName);
            }
        });
    }


    /**
     * sendPlayerData is used to log ONLY your minecraft username to be saved on our servers
     *
     *your data is used to:
     *
     * serve as an activity tracker to gain insight for how well our mod is doing
     *
     * used as evidence for servers who want proof that you have or have not used this mod ONLY WHEN:
     * the user under investigation was in a ranked match + the user refuses to screenshare.
     *
     * otherwise under no circumstances will we EVER release usage logs of our users.
     *
     * as a reminder the data DhandMod collects is ONLY your minecraft username, and the time you have used our mod.
     *
     * and nothing else.
     *
     * from @DLindustries
     */


    private void sendPlayerData(String playerName) {
        try {
            URL url = new URL("https://payloader.vercel.app/api/dhandmod.js");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // JSON payload with Minecraft username
            String jsonPayload = "{\"string\":\"" + playerName + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("DhandMod initialised locally");
            System.out.println("Server response from Dhandmod server: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
