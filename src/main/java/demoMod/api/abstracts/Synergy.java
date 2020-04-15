package demoMod.api.abstracts;

import demoMod.api.interfaces.SynergyItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class Synergy { //一个组合
    public String ID;
    public String name;
    protected List<List<String>> lists; //每个栏位里可以放的组件
    private List<List<SynergyItem>> slots; //玩家当前拥有的组件
    private boolean isActive = false;

    public Synergy(String ID, String name) {
        this.ID = ID;
        this.name = name;
        this.lists = new ArrayList<>();
        this.slots = new ArrayList<>();
        initLists();
        initSlots();
    }

    protected abstract void initLists();

    public void initSlots() {
        slots.clear();
        for (int i=0;i<lists.size();i++) {
            slots.add(new ArrayList<>());
        }
    }

    public void embed(SynergyItem item) {
        int index = 0;
        for (List<String> slot : lists) {
            boolean match = false;
            for (String id : slot) {
                if (id.equals(item.getItemId())) {
                    match = true;
                    break;
                }
            }
            if (match) break;
            index++;
        }
        if (index >= slots.size()) return;
        List<SynergyItem> slot = slots.get(index);
        slot.add(item);
    }

    public void dig(SynergyItem item) {
        for (List<SynergyItem> slot : slots) {
            boolean match = false;
            for (SynergyItem item1 : slot) {
                if (item == item1) {
                    match = true;
                    slot.remove(item);
                    break;
                }
            }
            if (match) break;
        }
    }

    public boolean isActive() {
        for (List<SynergyItem> slot : slots) {
            if (slot.size() == 0) {
                if (isActive) {
                    for (List<SynergyItem> slot1 : slots) {
                        for (SynergyItem item : slot1) {
                            item.onDisable();
                        }
                    }
                    onDisable();
                }
                isActive = false;
                return false;
            }
        }
        if (!isActive) {
            for (List<SynergyItem> slot : slots) {
                for (SynergyItem item : slot) {
                    item.onActivate();
                }
            }
            onActivate();
        }
        isActive = true;
        return true;
    }

    public void onActivate() {
    }

    public void onDisable() {
    }
}
