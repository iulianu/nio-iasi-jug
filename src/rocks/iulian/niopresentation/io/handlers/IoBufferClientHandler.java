package rocks.iulian.niopresentation.io.handlers;

import rocks.iulian.niopresentation.io.IoClientHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class IoBufferClientHandler implements IoClientHandler {

    @Override
    public void handle(Socket clientSocket, String dataFile) throws IOException  {
        try(var dataIs = new FileInputStream(dataFile)) {
            var socketOs = clientSocket.getOutputStream();
            var buffer = new byte[8192];
            var countRead = -1;
            while ((countRead = dataIs.read(buffer)) != -1) {
                socketOs.write(buffer, 0, countRead);
            }
        }
    }

}
