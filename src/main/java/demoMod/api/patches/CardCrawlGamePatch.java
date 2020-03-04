package demoMod.api.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import demoMod.api.interfaces.PostCardObtainSubscriber;

@SuppressWarnings("unused")
public class CardCrawlGamePatch {
    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "loadPlayerSave"
    )
    public static class PatchLoadPlayerSave {
        @SpireInsertPatch(rloc = 67)
        public static void Insert(CardCrawlGame game, AbstractPlayer p) {
            int index = p.masterDeck.group.size() - 1;
            System.out.println(index);
            if (index >= 0) {
                AbstractCard card = p.masterDeck.group.get(index);
                if (card instanceof PostCardObtainSubscriber) {
                    ((PostCardObtainSubscriber) card).onCardObtain(card);
                }
            }
        }
    }
}
