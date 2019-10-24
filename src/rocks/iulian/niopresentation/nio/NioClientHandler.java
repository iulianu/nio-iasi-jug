package rocks.iulian.niopresentation.nio;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public interface NioClientHandler {

    void handle(SocketChannel socket, String fileName) throws IOException;

}
