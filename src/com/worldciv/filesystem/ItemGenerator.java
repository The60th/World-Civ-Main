package com.worldciv.filesystem;

import com.worldciv.the60th.Main;
import com.worldciv.utility.*;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import javax.rmi.CORBA.Tie;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ItemGenerator {
    //Should make different min-max for armor vs weapons.
    private static final int tierOneMin = 1;
    private static final int tierOneMax = 6;

    private static final int tierTwoMin = 4;
    private static final int tierTwoMax = 9;

    private static final int tierThreeMin = 7;
    private static final int tierThreeMax = 11;

    private static final int tierFourMin = 9;
    private static final int tierFourMax = 13;

    private static final int tierFiveMin = 10;
    private static final int tierFiveMax = 16;

    private static final int commonMod = 0;
    private static final int uncommonMod = 1;
    private static final int rareMod = 2;
    private static final int epicMod = 3;
    private static final int legendaryMod = 5;

    private static final int SHIELD_ARMOR_FIX_VALUE_BUFF = 5;
    private static final int CHESTPLATE_ARMOR_FIX_VALUE_BUFF = 3;
    private static final int LEGGINGS_ARMOR_FIX_VALUE_BUFF = 2;


    public static CustomItem generateItem(ItemStack itemStack, Tier tier){

        return null;
    }

    //TODO Create methoods that you pass a custom name to,
    //For now just check names within the generator.
    public static CustomItem generateItem(ItemStack itemStack, Tier tier,WeaponType weaponType){
        int damage = 0;
        int armor = 0;
        Rarity rarity = calculateRarity(0);

        if(weaponType == WeaponType.ARROW){
            damage = calculateStat(rarity,tier);
        }
        else if(weaponType == WeaponType.AXE){
            damage = calculateStat(rarity,tier);
        }
        else if(weaponType == WeaponType.BOW){
            damage = calculateStat(rarity,tier);
        }
        else if(weaponType == WeaponType.SHIELD){
            damage = (calculateStat(rarity,tier)/3);
            if(damage < 1) damage = 1;
            armor = calculateStat(rarity,tier) + SHIELD_ARMOR_FIX_VALUE_BUFF;
        }
        else if(weaponType == WeaponType.SWORD){
            damage = calculateStat(rarity,tier);
        }
        String name = getItemType(weaponType,tier);
        String id = CustomItem.unhideItemUUID(createUUID());
        id = convertToInvisibleString(id);
        return new CustomItem(itemStack,name,id,damage,armor,rarity,tier);
    }
    public static CustomItem generateItem(ItemStack itemStack, Tier tier, ArmorType armorType){
        int armor = 0;
        int damage = 0;
        Rarity rarity = calculateRarity(0);
        if(armorType == ArmorType.HELM){
            armor = (calculateStat(rarity,tier)/4);
            if(armor <= 0){armor = 1;}
        }
        else if(armorType == ArmorType.CHESTPLATE){
            armor = (calculateStat(rarity,tier)/4);
            if(armor <= 0){armor = 1;}
            armor = armor+CHESTPLATE_ARMOR_FIX_VALUE_BUFF;
        }
        else if(armorType == ArmorType.LEGGINGS){
            armor = (calculateStat(rarity,tier)/4);
            if(armor <= 0){armor = 1;}
            armor = armor + LEGGINGS_ARMOR_FIX_VALUE_BUFF;
        }
        else if(armorType == ArmorType.BOOTS){
            armor = (calculateStat(rarity,tier)/4);
            if(armor <= 0){armor = 1;}
        }

        int stat = calculateStat(rarity,tier)/4;
        String name = getItemName(armorType,tier);
        String id = CustomItem.unhideItemUUID(createUUID());
        id = convertToInvisibleString(id);
        return new CustomItem(itemStack,name,id,damage,armor,rarity,tier);
    }


    private static Rarity calculateRarity(double modifier){
        //Add checks for the modifier later on.
        Random random = new Random(System.currentTimeMillis());
        int x = random.nextInt(100)+1;
        if(isBetween(x,0,40)){ return Rarity.Common; }
        else if(isBetween(x,41,65)){return Rarity.Uncommon;}
        else if(isBetween(x,65,85)){return Rarity.Rare;}
        else if(isBetween(x,85,95)){return Rarity.Epic;}
        else if(isBetween(x,95,100)){return Rarity.Legendary;}
        else{
            Main.logger.info(("Rarity generation error has happened."));
            return Rarity.Common;
        }
    }
    private static int calculateStat(Rarity rarity,Tier tier){
        int calculatedValue;
        switch (tier){
            case I:
                 calculatedValue = ThreadLocalRandom.current().nextInt(tierOneMin, tierOneMax + 1);
                break;
            case II:
                 calculatedValue = ThreadLocalRandom.current().nextInt(tierTwoMin, tierTwoMax + 1);
                break;
            case III:
                 calculatedValue = ThreadLocalRandom.current().nextInt(tierFourMin, tierThreeMax + 1);
                break;
            case IV:
                 calculatedValue = ThreadLocalRandom.current().nextInt(tierThreeMin, tierFourMax + 1);
                break;
            case V:
                calculatedValue = ThreadLocalRandom.current().nextInt(tierFiveMin, tierFiveMax + 1);
                break;
            default:
                calculatedValue = -1;
                break;
        }
        switch (rarity){
            case Common:
                calculatedValue = calculatedValue + commonMod;
                break;
            case Uncommon:
                calculatedValue = calculatedValue + uncommonMod;
                break;
            case Rare:
                calculatedValue = calculatedValue + rareMod;
                break;
            case Epic:
                calculatedValue = calculatedValue + epicMod;
                break;
            case Legendary:
                calculatedValue = calculatedValue + legendaryMod;
                break;
            default:
                calculatedValue = -1;
                break;
        }
        return calculatedValue;
    }
    public static ChatColor getColorFromRarity(Rarity rarity){
        switch (rarity){
            case Common:
                return ChatColor.WHITE;
            case Uncommon:
                return ChatColor.GREEN;
            case Rare:
                return ChatColor.BLUE;
            case Epic:
                return ChatColor.DARK_PURPLE;
            case Legendary:
                return ChatColor.GOLD;
            default:
                return ChatColor.RED;
        }
    }

    private static String getItemName(ArmorType armorType, Tier tier){
        return createRandomName() +getMaterialByTier(tier)+ " "+checkArmorType(armorType).toString().toLowerCase();
    }
    private  static String getItemType(WeaponType weaponType, Tier tier){
        return createRandomName() + getMaterialByTier(tier) +" "+ checkWeaponType(weaponType).toString().toLowerCase();
    }
    private static String createRandomName(){
        Random random = new Random();
        int x = random.nextInt(6)+1;
        switch (x){
            case 1:
                return WordLists.myWordListAppearance[random.nextInt(WordLists.myWordListAppearance.length)] + " ";
            case 2:
                return WordLists.myWordListColor[random.nextInt(WordLists.myWordListColor.length)] + " ";
            case 3:
                return WordLists.myWordListSize[random.nextInt(WordLists.myWordListSize.length)] + " ";
            case 4:
                return WordLists.myWordListTime[random.nextInt(WordLists.myWordListTime.length)] + " ";
            case 5:
                return WordLists.myWordListTouch[random.nextInt(WordLists.myWordListTouch.length)] + " ";
            case 6:
                break;
            default:
                x = random.nextInt(5)+1;
                String string = "";
                switch (x) {
                    case 1:
                        string = string + WordLists.myWordListAppearance[random.nextInt(WordLists.myWordListAppearance.length)] + " ";
                    case 2:
                        string = string + WordLists.myWordListColor[random.nextInt(WordLists.myWordListColor.length)] + " ";
                    case 3:
                        string = string + WordLists.myWordListSize[random.nextInt(WordLists.myWordListSize.length)] + " ";
                    case 4:
                        string = string + WordLists.myWordListTime[random.nextInt(WordLists.myWordListTime.length)] + " ";
                    case 5:
                        string = string + WordLists.myWordListTouch[random.nextInt(WordLists.myWordListTouch.length)] + " ";
                }
                x = random.nextInt(5)+1;
                switch (x) {
                    case 1:
                        string = string + WordLists.myWordListAppearance[random.nextInt(WordLists.myWordListAppearance.length)] + " ";
                    case 2:
                        string = string + WordLists.myWordListColor[random.nextInt(WordLists.myWordListColor.length)] + " ";
                    case 3:
                        string = string + WordLists.myWordListSize[random.nextInt(WordLists.myWordListSize.length)] + " ";
                    case 4:
                        string = string + WordLists.myWordListTime[random.nextInt(WordLists.myWordListTime.length)] + " ";
                    case 5:
                        string = string + WordLists.myWordListTouch[random.nextInt(WordLists.myWordListTouch.length)] + " ";
                }
                return string;
        }
        return "Bob's old ";
    }



    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    private static String createUUID(){
        return UUID.randomUUID().toString();
    }
    private static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }

    private static ArmorType checkArmorType(ArmorType armorType){
        switch (armorType){
            case HELM:
                return ArmorType.HELM;
            case CHESTPLATE:
                return ArmorType.CHESTPLATE;
            case LEGGINGS:
                return  ArmorType.LEGGINGS;
            case BOOTS:
                return ArmorType.BOOTS;
        }
        return ArmorType.DEFAULT;
    }
    private static WeaponType checkWeaponType(WeaponType weaponType){
        switch (weaponType){
            case SHIELD:
                return WeaponType.SWORD;
            case AXE:
                return WeaponType.AXE;
            case SWORD:
                return WeaponType.SWORD;
            case BOW:
                return WeaponType.BOW;
            case ARROW:
                return WeaponType.ARROW;
        }
        return WeaponType.DEFAULT;
    }

    private static String getMaterialByTier(Tier tier){
        switch (tier){
            case I:
                return "Iron";
            case II:
                return "Steel";
            case III:
                return "Hard Steel";
            case IV:
                return "Meteorite";
            case V:
                return "Dragon Steel";
        }
        return "Basic";
    }
}
