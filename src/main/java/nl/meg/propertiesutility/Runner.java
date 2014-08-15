/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meg.propertiesutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author meine
 */
public class Runner {
    
    public static void main (String[] args) throws FileNotFoundException, IOException{
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("voorbeeld.properties");
        props.load(fis);
        props.printList();
    }
    
}
