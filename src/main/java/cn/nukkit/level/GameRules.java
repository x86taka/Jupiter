package cn.nukkit.level;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import cn.nukkit.nbt.tag.CompoundTag;

public class GameRules {
    private final TreeMap<String, Value> theGameRules = new TreeMap<>();

    public GameRules() {
        this.addGameRule("sendcommandfeedback", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("tntexplodes", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("naturalregeneration", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("showcoordinates", "false", ValueType.BOOLEAN_VALUE);
        this.addGameRule("pvp", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("mobgriefing", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doweathercycle", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("firedamage", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("domobspawning", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("drowingdamage", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("falldamage", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("domobloot", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("dotiledrops", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("dofiretick", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("doentitydrops", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("dodaylightcycle", "true", ValueType.BOOLEAN_VALUE);
        this.addGameRule("keepinventory", "false", ValueType.BOOLEAN_VALUE);
        this.addGameRule("commandblockoutput", "true", ValueType.BOOLEAN_VALUE);
    }

    public void addGameRule(String key, String value, ValueType type) {
        this.theGameRules.put(key, new GameRules.Value(value, type));
    }

    public void setGameRule(String key, String ruleValue) {
        Value value = this.theGameRules.get(key);

        if (value != null) {
            value.setValue(ruleValue);
        } else {
            this.addGameRule(key, ruleValue, ValueType.ANY_VALUE);
        }
    }

    public String getString(String name) {
        Value value = this.theGameRules.get(name);
        return value != null ? value.getString() : "";
    }

    public boolean getBoolean(String name) {
        Value value = this.theGameRules.get(name);
        return value != null && value.getBoolean();
    }

    public int getInt(String name) {
        Value value = this.theGameRules.get(name);
        return value != null ? value.getInt() : 0;
    }

    public CompoundTag writeNBT() {
        CompoundTag nbt = new CompoundTag();

        for (Entry<String, Value> entry : this.theGameRules.entrySet()) {
            nbt.putString(entry.getKey(), entry.getValue().getString());
        }

        return nbt;
    }

    public void readNBT(CompoundTag nbt) {
        for (String key : nbt.getTags().keySet()) {
            this.setGameRule(key, nbt.getString(key));
        }
    }

    public String[] getRules() {
        Set<String> set = this.theGameRules.keySet();
        return set.toArray(new String[set.size()]);
    }

    public boolean hasRule(String name) {
        return this.theGameRules.containsKey(name);
    }

    public boolean areSameType(String key, ValueType otherValue) {
        Value value = this.theGameRules.get(key);
        return value != null && (value.getType() == otherValue || otherValue == ValueType.ANY_VALUE);
    }

    private static class Value {

        private String valueString;

        private boolean valueBoolean;

        private int valueInteger;

        private final ValueType type;

        public Value(String value, ValueType type) {
            this.type = type;
            this.setValue(value);
        }

        public void setValue(String value) {
            this.valueString = value;
            this.valueBoolean = Boolean.parseBoolean(value);
            this.valueInteger = this.valueBoolean ? 1 : 0;

            try {
                this.valueInteger = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                //ignore
            }
        }

        public String getString() {
            return this.valueString;
        }

        public boolean getBoolean() {
            return this.valueBoolean;
        }

        public int getInt() {
            return this.valueInteger;
        }

        public ValueType getType() {
            return this.type;
        }
    }

    public enum ValueType {
        ANY_VALUE,
        BOOLEAN_VALUE,
        NUMERICAL_VALUE;
    }
}
