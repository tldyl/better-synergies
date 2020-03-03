package demoMod.api.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

@SuppressWarnings("unused")
public class CardGroupPatch {
    @SpirePatch(
            clz = CardGroup.class,
            method = "removeCard",
            paramtypez = {
                String.class
            }
    )
    public static class PatchRemoveCard {
        @SpireInsertPatch(rloc = 3, localvars = {"e"})
        public static void Insert(CardGroup group, String id, @ByRef(type="cards.AbstractCard") Object[] _card) {
            if (group.type == CardGroup.CardGroupType.MASTER_DECK) {
                AbstractCard card = (AbstractCard) _card[0];
                System.out.println("BetterSynergies:Post on card removal.");
                card.onRemoveFromMasterDeck();
            }
        }
    }
}
