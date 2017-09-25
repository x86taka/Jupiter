package cn.nukkit.window.element;

public class Label implements Element{
	
	private final String type = "label";
	private String text;
	
	public Label(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}

}
