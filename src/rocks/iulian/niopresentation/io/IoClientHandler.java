package rocks.iulian.niopresentation.io;

import java.io.IOException;
import java.net.Socket;

public interface IoClientHandler {

    void handle(Socket socket, String fileName) throws IOException;

}
