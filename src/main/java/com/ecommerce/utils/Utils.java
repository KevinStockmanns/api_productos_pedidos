package com.ecommerce.utils;

public class Utils {
    
    public static String formatTitle(String text){ 
        String title = "";
        for(String word: text.trim().split("\s+")){
            title += word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() + " ";
        }
        title = title.trim();
        if(title.endsWith("."))
            title = title.substring(0, title.length()-1);
        return title;
    }

    public static String formatText(String text){
        text = text.trim();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        return text;
    }
}
