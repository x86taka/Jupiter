package cn.nukkit.window.element;

import java.util.ArrayList;
import java.util.List;

public class StepSlider implements Element{
	
	private final String type = "step_slider";
	private List<String> steps = new ArrayList<String>();
	private int defaultIndex;
	private String text;
	
	public StepSlider(String text, List<String> steps, int defaultIndex){
		this.text = text;
		this.steps = steps;
		this.defaultIndex = defaultIndex;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
	
	
	public void addStep(String step){
		if(!(this.steps.contains(steps))){
			this.steps.add(step);
		}
	}
	
	public void removeStep(int step){
		if(this.steps.contains(step)){
			this.steps.remove((Integer) step);
		}
	}
	
	public void removeSteps(){
		this.steps.clear();
	}
	
	public List<String> getSteps(){
		return this.steps;
	}
	
	public void setSteps(List<String> steps){
		this.steps = steps;
	}
	
	public void setdefaultIndex(int index){
		this.defaultIndex = index;
	}
	
	public int getDefaultIndex(){
		return this.defaultIndex;
	}
	
	@Override
	public String getName() {
		return "StepSlider";
	}

}
