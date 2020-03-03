package demoMod.api.abstracts;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import demoMod.BetterSynergies;
import demoMod.api.interfaces.SynergyItem;

public abstract class AbstractSynergyRelic extends CustomRelic implements SynergyItem {
    public AbstractSynergyRelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }

    public AbstractSynergyRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public AbstractSynergyRelic(String id, String imgName, RelicTier tier, LandingSound sfx) {
        super(id, imgName, tier, sfx);
    }

    @Override
    public void onEquip() {
        for (String ID : this.getAllSynergiesID()) {
            BetterSynergies.getSynergy(ID).embed(this);
            System.out.println("BetterSynergies:Is synergy " + BetterSynergies.getSynergy(ID).name + "activated:" + BetterSynergies.getSynergy(ID).isActive());
        }
    }

    @Override
    public void onUnequip() {
        for (String ID : this.getAllSynergiesID()) {
            BetterSynergies.getSynergy(ID).dig(this);
            System.out.println("BetterSynergies:Is synergy " + BetterSynergies.getSynergy(ID).name + "activated:" + BetterSynergies.getSynergy(ID).isActive());
        }
    }

    public String getItemId() {
        return this.relicId;
    }
}
