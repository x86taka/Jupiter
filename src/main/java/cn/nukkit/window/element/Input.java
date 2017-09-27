package cn.nukkit.window.element;

public class Input implements Element{
	
	private final String type = "input";
	private String text;
	private String placeholder;
	private String defaultText;
	
	public Input(String text, String placeholder, String defaultText){
		this.text = text;
		this.placeholder = placeholder;
		this.defaultText = defaultText;
	}
	
	public String getText(){
		return this.text;
	}
	
	public String getPlaceHolder(){
		return this.placeholder;
	}
	
	public String getDefaultText(){
		return this.defaultText;
	}

	@Override
	public String getName() {
		return "Input";
	}

}
