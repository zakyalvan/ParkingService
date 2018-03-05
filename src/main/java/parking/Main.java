package parking;

import parking.command.core.CommandDispatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * @author zakyalvan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        CommandDispatcher commandDispatcher = CommandDispatcher.defaultInstance();

        if(args.length == 0) {
            boolean stopScan = false;
            Scanner scanner = new Scanner(System.in);
            while (!stopScan) {
                System.out.println("Input:");
                String inputLine = scanner.nextLine();
                if(inputLine.trim().length() == 0) {
                    System.out.println("");
                }
                else if(inputLine.trim().equalsIgnoreCase("EXIT")) {
                    stopScan = true;
                    System.out.println("");
                    System.out.println("Output:");
                    System.out.println("Bye...");
                }
                else {
                    System.out.println("");
                    System.out.println("Output:");
                    String dispatchResult = commandDispatcher.dispatch(inputLine);
                    System.out.println(dispatchResult);
                    System.out.println("");
                }
            }
        }
        else if(args.length == 1) {
            File file = new File(args[0]);
            if(!file.exists() || !file.canRead()) {
                System.out.println("Error : Input file is not exists or unreadable");
                System.exit(1);
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                if(inputLine.trim().length() > 0) {
                    String dispatchResult = commandDispatcher.dispatch(inputLine);
                    System.out.println(dispatchResult);
                }
            }
        }
        else {
            System.out.println(String.format("Error : Given arguments is %d, which is not supported", args.length));
            System.exit(1);
        }
    }
}
