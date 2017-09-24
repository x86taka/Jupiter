package cn.nukkit.window.element;

public class Toggle implements Element{
	
	private final String type = "toggle";
	private String text;
	private boolean defaultValue;
	
	public Toggle(String text){
		this(text, false);
	}
	
	public Toggle(String text, boolean defaultValue){
		this.text = text;
		this.defaultValue = defaultValue;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setValue(boolean defaultValue){
		this.defaultValue = defaultValue;
	}
	
	public String getText(){
		return this.text;
	}
	
	public boolean getDefaultValue(){
		return defaultValue;
	}

}
