package demoMod.api.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import demoMod.BetterSynergies;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class FastCardObtainEffectPatch {
    @SpirePatch(
            clz = FastCardObtainEffect.class,
            method = "update"
    )
    public static class PatchUpdate {
        public static void Postfix(FastCardObtainEffect obj) {
            Field cardField;
            try {
                cardField = FastCardObtainEffect.class.getDeclaredField("card");
                cardField.setAccessible(true);
                AbstractCard card = (AbstractCard)cardField.get(obj);
                BetterSynergies.instance.onCardObtain(card);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
