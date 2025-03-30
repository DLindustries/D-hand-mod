package vigillant.dlindustries.fabric.dhandmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class dhandmodClient implements ClientModInitializer {


	@Override
	public void onInitializeClient() {
		MinecraftClient client = MinecraftClient.getInstance();
		System.out.println("[D-Hand] Version 1.4.0");

	}
}
