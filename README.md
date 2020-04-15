# better-synergies
A framework for creating and managing synergies in Slay The Spire.

一个在杀戮尖塔中创建和管理协同效果的框架

## 组合添加步骤
### 1.建立自己的组合类
要建立自己的组合类，首先需要继承Synergy抽象类：

    import demoMod.api.abstracts.Synergy;

    public class MySynergy extends Synergy {
    	public static final String ID;
    	public static final String NAME;
    
    	public MySynergy() {
    		super(ID, NAME);
    	}
    
	    @Override
	    protected void initLists() {
		    this.lists.add(new ArrayList<>());
		    this.lists.add(new ArrayList<>());
		    this.lists.get(0).add(TestCard1.ID);
		    this.lists.get(1).add(TestCard2.ID);
		    this.lists.get(1).add(TestCard3.ID);
	    }
    
	    @Override
	    public void onActivate() {
		    super.onActivate();
		    System.out.println("Synergy " + NAME + " has activated!");
	    }
	    
	    @Override
	    public void onDisable() {
		    super.onDisable();
		    System.out.println("Synergy " + NAME + " has disabled!");
	    }
	    
	    static {
		    ID = "MyPrefix:MySynergy";
		    NAME = "MySynergyName";
	    }
    
    }
这里要引入一个概念：栏位(机器人玩过吧，对，就是和这个类似的)

一个栏位中可以放入一种或多种不同的组件(指定种类之外的组件不允许放入)

一个有意义的组合要求至少有2个及以上的栏位

判断一个组合被激活的条件是：玩家同时拥有这个组合的每个栏位中的至少一个组件

上述代码中，initLists()方法初始化了这个组合的所有栏位，其中栏位0中只能放TestCard1，栏位1中可以放TestCard2或TestCard3，玩家在游戏过程中获得的所有TestCard都会被放到对应的栏位。
lists规定了每个栏位中能放什么组件

当栏位0和栏位1均不为空时，组合的效果被激活，这个组合类便会调用所有放入栏位中的组件的onActivate()方法

### 2.建立自己的组合组件
要建立自己的组合组件，需要继承对应的组件抽象类：

如果组件是卡牌，需要继承AbstractSynergyCard

如果组件是遗物，需要继承AbstractSynergyRelic

如果组件是药水，需要继承AbstractSynergyPotion

例：

	public class MySynergyRelic extends AbstractSynergyRelic {
	    public static final String ID = "MyPrefix:MySynergyRelic";
	    public static final String IMG_PATH = "myImages/relics/synergyRelic.png";
	
	    public SynergyRelic() {
	        super(ID, new Texture(IMG_PATH),
	                RelicTier.SPECIAL, LandingSound.SOLID);
	    }
	
	    @Override
	    public String getUpdatedDescription() {
	        return DESCRIPTIONS[0];
	    }
	
	    @Override
	    public String[] getAllSynergiesID() {
	        return new String[] { //这里返回有这个组件的所有组合
	                Synergy1.ID,
	                Synergy2.ID,
	                Synergy3.ID,
	                ...
	        };
	    }
	}


如果你的卡牌/遗物/药水已经继承了其他的抽象类，那么也可以像如下代码一样写：


	import demoMod.api.interfaces.PostCardObtainSubscriber;
	import demoMod.api.interfaces.SynergyItem;
	
	public class MyCard extends MyAbstracts implements PostCardObtainSubscriber, SynergyItem {
	    
	    ... //Your contents here
	    
	    @Override
	    public void onRemoveFromMasterDeck() {
	        for (String ID : this.getAllSynergiesID()) {
	            BetterSynergies.getSynergy(ID).dig(this);
	            BetterSynergies.getSynergy(ID).isActive()
	        }
	        BetterSynergies.unsubscribe(this);
	    }
	
	    @Override
	    public void onCardObtain(AbstractCard card) {
	        if (card == this) {
	            for (String ID : this.getAllSynergiesID()) {
	                BetterSynergies.getSynergy(ID).embed(this);
	                BetterSynergies.getSynergy(ID).isActive()
	            }
	        }
	    }
	    
	    @Override
	    public String[] getAllSynergiesID() {
	        return new String[] { //这里返回有这个组件的所有组合
	                Synergy1.ID,
	                Synergy2.ID,
	                Synergy3.ID,
	                ...
	        };
	    }
	    
	    @Override
	    public String getItemId() {
	        return this.cardID;
	    }
	}

### 3.向框架注册你的组合
通过前面的步骤，我们已经成功地创建了自己的组合以及对应的组件，现在只要向组合框架注册自己的组合，组合的效果就能在游戏中体现了！

(当然组合的效果还是得自己写，覆写SynergyItem接口的onActivate方法，把组合效果写在那里即可)

	@SpireInitializer
	public class MyMod implements ... {
	
	    ...
	
	    @Override
	    public void receivePostInitialize() {
	        BetterSynergies.addSynergy(new MySynergy1());
	        BetterSynergies.addSynergy(new MySynergy2());
	        ...
	    }
	}
