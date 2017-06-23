package cn.nukkit.javascript;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.nukkit.plugin.PluginDescription;
import cn.nukkit.utils.Utils;

public class JavaScriptPluginLoader {
	
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
        return this.getPluginDescription(new File(filename));
    }
}
