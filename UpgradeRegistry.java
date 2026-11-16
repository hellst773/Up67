package up67.rolesmod.upgrade;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * Статическая таблица апгрейдов: ключ - предмет, который кладём в апгрейдер,
 * значение - во что он может превратиться, какой материал нужен и какой шанс успеха.
 * При провале исходный предмет теряется (сгорает в "апгрейдере").
 */
public class UpgradeRegistry {

    private static final Map<Item, UpgradePath> PATHS = new HashMap<>();

    private static void add(Item from, Item to, Item cost, int costCount, float chance) {
        PATHS.put(from, new UpgradePath(from, to, cost, costCount, chance));
    }

    public static UpgradePath get(Item item) {
        return PATHS.get(item);
    }

    public static boolean hasUpgrade(Item item) {
        return PATHS.containsKey(item);
    }

    static {
        // --- Мечи ---
        add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.COBBLESTONE, 2, 0.9f);
        add(Items.STONE_SWORD, Items.IRON_SWORD, Items.IRON_INGOT, 2, 0.75f);
        add(Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.DIAMOND, 2, 0.5f);
        add(Items.DIAMOND_SWORD, Items.NETHERITE_SWORD, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Кирки ---
        add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.COBBLESTONE, 3, 0.9f);
        add(Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.IRON_INGOT, 3, 0.75f);
        add(Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.DIAMOND, 3, 0.5f);
        add(Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Топоры ---
        add(Items.WOODEN_AXE, Items.STONE_AXE, Items.COBBLESTONE, 3, 0.9f);
        add(Items.STONE_AXE, Items.IRON_AXE, Items.IRON_INGOT, 3, 0.75f);
        add(Items.IRON_AXE, Items.DIAMOND_AXE, Items.DIAMOND, 3, 0.5f);
        add(Items.DIAMOND_AXE, Items.NETHERITE_AXE, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Лопаты ---
        add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.COBBLESTONE, 1, 0.9f);
        add(Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.IRON_INGOT, 1, 0.75f);
        add(Items.IRON_SHOVEL, Items.DIAMOND_SHOVEL, Items.DIAMOND, 1, 0.5f);
        add(Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Мотыги ---
        add(Items.WOODEN_HOE, Items.STONE_HOE, Items.COBBLESTONE, 2, 0.9f);
        add(Items.STONE_HOE, Items.IRON_HOE, Items.IRON_INGOT, 2, 0.75f);
        add(Items.IRON_HOE, Items.DIAMOND_HOE, Items.DIAMOND, 2, 0.5f);
        add(Items.DIAMOND_HOE, Items.NETHERITE_HOE, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Броня: шлем ---
        add(Items.LEATHER_HELMET, Items.IRON_HELMET, Items.IRON_INGOT, 5, 0.6f);
        add(Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.DIAMOND, 5, 0.45f);
        add(Items.DIAMOND_HELMET, Items.NETHERITE_HELMET, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Броня: нагрудник ---
        add(Items.LEATHER_CHESTPLATE, Items.IRON_CHESTPLATE, Items.IRON_INGOT, 8, 0.6f);
        add(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.DIAMOND, 8, 0.45f);
        add(Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Броня: поножи ---
        add(Items.LEATHER_LEGGINGS, Items.IRON_LEGGINGS, Items.IRON_INGOT, 7, 0.6f);
        add(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.DIAMOND, 7, 0.45f);
        add(Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Броня: ботинки ---
        add(Items.LEATHER_BOOTS, Items.IRON_BOOTS, Items.IRON_INGOT, 4, 0.6f);
        add(Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.DIAMOND, 4, 0.45f);
        add(Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS, Items.NETHERITE_INGOT, 1, 0.3f);

        // --- Приколы (для прикольности, с маленьким шансом) ---
        add(Items.ROTTEN_FLESH, Items.GOLDEN_APPLE, Items.GOLD_INGOT, 8, 0.03f); // почти всегда "ПРОВАЛ"
        add(Items.COAL, Items.DIAMOND, Items.COAL, 1, 0.02f); // "превратить уголь в алмаз" - шанс почти нулевой
        add(Items.DIRT, Items.DIAMOND_BLOCK, Items.DIRT, 64, 0.001f); // почти гарантированный лол-провал
        add(Items.STICK, Items.NETHERITE_INGOT, Items.STICK, 64, 0.0001f); // мем "палка в незерит"
    }

    private UpgradeRegistry() {
    }
}
