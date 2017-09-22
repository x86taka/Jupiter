package cn.nukkit.network.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class AvailableCommandsPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.AVAILABLE_COMMANDS_PACKET;
    public String commands; //JSON-encoded command data
    public String unknown = "";
    
    public static final int ARG_FLAG_VALID = 0x100000;
    public static final int ARG_TYPE_INT       = 0x01;
    public static final int ARG_TYPE_FLOAT     = 0x02;
    public static final int ARG_TYPE_VALUE     = 0x03;
    public static final int ARG_TYPE_TARGET    = 0x04;
    public static final int ARG_TYPE_STRING    = 0x0d;
    public static final int ARG_TYPE_POSITION  = 0x0e;
    public static final int ARG_TYPE_RAWTEXT   = 0x11;
    public static final int ARG_TYPE_TEXT      = 0x13;
    public static final int ARG_TYPE_JSON  = 0x16;
    public static final int ARG_TYPE_COMMAND      = 0x1d;
    
    public static final int ARG_FLAG_ENUM = 0x200000;
    
	/**
	 * This is used for for /xp <level: int>L.
	 */
    public static final int ARG_FLAG_POSTFIX = 0x1000000;
    
    public String[] enumValues;
    public int enumValuesCount = 0;
    public String[] postfixes;
	private Map<String, Object>[] enums;
	private Map<String, Object>[] commandData;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
    	for(int i=0, count = (int) this.getUnsignedVarInt(); i<count;++i){
    		this.enumValues[i] = this.getString();
    	}
    	
    	this.enumValuesCount = this.enumValues.length;
    	
    	for(int i=0, count = (int) this.getUnsignedVarInt(); i<count;++i){
    		this.postfixes[i] = this.getString();
    	}
    	
    	for(int i=0, count = (int) this.getUnsignedVarInt(); i<count;++i){
    		this.enums[i] = this.getEnum();
    	}
    	
    	for(int i=0, count = (int) this.getUnsignedVarInt(); i<count;++i){
    		this.commandData[i] = this.getCommandData();
    	}
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(this.commands);
        this.putString(this.unknown);
    }
    
    public Map<String, Object> getEnum(){
    	Map<String, Object> retval = new HashMap<String, Object>();
    	retval.put("enumName", this.getString());
    	
    	this.enumValues = new String[]{};
    	
    	for(int i=0, count = (int) this.getUnsignedVarInt(); i<count;++i){
    		this.enumValues[i] = this.enumValues[this.getEnumValueIndex()];
    	}
    	
    	retval.put("enumValues", enumValues);
    	
    	return retval;
    }
    
    protected int getEnumValueIndex(){
    	if(this.enumValuesCount < 256){
    		return this.getByte();
    	}else if(this.enumValuesCount < 65536){
    		return this.getLShort();
    	}else{
    		return this.getLInt();
    	}
    }
    
    protected Map<String, Object> getCommandData(){
    	Map<String, Object> retval = new HashMap<String, Object>();
    	retval.put("commandName", this.getString());
    	retval.put("commandDescription", this.getString());
    	retval.put("byte1", this.getByte());
    	retval.put("byte2", this.getByte());
    	retval.put("aliasesEnum", this.enums[this.getLInt()] != null ? this.enums[this.getLInt()] : null);
    	
    	for(int i=0, overloadCount =(int) this.getUnsignedVarInt();i<overloadCount;++i){
    		for(int j=0, paramCount =(int) this.getUnsignedVarInt();i<paramCount;++i){
    			int type = this.getLInt();
    			
    			retval.put("overloads", new Object[]{i, "params", j, "paramName", this.getString()});
    			retval.put("overloads", new Object[]{i, "params", j, "paramType", type});
    			retval.put("overloads", new Object[]{i, "params", j, "optional", this.getBoolean()});
    		
    			if((type & ARG_FLAG_ENUM) == 1){
    				int index = type & 0xffff;
    				if(this.enums[index] != null ||!this.enums[index].isEmpty()){
    					retval.put("overloads", new Object[]{i, "params", j, "enum", this.enums[index]});
    				}else{
    					retval.put("overloads", new Object[]{i, "params", j, "enum", null});
    				}
    			}
    			
    			retval.put("overloads", new Object[]{i, "params", j, "paramTypeString", this.argTypeToString(type)});
    		}
    	}
    	return retval;
    }
    
    public String argTypeToString(int type){
    	if((type & ARG_FLAG_VALID) == 1){
    		if((type & ARG_FLAG_ENUM) == 1){
    			return "stringnum (" + (type & 0xffff) + ")";
    		}
    		
    		switch(type & 0xffff){
    			case ARG_TYPE_INT:
    				return "int";
    			case ARG_TYPE_FLOAT:
    				return "float";
    			case ARG_TYPE_VALUE:
    				return "mixed";
    			case ARG_TYPE_TARGET:
    				return "target";
    			case ARG_TYPE_STRING:
    				return "string";
    			case ARG_TYPE_POSITION:
    				return "xyz";
    			case ARG_TYPE_RAWTEXT:
    				return "rawtext";
    			case ARG_TYPE_TEXT:
    				return "text";
    			case ARG_TYPE_JSON:
    				return "json";
    			case ARG_TYPE_COMMAND:
    				return "command";
    		}
    	}else if(type != 0){
			int baseType = type >> 24;
    		String typeName = this.argTypeToString(ARG_FLAG_VALID | baseType) ;
    		String postfix = this.postfixes[type & 0xffff];
    		
    		return typeName + " (" + postfix + ")";
		}
    	return "unknown (" + type + ")";
    }
}
