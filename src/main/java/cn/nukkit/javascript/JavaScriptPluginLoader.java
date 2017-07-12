package cn.nukkit.javascript;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.utils.Utils;

public class JavaScriptPluginLoader {

	private Server server;

	private final Map<String, ScriptLoader> loaders = new HashMap<>();

	public JavaScriptPluginLoader(){
		this.server = Server.getInstance();
	}

	public JavaScriptPlugin loadPlugin(File file) throws Exception{
		PluginDescription description = this.getPluginDescription(file);
		if (description != null) {
            this.server.getLogger().info("JaveScriptプラグイン: " + description.getFullName() + "を読み込み中...");
            File dataFolder = new File(file.getParentFile(), description.getName());
            if (dataFolder.exists() && !dataFolder.isDirectory()) {
                throw new IllegalStateException("Projected dataFolder '" + dataFolder.toString() + "' for " + description.getName() + " exists and is not a directory");
            }

            String className = description.getMain();
            ScriptLoader loader = new ScriptLoader(this);
            this.loaders.put(description.getName(), loader);
		}

		return null;
	}

    public PluginDescription getPluginDescription(File file) {
        try (ZipFile zip = new ZipFile(file)) {
            ZipEntry entry = zip.getEntry("nukkit.yml");
            if (entry == null) {
                entry = zip.getEntry("plugin.yml");
                if (entry == null) {
                    return null;
                }
            }
            try (InputStream stream = zip.getInputStream(entry)) {
                return new PluginDescription(Utils.readFile(stream));
            }
        } catch (IOException e) {
            return null;
        }
    }

    public PluginDescription getPluginDescription(String filename) {
        return this.getPluginDescription(new File(Server.getInstance().getPluginPath() + filename));
    }
}
