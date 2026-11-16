package up67.rolesmod.menu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import up67.rolesmod.registry.ModMenuTypes;
import up67.rolesmod.upgrade.JokeMessages;
import up67.rolesmod.upgrade.UpgradePath;
import up67.rolesmod.upgrade.UpgradeRegistry;

import java.util.concurrent.ThreadLocalRandom;

public class UpgraderMenu extends AbstractContainerMenu {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_COST = 1;

    public static final int BUTTON_UPGRADE = 0;

    private final Container container = new SimpleContainer(2) {
        @Override
        public boolean stillValid(Player player) {
            return true;
        }
    };

    private final Player player;

    public UpgraderMenu(int containerId, Inventory playerInventory) {
        super(ModMenuTypes.UPGRADER_MENU.get(), containerId);
        this.player = playerInventory.player;

        // слот для предмета, который апгрейдим
        this.addSlot(new Slot(container, SLOT_INPUT, 44, 35));
        // слот для материала-оплаты апгрейда
        this.addSlot(new Slot(container, SLOT_COST, 44, 65));

        // инвентарь игрока
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 102 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 160));
        }
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == BUTTON_UPGRADE) {
            tryUpgrade(player);
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    private void tryUpgrade(Player player) {
        ItemStack input = container.getItem(SLOT_INPUT);
        if (input.isEmpty()) {
            return;
        }

        UpgradePath path = UpgradeRegistry.get(input.getItem());
        if (path == null) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Этот предмет апгрейдить нельзя. Даже не пытайся."),
                    true);
            return;
        }

        ItemStack cost = container.getItem(SLOT_COST);
        if (cost.getItem() != path.cost() || cost.getCount() < path.costCount()) {
            player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Не хватает материала для апгрейда."),
                    true);
            return;
        }

        // списываем ресурсы
        input.shrink(1);
        cost.shrink(path.costCount());

        boolean success = ThreadLocalRandom.current().nextFloat() < path.chance();

        if (success) {
            ItemStack result = new ItemStack(path.to());
            if (!player.getInventory().add(result)) {
                player.drop(result, false);
            }
            player.displayClientMessage(JokeMessages.randomSuccess(path.chance()), false);
            player.level().playSound(null, player.blockPosition(),
                    net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP,
                    net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            player.displayClientMessage(JokeMessages.randomFail(), false);
            player.level().playSound(null, player.blockPosition(),
                    net.minecraft.sounds.SoundEvents.ANVIL_BREAK,
                    net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 0.8f);
        }

        this.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < 2) {
                // из слотов апгрейдера -> в инвентарь игрока
                if (!this.moveItemStackTo(originalStack, 2, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // из инвентаря игрока -> в слот апгрейдера (сначала вход, потом стоимость)
                if (!this.moveItemStackTo(originalStack, SLOT_INPUT, SLOT_COST + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            this.clearContainer(player, container);
        }
    }
}
