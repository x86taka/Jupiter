package cn.nukkit.window.element;

public class Slider implements Element{

    private final String type = "slider";
    private String text;
    private float min = 0.0f;
    private float max = 0.0f;
    private float step = 0.0f;
    private float defaultValue = 0.0f;

    public Slider(String text, float max){
        this(text, 0, max);
    }

    public Slider(String text, float min, float max){
        this(text, min, max, 0.0f);
    }

    public Slider(String text, float min, float max, float step){
        this(text, min, max, 0.0f, 0.0f);
    }

    public Slider(String text, float min, float max, float step, float defaultValue){
        this.text = text;
        this.min = min;
        this.max = max;
        this.step = step;
        this.defaultValue = defaultValue;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setMin(float min){
        this.min = min;
    }

    public void setMax(float max){
        this.max = max;
    }

    public void setStep(float step){
        this.step =step;
    }

    public void setDefaultValue(float defaultValue){
        this.defaultValue = defaultValue;
    }

    public float getMin(){
        return this.min;
    }

    public float getMax(){
        return this.max;
    }

    public float getStep(){
        return this.step;
    }

    public float getDefaultValue(){
        return this.defaultValue;
    }

    @Override
    public String getName() {
        return "Slider";
    }

}
