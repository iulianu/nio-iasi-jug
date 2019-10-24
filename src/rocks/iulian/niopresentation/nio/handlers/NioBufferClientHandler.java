package rocks.iulian.niopresentation.nio.handlers;

import rocks.iulian.niopresentation.nio.NioClientHandler;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioBufferClientHandler implements NioClientHandler {

    @Override
    public void handle(SocketChannel toChannel, String fileName) throws IOException {
        try(var fromFile = new RandomAccessFile(fileName, "r")) {
            var fromChannel = fromFile.getChannel();
            var buffer = ByteBuffer.allocate(8192);
            while (fromChannel.read(buffer) != -1) {
                buffer.flip();
                toChannel.write(buffer);
                buffer.flip();
            }
        }
    }

}
