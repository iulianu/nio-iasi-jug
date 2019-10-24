package rocks.iulian.niopresentation.nio;

import rocks.iulian.niopresentation.nio.handlers.NioBufferClientHandler;
import rocks.iulian.niopresentation.nio.handlers.NioMemoryMappingClientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static java.lang.System.exit;

public class NioSelectorServer {

    private static final int SERVER_PORT = 8098;
//    private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/small_file";
    private static final String DATA_FILE = "/Users/iulian/Downloads/niodata/large_file";
    private static final NioClientHandler handler = new NioBufferClientHandler();

    public void run() {
        try {
            var selector = Selector.open();
            var serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(SERVER_PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(true) {
                try {
                    selector.select();
                    var iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        var key = iter.next();
                        if(key.isAcceptable()) {
                            System.out.println("Accepted");
                            var clientSocketChannel = serverSocketChannel.accept();
//                            clientSocketChannel.configureBlocking(false);
//                            clientSocketChannel.register(selector, SelectionKey.OP_READ);
                            var startTime = System.currentTimeMillis();
                            handler.handle(clientSocketChannel, DATA_FILE);
                            clientSocketChannel.close();
                            var endTime = System.currentTimeMillis();
                            System.out.printf("Served in %.2f seconds\n", (endTime - startTime) / 1000.0);
                        }
                        if(key.isReadable()) {
                            System.out.println("Reading");
                            var clientSocketChannel = (SocketChannel)key.channel();
                            // Stuff to read from client
                        }
                        iter.remove();
                    }
                    Thread.sleep(10);
                } catch (IOException e) {
                    e.printStackTrace();
                    exit(2);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NioSelectorServer().run();
    }
}
