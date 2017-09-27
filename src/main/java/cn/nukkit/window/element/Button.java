package cn.nukkit.window.element;

public class Button implements Element{
	
	private final String type = "button";
	private String text;
	private String imageType;
	private String imagePath;
	
	public Button(String text){
		this.text = text;
	}
	
	public void setImage(String type, String path){
		if(!type.equals("path") || !(type.equals("url"))){
			try {
				throw new Exception("許可されていないタイプの画像です！pathもしくはurlのみが許可されています！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.imageType = type;
		this.imagePath = path;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}

	@Override
	public String getName() {
		return "Button";
	}

}
