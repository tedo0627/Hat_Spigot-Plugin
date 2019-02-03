package tedo0627.hat;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EventListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (!(inventory instanceof PlayerInventory)) {
			return;
		}
		if (event.getSlotType() != SlotType.ARMOR) {
			return;
		}
		if (event.getSlot() != 39) {
			return;
		}
		HumanEntity player = event.getWhoClicked();
		ItemStack stack = player.getInventory().getHelmet();
		ItemStack cursor = event.getCursor();
		if (player.getInventory().getHelmet() == null) {
			if (cursor == null || cursor.isSimilar(new ItemStack(Material.AIR))) {
				return;
			}
			if (event.getClick() == ClickType.LEFT) {
				player.getInventory().setHelmet(cursor);
				player.getOpenInventory().setCursor(null);
				event.setCancelled(true);
			} else if (event.getClick() == ClickType.RIGHT) {
				stack = cursor.clone();
				stack.setAmount(1);
				player.getInventory().setHelmet(stack);

				cursor.setAmount(cursor.getAmount() - 1);
				player.getOpenInventory().setCursor(cursor);
				event.setCancelled(true);
			}
		} else {
			if (player.getInventory().getHelmet().isSimilar(event.getCursor())) {
				if (event.getClick() == ClickType.LEFT) {
					int amount = stack.getAmount() + cursor.getAmount();
					int count = 0;
					if (amount > stack.getMaxStackSize()) {
						count = amount - 64;
						amount = 64;
					}
					stack.setAmount(amount);
					player.getInventory().setHelmet(stack);
					cursor.setAmount(count);
					player.getOpenInventory().setCursor(cursor);
					event.setCancelled(true);
				} else if (event.getClick() == ClickType.RIGHT) {
					event.setCancelled(true);
					if (stack.getMaxStackSize() < stack.getAmount() + 1) {
						return;
					}
					stack.setAmount(stack.getAmount() + 1);
					player.getInventory().setHelmet(stack);

					cursor.setAmount(cursor.getAmount() - 1);
					player.getOpenInventory().setCursor(cursor);
				}
			} else {
				if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT) {
					player.getInventory().setHelmet(cursor);
					player.getOpenInventory().setCursor(stack);
					event.setCancelled(true);
				}
			}
		}
	}
}
