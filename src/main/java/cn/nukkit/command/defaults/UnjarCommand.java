package cn.nukkit.command.defaults;

import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;

public class UnjarCommand extends VanillaCommand{

	static CommandSender sender;
	int collect = 0;

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
		        sender.sendMessage((i + 1) + ": " + TextFormat.GREEN +  file.getName());
		    }
		    return true;
		}

		try {
			FileSystem fileSystem = FileSystems.getDefault ();

			Path inputFile = fileSystem.getPath (sender.getServer().getPluginPath() + args[0]);
			sender.sendMessage(inputFile.toString() + "を解凍します。");
			Path outFile = Paths.get(path + ".zip");

			if(!new File(path).exists()){
				Files.copy (inputFile, outFile, REPLACE_EXISTING);

				if (!Files.exists (outFile)){
					sender.sendMessage(TextFormat.RED + "エラーが発生しました。(ディレクトリが存在しません。)");
					return false;
				}
				if (Files.size (outFile) != Files.size (outFile) ) {
					sender.sendMessage(TextFormat.RED + "エラーが発生しました。(ファイルサイズが等しくありません。)");
				return false;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
			return false;
		}

		sender.sendMessage(TextFormat.GREEN + "転送完了");

		File f = new File(path);
		File f1 = new File(path + ".zip");

		//f.renameTo(f1);

		try {
			unZip(f1, new File(path.replaceAll(".jar", "")));
		} catch (IOException e) {
			e.printStackTrace();
			sender.sendMessage(TextFormat.RED + "エラーが発生しました。(IOException)");
			return false;
		}

		f.delete();
		f1.delete();

        sender.sendMessage(TextFormat.GREEN + "解凍完了");

		return true;
	}

	public static List<File> unZip(final File zipFile, final File outputDir) throws FileNotFoundException, ZipException,
			IOException {
			if (zipFile == null) {
				throw new IllegalArgumentException("引数(zipFile)がnullです。");
			}
			if (outputDir == null) {
				throw new IllegalArgumentException("引数(outputDir)がnullです。");
			}
			if (outputDir.exists() && !outputDir.isDirectory()) {
				throw new IllegalArgumentException(
					"引数(outputDir)はディレクトリではありません。outputDir=" + outputDir);
			}

			// 出力ディレクトリ直下に解凍されたファイルまたディレクトリのセット
			final Set<File> fileSet = new HashSet<File>();

			// 解凍したファイルの親ディレクトリのセット
			final Set<File> parentDirSet = new HashSet<File>();

			ZipFile zip = null;
			try {
				try {
					// 文字コードを指定することで文字化けを回避
					zip = new ZipFile(zipFile);
				} catch (IOException e) {
					throw e;
				}

				final Enumeration<?> zipEnum = zip.entries();
				while (zipEnum.hasMoreElements()) {
					// 解凍するアイテムを取得
					final ZipEntry entry = (ZipEntry) zipEnum.nextElement();

					if (entry.isDirectory()) {
						// 解凍対象がディレクトリの場合
						final File dir = new File(outputDir, entry.getName());
						if (dir.getParentFile()
							.equals(outputDir)) {
							// 親ディレクトリが出力ディレクトリなのでfileSetに格納
							fileSet.add(dir);
						}
						// ディレクトリは自分で生成
						if (!dir.exists() && !dir.mkdirs()) {
							sender.sendMessage(TextFormat.RED + "エラーが発生しました。(ディレクトリ: " + dir + ")");
						}
					} else {
						// 解凍対象がファイルの場合
						final File file = new File(outputDir, entry.getName());
						final File parent = file.getParentFile();
						assert parent != null;

						if (parent.equals(outputDir)) {
							// 解凍ファイルの親ディレクトリが出力ディレクトリの場合
							fileSet.add(file);
						}

						if (!parentDirSet.contains(parent)) {
							// 親ディレクトリが初見の場合
							parentDirSet.add(parent);

							// 解凍ファイルの上位にある出力ディレクトリ直下のディレクトリを取得
							final File rootDir = getRootDir(outputDir, file);
							assert rootDir != null;
							fileSet.add(rootDir);

							// 親ディレクトリを生成
							if (!parent.exists() && !parent.mkdirs()) {
								sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
							}
						}

						// 解凍対象のファイルを書き出し
						FileOutputStream fos = null;
						InputStream is = null;
						try {
							fos = new FileOutputStream(file);
							is = zip.getInputStream(entry);

							byte[] buf = new byte[1024];
							int size = 0;
							while ((size = is.read(buf)) != -1) {
								fos.write(buf, 0, size);
							}
							fos.flush();
						} catch (FileNotFoundException e) {
							throw e;
						} catch (ZipException e) {
							throw e;
						} catch (IOException e) {
							throw e;
						} finally {
							if (fos != null) {
								try {
									fos.close();
								} catch (IOException e1) {
									sender.sendMessage(TextFormat.RED + "エラーが発生しました。(IOException)");
									e1.printStackTrace();
									return null;
								}
							}
							if (is != null) {
								try {
									is.close();
								} catch (IOException e1) {
									sender.sendMessage(TextFormat.RED + "エラーが発生しました。(IOException)");
									e1.printStackTrace();
									return null;
								}
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				throw e;
			} catch (ZipException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} finally {
				if (zip != null) {
					try {
						zip.close();
					} catch (IOException e) {
						sender.sendMessage(TextFormat.RED + "エラーが発生しました。(IOException)");
						e.printStackTrace();
						return null;
					}
				}
			}
			//Listに変換
			List<File> retList = new ArrayList<File>(fileSet);
			// ソート
			Collections.sort(retList);

			return retList;
		}

	private static File getRootDir(final File dir, final File file) {
		assert dir != null;
		assert !dir.exists() || dir.exists() && dir.isDirectory();
		assert file != null;

		final File parent = file.getParentFile();
		if (parent == null) {
			sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
			return null;
		}
		if (parent.equals(dir)) {
			return file;
		}
		return getRootDir(dir, parent);
	}
}
