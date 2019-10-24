package rocks.iulian.niopresentation.io.handlers;

import rocks.iulian.niopresentation.io.IoClientHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class IoTransferFileHandler implements IoClientHandler {

    @Override
    public void handle(Socket clientSocket, String dataFile) throws IOException  {
        try(var dataIs = new FileInputStream(dataFile)) {
            var socketOs = clientSocket.getOutputStream();
            dataIs.transferTo(socketOs);
        }
    }

}
