package dlindustries.optimizer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dlindustries.optimizer.CpvpUtilsMod;
import dlindustries.optimizer.command.EnableOptimizerCommand;

import static dlindustries.optimizer.CpvpUtilsMod.limitPackets;
import static dlindustries.optimizer.CpvpUtilsMod.mc;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
    private void onDoItemUse(CallbackInfo ci) {
        if (EnableOptimizerCommand.fastCrystalEnabled) {
            ItemStack mainHand = mc.player.getMainHandStack();
            if (mainHand.isOf(Items.END_CRYSTAL))
                //ensures only are packets are being sent through
                if (CpvpUtilsMod.hitCount != limitPackets())
                    ci.cancel();
        }
    }
}
