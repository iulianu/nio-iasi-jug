package rocks.iulian.niopresentation.io;

import rocks.iulian.niopresentation.io.handlers.IoBufferClientHandler;
import rocks.iulian.niopresentation.io.handlers.IoByteClientHandler;
import rocks.iulian.niopresentation.io.handlers.IoTransferFileHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

    private static final int SERVER_PORT = 8098;
    // private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/small_file";
    private static final IoClientHandler handler = new IoTransferFileHandler();
    private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/large_file";

    private void serve(Socket clientSocket) {
        try {
            var startTime = System.currentTimeMillis();
            handler.handle(clientSocket, DATA_FILE);
            clientSocket.close();
            var endTime = System.currentTimeMillis();
            System.out.printf("Served in %.2f seconds\n", (endTime - startTime) / 1000.0);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            var serverSocket = new ServerSocket(SERVER_PORT);
            while(true) {
                try {
                    var clientSocket = serverSocket.accept();
                    new Thread(() -> serve(clientSocket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new IoServer().run();
    }
}
