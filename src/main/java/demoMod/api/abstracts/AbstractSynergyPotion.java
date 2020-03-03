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
            for (String ID : this.getAllSynergiesID()) {
                BetterSynergies.getSynergy(ID).embed(this);
                System.out.println("BetterSynergies:Is synergy " + BetterSynergies.getSynergy(ID).name + "activated:" + BetterSynergies.getSynergy(ID).isActive());
            }
        }
    }

    public void use(AbstractCreature target) {
        for (String ID : this.getAllSynergiesID()) {
            BetterSynergies.getSynergy(ID).dig(this);
            System.out.println("BetterSynergies:Is synergy " + BetterSynergies.getSynergy(ID).name + "activated:" + BetterSynergies.getSynergy(ID).isActive());
        }
        BetterSynergies.unsubscribe(this);
    }

    @Override
    public String getItemId() {
        return this.ID;
    }
}
