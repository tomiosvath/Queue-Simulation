/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for reading data from an input file and writing data to an output file
 */
package model;

import controller.SimulationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileService {
    private static String inFile, outFile;
    private static FileWriter writer;

    /**
     * Static function for setting the input and output file
     * @param in input file
     * @param out output file
     */
    public static void setFiles(String in, String out) {
        inFile = in;
        outFile = out;
        try {
            writer = new FileWriter(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function for returning the output file
     * @return output file
     */
    public static String getOutFile(){
        return outFile;
    }

    /**
     * Function for reading the data into a SimulationManager
     * @param manager the SimulationManager object
     */
    public static void readSimulationData(controller.SimulationManager manager){
        String s = null;
        try {
            s = new Scanner(new File(inFile)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String arr[] = s.split("\\r?\\n");

        String integers[] = new String[7];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            String line[] = arr[i].split(",");
            for(int j = 0 ; j < line.length ; j++){
                integers[index++] = line[j];
            }
        }

        manager.setNumberOfClients(Integer.parseInt(integers[0]));
        manager.setNumberOfQueues(Integer.parseInt(integers[1]));
        manager.setTimeLimit(Integer.parseInt(integers[2]));
        manager.setMinArrivalTime(Integer.parseInt(integers[3]));
        manager.setMaxArrivalTime(Integer.parseInt(integers[4]));
        manager.setMinProcessingTime(Integer.parseInt(integers[5]));
        manager.setMaxProcessingTime(Integer.parseInt(integers[6]));
    }

    /**
     * Function for writing a line into a file
     * @param str the String to be written
     */
    public static void write(String str){
        try {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function for writing a line into a file, and then appending a newline
     * @param str the String to be written
     */
    public static void writeln(String str){
        try {
            writer.write(str);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function for closing the file
     */
    public static void closeFile(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
