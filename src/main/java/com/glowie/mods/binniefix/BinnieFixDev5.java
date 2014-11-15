package com.glowie.mods.binniefix;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.MessageFormatMessage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = BinnieFixDev5.MODID, name = BinnieFixDev5.MODNAME, version = BinnieFixDev5.VERSION, dependencies = "required-after:ExtraTrees")
public class BinnieFixDev5 {
	public static final String MODID = "binnie-fix-dev5";
	public static final String MODNAME = "BinnieFix Dev5";
	public static final String VERSION = "@VERSION@";
	
	@Instance(BinnieFixDev5.MODID)
	public static BinnieFixDev5 instance = new BinnieFixDev5();
	
	public static void debug(String msg) {
		LogManager.getLogger(MODID).debug(msg);
	}

	/**
	 * Run before anything else: config, create blocks, items, register with GameRegistry
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
	}
	
	/**
	 * Mod setup: Data structures and recipies
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		debug("Version: " + VERSION);
	}
	
	/**
	 * Post init: handle interaction with other mods.
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

		// Recipe Removal Start:
		int totalRecipes = CraftingManager.getInstance().getRecipeList().size();
		debug("totalRecipes: " + Integer.toString(totalRecipes));
		
		removeNBTShapedRecipes();
		
		// Recipe Removal Result:
		int resultRecipes = CraftingManager.getInstance().getRecipeList().size();
		debug("resultRecipes: " + Integer.toString(resultRecipes));
	}
	
	/**
	 * Binne has a bajillion recipies stuffed in his own recipe holder NBTShapedRecipes
	 * which returns null for getRecipeOutput() because it could return damn near anything.
	 */
	public void removeNBTShapedRecipes() {
		Iterator it = CraftingManager.getInstance().getRecipeList().iterator();
		while (it.hasNext()) {
			IRecipe recipe = (IRecipe)it.next();
			Class<?> clazz = recipe.getClass();
			if ("binnie.extratrees.block.decor.NBTShapedRecipes".equals(clazz.getName())) {
				it.remove();
				debug("Found and removed the NBTShapedRecipes!");
				return;
			}
		}
	}
	
	public void debugItemStack(ItemStack exampleStack, String label) {
		String msg = label;
		msg += ": " + exampleStack.toString();
		msg += ", " + exampleStack.getDisplayName();
		msg += ", " + exampleStack.getUnlocalizedName();
		msg += ", " + exampleStack.getItemDamage();
		msg += ":";
		
		NBTTagCompound tagCompound = exampleStack.getTagCompound();
		String tags = "";
		if (tagCompound != null) {
			int val = tagCompound.getInteger("meta");
			msg += Integer.toString(val);
		} else {
			msg += "null";
		}
		
		debug(msg);
		
	}

}


