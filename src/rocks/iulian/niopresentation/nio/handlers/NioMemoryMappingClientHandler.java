package rocks.iulian.niopresentation.nio.handlers;

import rocks.iulian.niopresentation.nio.NioClientHandler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NioMemoryMappingClientHandler implements NioClientHandler {

    @Override
    public void handle(SocketChannel toChannel, String fileName) throws IOException {
        try(var fromFile = new RandomAccessFile(fileName, "r")) {
            var fromChannel = fromFile.getChannel();
            var buffer = fromChannel.map(FileChannel.MapMode.READ_ONLY, 0, fromChannel.size());
            toChannel.write(buffer);
            fromChannel.close();
        }
    }
}
