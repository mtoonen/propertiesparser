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
    private String value;
    private Integer index;
    private Properties.LineType lineType;

    public Property(String value, Properties.LineType lineType, Integer index) {
        this.value = value;
        this.lineType = lineType;
    }

    public Property(Properties.LineType lineType, Integer index) {
        this.lineType = lineType;
    }
    
    public Property(String key, String value, Integer index, Properties.LineType lineType) {
        this.key = key;
        this.value = value;
        this.index = index;
        this.lineType = lineType;
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

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public Properties.LineType getLineType() {
        return lineType;
    }

    public void setLineType(Properties.LineType lineType) {
        this.lineType = lineType;
    }
    
    @Override
    public String toString(){
        if(lineType ==  Properties.LineType.PROPERTY){
            return key + "=" + value;
        }else if( lineType == Properties.LineType.EMPTY){
            return "";
        }else if( lineType == Properties.LineType.COMMENT){
            return value;
        }else{
            System.err.println("Error!");
        }
        
        return null;
    }
    
    @Override
    public int compareTo(Property o) {
        return o.getIndex().compareTo(this.getIndex());
    }
}
