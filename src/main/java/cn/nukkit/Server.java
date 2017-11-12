package cn.nukkit;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.common.base.Preconditions;

import cn.nukkit.ai.AI;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityBanner;
import cn.nukkit.blockentity.BlockEntityBeacon;
import cn.nukkit.blockentity.BlockEntityBrewingStand;
import cn.nukkit.blockentity.BlockEntityCauldron;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.blockentity.BlockEntityCommandBlock;
import cn.nukkit.blockentity.BlockEntityDispenser;
import cn.nukkit.blockentity.BlockEntityDropper;
import cn.nukkit.blockentity.BlockEntityEnchantTable;
import cn.nukkit.blockentity.BlockEntityEnderChest;
import cn.nukkit.blockentity.BlockEntityFlowerPot;
import cn.nukkit.blockentity.BlockEntityFurnace;
import cn.nukkit.blockentity.BlockEntityItemFrame;
import cn.nukkit.blockentity.BlockEntityMobSpawner;
import cn.nukkit.blockentity.BlockEntityShulkerBox;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.blockentity.BlockEntitySkull;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandReader;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.entity.item.EntityArmorStand;
import cn.nukkit.entity.item.EntityBoat;
import cn.nukkit.entity.item.EntityEnderCrystal;
import cn.nukkit.entity.item.EntityFallingBlock;
import cn.nukkit.entity.item.EntityMinecartChest;
import cn.nukkit.entity.item.EntityMinecartEmpty;
import cn.nukkit.entity.item.EntityMinecartHopper;
import cn.nukkit.entity.item.EntityMinecartTNT;
import cn.nukkit.entity.item.EntityPainting;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.entity.item.EntityXPOrb;
import cn.nukkit.entity.mob.EntityBlaze;
import cn.nukkit.entity.mob.EntityCaveSpider;
import cn.nukkit.entity.mob.EntityCreeper;
import cn.nukkit.entity.mob.EntityElderGuardian;
import cn.nukkit.entity.mob.EntityEnderDragon;
import cn.nukkit.entity.mob.EntityEnderman;
import cn.nukkit.entity.mob.EntityEndermite;
import cn.nukkit.entity.mob.EntityEvoker;
import cn.nukkit.entity.mob.EntityGhast;
import cn.nukkit.entity.mob.EntityGuardian;
import cn.nukkit.entity.mob.EntityHask;
import cn.nukkit.entity.mob.EntityMagmaCube;
import cn.nukkit.entity.mob.EntityShulker;
import cn.nukkit.entity.mob.EntitySilverfish;
import cn.nukkit.entity.mob.EntitySkeleton;
import cn.nukkit.entity.mob.EntitySlime;
import cn.nukkit.entity.mob.EntitySpider;
import cn.nukkit.entity.mob.EntityStray;
import cn.nukkit.entity.mob.EntityVex;
import cn.nukkit.entity.mob.EntityVindicator;
import cn.nukkit.entity.mob.EntityWitch;
import cn.nukkit.entity.mob.EntityWither;
import cn.nukkit.entity.mob.EntityWitherSkeleton;
import cn.nukkit.entity.mob.EntityZombie;
import cn.nukkit.entity.mob.EntityZombiePigman;
import cn.nukkit.entity.mob.EntityZombieVillager;
import cn.nukkit.entity.passive.EntityBat;
import cn.nukkit.entity.passive.EntityChicken;
import cn.nukkit.entity.passive.EntityCow;
import cn.nukkit.entity.passive.EntityDonkey;
import cn.nukkit.entity.passive.EntityHorse;
import cn.nukkit.entity.passive.EntityIronGolem;
import cn.nukkit.entity.passive.EntityLlama;
import cn.nukkit.entity.passive.EntityMooshroom;
import cn.nukkit.entity.passive.EntityMule;
import cn.nukkit.entity.passive.EntityOcelot;
import cn.nukkit.entity.passive.EntityParrot;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.entity.passive.EntityPolarBear;
import cn.nukkit.entity.passive.EntityRabbit;
import cn.nukkit.entity.passive.EntitySheep;
import cn.nukkit.entity.passive.EntitySkeletonHorse;
import cn.nukkit.entity.passive.EntitySnowGolem;
import cn.nukkit.entity.passive.EntitySquid;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.entity.passive.EntityWolf;
import cn.nukkit.entity.passive.EntityZombieHorse;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.entity.projectile.EntityDragonFireball;
import cn.nukkit.entity.projectile.EntityEgg;
import cn.nukkit.entity.projectile.EntityEnderPearl;
import cn.nukkit.entity.projectile.EntityExpBottle;
import cn.nukkit.entity.projectile.EntityFireball;
import cn.nukkit.entity.projectile.EntityFireworkRocket;
import cn.nukkit.entity.projectile.EntityFishingHook;
import cn.nukkit.entity.projectile.EntityPotion;
import cn.nukkit.entity.projectile.EntityPotionLingering;
import cn.nukkit.entity.projectile.EntityShulkerBullet;
import cn.nukkit.entity.projectile.EntitySnowball;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.level.LevelInitEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.server.QueryRegenerateEvent;
import cn.nukkit.event.server.TrayIconClickEvent;
import cn.nukkit.inventory.CraftingManager;
import cn.nukkit.inventory.FurnaceRecipe;
import cn.nukkit.inventory.Recipe;
import cn.nukkit.inventory.ShapedRecipe;
import cn.nukkit.inventory.ShapelessRecipe;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.lang.BaseLang;
import cn.nukkit.lang.TextContainer;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.LevelProviderManager;
import cn.nukkit.level.format.anvil.Anvil;
import cn.nukkit.level.format.leveldb.LevelDB;
import cn.nukkit.level.format.mcregion.McRegion;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.Nether;
import cn.nukkit.level.generator.Normal;
import cn.nukkit.level.generator.biome.Biome;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.metadata.EntityMetadataStore;
import cn.nukkit.metadata.LevelMetadataStore;
import cn.nukkit.metadata.PlayerMetadataStore;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.CompressBatchedTask;
import cn.nukkit.network.Network;
import cn.nukkit.network.RakNetInterface;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.protocol.BatchPacket;
import cn.nukkit.network.protocol.CraftingDataPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.PlayerListPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.query.QueryHandler;
import cn.nukkit.network.rcon.RCON;
import cn.nukkit.permission.BanEntry;
import cn.nukkit.permission.BanList;
import cn.nukkit.permission.DefaultPermissions;
import cn.nukkit.permission.Permissible;
import cn.nukkit.plugin.JavaPluginLoader;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginCompiler;
import cn.nukkit.plugin.PluginLoadOrder;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.plugin.service.NKServiceManager;
import cn.nukkit.plugin.service.ServiceManager;
import cn.nukkit.potion.Effect;
import cn.nukkit.potion.Potion;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.nukkit.scheduler.FileWriteTask;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.FastAppender;
import cn.nukkit.utils.LevelException;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.ServerException;
import cn.nukkit.utils.ServerKiller;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.nukkit.utils.Zlib;
import cn.nukkit.window.ServerSettingsWindow;
import co.aikar.timings.Timings;

/**
 * @author MagicDroidX
 * @author Box
 */
public class Server implements ActionListener{

    public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "nukkit.broadcast.admin";
    public static final String BROADCAST_CHANNEL_USERS = "nukkit.broadcast.user";

    private static Server instance = null;

    private AI ai = null;

    private BanList banByName = null;

    private BanList banByIP = null;

    private Config operators = null;

    private Config whitelist = null;

    private boolean isRunning = true;

    private boolean hasStopped = false;

    private PluginManager pluginManager = null;

    private int profilingTickrate = 20;


    private ServerScheduler scheduler = null;

    private int tickCounter;

    private long nextTick;

    private final float[] tickAverage = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};

    private final float[] useAverage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private float maxTick = 20;

    private float maxUse = 0;

    private int sendUsageTicker = 0;

    private boolean dispatchSignals = false;

    private final MainLogger logger;

    private final CommandReader console;

    private SimpleCommandMap commandMap;

    private CraftingManager craftingManager;

    private ResourcePackManager resourcePackManager;

    private ConsoleCommandSender consoleSender;

    private int maxPlayers;

    private boolean autoSave;

    private RCON rcon;

    private EntityMetadataStore entityMetadata;

    private PlayerMetadataStore playerMetadata;

    private LevelMetadataStore levelMetadata;

    private Network network;

    private boolean networkCompressionAsync = true;
    public int networkCompressionLevel = 7;

    private boolean autoTickRate = true;
    private int autoTickRateLimit = 20;
    private boolean alwaysTickPlayers = false;
    private int baseTickRate = 1;
    private Boolean getAllowFlight = null;

    private int autoSaveTicker = 0;
    private int autoSaveTicks = 6000;

    private BaseLang baseLang;

    private boolean forceLanguage = false;

    private UUID serverID;

    private final String filePath;
    private final String dataPath;
    private final String pluginPath;
    private String defaultplugin = null;

    private final Set<UUID> uniquePlayers = new HashSet<>();

    private QueryHandler queryHandler;

    private QueryRegenerateEvent queryRegenerateEvent;

    private Config properties;
    private Config config;

    private final Map<String, Player> players = new HashMap<>();

    private final Map<UUID, Player> playerList = new HashMap<>();

    private final Map<Integer, String> identifier = new HashMap<>();

    private final Map<Integer, Level> levels = new HashMap<>();

    private final ServiceManager serviceManager = new NKServiceManager();

    private Level defaultLevel = null;

    private Thread currentThread;
    private Map<String, Object> jupiterconfig;
    private List<Player> loggedInPlayers = new ArrayList<>();

    private LinkedHashMap<Integer, ServerSettingsWindow> defaultServerSettings = new LinkedHashMap<>();

    private boolean printPackets = false;

    @SuppressWarnings("unchecked")
    Server(MainLogger logger, final String filePath, String dataPath, String pluginPath) {
        Preconditions.checkState(instance == null, "Already initialized!");
        currentThread = Thread.currentThread(); // Saves the current thread instance as a reference, used in Server#isPrimaryThread()
        instance = this;
        this.logger = logger;

        this.console = new CommandReader();

        this.console.start();

        this.logger.info("");
        this.logger.info(FastAppender.get(TextFormat.BLUE, "Jupiter", TextFormat.WHITE, " by JupiterDevelopmentTeam"));
        this.logger.info("");
        this.logger.info("");

        this.filePath = filePath;
        if (!new File(dataPath + "worlds/").exists()) {
            new File(dataPath + "worlds/").mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, dataPath, "worlds/  を作成しました。"));
        }

        if (!new File(dataPath + "players/").exists()) {
            new File(dataPath + "players/").mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, dataPath, "players/  を作成しました。"));
        }

        if (!new File(pluginPath).exists()) {
            new File(pluginPath).mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, dataPath, "plugins/  を作成しました。"));
        }

        if (!new File(pluginPath).exists()) {
            new File(pluginPath).mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, pluginPath, "  を作成しました。"));
        }

        if (!new File(dataPath + "unpackedPlugins/").exists()) {
            new File(dataPath + "unpackedPlugins/").mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, pluginPath, "unpackedPlugins/  を作成しました。"));
        }

        if (!new File(dataPath + "compileOrder/").exists()) {
            new File(dataPath + "compileOrder/").mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, pluginPath, "compileOrder/  を作成しました。"));
        }

        if (!new File(dataPath + "makeOrder/").exists()) {
            new File(dataPath + "makeOrder/").mkdirs();
            this.logger.info(FastAppender.get(TextFormat.AQUA, pluginPath, "makeOrder/  を作成しました。"));
        }

        this.dataPath = new File(dataPath).getAbsolutePath() + "/";

        this.pluginPath = new File(pluginPath).getAbsolutePath() + "/";

        if (!new File(this.dataPath + "nukkit.yml").exists()) {
            this.getLogger().info(FastAppender.get(TextFormat.GREEN, "ようこそ。言語を選択してください。"));
            try {
                String[] lines = Utils.readFile(this.getClass().getClassLoader().getResourceAsStream("lang/language.list")).split("\n");
                for (String line : lines) {
                    this.logger.info(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String fallback = BaseLang.FALLBACK_LANGUAGE;
            String language = null;
            while (language == null) {
                String lang = this.console.readLine();
                InputStream conf = this.getClass().getClassLoader().getResourceAsStream("lang/" + lang + "/lang.ini");
                if (conf != null) {
                    language = lang;
                }
            }

            InputStream advacedConf = this.getClass().getClassLoader().getResourceAsStream("lang/" + language + "/nukkit.yml");
            if (advacedConf == null) {
                advacedConf = this.getClass().getClassLoader().getResourceAsStream("lang/" + fallback + "/nukkit.yml");
            }

            try {
                Utils.writeFile(this.dataPath + "nukkit.yml", advacedConf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


        this.logger.info(FastAppender.get(TextFormat.GREEN, "nukkit.yml", TextFormat.WHITE, "を読み込んでいます..."));
        this.config = new Config(this.dataPath + "nukkit.yml", Config.YAML);

        this.logger.info(FastAppender.get(TextFormat.GREEN, "server.properties", TextFormat.WHITE, "を読み込んでいます..."));
        this.properties = new Config(this.dataPath + "server.properties", Config.PROPERTIES, new ConfigSection() {
            {
                put("motd", "Jupiter Server For Minecraft: BE");
                put("sub-motd", "Powered by Jupiter");
                put("server-port", 19132);
                put("server-ip", "0.0.0.0");
                put("view-distance", 10);
                put("white-list", false);
                put("achievements", true);
                put("announce-player-achievements", true);
                put("spawn-protection", 16);
                put("max-players", 20);
                put("allow-flight", false);
                put("spawn-animals", true);
                put("spawn-mobs", true);
                put("gamemode", 0);
                put("force-gamemode", false);
                put("hardcore", false);
                put("pvp", true);
                put("difficulty", 1);
                put("generator-settings", "");
                put("level-name", "world");
                put("level-seed", "");
                put("level-type", "DEFAULT");
                put("enable-query", true);
                put("enable-rcon", false);
                put("rcon.password", Base64.getEncoder().encodeToString(UUID.randomUUID().toString().replace("-", "").getBytes()).substring(3, 13));
                put("auto-save", true);
                put("force-resources", false);
            }
        });

        this.logger.info(FastAppender.get(TextFormat.GREEN, "jupiter.yml", TextFormat.WHITE, "を読み込んでいます..."));

        if (!new File(this.dataPath + "jupiter.yml").exists()) {
            BufferedInputStream advacedConf = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("lang/jpn/jupiter.yml"));
            try {
                Utils.writeFile(this.dataPath + "jupiter.yml", advacedConf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.loadJupiterConfig();

        /*
        if(this.getJupiterConfigBoolean("destroy-block-particle")){
            Level.sendDestroyParticle = true;
        }else{
            Level.sendDestroyParticle = false;
        }
        */

        this.forceLanguage = (Boolean) this.getConfig("settings.force-language", false);
        this.baseLang = new BaseLang((String) this.getConfig("settings.language", BaseLang.FALLBACK_LANGUAGE));
        this.logger.info(this.getLanguage().translateString("language.selected", new String[]{getLanguage().getName(), getLanguage().getLang()}));
        this.logger.info(this.getLanguage().translateString("nukkit.server.start", FastAppender.get(TextFormat.AQUA, this.getVersion(), TextFormat.WHITE)));

        Object poolSize = this.getConfig("settings.async-workers", "auto");
        if (!(poolSize instanceof Integer)) {
            try {
                poolSize = Integer.valueOf((String) poolSize);
            } catch (Exception e) {
                poolSize = Math.max(Runtime.getRuntime().availableProcessors() + 1, 4);
            }
        }

        ServerScheduler.WORKERS = (int) poolSize;

        this.networkCompressionLevel = (int) this.getConfig("network.compression-level", 7);
        this.networkCompressionAsync = (boolean) this.getConfig("network.async-compression", true);

        this.networkCompressionLevel = (int) this.getConfig("network.compression-level", 7);
        this.networkCompressionAsync = (boolean) this.getConfig("network.async-compression", true);

        this.autoTickRate = (boolean) this.getConfig("level-settings.auto-tick-rate", true);
        this.autoTickRateLimit = (int) this.getConfig("level-settings.auto-tick-rate-limit", 20);
        this.alwaysTickPlayers = (boolean) this.getConfig("level-settings.always-tick-players", false);
        this.baseTickRate = (int) this.getConfig("level-settings.base-tick-rate", 1);

        this.scheduler = new ServerScheduler();

        if (this.getPropertyBoolean("enable-rcon", false)) {
            this.rcon = new RCON(this, this.getPropertyString("rcon.password", ""), (!this.getIp().equals("")) ? this.getIp() : "0.0.0.0", this.getPropertyInt("rcon.port", this.getPort()));
        }

        this.entityMetadata = new EntityMetadataStore();
        this.playerMetadata = new PlayerMetadataStore();
        this.levelMetadata = new LevelMetadataStore();

        this.operators = new Config(this.dataPath + "ops.txt", Config.ENUM);
        this.whitelist = new Config(this.dataPath + "white-list.txt", Config.ENUM);
        this.banByName = new BanList(this.dataPath + "banned-players.json");
        this.banByName.load();
        this.banByIP = new BanList(this.dataPath + "banned-ips.json");
        this.banByIP.load();

        this.maxPlayers = this.getPropertyInt("max-players", 20);
        this.setAutoSave(this.getPropertyBoolean("auto-save", true));

        if (this.getPropertyBoolean("hardcore", false) && this.getDifficulty() < 3) {
            this.setPropertyInt("difficulty", 3);
        }

        Nukkit.DEBUG = (int) this.getConfig("debug.level", 1);
        if (this.logger instanceof MainLogger) {
            this.logger.setLogDebug(Nukkit.DEBUG > 1);
        }

        this.logger.info(this.getLanguage().translateString("nukkit.server.networkStart", new String[]{this.getIp().equals("") ? "*" : this.getIp(), String.valueOf(this.getPort())}));
        this.serverID = UUID.randomUUID();

        this.network = new Network(this);
        this.network.setName(this.getMotd());
        this.network.setSubName(this.getSubMotd());

        this.logger.info(this.getLanguage().translateString("nukkit.server.license", this.getName()));


        this.consoleSender = new ConsoleCommandSender();
        this.commandMap = new SimpleCommandMap(this);

        this.registerEntities();
        this.registerBlockEntities();

        Block.init();
        Enchantment.init();
        Item.init();
        Biome.init();
        Effect.init();
        Potion.init();
        Attribute.init();

        /* TODO AI
        this.ai = new AI(this);
        ai.initAI();
        */

        this.craftingManager = new CraftingManager();

        this.resourcePackManager = new ResourcePackManager(new File(Nukkit.DATA_PATH, "resource_packs"));

        this.pluginManager = new PluginManager(this, this.commandMap);
        this.pluginManager.subscribeToPermission(Server.BROADCAST_CHANNEL_ADMINISTRATIVE, this.consoleSender);

        this.pluginManager.registerInterface(JavaPluginLoader.class);

        this.queryRegenerateEvent = new QueryRegenerateEvent(this, 5);

        this.network.registerInterface(new RakNetInterface(this));

        this.printPackets = this.getJupiterConfigBoolean("print-packets");

        if(this.checkingUsingGUI()){
            try {
                this.loadTrayIcon();
            } catch (AWTException e) {
                this.getLogger().critical("TrayIconを実行できませんでした！");
            }
        }

        Calendar now = Calendar.getInstance();

        int y = now.get(Calendar.YEAR);
        int mo = now.get(Calendar.MONTH) + 1;
        int d = now.get(Calendar.DATE);
        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);
        int s = now.get(Calendar.SECOND);

        this.logger.info("");
        this.logger.info(FastAppender.get("日時: \t\t\t", TextFormat.BLUE, y, "/", mo, "/", d, " ", h, "時", m, "分", s, "秒"));
        this.logger.info(FastAppender.get("サーバー名: \t\t", TextFormat.GREEN, this.getMotd()));
        this.logger.info(FastAppender.get("IP: \t\t\t", TextFormat.GREEN, this.getIp()));
        this.logger.info(FastAppender.get("ポート: \t\t", TextFormat.GREEN, this.getPort()));
        this.logger.info(FastAppender.get("Jupiterバージョン: \t", TextFormat.GREEN, this.getJupiterVersion()));
        this.logger.info(FastAppender.get("Nukkitバージョン: \t", TextFormat.GREEN, this.getNukkitVersion()));
        this.logger.info(FastAppender.get("APIバージョン: \t", TextFormat.GREEN, this.getApiVersion()));
        this.logger.info(FastAppender.get("コードネーム: \t\t", TextFormat.GREEN, this.getCodename()));
        this.logger.info("");

        if(this.getJupiterConfigBoolean("jupiter-compiler-mode")){
            this.logger.info(FastAppender.get(TextFormat.AQUA, "コンパイルしています..."));
            File f = new File(dataPath + "compileOrder/");
            File[] list = f.listFiles();
            int len = list.length;
            for(int i = 0; i < len; i++){
                if(new PluginCompiler().Compile(list[i]))
                    this.logger.info(FastAppender.get(list[i].toPath().toString(), " :", TextFormat.GREEN, "完了"));
                else
                    this.logger.info(FastAppender.get(list[i].toPath().toString(), " :", TextFormat.RED, "失敗"));
            }
            this.logger.info("");
        }

        this.logger.info(FastAppender.get(TextFormat.AQUA, "プラグインを読み込んでいます..."));
        this.pluginManager.loadPlugins(this.pluginPath);

        this.enablePlugins(PluginLoadOrder.STARTUP);

        this.logger.info("");

        LevelProviderManager.addProvider(this, Anvil.class);
        LevelProviderManager.addProvider(this, McRegion.class);
        LevelProviderManager.addProvider(this, LevelDB.class);

        Generator.addGenerator(Flat.class, "flat", Generator.TYPE_FLAT);
        Generator.addGenerator(Normal.class, "normal", Generator.TYPE_INFINITE);
        Generator.addGenerator(Normal.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(Nether.class, "nether", Generator.TYPE_NETHER);
        //todo: add old generator and hell generator

        for (String name : ((Map<String, Object>) this.getConfig("worlds", new HashMap<>())).keySet()) {
            if (!this.loadLevel(name)) {
                long seed;
                try {
                    seed = ((Integer) this.getConfig(FastAppender.get("worlds.", name, ".seed"))).longValue();
                } catch (Exception e) {
                    seed = System.currentTimeMillis();
                }

                Map<String, Object> options = new HashMap<>();
                String[] opts = ((String) this.getConfig(FastAppender.get("worlds.", name, ".generator"), Generator.getGenerator("default").getSimpleName())).split(":");
                Class<? extends Generator> generator = Generator.getGenerator(opts[0]);
                int len = opts.length;
                if (len > 1) {
                    String preset = "";
                    for (int i = 1; i < len; i++) {
                        preset += opts[i] + ":";
                    }
                    preset = preset.substring(0, preset.length() - 1);

                    options.put("preset", preset);
                }

                this.generateLevel(name, seed, generator, options);
            }
        }

        try {

            this.getDefaultLevel().getName();

        }catch (NullPointerException ex) {
            String defaultName = this.getPropertyString("level-name", "world");
            if (defaultName == null || "".equals(defaultName.trim())) {
                this.logger.warning("level-name cannot be null, using default");
                defaultName = "world";
                this.setPropertyString("level-name", defaultName);
            }

            if (!this.loadLevel(defaultName)) {
                long seed;
                String seedString = String.valueOf(this.getProperty("level-seed", System.currentTimeMillis()));
                try {
                    seed = Long.valueOf(seedString);
                } catch (NumberFormatException e) {
                    seed = seedString.hashCode();
                }
                this.generateLevel(defaultName, seed == 0 ? System.currentTimeMillis() : seed);
            }

            this.setDefaultLevel(this.getLevelByName(defaultName));
        }

        this.properties.save(true);
        try{

            this.getDefaultLevel().getName();

        }catch (NullPointerException e) {
            this.logger.emergency(this.getLanguage().translateString("nukkit.level.defaultError"));
            this.forceShutdown();

            return;
        }

        if ((int) this.getConfig("ticks-per.autosave", 6000) > 0) {
            this.autoSaveTicks = (int) this.getConfig("ticks-per.autosave", 6000);
        }

        this.logger.info("");
        this.enablePlugins(PluginLoadOrder.POSTWORLD);
        this.logger.info("");

        this.start();
    }

    public TrayIcon getTrayIcon(){
        if(!this.checkingUsingGUI()) return null;
        TrayIcon icon = null;
        try {
            icon = new TrayIcon(ImageIO.read(new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream("images/Jupiter.png"))));
        } catch (IOException e) {
            this.getLogger().critical("ソースにTrayイメージを確認できませんでした！");
        } catch (UnsupportedOperationException e) {
            this.getLogger().notice("Trayに対応していないOSの為、表示しませんでした。(エラーではありません)");
        }
        return icon;
    }

    public void trayMessage(String message){
        if(!this.checkingUsingGUI() || this.getTrayIcon() == null) return;
        this.trayMessage(message, MessageType.INFO);
    }

    public void trayMessage(String message, MessageType type){
        if(!this.checkingUsingGUI() || this.getTrayIcon() == null) return;
        this.trayMessage("Jupiter", message, type);
    }

    public void trayMessage(String title, String message, MessageType type){
        if(!this.checkingUsingGUI() || this.getTrayIcon() == null) return;
        if (this.getTrayIcon() != null){
            this.getTrayIcon().displayMessage(title, message, type);
        }
    }

    private void loadTrayIcon() throws AWTException{
        if(!this.checkingUsingGUI()) return;
        SystemTray.getSystemTray().remove(getTrayIcon());

        TrayIcon icon = this.getTrayIcon();
        if (icon == null) {
            return;
        }

        icon.removeActionListener(this);
        icon.addActionListener(this);

        SystemTray.getSystemTray().add(icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TrayIconClickEvent event = new TrayIconClickEvent(getTrayIcon());
        getPluginManager().callEvent(event);
        if(event.isCancelled()){
            return;
        }
        trayMessage(FastAppender.get("参加人数:", getOnlinePlayers().size(), "/", getPropertyInt("max-players")));
    }

    /**
     * サーバーにいる人全員にメッセージを送ります。
     * <br>ミュート状態では表示されません。
     * (ミュート状態...isMuted()の戻り値)
     * @see "ミュート状態でも表示したい場合"
     * @see Player#sendImportantMessage(String) sendImportantMessage
     * @see Player#isMuted() isMuted()
     * @param message 送る文
     * @return int
     */
    public int broadcastMessage(String message) {
        return this.broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * サーバーにいる人全員にメッセージを送ります。
     * <br>ミュート状態では表示されません。
     * (ミュート状態...isMuted()の戻り値)
     * @see "ミュート状態でも表示したい場合"
     * @see Player#sendImportantMessage(String) sendImportantMessage
     * @see Player#isMuted() isMuted()
     * @param message 送る文
     * @return int
     */
    public int broadcastMessage(TextContainer message) {
        return this.broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    public int broadcastMessage(String message, CommandSender[] recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.length;
    }

    public int broadcastMessage(String message, Collection<CommandSender> recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    public int broadcastMessage(TextContainer message, Collection<CommandSender> recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    /**
     * サーバーにいる人全員にポップアップを送ります。
     * <br>ミュート状態では表示されません。
     * (ミュート状態...isMuted()の戻り値)
     * @param message 送る文
     * @return int
     */
    public int broadcastPopup(String message) {
        return this.broadcastPopup(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * サーバーにいる人全員にチップを送ります。
     * <br>ミュート状態では表示されません。
     * @param message 送る文
     * @return int
     */
    public int broadcastTip(String message) {
        return this.broadcastPopup(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * サーバーにいる人全員にタイトルを送ります。
     * <br>ミュート状態では表示されません。
     * @param message 送るタイトル
     * @return int
     */
    public int broadcastTitle(String message) {
        return this.broadcastTitle(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * サーバーにいる人全員にメサブタイトルを送ります。
     * <br>ミュート状態では表示されません。
     * @param message 送る文
     * @return int
     */
    public int broadcastSubtitle(String message) {
        return this.broadcastSubtitle(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * サーバーにいる人全員にメッセージを送ります。
     * <br>ミュート状態でも表示されます。
     * (ミュート状態...isMuted()の戻り値)
     * @see Player#isMuted() isMuted()
     * @param message 送る文
     * @return int
     * @author Itsu
     */
    public int broadcastImportantMessage(String message) {
        return this.broadcastImportantMessage(message, BROADCAST_CHANNEL_USERS);
    }



    public int broadcast(String message, String permissions) {
        Set<CommandSender> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                    recipients.add((CommandSender) permissible);
                }
            }
        }

        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    public int broadcast(TextContainer message, String permissions) {
        Set<CommandSender> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                    recipients.add((CommandSender) permissible);
                }
            }
        }

        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    public int broadcastPopup(String message, String permissions) {
        Set<Player> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof Player && permissible.hasPermission(permission)) {
                    recipients.add((Player) permissible);
                }
            }
        }

        for (Player recipient : recipients) {
            recipient.sendPopup(message);
        }

        return recipients.size();
    }

    public int broadcastTip(String message, String permissions) {
        Set<Player> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof Player && permissible.hasPermission(permission)) {
                    recipients.add((Player) permissible);
                }
            }
        }

        for (Player recipient : recipients) {
            recipient.sendTip(message);
        }

        return recipients.size();
    }

    public int broadcastTitle(String message, String permissions) {
        Set<Player> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof Player && permissible.hasPermission(permission)) {
                    recipients.add((Player) permissible);
                }
            }
        }

        for (Player recipient : recipients) {
            recipient.sendTitle(message);
        }

        return recipients.size();
    }

    public int broadcastSubtitle(String message, String permissions) {
        Set<Player> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof Player && permissible.hasPermission(permission)) {
                    recipients.add((Player) permissible);
                }
            }
        }

        for (Player recipient : recipients) {
            recipient.setSubtitle(message);
        }

        return recipients.size();
    }

    public int broadcastImportantMessage(String message, String permissions) {
        Set<CommandSender> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                    recipients.add((CommandSender) permissible);
                }
            }
        }

        for (CommandSender recipient : recipients) {
            recipient.sendImportantMessage(message);
        }

        return recipients.size();
    }

    /**
     * サーバーにいる人全員にパケットを送ります。
     * @param players プレイヤー
     * @param packet 送るパケット
     * @return void
     */
    public static void broadcastPacket(Collection<Player> players, DataPacket packet) {
        broadcastPacket(players.stream().toArray(Player[]::new), packet);
    }

    /**
     * サーバーにいる人全員にパケットを送ります。
     * @param players プレイヤー
     * @param packet 送るパケット
     * @return void
     */
    public static void broadcastPacket(Player[] players, DataPacket packet) {
        packet.encode();
        packet.isEncoded = true;

        for (Player player : players) {
            player.dataPacket(packet);
        }

        if (packet.encapsulatedPacket != null) {
            packet.encapsulatedPacket = null;
        }
    }

    public void batchPackets(Player[] players, DataPacket[] packets) {
        this.batchPackets(players, packets, false);
    }

    public void batchPackets(Player[] players, DataPacket[] packets, boolean forceSync) {
        if (players == null || packets == null || players.length == 0 || packets.length == 0) {
            return;
        }

        Timings.playerNetworkSendTimer.startTiming();
        byte[][] payload = new byte[packets.length * 2][];
        for (int i = 0; i < packets.length; i++) {
            DataPacket p = packets[i];
            if (!p.isEncoded) {
                p.encode();
            }
            byte[] buf = p.getBuffer();
            payload[i * 2] = Binary.writeUnsignedVarInt(buf.length);
            payload[i * 2 + 1] = buf;
        }
        byte[] data;
        data = Binary.appendBytes(payload);

        List<String> targets = new ArrayList<>();
        for (Player p : players) {
            if (p.isConnected()) {
                targets.add(this.identifier.get(p.rawHashCode()));
            }
        }

        if (!forceSync && this.networkCompressionAsync) {
            this.getScheduler().scheduleAsyncTask(new CompressBatchedTask(data, targets, this.networkCompressionLevel));
        } else {
            try {
                this.broadcastPacketsCallback(Zlib.deflate(data, this.networkCompressionLevel), targets);
                //this.broadcastPacketsCallback(data, targets);  非圧縮
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Timings.playerNetworkSendTimer.stopTiming();
    }

    public void broadcastPacketsCallback(byte[] data, List<String> identifiers) {
        BatchPacket pk = new BatchPacket();
        pk.payload = data;

        for (String i : identifiers) {
            if (this.players.containsKey(i)) {
                this.players.get(i).dataPacket(pk);
            }
        }
    }

    public void enablePlugins(PluginLoadOrder type) {
        for (Plugin plugin : new ArrayList<>(this.pluginManager.getPlugins().values())) {
            if (!plugin.isEnabled() && type == plugin.getDescription().getOrder()) {
                this.enablePlugin(plugin);
            }
        }

        if (type == PluginLoadOrder.POSTWORLD) {
            this.commandMap.registerServerAliases();
            DefaultPermissions.registerCorePermissions();
        }
    }

    /**
     * 引数で指定したプラグインを有効にします。
     * @param plugin 有効にするプラグイン
     */
    public void enablePlugin(Plugin plugin) {
        this.pluginManager.enablePlugin(plugin);
    }

    /**
     * 全てのプラグインを無効にします。
     */
    public void disablePlugins() {
        this.pluginManager.disablePlugins();
    }

    /**
     * コマンドを実行します。
     * @param sender 対象のCommandSender
     * @param commandLine 送るパケット
     * @return boolean trueが完了/falseが失敗
     */
    public boolean dispatchCommand(CommandSender sender, String commandLine) throws ServerException {
        // First we need to check if this command is on the main thread or not, if not, warn the user
        if (!this.isPrimaryThread()) {
            getLogger().warning("Command Dispatched Async: " + commandLine);
            getLogger().warning("Please notify author of plugin causing this execution to fix this bug!", new Throwable());
            // TODO: We should sync the command to the main thread too!
        }
        if (sender == null) {
            throw new ServerException("CommandSender is not valid");
        }

        if (this.commandMap.dispatch(sender, commandLine)) {
            return true;
        }

        sender.sendMessage(TextFormat.RED + this.getLanguage().translateString("commands.generic.notFound"));

        return false;
    }

    //todo: use ticker to check console
    public ConsoleCommandSender getConsoleSender() {
        return consoleSender;
    }

    /**
     * サーバーを再読み込みさせます。
     * @return void
     */
    public void reload() {
        this.logger.info("再読み込み中...");

        this.logger.info("ワールドを保存しています...");

        for (Level level : this.levels.values()) {
            level.save();
        }

        this.pluginManager.disablePlugins();
        this.pluginManager.clearPlugins();
        this.commandMap.clearCommands();

        this.logger.info("server.propertiesを再読み込みしています...");
        this.properties.reload();
        this.maxPlayers = this.getPropertyInt("max-players", 20);

        if (this.getPropertyBoolean("hardcore", false) && this.getDifficulty() < 3) {
            this.setPropertyInt("difficulty", 3);
        }

        this.banByIP.load();
        this.banByName.load();
        this.reloadWhitelist();
        this.operators.reload();

        for (BanEntry entry : this.getIPBans().getEntires().values()) {
            this.getNetwork().blockAddress(entry.getName(), -1);
        }

        this.pluginManager.registerInterface(JavaPluginLoader.class);
        this.pluginManager.loadPlugins(this.pluginPath);
        this.enablePlugins(PluginLoadOrder.STARTUP);
        this.enablePlugins(PluginLoadOrder.POSTWORLD);



        Timings.reset();
    }

    /**
     * サーバーを終了させます。
     * @return void
     */
    public void shutdown() {
        if (this.isRunning) {
            ServerKiller killer = new ServerKiller(90);
            killer.start();
        }
        this.isRunning = false;
    }

    public void forceShutdown() {
        if (this.hasStopped) {
            return;
        }

        try {
            if (!this.isRunning) {
                //todo sendUsage
            }

            // clean shutdown of console thread asap
            this.console.shutdown();

            this.hasStopped = true;

            this.shutdown();

            if (this.rcon != null) {
                this.rcon.close();
            }

            this.getLogger().debug("Disabling all plugins");
            this.pluginManager.disablePlugins();

            for (Player player : new ArrayList<>(this.players.values())) {
                player.close(player.getLeaveMessage(), (String) this.getConfig("settings.shutdown-message", "Server closed"));
            }

            this.getLogger().debug("Unloading all levels");
            for (Level level : new ArrayList<>(this.getLevels().values())) {
                this.unloadLevel(level, true);
            }

            this.getLogger().debug("Removing event handlers");
            HandlerList.unregisterAll();

            this.getLogger().debug("Stopping all tasks");
            this.scheduler.cancelAllTasks();
            this.scheduler.mainThreadHeartbeat(Integer.MAX_VALUE);

            this.getLogger().debug("Closing console");
            this.console.interrupt();

            this.getLogger().debug("Stopping network interfaces");
            for (SourceInterface interfaz : this.network.getInterfaces()) {
                interfaz.shutdown();
                this.network.unregisterInterface(interfaz);
            }

            this.getLogger().debug("Disabling timings");
            Timings.stopServer();
            //todo other things
        } catch (Exception e) {
            this.logger.logException(e); //todo remove this?
            this.logger.emergency("Exception happened while shutting down, exit the process");
            this.trayMessage("サーバーシャットダウン中にエラーが発生したため、強制終了しました。", MessageType.ERROR);
            System.exit(1);
        }
    }

    /**
     * サーバーを開始させます。
     * @return void
     */
    public void start() {
        if (this.getPropertyBoolean("enable-query", true)) {
            this.queryHandler = new QueryHandler();
        }

        for (BanEntry entry : this.getIPBans().getEntires().values()) {
            this.network.blockAddress(entry.getName(), -1);
        }

        //todo send usage setting

        this.tickCounter = 0;

        this.logger.info(this.getLanguage().translateString("nukkit.server.defaultGameMode", getGamemodeString(this.getDefaultGamemode())));
        this.logger.info(this.getLanguage().translateString("nukkit.server.startFinished", String.valueOf((double) (System.currentTimeMillis() - Nukkit.START_TIME) / 1000)));
        this.trayMessage(FastAppender.get("サーバー起動完了(", String.valueOf((double) (System.currentTimeMillis() - Nukkit.START_TIME) / 1000), "秒)"), MessageType.INFO);

        this.tickProcessor();
        this.forceShutdown();
    }

    public void handlePacket(String address, int port, byte[] payload) {
        try {
            if (payload.length > 2 && Arrays.equals(Binary.subBytes(payload, 0, 2), new byte[]{(byte) 0xfe, (byte) 0xfd}) && this.queryHandler != null) {
                this.queryHandler.handle(address, port, payload);
            }
        } catch (Exception e) {
            this.logger.logException(e);

            this.getNetwork().blockAddress(address, 600);
        }
    }

    public void tickProcessor() {
        this.nextTick = System.currentTimeMillis();
        try {
            while (this.isRunning) {
                try {
                    this.tick();

                    long next = this.nextTick;
                    long current = System.currentTimeMillis();

                    if (next - 0.1 > current) {
                        Thread.sleep(next - current - 1, 900000);
                    }
                } catch (RuntimeException e) {
                    this.getLogger().logException(e);
                }
            }
        } catch (Throwable e) {
            this.logger.emergency("Exception happened while ticking server");
            this.logger.alert(Utils.getExceptionMessage(e));
            this.logger.alert(Utils.getAllThreadDumps());
        }
    }

    public void onPlayerCompleteLoginSequence(Player player) {
        this.sendFullPlayerListData(player);
    }

    public void onPlayerLogin(Player player) {
        if (this.sendUsageTicker > 0) {
            this.uniquePlayers.add(player.getUniqueId());
              this.loggedInPlayers.add(player);
        }
    }

    /*
    public void onPlayerCompleteLoginSequence(Player player){
          this.sendFullPlayerListData(player);
          player.dataPacket(this.craftingManager.getCraftingDataPacket());
      }
      */

    public void onPlayerLogout(Player player){
        this.loggedInPlayers.remove(player.getUniqueId());
    }

    public void addPlayer(String identifier, Player player) {
        this.players.put(identifier, player);
        this.identifier.put(player.rawHashCode(), identifier);
    }

    public void addOnlinePlayer(Player player) {
        this.playerList.put(player.getUniqueId(), player);
        this.updatePlayerListData(player.getUniqueId(), player.getId(), player.getDisplayName(), player.getSkin(), player.getLoginChainData().getXUID());
    }

    public void removeOnlinePlayer(Player player) {
        if (this.playerList.containsKey(player.getUniqueId())) {
            this.playerList.remove(player.getUniqueId());

            /*
            PlayerListPacket pk = new PlayerListPacket();
            pk.type = PlayerListPacket.TYPE_REMOVE;
            pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(player.getUniqueId())};
            Server.broadcastPacket(this.playerList.values(), pk);
            */

            this.removePlayerListData(player.getUniqueId());
        }
    }

    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin) {
        this.updatePlayerListData(uuid, entityId, name, skin, "", this.playerList.values());
    }

    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId) {
        this.updatePlayerListData(uuid, entityId, name, skin, xboxUserId, this.playerList.values());
    }

    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, Player[] players) {
        this.updatePlayerListData(uuid, entityId, name, skin, "", players);
    }

    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId, Player[] players) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_ADD;
        pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(uuid, entityId, name, skin, xboxUserId)};
        Server.broadcastPacket(players, pk);
    }

    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId, Collection<Player> players) {
        this.updatePlayerListData(uuid, entityId, name, skin, xboxUserId,
                players.stream()
                        .filter(p -> !p.getUniqueId().equals(uuid))
                        .toArray(Player[]::new));
    }

    public void removePlayerListData(UUID uuid) {
        this.removePlayerListData(uuid, this.playerList.values());
    }

    public void removePlayerListData(UUID uuid, Player[] players) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_REMOVE;
        pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(uuid)};
        Server.broadcastPacket(players, pk);
    }

    public void removePlayerListData(UUID uuid, Collection<Player> players) {
        this.removePlayerListData(uuid, players.stream().toArray(Player[]::new));
    }

    public void sendFullPlayerListData(Player player) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_ADD;
        pk.entries = this.playerList.values().stream()
                .map(p -> new PlayerListPacket.Entry(
                        p.getUniqueId(),
                        p.getId(),
                        p.getDisplayName(),
                        p.getSkin(),
                        p.getLoginChainData().getXUID()))
                .toArray(PlayerListPacket.Entry[]::new);

        player.dataPacket(pk);
    }

    public void sendRecipeList(Player player) {
        CraftingDataPacket pk = new CraftingDataPacket();
        pk.cleanRecipes = true;

        for (Recipe recipe : this.getCraftingManager().getRecipes().values()) {
            if (recipe instanceof ShapedRecipe) {
                pk.addShapedRecipe((ShapedRecipe) recipe);
            } else if (recipe instanceof ShapelessRecipe) {
                pk.addShapelessRecipe((ShapelessRecipe) recipe);
            }
        }

        for (FurnaceRecipe recipe : this.getCraftingManager().getFurnaceRecipes().values()) {
            pk.addFurnaceRecipe(recipe);
        }

        player.dataPacket(pk);
    }

    private void checkTickUpdates(int currentTick, long tickTime) {
        for (Player p : new ArrayList<>(this.players.values())) {
            /*if (!p.loggedIn && (tickTime - p.creationTime) >= 10000 && p.kick(PlayerKickEvent.Reason.LOGIN_TIMEOUT, "Login timeout")) {
                continue;
            }

            client freezes when applying resource packs
            todo: fix*/

            if (this.alwaysTickPlayers) {
                p.onUpdate(currentTick);
            }
        }

        //Do level ticks
        for (Level level : this.getLevels().values()) {
            if (level.getTickRate() > this.baseTickRate && --level.tickRateCounter > 0) {
                continue;
            }

            try {
                long levelTime = System.currentTimeMillis();
                level.doTick(currentTick);
                int tickMs = (int) (System.currentTimeMillis() - levelTime);
                level.tickRateTime = tickMs;

                if (this.autoTickRate) {
                    if (tickMs < 50 && level.getTickRate() > this.baseTickRate) {
                        int r;
                        level.setTickRate(r = level.getTickRate() - 1);
                        if (r > this.baseTickRate) {
                            level.tickRateCounter = level.getTickRate();
                        }
                        this.getLogger().debug("Raising level \"" + level.getName() + "\" tick rate to " + level.getTickRate() + " ticks");
                    } else if (tickMs >= 50) {
                        if (level.getTickRate() == this.baseTickRate) {
                            level.setTickRate((int) Math.max(this.baseTickRate + 1, Math.min(this.autoTickRateLimit, Math.floor(tickMs / 50))));
                            this.getLogger().debug("Level \"" + level.getName() + "\" took " + NukkitMath.round(tickMs, 2) + "ms, setting tick rate to " + level.getTickRate() + " ticks");
                        } else if ((tickMs / level.getTickRate()) >= 50 && level.getTickRate() < this.autoTickRateLimit) {
                            level.setTickRate(level.getTickRate() + 1);
                            this.getLogger().debug("Level \"" + level.getName() + "\" took " + NukkitMath.round(tickMs, 2) + "ms, setting tick rate to " + level.getTickRate() + " ticks");
                        }
                        level.tickRateCounter = level.getTickRate();
                    }
                }
            } catch (Exception e) {
                if (Nukkit.DEBUG > 1 && this.logger != null) {
                    this.logger.logException(e);
                }

                this.logger.critical(this.getLanguage().translateString("nukkit.level.tickError", new String[]{level.getName(), e.toString()}));
                this.logger.logException(e);
            }
        }
    }

    public void doAutoSave() {
        if (this.getAutoSave()) {
            Timings.levelSaveTimer.startTiming();
            for (Player player : new ArrayList<>(this.players.values())) {
                if (player.isOnline()) {
                    player.save(true);
                } else if (!player.isConnected()) {
                    this.removePlayer(player);
                }
            }

            for (Level level : this.getLevels().values()) {
                level.save();
            }
            Timings.levelSaveTimer.stopTiming();
        }
    }

    private boolean tick() {
        long tickTime = System.currentTimeMillis();
        long tickTimeNano = System.nanoTime();
        if ((tickTime - this.nextTick) < -25) {
            return false;
        }

        Timings.fullServerTickTimer.startTiming();

        ++this.tickCounter;

        Timings.connectionTimer.startTiming();
        this.network.processInterfaces();

        if (this.rcon != null) {
            this.rcon.check();
        }
        Timings.connectionTimer.stopTiming();

        Timings.schedulerTimer.startTiming();
        this.scheduler.mainThreadHeartbeat(this.tickCounter);
        Timings.schedulerTimer.stopTiming();

        this.checkTickUpdates(this.tickCounter, tickTime);

        for (Player player : new ArrayList<>(this.players.values())) {
            player.checkNetwork();
        }

        if ((this.tickCounter & 0b1111) == 0) {
            this.maxTick = 20;
            this.maxUse = 0;

            if ((this.tickCounter & 0b111111111) == 0) {
                try {
                    this.getPluginManager().callEvent(this.queryRegenerateEvent = new QueryRegenerateEvent(this, 5));
                    if (this.queryHandler != null) {
                        this.queryHandler.regenerateInfo();
                    }
                } catch (Exception e) {
                    this.logger.logException(e);
                }
            }

            this.getNetwork().updateName();
        }

        if (this.autoSave && ++this.autoSaveTicker >= this.autoSaveTicks) {
            this.autoSaveTicker = 0;
            this.doAutoSave();
        }

        if (this.sendUsageTicker > 0 && --this.sendUsageTicker == 0) {
            this.sendUsageTicker = 6000;
            //todo sendUsage
        }

        if (this.tickCounter % 100 == 0) {
            for (Level level : this.levels.values()) {
                level.clearCache();
                level.doChunkGarbageCollection();
            }
        }

        Timings.fullServerTickTimer.stopTiming();
        //long now = System.currentTimeMillis();
        long nowNano = System.nanoTime();
        //float tick = Math.min(20, 1000 / Math.max(1, now - tickTime));
        //float use = Math.min(1, (now - tickTime) / 50);

        float tick = (float) Math.min(20, 1000000000 / Math.max(1000000, ((double) nowNano - tickTimeNano)));
        float use = (float) Math.min(1, ((double) (nowNano - tickTimeNano)) / 50000000);

        if (this.maxTick > tick) {
            this.maxTick = tick;
        }

        if (this.maxUse < use) {
            this.maxUse = use;
        }

        System.arraycopy(this.tickAverage, 1, this.tickAverage, 0, this.tickAverage.length - 1);
        this.tickAverage[this.tickAverage.length - 1] = tick;

        System.arraycopy(this.useAverage, 1, this.useAverage, 0, this.useAverage.length - 1);
        this.useAverage[this.useAverage.length - 1] = use;

        if ((this.nextTick - tickTime) < -1000) {
            this.nextTick = tickTime;
        } else {
            this.nextTick += 50;
        }

        return true;
    }

    public QueryRegenerateEvent getQueryInformation() {
        return this.queryRegenerateEvent;
    }

    /**
     * サーバーの名前を取得します。
     * @return String どんな場合でも"Jupiter"が返ってきます。
     */
    public String getName() {
        return "Jupiter";
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Nukkitのバージョンを取得します。
     * @return String Nukkitバージョン
     */
    public String getNukkitVersion() {
        return Nukkit.VERSION;
    }

    /**
     * コードネームを取得します。
     * @return String コードネーム
     */
    public String getCodename() {
        return Nukkit.CODENAME;
    }

    /**
     * マインクラフトPEのバージョンを取得します。
     * @return String Minecraftバージョン
     */
    public String getVersion() {
        return ProtocolInfo.MINECRAFT_VERSION;
    }

    /**
     * APIバージョンを取得します。
     * @return String APIバージョン
     */
    public String getApiVersion() {
        return Nukkit.API_VERSION;
    }

    /**
     * Jupiterバージョンを取得します。
     * @return String Jupiterバージョン
     */
    public String getJupiterVersion() {
        return Nukkit.JUPITER_VERSION;
    }

    /**
     * ファイルパスを取得します。
     * @return String ファイルパス
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * データパスを取得します。
     * <br>この場合、jarがあるディレクトリのパスです。
     * @return String データパス
     */
    public String getDataPath() {
        return dataPath;
    }

    /**
     * pluginsのパスを取得します。
     * <br>この場合、getDataPath()の戻り値に/pluginsがついたものとなります。
     * @return String プラグインフォルダのパス
     * @see Server#getDataPath()
     */
    public String getPluginPath() {
        return pluginPath;
    }

    public String getDefaultplugins(){
        return defaultplugin;
    }

    /**
     * サーバーの最大参加可能人数を取得します。
     * @return int 最大参加可能人数
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * サーバーのポートを取得します。
     * <br>server.propertiesのserver-portの値です。
     * @return int ポート
     */
    public int getPort() {
        return this.getPropertyInt("server-port", 19132);
    }

    /**
     * サーバーの描画距離を取得します。
     * <br>server.propertiesのview-distancetの値です。
     * @return int 描画距離
     */
    public int getViewDistance() {
        return this.getPropertyInt("view-distance", 10);
    }

    /**
     * サーバーのIPアドレスを取得します。
     * <br>server.propertiesのserver-ipの値です。
     * @return String IPアドレス
     */
    public String getIp() {
        return this.getPropertyString("server-ip", "0.0.0.0");

    }

    public UUID getServerUniqueId() {
        return this.serverID;
    }

    /**
     * サーバーのオートセーブが有効かどうかを取得します。
     * @return boolean trueが有効/falseが無効
     */
    public boolean getAutoSave() {
        return this.autoSave;
    }

    /**
     * サーバーのオートセーブを設定します。
     * @param autoSave trueが有効/falseが無効
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        for (Level level : this.getLevels().values()) {
            level.setAutoSave(this.autoSave);
        }
    }
    /**
     * サーバーのワールドタイプを取得します。
     * <br>server.propertiesのlevel-typeの値です。
     * <br>
     * <br>[ワールドタイプ]
     * <br>FLAT フラットワールド
     * <br>DEFAULT デフォルトワールド
     * @return String ワールドタイプ
     */
    public String getLevelType() {
        return this.getPropertyString("level-type", "DEFAULT");
    }

    public boolean getGenerateStructures() {
        return this.getPropertyBoolean("generate-structures", true);
    }

    public boolean getForceGamemode() {
        return this.getPropertyBoolean("force-gamemode", false);
    }

    /**
     * サーバーのゲームモードを名前で取得します。
     * <br>server.propertiesのgamemodeの値です。
     * <br>
     * <br>[戻ってくる名前:ゲームモード(入力した番号)]
     * <br>サバイバルモード(0)
     * <br>クリエイティブモード(1)
     * <br>アドベンチャーモード(2)
     * <br>スペクテイターモード(3)
     * <br>UNKNOWN(0-3以外の数値を入力した場合)
     * @param mode 名前に変換したいゲームモードの番号(0, 1, 2, 3)
     * @param direct ダイレクトかどうか
     * @return String ゲームモード
     */
    public static String getGamemodeString(int mode) {
        return getGamemodeString(mode, false);
    }

    public static String getGamemodeString(int mode, boolean direct) {
        switch (mode) {
            case Player.SURVIVAL:
                return direct ? "Survival" : "%gameMode.survival";
            case Player.CREATIVE:
                return direct ? "Creative" : "%gameMode.creative";
            case Player.ADVENTURE:
                return direct ? "Adventure" : "%gameMode.adventure";
            case Player.SPECTATOR:
                return direct ? "Spectator" : "%gameMode.spectator";
        }
        return "UNKNOWN";
    }

    public static int getGamemodeFromString(String str) {
        switch (str.trim().toLowerCase()) {
            case "0":
            case "survival":
            case "s":
                return Player.SURVIVAL;

            case "1":
            case "creative":
            case "c":
                return Player.CREATIVE;

            case "2":
            case "adventure":
            case "a":
                return Player.ADVENTURE;

            case "3":
            case "spectator":
            case "spc":
            case "view":
            case "v":
                return Player.SPECTATOR;
        }
        return -1;
    }

    public static int getDifficultyFromString(String str) {
        switch (str.trim().toLowerCase()) {
            case "0":
            case "peaceful":
            case "p":
                return 0;

            case "1":
            case "easy":
            case "e":
                return 1;

            case "2":
            case "normal":
            case "n":
                return 2;

            case "3":
            case "hard":
            case "h":
                return 3;
        }
        return -1;
    }

    /**
     * サーバーの難易度を取得します。
     * <br>server.propertiesのdifficultyの値です。
     * @return int 難易度
     */
    public int getDifficulty() {
        return this.getPropertyInt("difficulty", 1);
    }

    /**
     * サーバーがホワイトリスト状態かどうかを取得します。
     * <br>server.propertiesのwhite-listの値です。
     * @return boolean trueが有効/falseが無効
     */
    public boolean hasWhitelist() {
        return this.getPropertyBoolean("white-list", false);
    }

    /**
     * スポーン地点から半径何ブロックが破壊できないかを取得します。
     * <br>server.propertiesのspawn-protectionの値です。
     * @return int 半径
     */
    public int getSpawnRadius() {
        return this.getPropertyInt("spawn-protection", 16);
    }

    public boolean getAllowFlight() {
        if (getAllowFlight == null) {
            getAllowFlight = this.getPropertyBoolean("allow-flight", false);
        }
        return getAllowFlight;
    }

    /**
     * サーバーがハードコア状態かどうかを取得します。
     * <br>server.propertiesのhardcoreの値です。
     * @return boolean trueが有効/falseが無効
     */
    public boolean isHardcore() {
        return this.getPropertyBoolean("hardcore", false);
    }

    /**
     * サーバーのデフォルトのゲームモードを取得します。
     * <br>server.propertiesのgamemodeの値です。
     * <br>
     * <br>[ゲームモード]
     * <br>0:サバイバルモード
     * <br>1:クリエイティブモード
     * <br>2:アドベンチャーモード
     * <br>3:スペクテイターモード
     * @return int ゲームモード
     */
    public int getDefaultGamemode() {
        return this.getPropertyInt("gamemode", 0);
    }

    /**
     * サーバー名を取得します。
     * <br>server.propertiesのmotdの値です。
     * @return String サーバー名
     */
    public String getMotd() {
        return this.getPropertyString("motd", "Nukkit Server For Minecraft: BE");
    }

    /**
     * サブサーバー名を取得します。
     * <br>server.propertiesのmotdの値です。
     * @return String サブサーバー名
     */
    public String getSubMotd() {
        return this.getPropertyString("sub-motd", "Powered by Jupiter");
    }

    public boolean getForceResources() {
        return this.getPropertyBoolean("force-resources", false);
    }

    /**
     * MainLoggerオブジェクトを取得します。
     * @return MainLogger
     */
    public MainLogger getLogger() {
        return this.logger;
    }

    public EntityMetadataStore getEntityMetadata() {
        return entityMetadata;
    }

    public PlayerMetadataStore getPlayerMetadata() {
        return playerMetadata;
    }

    public LevelMetadataStore getLevelMetadata() {
        return levelMetadata;
    }

    /**
     * プラグインマネージャを取得します。
     * <br>
     * <br>[Itsuのメモ: イベント登録]
     * <br>(Listenerインターフェースを実装/PluginBaseクラスを継承している場合)
     * <br>
     * <br>{@code this.getLogger().getPluginManager().registerEvents(this, this);}
     * @return PluginManager
     * @see PluginManager#registerEvents(cn.nukkit.event.Listener, Plugin)
     */
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * クラフティングマネージャを取得します。
     * @return CraftingManager
     */
    public CraftingManager getCraftingManager() {
        return craftingManager;
    }

    /**
     * リソースパックマネージャを取得します。
     * @return ResourcePackManager
     */
    public ResourcePackManager getResourcePackManager() {
        return resourcePackManager;
    }

    /**
     * スケジューラを取得します。
     * <br>
     * <br>[Itsuのメモ: スケジューラの使い方]
     * <pre>
     * ・繰り返し
     * {@code
     *  TaskHandler th;
     *  th = this.getServer().getScheduler().scheduleRepeatingTask(null, new Runnable(){
     *      //繰り返す処理
     *  };, 間隔tick(int));
     *
     *
     * ・遅延してから繰り返し
     *  TaskHandler th;
     *  th = this.getServer().getScheduler().scheduleDelayedRepeatingTask(null, new Runnable(){
     *      //繰り返す処理
     *  };, 遅延tick(int), 間隔tick(int));
     *
     *
     *  ・スケジューラを止める
     *  th.cancel();
     * </pre>
     * [豆知識]
     * <br>20tick = 1秒です!
     * @return ServerScheduler
     */
    public ServerScheduler getScheduler() {
        return scheduler;
    }

    public int getTick() {
        return tickCounter;
    }

    public float getTicksPerSecond() {
        return ((float) Math.round(this.maxTick * 100)) / 100;
    }

    public float getTicksPerSecondAverage() {
        float sum = 0;
        int count = this.tickAverage.length;
        for (float aTickAverage : this.tickAverage) {
            sum += aTickAverage;
        }
        return (float) NukkitMath.round(sum / count, 2);
    }

    public float getTickUsage() {
        return (float) NukkitMath.round(this.maxUse * 100, 2);
    }

    public float getTickUsageAverage() {
        float sum = 0;
        int count = this.useAverage.length;
        for (float aUseAverage : this.useAverage) {
            sum += aUseAverage;
        }
        return ((float) Math.round(sum / count * 100)) / 100;
    }

    public SimpleCommandMap getCommandMap() {
        return commandMap;
    }

    public Map<UUID, Player> getOnlinePlayers() {
        return new HashMap<>(playerList);
    }

    public void addRecipe(Recipe recipe) {
        this.craftingManager.registerRecipe(recipe);
    }

    public IPlayer getOfflinePlayer(String name) {
        IPlayer result = this.getPlayerExact(name.toLowerCase());
        if (result == null) {
            return new OfflinePlayer(this, name);
        }

        return result;
    }

    public CompoundTag getOfflinePlayerData(String name) {
        name = name.toLowerCase();
        String path = this.getDataPath() + "players/";
        File file = new File(FastAppender.get(path, name, ".dat"));

        if (this.shouldSavePlayerData() && file.exists()) {
            try {
                return NBTIO.readCompressed(new FileInputStream(file));
            } catch (Exception e) {
                file.renameTo(new File(FastAppender.get(path, name, ".dat.bak")));
                this.logger.notice(this.getLanguage().translateString("nukkit.data.playerCorrupted", name));
            }
        } else {
            this.logger.notice(this.getLanguage().translateString("nukkit.data.playerNotFound", name));
        }

        Position spawn = this.getDefaultLevel().getSafeSpawn();
        CompoundTag nbt = new CompoundTag()
                .putLong("firstPlayed", System.currentTimeMillis() / 1000)
                .putLong("lastPlayed", System.currentTimeMillis() / 1000)
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("0", spawn.x))
                        .add(new DoubleTag("1", spawn.y))
                        .add(new DoubleTag("2", spawn.z)))
                .putString("Level", this.getDefaultLevel().getName())
                .putList(new ListTag<>("Inventory"))
                .putCompound("Achievements", new CompoundTag())
                .putInt("playerGameType", this.getDefaultGamemode())
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("0", 0))
                        .add(new DoubleTag("1", 0))
                        .add(new DoubleTag("2", 0)))
                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("0", 0))
                        .add(new FloatTag("1", 0)))
                .putFloat("FallDistance", 0)
                .putShort("Fire", 0)
                .putShort("Air", 300)
                .putBoolean("OnGround", true)
                .putBoolean("Invulnerable", false)
                .putString("NameTag", name);

        this.saveOfflinePlayerData(name, nbt);
        return nbt;
    }

    public void saveOfflinePlayerData(String name, CompoundTag tag) {
        this.saveOfflinePlayerData(name, tag, false);
    }

    public void saveOfflinePlayerData(String name, CompoundTag tag, boolean async) {
        if (this.shouldSavePlayerData()) {
            try {
                if (async) {
                    this.getScheduler().scheduleAsyncTask(new FileWriteTask(FastAppender.get(this.getDataPath() + "players/", name.toLowerCase(), ".dat"), NBTIO.writeGZIPCompressed(tag, ByteOrder.BIG_ENDIAN)));
                } else {
                    Utils.writeFile(FastAppender.get(this.getDataPath(), "players/", name.toLowerCase(), ".dat"), new ByteArrayInputStream(NBTIO.writeGZIPCompressed(tag, ByteOrder.BIG_ENDIAN)));
                }
            } catch (Exception e) {
                this.logger.critical(this.getLanguage().translateString("nukkit.data.saveError", new String[]{name, e.getMessage()}));
                if (Nukkit.DEBUG > 1) {
                    this.logger.logException(e);
                }
            }
        }
    }

    /**
     * プレイヤーオブジェクトを名前から取得します。
     * @param name 取得したいプレイヤーの名前
     * @return Player 取得したプレイヤー
     */
    public Player getPlayer(String name) {
        Player found = null;
        name = name.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase().startsWith(name)) {
                int curDelta = player.getName().length() - name.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            }
        }

        return found;
    }

    public Player getPlayerExact(String name) {
        name = name.toLowerCase();
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase().equals(name)) {
                return player;
            }
        }

        return null;
    }

    public Player[] matchPlayer(String partialName) {
        partialName = partialName.toLowerCase();
        List<Player> matchedPlayer = new ArrayList<>();
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase().equals(partialName)) {
                return new Player[]{player};
            } else if (player.getName().toLowerCase().contains(partialName)) {
                matchedPlayer.add(player);
            }
        }

        return matchedPlayer.toArray(new Player[matchedPlayer.size()]);
    }

    public void removePlayer(Player player) {
        if (this.identifier.containsKey(player.rawHashCode())) {
            String identifier = this.identifier.get(player.rawHashCode());
            this.players.remove(identifier);
            this.identifier.remove(player.rawHashCode());
            return;
        }

        for (String identifier : new ArrayList<>(this.players.keySet())) {
            Player p = this.players.get(identifier);
            if (player == p) {
                this.players.remove(identifier);
                this.identifier.remove(player.rawHashCode());
                break;
            }
        }
    }

    public Map<Integer, Level> getLevels() {
        return levels;
    }

    /**
     * デフォルトで設定されているワールドのオブジェクトを取得します。
     * @return Level デフォルトで設定されているワールドのオブジェクト
     */
    public Level getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(Level defaultLevel) {
        if (defaultLevel == null || (this.isLevelLoaded(defaultLevel.getFolderName()) && defaultLevel != this.defaultLevel)) {
            this.defaultLevel = defaultLevel;
        }
    }

    public boolean isLevelLoaded(String name) {
        return this.getLevelByName(name) != null;
    }

    public Level getLevel(int levelId) {
        if (this.levels.containsKey(levelId)) {
            return this.levels.get(levelId);
        }
        return null;
    }

    /**
     * ワールドオブジェクトを名前から取得します。
     * @param name 取得したいワールドの名前
     * @return Level 取得したワールドオブジェクト
     */
    public Level getLevelByName(String name) {
        for (Level level : this.getLevels().values()) {
            if (level.getFolderName().equals(name)) {
                return level;
            }
        }

        return null;
    }

    public boolean unloadLevel(Level level) {
        return this.unloadLevel(level, false);
    }

    public boolean unloadLevel(Level level, boolean forceUnload) {
        if (level == this.getDefaultLevel() && !forceUnload) {
            throw new IllegalStateException("The default level cannot be unloaded while running, please switch levels.");
        }

        return level.unload(forceUnload);

    }

    public boolean loadLevel(String name) {
        if (Objects.equals(name.trim(), "")) {
            throw new LevelException("Invalid empty level name");
        }
        if (this.isLevelLoaded(name)) {
            return true;
        } else if (!this.isLevelGenerated(name)) {
            this.logger.notice(this.getLanguage().translateString("nukkit.level.notFound", name));

            return false;
        }

        String path;

        if (name.contains("/") || name.contains("\\")) {
            path = name;
        } else {
            path = FastAppender.get(this.getDataPath(), "worlds/", name, "/");
        }

        Class<? extends LevelProvider> provider = LevelProviderManager.getProvider(path);

        if (provider == null) {
            this.logger.error(this.getLanguage().translateString("nukkit.level.loadError", new String[]{name, "Unknown provider"}));

            return false;
        }

        Level level;
        try {
            level = new Level(this, name, path, provider);
        } catch (Exception e) {
            this.logger.error(this.getLanguage().translateString("nukkit.level.loadError", new String[]{name, e.getMessage()}));
            this.logger.logException(e);
            return false;
        }

        this.levels.put(level.getId(), level);

        level.initLevel();

        this.getPluginManager().callEvent(new LevelLoadEvent(level));

        level.setTickRate(this.baseTickRate);

        return true;
    }

    public boolean generateLevel(String name) {
        return this.generateLevel(name, new java.util.Random().nextLong());
    }

    public boolean generateLevel(String name, long seed) {
        return this.generateLevel(name, seed, null);
    }

    public boolean generateLevel(String name, long seed, Class<? extends Generator> generator) {
        return this.generateLevel(name, seed, generator, new HashMap<>());
    }

    public boolean generateLevel(String name, long seed, Class<? extends Generator> generator, Map<String, Object> options) {
        return generateLevel(name, seed, generator, options, null);
    }

    public boolean generateLevel(String name, long seed, Class<? extends Generator> generator, Map<String, Object> options, Class<? extends LevelProvider> provider) {
        if (Objects.equals(name.trim(), "") || this.isLevelGenerated(name)) {
            return false;
        }

        if (!options.containsKey("preset")) {
            options.put("preset", this.getPropertyString("generator-settings", ""));
        }

        if (generator == null) {
            generator = Generator.getGenerator(this.getLevelType());
        }

        if (provider == null) {
            if ((provider = LevelProviderManager.getProviderByName
                    ((String) this.getConfig("level-settings.default-format", "anvil"))) == null) {
                provider = LevelProviderManager.getProviderByName("anvil");
            }
        }

        String path;

        if (name.contains("/") || name.contains("\\")) {
            path = name;
        } else {
            path = FastAppender.get(this.getDataPath(), "worlds/", name, "/");
        }

        Level level;
        try {
            provider.getMethod("generate", String.class, String.class, long.class, Class.class, Map.class).invoke(null, path, name, seed, generator, options);

            level = new Level(this, name, path, provider);
            this.levels.put(level.getId(), level);

            level.initLevel();

            level.setTickRate(this.baseTickRate);
        } catch (Exception e) {
            this.logger.error(this.getLanguage().translateString("nukkit.level.generationError", new String[]{name, e.getMessage()}));
            this.logger.logException(e);
            return false;
        }

        this.getPluginManager().callEvent(new LevelInitEvent(level));

        this.getPluginManager().callEvent(new LevelLoadEvent(level));

        /*this.getLogger().notice(this.getLanguage().translateString("nukkit.level.backgroundGeneration", name));

        int centerX = (int) level.getSpawnLocation().getX() >> 4;
        int centerZ = (int) level.getSpawnLocation().getZ() >> 4;

        TreeMap<String, Integer> order = new TreeMap<>();

        for (int X = -3; X <= 3; ++X) {
            for (int Z = -3; Z <= 3; ++Z) {
                int distance = X * X + Z * Z;
                int chunkX = X + centerX;
                int chunkZ = Z + centerZ;
                order.put(Level.chunkHash(chunkX, chunkZ), distance);
            }
        }

        List<Map.Entry<String, Integer>> sortList = new ArrayList<>(order.entrySet());

        Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        for (String index : order.keySet()) {
            Chunk.Entry entry = Level.getChunkXZ(index);
            level.populateChunk(entry.chunkX, entry.chunkZ, true);
        }*/

        return true;
    }

    public boolean isLevelGenerated(String name) {
        if (Objects.equals(name.trim(), "")) {
            return false;
        }

        String path = FastAppender.get(this.getDataPath(), "worlds/", name, "/");
        if (this.getLevelByName(name) == null) {

            if (LevelProviderManager.getProvider(path) == null) {
                return false;
            }
        }

        return true;
    }

    public BaseLang getLanguage() {
        return baseLang;
    }

    public boolean isLanguageForced() {
        return forceLanguage;
    }

    public Network getNetwork() {
        return network;
    }

    //Revising later...
    public Config getConfig() {
        return this.config;
    }

    public Object getConfig(String variable) {
        return this.getConfig(variable, null);
    }

    public Object getConfig(String variable, Object defaultValue) {
        Object value = this.config.get(variable);
        return value == null ? defaultValue : value;
    }

    /**
     * server.propertiesのオブジェクト(Config)を取得します。
     * @return Config プロパティーのオブジェクト
     */
    public Config getProperties() {
        return this.properties;
    }

    /**
     * server.propertiesの引数で指定したキーの値を取得します。
     * <br>比較などするときは、適切にキャストする必要があります。
     * @param variable キー
     * @return Object 取得した値
     */
    public Object getProperty(String variable) {
        return this.getProperty(variable, null);
    }

    public Object getProperty(String variable, Object defaultValue) {
        return this.properties.exists(variable) ? this.properties.get(variable) : defaultValue;
    }

    /**
     * server.propertiesの引数で指定したキーの値をString型で設定します。
     * @param variable キー
     * @param value 値
     * @return void
     */
    public void setPropertyString(String variable, String value) {
        this.properties.set(variable, value);
        this.properties.save();
    }

    /**
     * server.propertiesの引数で指定したキーの値をString型で取得します。
     * @param variable キー
     * @return String 取得した値
     */
    public String getPropertyString(String variable) {
        return this.getPropertyString(variable, null);
    }

    public String getPropertyString(String variable, String defaultValue) {
        return this.properties.exists(variable) ? (String) this.properties.get(variable) : defaultValue;
    }

    /**
     * server.propertiesの引数で指定したキーの値をint型で取得します。
     * @param variable キー
     * @return int 取得した値
     */
    public int getPropertyInt(String variable) {
        return this.getPropertyInt(variable, null);
    }

    public int getPropertyInt(String variable, Integer defaultValue) {
        return this.properties.exists(variable) ? (!this.properties.get(variable).equals("") ? Integer.parseInt(String.valueOf(this.properties.get(variable))) : defaultValue) : defaultValue;
    }

    /**
     * server.propertiesの引数で指定したキーの値をint型で設定します。
     * @param variable キー
     * @param value 値
     * @return void
     */
    public void setPropertyInt(String variable, int value) {
        this.properties.set(variable, value);
        this.properties.save();
    }

    /**
     * server.propertiesの引数で指定したキーの値をboolean型で取得します。
     * @param variable キー
     * @return boolean 取得した値
     */
    public boolean getPropertyBoolean(String variable) {
        return this.getPropertyBoolean(variable, null);
    }

    /**
     * server.propertiesの引数で指定したキーの値をboolean型で取得します。
     * <br>第二引数はデフォルトの値です。
     * @param variable キー
     * @param defaultValue デフォルトの値
     * @return boolean 取得した値
     */
    public boolean getPropertyBoolean(String variable, Object defaultValue) {
        Object value = this.properties.exists(variable) ? this.properties.get(variable) : defaultValue;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        switch (String.valueOf(value)) {
            case "on":
            case "true":
            case "1":
            case "yes":
                return true;
        }
        return false;
    }

    /**
     * server.propertiesの引数で指定したキーの値をboolean型で設定します。
     * @param variable キー
     * @param value 値
     * @return void
     */
    public void setPropertyBoolean(String variable, boolean value) {
        this.properties.set(variable, value ? "1" : "0");
        this.properties.save();
    }

    /**
     * jupiter.ymlのオブジェクト(Config)を取得します。
     * @return Config jupiter.ymlのオブジェクト
     */
    public Config getJupiterConfig(){
        return new Config(this.getDataPath() + "jupiter.yml");
    }


    /**
     * jupiter.ymlが読み込まれているかを取得します。
     * <br>!(getJupiterConfig().isEmpty())の戻り値と同等です。
     * @return boolean
     */
    public boolean isLoadedJupiterConfig(){
        return !this.jupiterconfig.isEmpty();
    }

    /**
     * jupiter.ymlをロードします。
     * @return void
     */
    public void loadJupiterConfig(){
        this.jupiterconfig = new HashMap<String, Object>(this.getJupiterConfig().getAll());
    }

    /**
     * jupiter.ymlの引数で指定したキーの値をString型で取得します。
     * @param key キー
     * @return String 取得した値
     */
    public String getJupiterConfigString(String key){
        return (String) this.jupiterconfig.get(key);
    }

    /**
     * jupiter.ymlの引数で指定したキーの値をint型で取得します。
     * @param key キー
     * @return int 取得した値
     */
    public int getJupiterConfigInt(String key){
        return (int) this.jupiterconfig.get(key);
    }

    /**
     * jupiter.ymlの引数で指定したキーの値をBoolean型で取得します。
     * <br>デフォルトではtrueが返ります。
     * @param key キー
     * @return boolean 取得した値
     */
    public Boolean getJupiterConfigBoolean(String key){
        return this.getJupiterConfigBoolean(key, null);
    }


    /**
     * jupiter.ymlの引数で指定したキーの値をBoolean型で取得します。
     * <br>第二引数はデフォルトの値です。
     * @param key キー
     * @param defaultValue キーが存在しない場合に返すもの
     * @return boolean 取得した値
     */
    public boolean getJupiterConfigBoolean(String variable, Object defaultValue) {
        Object value = this.jupiterconfig.containsKey(variable) ? this.jupiterconfig.get(variable) : defaultValue;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        switch (String.valueOf(value)) {
            case "on":
            case "true":
            case "1":
            case "yes":
                return true;
        }
        return false;
    }

    public PluginIdentifiableCommand getPluginCommand(String name) {
        Command command = this.commandMap.getCommand(name);
        if (command instanceof PluginIdentifiableCommand) {
            return (PluginIdentifiableCommand) command;
        } else {
            return null;
        }
    }

    /**
     * 名前Banされた人のリストを取得します。
     * @return BanList 名前Banリスト
     */
    public BanList getNameBans() {
        return this.banByName;
    }

    /**
     * IPBanされた人のリストを取得します。
     * @return BanList IPBanリスト
     */
    public BanList getIPBans() {
        return this.banByIP;
    }

    /**
     * 引数で指定した名前のプレイヤーをOPにします。
     * @param name OPにしたいプレイヤーの名前
     * @return void
     */
    public void addOp(String name) {
        this.operators.set(name.toLowerCase(), true);
        Player player = this.getPlayerExact(name);
        if (player != null) {
            player.recalculatePermissions();
        }
        this.operators.save(true);
    }

    /**
     * 引数で指定した名前のプレイヤーのOP権を剥奪します。
     * @param name OPを剥奪したいプレイヤーの名前
     * @return void
     */
    public void removeOp(String name) {
        this.operators.remove(name.toLowerCase());
        Player player = this.getPlayerExact(name);
        if (player != null) {
            player.recalculatePermissions();
        }
        this.operators.save();
    }

    /**
     * 引数で指定した名前のプレイヤーをホワイトリストに追加にします。
     * @param name ホワイトリストに追加したいプレイヤーの名前
     * @return void
     */
    public void addWhitelist(String name) {
        this.whitelist.set(name.toLowerCase(), true);
        this.whitelist.save(true);
    }

    /**
     * 引数で指定した名前のプレイヤーをホワイトリストから外します。
     * @param name ホワイトリストから外したいプレイヤーの名前
     * @return void
     */
    public void removeWhitelist(String name) {
        this.whitelist.remove(name.toLowerCase());
        this.whitelist.save(true);
    }

    /**
     * 引数で指定した名前のプレイヤーがホワイトリストに追加されているかどうかを取得します。
     * @param name 調べたい人の名前
     * @return boolean trueが追加されている/falseが追加されていない
     */
    public boolean isWhitelisted(String name) {
        return !this.hasWhitelist() || this.operators.exists(name, true) || this.whitelist.exists(name, true);
    }

    /**
     * 引数で指定した名前のプレイヤーがOPかどうかを取得します。
     * @param name 調べたい人の名前
     * @return boolean trueがOP/falseが非OP
     */
    public boolean isOp(String name) {
        return this.operators.exists(name, true);
    }

    /**
     * ホワイトリストをコンフィグから取得します。
     * @return Config サーバーのホワイトリストのメンバー
     */
    public Config getWhitelist() {
        return whitelist;
    }

    /**
     * OPをコンフィグから取得します。
     * @return Config サーバーのOPメンバー
     */
    public Config getOps() {
        return operators;
    }

    /**
     * ホワリスをリロードします。
     * @return void
     */
    public void reloadWhitelist() {
        this.whitelist.reload();
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getCommandAliases() {
        Object section = this.getConfig("aliases");
        Map<String, List<String>> result = new LinkedHashMap<>();
        if (section instanceof Map) {
            for (Map.Entry entry : (Set<Map.Entry>) ((Map) section).entrySet()) {
                List<String> commands = new ArrayList<>();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value instanceof List) {
                    for (String string : (List<String>) value) {
                        commands.add(string);
                    }
                } else {
                    commands.add((String) value);
                }

                result.put(key, commands);
            }
        }

        return result;

    }

    public boolean shouldSavePlayerData() {
        return (Boolean) this.getConfig("player.save-player-data", true);
    }

    public LinkedHashMap<Integer, ServerSettingsWindow> getDefaultServerSettings() {
        return this.defaultServerSettings;
    }

    public void addDefaultServerSettings(ServerSettingsWindow window) {
        this.defaultServerSettings.put(window.getId(), window);
    }

    /**
     * Checks the current thread against the expected primary thread for the server.
     *
     * <b>Note:</b> this method should not be used to indicate the current synchronized state of the runtime. A current thread matching the main thread indicates that it is synchronized, but a mismatch does not preclude the same assumption.
     * @return true if the current thread matches the expected primary thread, false otherwise
     */
    public boolean isPrimaryThread() {
        return (Thread.currentThread() == currentThread);
    }

    private void registerEntities() {
        //mob
        Entity.registerEntity("Blaze", EntityBlaze.class);
        Entity.registerEntity("CaveSpider", EntityCaveSpider.class);
        Entity.registerEntity("Creeper", EntityCreeper.class);
        Entity.registerEntity("Enderman", EntityEnderman.class);
        Entity.registerEntity("Endermite", EntityEndermite.class);
        Entity.registerEntity("Evoker", EntityEvoker.class);
        Entity.registerEntity("Ghast", EntityGhast.class);
        Entity.registerEntity("Guardian", EntityGuardian.class);
        Entity.registerEntity("Hask", EntityHask.class);
        Entity.registerEntity("MagmaCube", EntityMagmaCube.class);
        Entity.registerEntity("Shulker", EntityShulker.class);
        Entity.registerEntity("Silverfish", EntitySilverfish.class);
        Entity.registerEntity("Skeleton", EntitySkeleton.class);
        Entity.registerEntity("Slime", EntitySlime.class);
        Entity.registerEntity("Spider", EntitySpider.class);
        Entity.registerEntity("Stray", EntityStray.class);
        Entity.registerEntity("Vex", EntityVex.class);
        Entity.registerEntity("Vindicator", EntityVindicator.class);
        Entity.registerEntity("Witch", EntityWitch.class);
        Entity.registerEntity("WitherSkeleton", EntityWitherSkeleton.class);
        Entity.registerEntity("Zombie", EntityZombie.class);
        Entity.registerEntity("ZombiePigman", EntityZombiePigman.class);
        Entity.registerEntity("ZombieVillager", EntityZombieVillager.class);
        //passive
        Entity.registerEntity("Bat", EntityBat.class);
        Entity.registerEntity("Chicken", EntityChicken.class);
        Entity.registerEntity("Cow", EntityCow.class);
        Entity.registerEntity("Donkey", EntityDonkey.class);
        Entity.registerEntity("Horse", EntityHorse.class);
        Entity.registerEntity("IronGolem", EntityIronGolem.class);
        Entity.registerEntity("Llama", EntityLlama.class);
        Entity.registerEntity("Mooshroom", EntityMooshroom.class);
        Entity.registerEntity("Mule", EntityMule.class);
        Entity.registerEntity("Ocelot", EntityOcelot.class);
        Entity.registerEntity("Parrot", EntityParrot.class);
        Entity.registerEntity("Pig", EntityPig.class);
        Entity.registerEntity("PolarBear", EntityPolarBear.class);
        Entity.registerEntity("Rabbit", EntityRabbit.class);
        Entity.registerEntity("Sheep", EntitySheep.class);
        Entity.registerEntity("SkeletonHorse", EntitySkeletonHorse.class);
        Entity.registerEntity("SnowGolem", EntitySnowGolem.class);
        Entity.registerEntity("Squid", EntitySquid.class);
        Entity.registerEntity("Villager", EntityVillager.class);
        Entity.registerEntity("Wolf", EntityWolf.class);
        Entity.registerEntity("ZombieHorse", EntityZombieHorse.class);

        //Bosses
        Entity.registerEntity("ElderGuardian", EntityElderGuardian.class);
        Entity.registerEntity("EnderDragon", EntityEnderDragon.class);
        Entity.registerEntity("EnderWither", EntityWither.class);

        //item
        Entity.registerEntity("ArmorStand", EntityArmorStand.class);
        Entity.registerEntity("EnderCrystal", EntityEnderCrystal.class);
        Entity.registerEntity("FallingSand", EntityFallingBlock.class);
        Entity.registerEntity("PrimedTnt", EntityPrimedTNT.class);
        Entity.registerEntity("Painting", EntityPainting.class);
        Entity.registerEntity("XpOrb", EntityXPOrb.class);
        Entity.registerEntity("MinecartRideable", EntityMinecartEmpty.class);
        Entity.registerEntity("MinecartChest", EntityMinecartChest.class);
        Entity.registerEntity("MinecartHopper", EntityMinecartHopper.class);
        Entity.registerEntity("MinecartTnt", EntityMinecartTNT.class);
        Entity.registerEntity("Boat", EntityBoat.class);

        //projectile
        Entity.registerEntity("Arrow", EntityArrow.class);
        Entity.registerEntity("DragonFireball", EntityDragonFireball.class);
        Entity.registerEntity("Egg", EntityEgg.class);
        Entity.registerEntity("EnderPearl", EntityEnderPearl.class);
        Entity.registerEntity("ThrownExpBottle", EntityExpBottle.class);
        Entity.registerEntity("Fireball", EntityFireball.class);
        Entity.registerEntity("FireworkRocket", EntityFireworkRocket.class);
        Entity.registerEntity("FishingHook", EntityFishingHook.class);
        Entity.registerEntity("ThrownPotion", EntityPotion.class);
        Entity.registerEntity("ThrownLingeringPotion", EntityPotionLingering.class);
        Entity.registerEntity("ShulkerBullet", EntityShulkerBullet.class);
        Entity.registerEntity("Snowball", EntitySnowball.class);

        //weather
        Entity.registerEntity("Lightning", EntityLightning.class);

        //other
        Entity.registerEntity("Human", EntityHuman.class, true);
    }

    private void registerBlockEntities() {
        BlockEntity.registerBlockEntity(BlockEntity.FURNACE, BlockEntityFurnace.class);
        BlockEntity.registerBlockEntity(BlockEntity.CHEST, BlockEntityChest.class);
        BlockEntity.registerBlockEntity(BlockEntity.SIGN, BlockEntitySign.class);
        BlockEntity.registerBlockEntity(BlockEntity.ENCHANT_TABLE, BlockEntityEnchantTable.class);
        BlockEntity.registerBlockEntity(BlockEntity.SKULL, BlockEntitySkull.class);
        BlockEntity.registerBlockEntity(BlockEntity.FLOWER_POT, BlockEntityFlowerPot.class);
        BlockEntity.registerBlockEntity(BlockEntity.BREWING_STAND, BlockEntityBrewingStand.class);
        BlockEntity.registerBlockEntity(BlockEntity.ITEM_FRAME, BlockEntityItemFrame.class);
        BlockEntity.registerBlockEntity(BlockEntity.CAULDRON, BlockEntityCauldron.class);
        BlockEntity.registerBlockEntity(BlockEntity.ENDER_CHEST, BlockEntityEnderChest.class);
        BlockEntity.registerBlockEntity(BlockEntity.BEACON, BlockEntityBeacon.class);
        BlockEntity.registerBlockEntity(BlockEntity.SHULKER_BOX, BlockEntityShulkerBox.class);
        BlockEntity.registerBlockEntity(BlockEntity.MOB_SPAWNER, BlockEntityMobSpawner.class);
        BlockEntity.registerBlockEntity(BlockEntity.DISPENSER, BlockEntityDispenser.class);
        BlockEntity.registerBlockEntity(BlockEntity.DROPPER, BlockEntityDropper.class);
        BlockEntity.registerBlockEntity(BlockEntity.COMMAND_BLOCK, BlockEntityCommandBlock.class);
        BlockEntity.registerBlockEntity(BlockEntity.BANNER, BlockEntityBanner.class);
    }

    /**
     * サーバーのインスタンスを取得します。
     * @return Server サーバーのインスタンス
     */
    public static Server getInstance() {
        return instance;
    }

    /**
     * GUI機能を使うかどうかを取得します。
     * @return boolean 使う場合はtrue、使わない場合はfalse
     */
    public boolean checkingUsingGUI(){
        return this.getJupiterConfigBoolean("using-gui", true);
    }

    /**
     * 送受信しているパケットを表示するかどうかを取得します。
     * @return boolean する場合はtrue、しない場合はfalse
     */
    public boolean printPackets(){
        return this.printPackets;
    }

    /*TODO getAI()
    public AI getAI() {
        return this.ai;
    }
     */
}
