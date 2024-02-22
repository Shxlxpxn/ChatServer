import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final ChatServer server;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientHandler(Socket clientSocket, ChatServer server) throws IOException {
        this.clientSocket = clientSocket;
        this.server = server;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String name = in.readLine();
            server.broadcastMessage(name + " присоединился к чату");

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }

                server.broadcastMessage(name + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Клиент " + clientSocket.getInetAddress() + " отключен");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
