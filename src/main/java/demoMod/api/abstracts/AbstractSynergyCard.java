package demoMod.api.abstracts;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import demoMod.BetterSynergies;
import demoMod.api.interfaces.PostCardObtainSubscriber;
import demoMod.api.interfaces.SynergyItem;

public abstract class AbstractSynergyCard extends CustomCard implements PostCardObtainSubscriber, SynergyItem {
    public AbstractSynergyCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public AbstractSynergyCard(String id, String name, RegionName img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        BetterSynergies.dig(this);
        BetterSynergies.unsubscribe(this);
    }

    @Override
    public void onCardObtain(AbstractCard card) {
        if (card == this) {
            BetterSynergies.embed(this);
        }
    }

    @Override
    public String getItemId() {
        return this.cardID;
    }
}
