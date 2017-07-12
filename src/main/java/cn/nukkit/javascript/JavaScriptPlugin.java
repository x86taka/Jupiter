package cn.nukkit.javascript;

import java.io.File;
import java.io.InputStream;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;

public interface JavaScriptPlugin {
	
	void onLoad();
	
	void onEnable();
	
	void onDisable();
	
	boolean isEnabled();
	
	boolean isDisabled();
	
	File getDataFolder();
	
	PluginDescription getDescription();
	
	InputStream getResource(String filename);

	boolean saveResource(String filename);
	
	boolean saveResource(String filename, boolean replace);

    boolean saveResource(String filename, String outputName, boolean replace);
    
    Config getConfig();
    
    void saveConfig();
    
    void saveDefaultConfig();
    
    void reloadConfig();
    
    Server getServer();
    
    String getName();

    PluginLogger getLogger();
    
    JavaScriptPluginLoader getJavaScriptPluginLoader();
}
