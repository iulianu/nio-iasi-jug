package rocks.iulian.niopresentation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client {

    private static final SocketAddress SERVER_ADDRESS = new InetSocketAddress("localhost", 8098);

    public static void main(String[] args) throws IOException {
        var socket = new Socket();
        socket.connect(SERVER_ADDRESS);
        var is = socket.getInputStream();
        int countRead = -1;
        byte[] buffer = new byte[8192];
        long totalCountRead = 0;
        while( (countRead = is.read(buffer)) != -1 ) {
            totalCountRead += countRead;
        }
        System.out.printf("Successfully read %d bytes", totalCountRead);
    }
}
