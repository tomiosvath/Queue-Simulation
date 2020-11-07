/**
 * @Author Osvath Tamas
 * @Date 27.03.2020
 * Class for testing the application
 */
package controller;

import model.FileService;

public class Main {
    /**
     * The first argument is the input file, the second one the output file
     */
    public static void main(String[] args){
        String in = args[0];
        String out = args[1];
        FileService.setFiles(in, out);
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}
