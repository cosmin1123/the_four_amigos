package com.the_four_amigos.panic_helper;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: cosmin
 * Date: 10/19/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configurations {
    public static float alarmGravity = 1.0f; //vom citi din fisier
    public static boolean [] myConfiguration = {false, false, false, false, false, false, false, false, false};
    public static String mySms = "Type your message...";
    public static String [] myPhoneNumbers = new String[0];

    public static void loadConfigFile() throws IOException {
        File file = new File("MyConfigurations\\configFile.txt");
        BufferedReader configFile = new BufferedReader(new FileReader(file));

        String line = configFile.readLine();

        if (line != null) {
           alarmGravity = Float.parseFloat(line);
           line = configFile.readLine();

           if (line != null) {
               String[] myConfigs = new String[9];
               myConfigs = line.split(" ");
               for(int i = 0; i < myConfigs.length; i++) {
                   myConfiguration[i] = Boolean.parseBoolean(myConfigs[i]);
               }
               line = configFile.readLine();

               if (line != null) {
                   mySms = line;
                   line = configFile.readLine();

                   if (line != null) {
                       myPhoneNumbers = new String[line.split(" ").length];
                       myPhoneNumbers = line.split(" ");
                   }
               }
           }
        }
        configFile.close();
    }

    public static void saveConfigFile(float gravity, boolean [] myConfig, String mySms, String[] myPhoneNos) throws IOException {

        String newLine = System.getProperty("line.separator");
        String result = "";

        FileWriter f = new FileWriter(new File("MyConfigurations\\configFile.txt"));
        BufferedWriter configFile = new BufferedWriter(f);

        result += gravity + newLine;

        for (int i = 0; i < myConfig.length; i++) {
            result += myConfig[i] + " ";
        }

        result += newLine + mySms + newLine;

        for (int i = 0; i < myConfig.length; i++) {
            result += myPhoneNos[i] + " ";
        }

        configFile.write(result);
        configFile.close();
    }
}
