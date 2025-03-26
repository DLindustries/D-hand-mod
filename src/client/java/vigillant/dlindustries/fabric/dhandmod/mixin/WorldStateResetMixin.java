// WorldStateResetMixin.java (new name)
package vigillant.dlindustries.fabric.dhandmod.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vigillant.dlindustries.fabric.dhandmod.util.InventorySafetyHandler;

@Mixin(MinecraftClient.class)
public abstract class WorldStateResetMixin {
    @Inject(method = "disconnect", at = @At("HEAD"))
    private void onDisconnect(CallbackInfo ci) {
        InventorySafetyHandler.resetState();
    }
}