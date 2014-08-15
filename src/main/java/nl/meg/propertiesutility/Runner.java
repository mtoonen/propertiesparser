/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meg.propertiesutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author meine
 */
public class Runner {
    
    public static void main (String[] args) throws FileNotFoundException{
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("voorbeeld.properties");
        props.load(fis);
    }
    
}
