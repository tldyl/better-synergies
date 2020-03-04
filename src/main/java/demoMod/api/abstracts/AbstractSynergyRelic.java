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
        BetterSynergies.embed(this);
    }

    @Override
    public void onUnequip() {
        BetterSynergies.dig(this);
    }

    public String getItemId() {
        return this.relicId;
    }
}
