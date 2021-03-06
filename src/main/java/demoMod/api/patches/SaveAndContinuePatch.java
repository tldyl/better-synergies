package demoMod.api.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import demoMod.BetterSynergies;

@SuppressWarnings("unused")
public class SaveAndContinuePatch {
    @SpirePatch(
            clz = SaveAndContinue.class,
            method = "deleteSave"
    )
    public static class PatchDeleteSave {
        public static void Postfix(AbstractPlayer p) {
            BetterSynergies.resetAll();
        }
    }
}
