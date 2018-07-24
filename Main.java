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

            if(args.length < 2 || args[1].isEmpty()){
                sb.append("Cannot send an email with no body.\n");
                console.write(sb.toString());
                return;
            }

            String[] emails;
            String msg;
            if(args[0].equals("-im")){
                emails = args[1].split(",");
                msg = args[2];

            }else{
                emails = args[0].split(",");
                msg = args[1];
            }

            //for story 4, the example given does not quite agree with the description, i.e., comma-separated one arguments vs. space-separated several arguments
            //here we stick with comma-separated.

            StringBuilder sbInvalidEmail = new StringBuilder();
            for(int i = 0; i < emails.length; i++){
                if(!validEmail(emails[i])){
                    sbInvalidEmail.append(" ").append(emails[i]).append(",");
                }
            }

            if(sbInvalidEmail.length() > 0){
                sbInvalidEmail.deleteCharAt(sbInvalidEmail.length() - 1);
                if(sbInvalidEmail.indexOf(",") == -1){
                    sb.append("Invalid email address:");
                }else{
                    sb.append("Invalid email addresses:");
                }
                sb.append(sbInvalidEmail).append("\n");
                console.write(sb.toString());
                return;
            }

            //handle differently for email and chat
            if(args[0].equals("-im")){
                sb.append("connect chat\n");
                for(int i = 0; i < emails.length; i++) {
                    sb.append('<').append(emails[i]).append('>').append('(').append(msg).append(')').append("\n");
                }
                sb.append("disconnect\n");
            }else {
                sb.append("connect smtp\n");
                for (int i = 0; i < emails.length; i++) {
                    sb.append("To: ").append(emails[i]).append("\n");
                }
                sb.append("\n").append(args[1]).append("\n\n").append("disconnect\n");

            }
            network.write(sb.toString());
        }catch(IOException ioe){
            try{
                console.write("Connection error. Please try again.\n");
            }catch(IOException ioe2){
                ioe2.printStackTrace();
            }finally {
                //
            }

        }finally {
            //
        }

    }


    public static boolean validEmail(String email){
        if(email == null || email.length() < 3 || !email.contains("@")){
            return false;
        }
        return true;
    }

}