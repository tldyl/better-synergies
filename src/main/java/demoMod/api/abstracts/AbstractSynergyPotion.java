package demoMod.api.abstracts;

import basemod.abstracts.CustomPotion;
import basemod.interfaces.PotionGetSubscriber;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import demoMod.BetterSynergies;
import demoMod.api.interfaces.SynergyItem;

public abstract class AbstractSynergyPotion extends CustomPotion implements PotionGetSubscriber, SynergyItem {
    public AbstractSynergyPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) {
        super(name, id, rarity, size, color);
    }

    @Override
    public void receivePotionGet(AbstractPotion potion) {
        if (potion == this) {
            BetterSynergies.embed(this);
        }
    }

    public void use(AbstractCreature target) {
        BetterSynergies.dig(this);
        BetterSynergies.unsubscribe(this);
    }

    @Override
    public String getItemId() {
        return this.ID;
    }
}
