package rocks.iulian.niopresentation.nio;

import rocks.iulian.niopresentation.nio.handlers.NioBufferClientHandler;
import rocks.iulian.niopresentation.nio.handlers.NioDirectTransferFileHandler;
import rocks.iulian.niopresentation.nio.handlers.NioMemoryMappingClientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {

    private static final int SERVER_PORT = 8098;
//    private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/small_file";
    private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/large_file";
    private static final NioClientHandler handler = new NioMemoryMappingClientHandler();

    private void serve(SocketChannel clientSocketChannel) {
        try {
            var startTime = System.currentTimeMillis();
            handler.handle(clientSocketChannel, DATA_FILE);
            clientSocketChannel.close();
            var endTime = System.currentTimeMillis();
            System.out.printf("Served in %.2f seconds\n", (endTime - startTime) / 1000.0);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            var serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(SERVER_PORT));
            while(true) {
                try {
                    var clientSocketChannel = serverSocketChannel.accept();
                    new Thread(() -> serve(clientSocketChannel)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioServer().run();
    }
}
