/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meg.propertiesutility;

/**
 *
 * @author meine
 */
public class Property implements Comparable<Property>{
    private String key;
    private String origKey;
    
    private String value;
    private String origValue;
    
    private final Integer index;
    private final Properties.LineType lineType;

    public Property(String value,String origValue, Properties.LineType lineType, Integer index) {
        this.value = value;
        this.lineType = lineType;
        this.index = index;
        this.origValue = origValue;
    }

    public Property(Properties.LineType lineType, Integer index) {
        this.lineType = lineType;
        this.index = index;
    }
    
    public Property(String key, String origKey,String value, String origValue,Integer index, Properties.LineType lineType) {
        this.key = key;
        this.value = value;
        this.index = index;
        this.lineType = lineType;
        this.origValue = origValue;
        this.origKey = origKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public Properties.LineType getLineType() {
        return lineType;
    }
    
    @Override
    public String toString(){
        if(lineType ==  Properties.LineType.PROPERTY){
            return origKey + "=" + this.origValue;
        }else if( lineType == Properties.LineType.EMPTY){
            return "";
        }else if( lineType == Properties.LineType.COMMENT){
            return this.origValue;
        }else{
            System.err.println("Error!");
        }
        
        return null;
    }
    
    @Override
    public int compareTo(Property o) {
        return this.getIndex().compareTo(o.getIndex());
    }
}
