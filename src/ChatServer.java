import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер запущен на порту " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Новый клиент подключен: " + clientSocket.getInetAddress());

            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            clientHandler.start();
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}