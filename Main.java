package net.messaging;

import java.io.*;

public class Main {
    private static Writer network;
    private static Writer console;

    public static void setNetwork(Writer network) {
        Main.network = network;
    }

    public static void setConsole(Writer console) {
        Main.console = console;
    }

    public static void main(String... args) {
        StringBuilder sb = new StringBuilder();
        try{
            if(!validEmail(args[0])){
                //Invalid email address: jowithnoatsign
                sb.append("Invalid email address: ").append(args[0]).append("\n");
                console.write(sb.toString());
                return;
            }
            sb.append("connect smtp\nTo: ").append(args[0]).append("\n\n").append(args[1]).append("\n\n").append("disconnect\n");

            network.write(sb.toString());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static boolean validEmail(String email){
        if(email == null || email.length() < 3 || !email.contains("@")){
            return false;
        }
        return true;
    }

}