package vigillant.dlindustries.fabric.dhandmod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class dhandmodClient implements ClientModInitializer {
	private static final String DISCORD_INVITE = "https://discord.com/invite/yynpznJVkC";

	@Override
	public void onInitializeClient() {
		MinecraftClient client = MinecraftClient.getInstance();
		System.out.println("[D-Hand] Version 1.3.2");


		// Copy to clipboard
		Toolkit.getDefaultToolkit()
				.getSystemClipboard()
				.setContents(new StringSelection(DISCORD_INVITE), null);


		client.execute(() -> {
			if (client.player != null) {
				client.player.sendMessage(
						Text.literal("[DHANDMOD] Discord invite copied to clipboard. Consider joining for bug reporting and improvements!"),
						false
				);
			}
		});
	}
}
