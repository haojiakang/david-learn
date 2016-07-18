package com.david.learn;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * Created by haojk on 5/25/16.
 */
public class CharsetTest {

    private void listCharsets(){
        SortedMap<String, Charset> map = Charset.availableCharsets();
        for(String alias: map.keySet()){
            System.out.println(alias+"----->"+map.get(alias));
        }
    }

    public static void main(String[] args){
        CharsetTest test = new CharsetTest();
        test.listCharsets();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
    }

}
