import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServerThread {
    private static ServerSocket servSock;
    private static final int PORT = 12345;

    public TCPEchoServerThread() {
    }

    public void start() {
        try {
            servSock = new ServerSocket(PORT);

            while (true) {
                Socket clientSocket = servSock.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Menutup koneksi....");
                servSock.close();
            } catch (IOException e) {
                System.out.println("Tidak dapat menutup koneksi");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        TCPEchoServerThread server = new TCPEchoServerThread();
        server.start();
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Menerima pesan: " + message);
                out.println("Server menerima pesan: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Menutup koneksi dengan klien.");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}