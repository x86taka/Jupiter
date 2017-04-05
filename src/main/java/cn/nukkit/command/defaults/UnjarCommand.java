
//まだ未完成です。<Itsu>
//使えません。

package cn.nukkit.command.defaults;

import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;

public class UnjarCommand extends VanillaCommand{

	CommandSender sender;

	public UnjarCommand(String name) {
		super(name, "プラグインを解凍してメインクラスを取り出します。(引数にlistでプラグインフォルダ一覧表示)", "/unjar [ファイル名(フルネーム)] or list");
		this.setPermission("nukkit.command.unjar");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("Filename", CommandParameter.ARG_TYPE_TARGET, false)
        });
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

		if (!this.testPermission(sender)) {
            return true;
        }
		try{if(args[0] != null){}}
		catch(ArrayIndexOutOfBoundsException e){
			sender.sendMessage(TextFormat.RED + "ファイル名を入力してください。(またはlist)");
			return false;
		}

		String path = sender.getServer().getDataPath() + "unpackedPlugins/" + args[0];
		this.sender = sender;

		if(args[0].equals("list")){
		    File dir = new File(sender.getServer().getPluginPath());
		    File[] files = dir.listFiles();
		    sender.sendMessage(TextFormat.AQUA + "プラグインフォルダ一覧の表示:");
		    for (int i = 0; i < files.length; i++) {
		        File file = files[i];
		        sender.sendMessage((i + 1) + ":    " + TextFormat.GREEN +  file.getName());
		    }
		    return true;
		}

		try {
			FileSystem fileSystem = FileSystems.getDefault ();

			Path inputFile = fileSystem.getPath (sender.getServer().getPluginPath() + args[0]);
			sender.sendMessage(inputFile.toString() + "を解凍します。");
			Path outFile = Paths.get (path);

			Files.copy (inputFile, outFile, REPLACE_EXISTING);

			if (!Files.exists (outFile)){
				sender.sendMessage(TextFormat.RED + "エラーが発生しました。(ディレクトリが存在しません。)");
				return false;
			}
			if (Files.size (outFile) != Files.size (outFile) ) {
				sender.sendMessage(TextFormat.RED + "エラーが発生しました。(ファイルサイズが等しくありません。)");
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
			return false;
		}

		sender.sendMessage(TextFormat.GREEN + "転送完了");

		decode(new File(path + ".zip"), path.replaceAll(".jar", ""));

		File f = new File(path);
		File f1 = new File(path + ".zip");

		f.delete();
		f1.delete();

		return true;
	}

	 public void decode( File aZipFile, String aOutDir ){
	        FileInputStream  fileIn  = null;
	        FileOutputStream fileOut = null;

	        try{
	            File outDir = new File( aOutDir );
	            outDir.mkdirs();
	            fileIn = new FileInputStream( aZipFile );
	            ZipInputStream zipIn = new ZipInputStream( fileIn );

	            ZipEntry entry = null;
	            while( ( entry = zipIn.getNextEntry() ) != null ){
	                if( entry.isDirectory() ){
	                    String relativePath = entry.getName();
	                    outDir = new File( outDir, relativePath );
	                    outDir.mkdirs();

	                } else {
	                    String relativePath = entry.getName();
	                    File   outFile = new File( outDir, relativePath );

	                    File   parentFile = outFile.getParentFile();
	                    parentFile.mkdirs();

	                    fileOut = new FileOutputStream( outFile );

	                    byte[] buf  = new byte[ 256 ];
	                    int    size = 0;
	                    while( ( size = zipIn.read( buf ) ) > 0 ){
	                        fileOut.write( buf, 0, size );
	                    }
	                    fileOut.close();
	                    fileOut = null;
	                }
	                zipIn.closeEntry();
	            }

	        }catch( Exception e){
	            e.printStackTrace();
	            sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
	            return;
	        } finally {
	            if( fileIn != null ){
	                try{
	                    fileIn.close();
	                }catch( Exception e){
	                	sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
	                	return;
	                }
	            }
	            if( fileOut != null ){
	                try{
	                    fileOut.close();
	                }catch( Exception e){
	                	sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
	                	return;
	                }
	            }
	        }
	        sender.sendMessage(TextFormat.GREEN + "解凍完了");
	        return;
	    }

}
