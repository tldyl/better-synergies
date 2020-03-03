package demoMod.api.interfaces;

import basemod.interfaces.ISubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PostCardObtainSubscriber extends ISubscriber {
    void onCardObtain(AbstractCard card);
}
