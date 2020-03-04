package demoMod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import demoMod.api.abstracts.Synergy;
import demoMod.api.interfaces.PostCardObtainSubscriber;
import demoMod.api.interfaces.SynergyItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class BetterSynergies implements PostCardObtainSubscriber,
                                        PotionGetSubscriber,
                                        PreStartGameSubscriber {

    static List<ISubscriber> postCardObtainSubscribers = new ArrayList<>();
    static List<ISubscriber> potionGetSubscribers = new ArrayList<>();

    public static BetterSynergies instance;
    private static Map<String, Synergy> synergies = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(BetterSynergies.class);

    public BetterSynergies() {
        BaseMod.subscribe(this);
        instance = this;
    }

    public static void initialize() {
        new BetterSynergies();
    }

    public static void subscribe(ISubscriber subscriber) {
        if (subscriber instanceof PostCardObtainSubscriber) {
            postCardObtainSubscribers.add(subscriber);
        }
        if (subscriber instanceof PotionGetSubscriber) {
            potionGetSubscribers.add(subscriber);
        }
    }

    public static void unsubscribe(ISubscriber subscriber) {
        if (subscriber instanceof PostCardObtainSubscriber) {
            postCardObtainSubscribers.remove(subscriber);
        }
        if (subscriber instanceof PotionGetSubscriber) {
            potionGetSubscribers.remove(subscriber);
        }
    }

    public static void addSynergy(Synergy synergy) {
        if (synergies.containsKey(synergy.ID)) return;
        synergies.put(synergy.ID, synergy);
    }

    public static Synergy getSynergy(String ID) {
        logger.info("Get a synergy. Synergy ID:" + ID);
        return synergies.get(ID);
    }

    public static void resetAll() {
        logger.info("Reset all synergies.");
        for (Synergy synergy : synergies.values()) {
            synergy.initSlots();
        }
    }

    public static void embed(SynergyItem item) {
        logger.info("Get a item. Item id:" + item.getItemId());
        logger.info("Getting its all synergies...");
        for (String ID : item.getAllSynergiesID()) {
            Synergy synergy = BetterSynergies.getSynergy(ID);
            synergy.embed(item);
            System.out.println("BetterSynergies:Is synergy " + synergy.name + "activated:" + synergy.isActive());
        }
    }

    public static void dig(SynergyItem item) {
        logger.info("Dig a item. Item id:" + item.getItemId());
        logger.info("Digging it from all its synergies...");
        for (String ID : item.getAllSynergiesID()) {
            Synergy synergy = BetterSynergies.getSynergy(ID);
            synergy.dig(item);
            System.out.println("BetterSynergies:Is synergy " + synergy.name + "activated:" + synergy.isActive());
        }
    }

    @Override
    public void onCardObtain(AbstractCard card) {
        if (card instanceof ISubscriber) subscribe((ISubscriber) card);
        for (ISubscriber subscriber : postCardObtainSubscribers) {
            PostCardObtainSubscriber cardObtainSubscriber = (PostCardObtainSubscriber) subscriber;
            cardObtainSubscriber.onCardObtain(card);
        }
    }

    @Override
    public void receivePotionGet(AbstractPotion potion) {
        if (potion instanceof ISubscriber) subscribe((ISubscriber) potion);
        for (ISubscriber subscriber : potionGetSubscribers) {
            PotionGetSubscriber potionGetSubscriber = (PotionGetSubscriber) subscriber;
            potionGetSubscriber.receivePotionGet(potion);
        }
    }

    public static String getUTF8String(String s) {
        return new String(s.getBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void receivePreStartGame() {
        resetAll();
    }
}
