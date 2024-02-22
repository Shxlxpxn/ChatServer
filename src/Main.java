import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 1234;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат порта: " + args[0]);
                System.exit(1);
            }
        }

        ChatServer server = new ChatServer(port);
        server.start();
    }
}