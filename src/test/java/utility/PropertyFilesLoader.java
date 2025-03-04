package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFilesLoader {


    static Properties prop = new Properties();

    /**
     * This method will get the key value from properties  file
     *  It will get the data from project level we need to pass specific folder path
     * @param key
     * @param folderLocation
     * @return driver instance
     * @throws IOException
     * @overloaded
     */
    public static String getProperty(String key, String folderLocation) throws IOException {
        String path = System.getProperty("user.dir") + folderLocation;
        File baseFileLocation = new File(path);

        for (File file : baseFileLocation.listFiles()) {
            if (file.getName().endsWith(".properties")) {
                try {
                    FileInputStream inputFile = new FileInputStream(file);
                    prop.load(inputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error : " + e);
                }

            }
        }
        return prop.getProperty(key);
    }

    /**
     * This method will get the key value from properties  file
     * it will get the data from single folder, because file path hot coded
     * @param key
     * @return String
     * @throws IOException
     */
    public static String GetProperty(String key) throws IOException {
        Properties prop = new Properties();
        String path = System.getProperty("user.dir") + "/src/test/resources/testDataFiles";
        File baseFileLocation = new File(path);

        for (File file : baseFileLocation.listFiles()) {
            if (file.getName().endsWith(".properties")) {
                try {
                    FileInputStream inputFile = new FileInputStream(file);
                    prop.load(inputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error : " + e);
                }

            }
        }
        return prop.getProperty(key);
    }

    /**
     * This Method NonStatic Method Based on Your requirement use it
     * @param key
     * @return
     */
    public String getProperty(String key) {
        Properties prop = new Properties();
        String path = System.getProperty("user.dir") + "/src/test/resources/testDataFiles";
        File baseFileLocation = new File(path);

        for (File file : baseFileLocation.listFiles()) {
            if (file.getName().endsWith(".properties")) {
                try {
                    FileInputStream inputFile = new FileInputStream(file);
                    prop.load(inputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Error : " + e);
                }

            }
        }
        return prop.getProperty(key);
    }



}
