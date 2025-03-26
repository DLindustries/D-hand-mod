// InventorySwitchMixin.java
package vigillant.dlindustries.fabric.dhandmod.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vigillant.dlindustries.fabric.dhandmod.util.InventorySafetyHandler;

@Mixin(MinecraftClient.class)
public abstract class InventorySwitchMixin {
    @Inject(
            method = "handleInputEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;<init>(Lnet/minecraft/entity/player/PlayerEntity;)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void onInventoryOpening(CallbackInfo ci) {
        InventorySafetyHandler.onInventoryKeyPressed();
        ci.cancel();
    }
}