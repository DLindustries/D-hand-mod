package dlindustries.optimizer.util;

import net.minecraft.client.MinecraftClient;

public interface MC {
    default MinecraftClient getMc() {
        return MinecraftClient.getInstance();
    }
}
