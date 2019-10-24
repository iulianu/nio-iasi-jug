package rocks.iulian.niopresentation.nio.handlers;

import rocks.iulian.niopresentation.nio.NioClientHandler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.SocketChannel;

public class NioDirectTransferFileHandler implements NioClientHandler {

    @Override
    public void handle(SocketChannel toChannel, String fileName) throws IOException {
        try(var fromFile = new RandomAccessFile(fileName, "r")) {
            var fromChannel = fromFile.getChannel();
            var position = 0;
            var count = fromChannel.size();
            fromChannel.transferTo(position, count, toChannel);
        }
    }
}
