package com.verdantartifice.thaumicwonders;

import org.apache.logging.log4j.Logger;

import com.verdantartifice.thaumicwonders.common.misc.CreativeTabTW;
import com.verdantartifice.thaumicwonders.compat.datafixes.ItemDataFixer;
import com.verdantartifice.thaumicwonders.proxy.IProxyTW;
import com.verdantartifice.thaumicwonders.thaumicwonders.Tags;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ThaumicWonders.MODID, name = ThaumicWonders.NAME, version = ThaumicWonders.VERSION, dependencies = ThaumicWonders.DEPENDENCIES)
public class ThaumicWonders {
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = "Thaumic Wonders";
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:thaumcraft@[1.12.2-6.1.BETA26,)";

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabTW(CreativeTabs.getNextID(), ThaumicWonders.MODID);

    public static Logger LOGGER;
    
    @Mod.Instance(ThaumicWonders.MODID)
    public static ThaumicWonders INSTANCE;
    
    @SidedProxy(clientSide="com.verdantartifice.thaumicwonders.proxy.ClientProxy", serverSide="com.verdantartifice.thaumicwonders.proxy.ServerProxy")
    public static IProxyTW proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(MODID, 1);
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemDataFixer());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
    
    static {
        FluidRegistry.enableUniversalBucket();
    }
}
