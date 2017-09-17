package cn.nukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.nukkit.command.CommandReader;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.ServerKiller;

/**
 * `_   _       _    _    _ _
 * | \ | |     | |  | |  (_) |
 * |  \| |_   _| | _| | ___| |_
 * | . ` | | | | |/ / |/ / | __|
 * | |\  | |_| |   <|   <| | |_
 * |_| \_|\__,_|_|\_\_|\_\_|\__|
 */

/**
 * Nukkit启动类，包含{@code main}函数。<br>
 * The launcher class of Nukkit, including the {@code main} function.
 *
 * @author MagicDroidX(code) @ Nukkit Project
 * @author 粉鞋大妈(javadoc) @ Nukkit Project
 * @since Nukkit 1.0 | Nukkit API 1.0.0
 */
public class Nukkit {

    public final static String VERSION = "1.0dev";
    public final static String JUPITER_VERSION = ProtocolInfo.MINECRAFT_VERSION_NETWORK + ".4";
    public final static String API_VERSION = "1.0.0";
    public final static String CODENAME = "Jupiter";
    @Deprecated
    public final static String MINECRAFT_VERSION = ProtocolInfo.MINECRAFT_VERSION;
    @Deprecated
    public final static String MINECRAFT_VERSION_NETWORK = ProtocolInfo.MINECRAFT_VERSION_NETWORK;

    public final static String PATH = System.getProperty("user.dir") + "/";
    public final static String DATA_PATH = System.getProperty("user.dir") + "/";
    public final static String PLUGIN_PATH = DATA_PATH + "plugins";
    public static final long START_TIME = System.currentTimeMillis();
    public static boolean ANSI = true;
    public static boolean shortTitle = false;
    public static int DEBUG = 1;
    
    private static Collection<Callable<Server>> jobs = new ArrayList<Callable<Server>>();
    private static ExecutorService threadpool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
		        //Shorter title for windows 8/2012
		        String osName = System.getProperty("os.name").toLowerCase();
		        if (osName.contains("windows")) {
		            if (osName.contains("windows 8") || osName.contains("2012")) {
		                shortTitle = true;
		            }
		        }

		        //启动参数
		        for (String arg : args) {
		            switch (arg) {
		                case "disable-ansi":
		                    ANSI = false;
		                    break;
		            }
		        }

		        MainLogger logger = new MainLogger(DATA_PATH + "server.log");

                System.out.println("Minecraft PE用Jupiterサーバーを開始しています。");

                jobs.add(new Callable<Server>(){
                    @Override
                    public Server call() throws Exception {
                        return new Server(logger, PATH, DATA_PATH, PLUGIN_PATH);
                    }

                });

                try {
                    threadpool.invokeAll(jobs);
                    threadpool.shutdown();
                    if(threadpool.awaitTermination(1L,TimeUnit.MINUTES)){// 1 minutes
                        System.out.println("サーバーを停止しています...");
                        logger.info("スレッドが停止しました。");

                        for (Thread thread : java.lang.Thread.getAllStackTraces().keySet()) {
                            if (!(thread instanceof InterruptibleThread)) {
                                continue;
                            }
                            logger.debug("Stopping " + thread.getClass().getSimpleName() + " thread");
                            if (thread.isAlive()) {
                                thread.interrupt();
                            }
                        }

                        ServerKiller killer = new ServerKiller(8);
                        killer.start();

                        logger.shutdown();
                        logger.interrupt();

                        CommandReader.getInstance().removePromptLine();

                        System.out.println("サーバーが停止しました。");

                        System.exit(0);
                    }else{
                        System.out.println("サーバーの停止に失敗しました。");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    threadpool.shutdown();
                }
    }

}
