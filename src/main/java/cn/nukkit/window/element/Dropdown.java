package cn.nukkit.window.element;

import java.util.ArrayList;
import java.util.List;

public class Dropdown implements Element{
	
	public final String type = "dropdown";
	private List<String> options = new ArrayList<String>();;
	private int defaultOptionIndex = 0;
	
	public Dropdown(List<String> options){
		this(options, 0);
	}
	
	public Dropdown(List<String> options, int def){
		this.options = options;
		this.defaultOptionIndex = def;
	}
	
	public List<String> getOptions(){
		return this.options;
	}
	
	public int getDefaultOptionIndex(){
		return this.defaultOptionIndex;
	}
	
	public void setDefaultOptionIndex(int index){
		this.defaultOptionIndex = index;
	}
	
	public void addOption(String option){
		this.options.add(option);
	}
	
	public void removeOption(String option){
		if(this.options.contains(option)){
			this.options.remove(option);
		}
	}
	
	public void resetOption(){
		this.options.clear();
	}
	
	public int getOptionSize(){
		return this.options.size();
	}
	
	public void setOption(List<String> options){
		this.options = options;
	}

	@Override
	public String getName() {
		return "Dropdown";
	}

}
