package cn.nukkit.window.element;

import java.util.ArrayList;
import java.util.List;

public class StepSlider implements Element{
	
	private final String type = "strpslider";
	private List<Integer> steps = new ArrayList<Integer>();
	private int defaultIndex;
	
	public StepSlider(List<Integer> steps, int defaultIndex){
		this.steps = steps;
		this.defaultIndex = defaultIndex;
	}
	
	public void addStep(int step){
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
	
	public List<Integer> getSteps(){
		return this.steps;
	}
	
	public void setSteps(List<Integer> steps){
		this.steps = steps;
	}
	
	public void setdefaultIndex(int index){
		this.defaultIndex = index;
	}
	
	public int getDefaultIndex(){
		return this.defaultIndex;
	}

}
