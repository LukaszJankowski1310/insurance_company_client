import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) exit(-1);

        String serverHostname = args[0];
        int serverPort = Integer.parseInt(args[1]);

        Socket socket;
        try {
            socket = new Socket(serverHostname, serverPort);
        } catch (Exception e) {
            System.out.println("Connection failed");
            return;
        }

        Client client = new Client(socket);


        int message = getIdFromInput();
        client.writeMessage(String.valueOf(message));

        int size;
        try {
            size = Integer.parseInt(client.readMessage());
            if (size > 0) {
                displayTableOfUserOffers();
                for (int i = 0; i < size; i++) {
                    String data = client.readMessage();
                    System.out.println(data);
                }
            } else if (size == -1 ) {
                System.out.println("User doesn't exist");
            }
            else {
                System.out.println("We don't have any offers to you ");
            }

        } catch (NumberFormatException e) {
            System.out.println("Incorrect data format");
        }
        socket.close();
    }


    public static void displayTableOfUserOffers() {
        System.out.printf("%-10s %-10s %-10s %s%n", "brand", "model", "insurer", "price" + "\n");
    }

    public static int getIdFromInput() {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        while (id == null) {
            System.out.print("Podaj id: ");
            try {

                String id_str = scanner.next();
                id = Integer.parseInt(id_str);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }

        return id;
    }


}