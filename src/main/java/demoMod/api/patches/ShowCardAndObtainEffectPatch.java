package demoMod.api.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import demoMod.BetterSynergies;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class ShowCardAndObtainEffectPatch {
    @SpirePatch(
            clz = ShowCardAndObtainEffect.class,
            method = "update"
    )
    public static class PatchUpdate {
        public static void Postfix(ShowCardAndObtainEffect obj) {
            if (obj.duration <= 0.0F) {
                try {
                    Field cardField = ShowCardAndObtainEffect.class.getDeclaredField("card");
                    cardField.setAccessible(true);
                    AbstractCard card = (AbstractCard)cardField.get(obj);
                    BetterSynergies.instance.onCardObtain(card);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
