package demoMod.api.interfaces;

public interface SynergyItem {
    String[] getAllSynergiesID();

    String getItemId();

    default void onActivate() {}

    default void onDisable() {}
}
