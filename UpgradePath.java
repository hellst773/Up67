package up67.rolesmod.upgrade;

import net.minecraft.world.item.Item;

/**
 * Описывает один шаг апгрейда: из какого предмета, во что, какая цена (доп. материал)
 * и какой шанс успеха.
 */
public record UpgradePath(Item from, Item to, Item cost, int costCount, float chance) {
}
