package net.satisfy.brewery.registry;

import com.google.common.collect.Lists;
import de.cristelknight.doapi.Util;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.satisfy.brewery.Brewery;
import net.satisfy.brewery.block.*;
import net.satisfy.brewery.block.brewingstation.*;
import net.satisfy.brewery.block.crops.BarleyCropBlock;
import net.satisfy.brewery.block.crops.CornCropBlock;
import net.satisfy.brewery.block.crops.HopsCropBodyBlock;
import net.satisfy.brewery.block.crops.HopsCropHeadBlock;
import net.satisfy.brewery.block.barrel.BigBarrelBlock;
import net.satisfy.brewery.block.barrel.BigBarrelMainHeadBlock;
import net.satisfy.brewery.block.barrel.BigBarrelRightBlock;
import net.satisfy.brewery.block.barrel.BigBarrelRightHeadBlock;
import net.satisfy.brewery.block.property.BrewMaterial;
import net.satisfy.brewery.item.*;
import net.satisfy.brewery.util.BreweryIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Brewery.MOD_ID, Registry.ITEM_REGISTRY);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Brewery.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();
    
    public static final RegistrySupplier<Block> WILD_HOPS = registerWithoutItem("wild_hops", () -> new TallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> HOPS_CROP = registerWithoutItem("hops_crop", () -> new HopsCropHeadBlock(getBushSettings().randomTicks()));
    public static final RegistrySupplier<Block> HOPS_CROP_BODY = registerWithoutItem("hops_crop_body", () -> new HopsCropBodyBlock(getBushSettings().randomTicks()));
    public static final RegistrySupplier<Item> HOPS_SEEDS = registerItem("hops_seeds", () -> new BlockItem(HOPS_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> HOPS = registerItem("hops", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> BARLEY_CROP = registerWithoutItem("barley_crop", () -> new BarleyCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> BARLEY_SEEDS = registerItem("barley_seeds", () -> new BlockItem(BARLEY_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> BARLEY = registerItem("barley", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> CORN_CROP = registerWithoutItem("corn_crop", () -> new CornCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> CORN_SEEDS = registerItem("corn_seeds", () -> new BlockItem(CORN_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> CORN = registerItem("corn", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> DRIED_WHEAT = registerWithItem("dried_wheat", () -> new BagBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistrySupplier<Block> DRIED_BARLEY = registerWithItem("dried_barley", () -> new BagBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistrySupplier<Block> DRIED_CORN = registerWithItem("dried_corn", () -> new BagBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistrySupplier<Block> BENCH = registerWithItem("bench", () -> new BenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> TABLE = registerWithItem("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> PATTERNED_WOOL = registerWithItem("patterned_wool", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL)));
    public static final RegistrySupplier<Block> PATTERNED_CARPET = registerWithItem("patterned_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_CARPET)));
    public static final RegistrySupplier<Block> CABINET = registerWithItem( "cabinet", () -> new StorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> DRAWER = registerWithItem("drawer", () -> new StorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> BAR_COUNTER = registerWithItem("bar_counter", () -> new BarCounterBlock(BlockBehaviour.Properties.of(Material.WOOD).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SIDEBOARD = registerWithItem("sideboard", () -> new SideBoardBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WALL_CABINET = registerWithItem("wall_cabinet", () -> new WallStorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Item> BREATHALYZER = registerItem("breathalyzer", () -> new Breathalyzer(getSettings()));
    public static final RegistrySupplier<Block> WOODEN_BREWINGSTATION = registerWithItem("wooden_brewingstation", () -> new BrewKettleBlock(BrewMaterial.WOOD, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> COPPER_BREWINGSTATION = registerWithItem("copper_brewingstation", () -> new BrewKettleBlock(BrewMaterial.COPPER, BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> NETHERITE_BREWINGSTATION = registerWithItem("netherite_brewingstation", () -> new BrewKettleBlock(BrewMaterial.NETHERITE, BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)));
    public static final RegistrySupplier<Block> BREW_WHISTLE = registerWithoutItem("brew_whistle", () -> new BrewWhistleBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> BREW_OVEN = registerWithoutItem("brew_oven", () -> new BrewOvenBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> BREW_TIMER = registerWithoutItem("brew_timer", () -> new BrewTimerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> SILO_WOOD = registerWithItem("silo_wood", () -> new SiloBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> SILO_COPPER = registerWithItem("silo_copper", () -> new SiloBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> BARREL_MAIN = registerWithItem("barrel_main", () -> new BigBarrelBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BARREL_MAIN_HEAD = registerWithoutItem("barrel_main_head", () -> new BigBarrelMainHeadBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BARREL_RIGHT = registerWithoutItem("barrel_right", () -> new BigBarrelRightBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BARREL_HEAD_RIGHT = registerWithoutItem("barrel_head_right", () -> new BigBarrelRightHeadBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item> ROPE = registerItem("rope", () -> new RopeItem(getSettings()));
    public static final RegistrySupplier<Block> HANGING_ROPE = registerWithoutItem("hanging_rope", () -> new HangingRope(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL)));
    public static final RegistrySupplier<Block> BEER_MUG = registerWithItem("beer_mug", () -> new BeerKegFlowerPotBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<Block> BEER_WHEAT = registerWithItemeverage("beer_wheat", () -> new BeverageBlock(getMugSettings()), MobEffectRegistry.SNOWWHITE.get(), 30 * 20);
    public static final RegistrySupplier<Block> BEER_HOPS = registerWithItemeverage("beer_hops", () -> new BeverageBlock(getMugSettings()), MobEffectRegistry.PARTYSTARTER.get(), 30 * 20);
    public static final RegistrySupplier<Block> BEER_BARLEY = registerWithItemeverage("beer_barley", () -> new BeverageBlock(getMugSettings()), MobEffectRegistry.PINTCHARISMA.get(), 30 * 20);
    public static final RegistrySupplier<Block> BEER_CHORUS = registerWithItemeverage("beer_chorus", () -> new BeverageBlock(getMugSettings()), MobEffects.LEVITATION, 20 * 20);
    public static final RegistrySupplier<Block> WHISKEY_MAGGOALLAN = registerWithItemeverage("whiskey_maggoallan", () -> new BeverageBlock(getBeverageSettings()), MobEffectRegistry.HEALINGTOUCH.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_HADARILABEL = registerWithItemeverage("whiskey_hadarilabel", () -> new BeverageBlock(getBeverageSettings()), MobEffectRegistry.RENEWINGTOUCH.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_LILITUSINGLEMALT = registerWithItemeverage("whiskey_lilitusinglemalt", () -> new BeverageBlock(getBeverageSettings()), MobEffectRegistry.PARTYSTARTER.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_JOJANNIK = registerWithItemeverage("whiskey_jojannik", () -> new BeverageBlock(getBeverageSettings()), MobEffectRegistry.TOXICTOUCH.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_CRISTELWALKER = registerWithItemeverage("whiskey_cristelwalker", () -> new BeverageBlock(getBeverageSettings()), MobEffectRegistry.PROTECTIVETOUCH.get(), 30 * 20);
    public static final RegistrySupplier<Item> PORK_KNUCKLE = registerItem("pork_knuckle", () -> new SaturatedItem(getFoodItemSettings(12, 1.2f, MobEffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> FRIED_CHICKEN = registerItem("fried_chicken", () -> new SaturatedItem(getFoodItemSettings(12, 1.2f, MobEffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> HALF_CHICKEN = registerItem("half_chicken", () -> new SaturatedItem(getFoodItemSettings(8, 1.0f, MobEffectRegistry.SATURATED.get(), 4000)));
    public static final RegistrySupplier<Item> SAUSAGE = registerItem("sausage", () -> new SaturatedItem(getFoodItemSettings(12, 1.2f, MobEffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> MASHED_POTATOES = registerItem("mashed_potatoes", () -> new SaturatedItem(getFoodItemSettings(8, 1.0f, MobEffectRegistry.SATURATED.get(), 4000)));
    public static final RegistrySupplier<Item> POTATO_SALAD = registerItem("potato_salad", () -> new SaturatedItem(getFoodItemSettings(10, 1.2f, MobEffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> DUMPLINGS = registerItem("dumplings", () -> new SaturatedItem(getFoodItemSettings(12, 1.2f, MobEffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> PRETZEL = registerItem("pretzel", () -> new SaturatedItem(getFoodItemSettings(6, 0.7f, MobEffectRegistry.SATURATED.get(), 2000)));
    public static final RegistrySupplier<Block> GINGERBREAD = registerWithItem("gingerbread", () -> new GingerBreadBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistrySupplier<Item> BEER_ELEMENTAL_SPAWN_EGG = registerItem("beer_elemental_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.BEER_ELEMENTAL, -1, -1, getSettings()));
    public static final RegistrySupplier<Item> BREWFEST_HAT = registerItem("brewfest_hat", () -> new BrewfestHatItem(getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> BREWFEST_REGALIA = registerItem("brewfest_regalia", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> BREWFEST_TROUSERS = registerItem("brewfest_trousers", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_LEATHER, EquipmentSlot.LEGS, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BREWFEST_BOOTS = registerItem("brewfest_boots", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_ARMOR, EquipmentSlot.FEET, getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> BREWFEST_HAT_RED = registerItem("brewfest_hat_red", () -> new BrewfestHatRedItem(getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> BREWFEST_DRESS = registerItem("brewfest_dress", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_DRESS, EquipmentSlot.LEGS, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BREWFEST_BLOUSE = registerItem("brewfest_blouse", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_DRESS, EquipmentSlot.CHEST, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> BREWFEST_SHOES = registerItem("brewfest_shoes", () -> new BrewfestArmorItem(ArmorMaterialRegistry.BREWFEST_DRESS, EquipmentSlot.FEET, getSettings().rarity(Rarity.UNCOMMON)));


    //TODO                      **** 1.20.1 ****
    //TODO * conditional recipes -> candlelight, Potato Salad / Dumplings
    //TODO * conditional recipes -> bakery, Pretzel / Gingerbread
    //TODO * Armor Renderer -> Custom 3D Armor für brewfest garb

    public static void init() {
        Brewery.LOGGER.debug("register Mod Block and Items for " + Brewery.MOD_ID);
        ITEMS.register();
        BLOCKS.register();
        createStandards();
    }

    public static final List<Supplier<Block>> STANDARD_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_WALL_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_FLOOR_BLOCKS = Lists.newArrayList();
    public static Supplier<Block> BEER_STANDARD;
    
    private static void createStandards() {
        BEER_STANDARD = BLOCKS.register(Brewery.MOD_ID("beer_standard"), () -> new BeerStandardBlock(properties(Material.WOOD, 1F).noCollission().sound(SoundType.WOOD)));
        Supplier<Block> adjWall = BLOCKS.register(Brewery.MOD_ID("beer_wall_standard"), () -> new BeerStandardWallBlock(properties(Material.WOOD, 1F).noCollission().sound(SoundType.WOOD).dropsLike(BEER_STANDARD.get())));

        ITEMS.register(Brewery.MOD_ID("beer_standard"), () -> new StandingAndWallBlockItem(BEER_STANDARD.get(), adjWall.get(), new Item.Properties().tab(Brewery.CREATIVE_TAB).stacksTo(16).rarity(Rarity.UNCOMMON)));
        STANDARD_BLOCKS.add(BEER_STANDARD);
        STANDARD_BLOCKS.add(adjWall);
    }

    public static void commonInit() {
        FuelRegistry.register(300, BEER_STANDARD.get(), BEER_MUG.get(), BENCH.get(), TABLE.get(), BAR_COUNTER.get(), WOODEN_BREWINGSTATION.get());
        FuelRegistry.register(100, CORN.get(), BARLEY.get(), HOPS.get());
        FuelRegistry.register(75, PATTERNED_WOOL.get(), PATTERNED_CARPET.get());
        FuelRegistry.register(50, BREWFEST_BOOTS.get(), BREWFEST_HAT.get(), BREWFEST_DRESS.get(), BREWFEST_REGALIA.get(), BREWFEST_TROUSERS.get());
    }

    public static final Map<Supplier<Item>, RegistrySupplier<Item>> SEEDCONVERSION = new HashMap<>();

    static {
        SEEDCONVERSION.put(() -> Items.WHEAT_SEEDS, ObjectRegistry.CORN_SEEDS);
        SEEDCONVERSION.put(() -> Items.BEETROOT_SEEDS, ObjectRegistry.BARLEY_SEEDS);
        SEEDCONVERSION.put(() -> Items.COOKED_CHICKEN, ObjectRegistry.HALF_CHICKEN);
        SEEDCONVERSION.put(() -> Items.BAKED_POTATO, ObjectRegistry.MASHED_POTATOES);
    }

    public static BlockBehaviour.Properties properties(Material material, float hardness) {
        return BlockBehaviour.Properties.of(material).strength(hardness, hardness);
    }

    private static Item.Properties getSettingsWithoutTab(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getSettingsWithoutTab() {
        return getSettingsWithoutTab(settings -> {
        });
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties().tab(Brewery.CREATIVE_TAB);
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration) {
        return getFoodItemSettings(nutrition, saturationMod, effect, duration, false, false);
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationMod, effect, duration, alwaysEat, fast));
    }

    private static BlockBehaviour.Properties getBeverageSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    private static BlockBehaviour.Properties getMugSettings() {
        return BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).noOcclusion().instabreak();
    }


    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }

    private static FoodProperties createFood(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
        if (alwaysEat) food.alwaysEat();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(effect, duration), 1.0f);
        return food.build();
    }


    private static <T extends Block> RegistrySupplier<T> registerWithItemeverage(String name, Supplier<T> block, MobEffect effect, int duration) {
        RegistrySupplier<T> toReturn = registerWithoutItem(name, block);
        registerItem(name, () -> new DrinkBlockItem(effect, duration, toReturn.get(), getSettings(settings -> settings.food(beverageFoodComponent()))));
        return toReturn;
    }

    private static FoodProperties beverageFoodComponent() {
        FoodProperties.Builder component = new FoodProperties.Builder().nutrition(2).saturationMod(1);
        return component.build();
    }


    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return registerWithItem(name, block, Brewery.CREATIVE_TAB);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new BreweryIdentifier(name), block, tab);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new BreweryIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new BreweryIdentifier(path), itemSupplier);
    }
}