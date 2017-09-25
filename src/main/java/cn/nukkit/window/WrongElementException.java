package cn.nukkit.window;

public class WrongElementException extends RuntimeException{
	
	public WrongElementException(String title){
		super("正しいパーツではありません！ タイトル(" + title + ")のウィンドウ");
	}

}
