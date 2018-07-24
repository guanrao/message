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
            if(args.length < 2 || args[1].isEmpty()){
                sb.append("Cannot send an email with no body.\n");
                console.write(sb.toString());
                return;
            }
            //for story 4, the example given does not quite agree with the description, i.e., comma-separated one arguments vs. space-separated several arguments
            //here we stick with comma-separated.
            String[] emails = args[0].split(",");

            sb.append("connect smtp\n");
            for(int i = 0; i < emails.length; i++){
                sb.append("To: ").append(emails[i]).append("\n");
            }
            sb.append("\n").append(args[1]).append("\n\n").append("disconnect\n");

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