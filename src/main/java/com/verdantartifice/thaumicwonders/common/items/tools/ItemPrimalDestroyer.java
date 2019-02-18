package com.verdantartifice.thaumicwonders.common.items.tools;

import java.util.List;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.api.items.ItemsTC;

public class ItemPrimalDestroyer extends ItemSword implements IWarpingGear {
    public static Item.ToolMaterial toolMatVoidflame = EnumHelper.addToolMaterial("VOIDFLAME", 4, 200, 8.0F, 6.0F, 20).setRepairItem(new ItemStack(ItemsTC.ingots, 1, 1));
    
    public ItemPrimalDestroyer() {
        super(toolMatVoidflame);
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        this.setRegistryName(ThaumicWonders.MODID, "primal_destroyer");
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
    }

    @Override
    public int getWarp(ItemStack itemStack, EntityPlayer player) {
        return 3;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        if (stack.isItemDamaged() && entityIn != null && (entityIn.ticksExisted % 20 == 0) && entityIn instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase)entityIn);
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (!target.world.isRemote && (!(target instanceof EntityPlayer) || !(attacker instanceof EntityPlayer) || FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())) {
            try {
                // Attempt to apply the greater sapping and voidflame effects
                target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 60));
                target.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 120));
                target.setFire(3);
            } catch (Exception e) {}
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GOLD + I18n.format("enchantment.special.sapgreat"));
        tooltip.add(TextFormatting.GOLD + I18n.format("enchantment.special.voidflame"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
