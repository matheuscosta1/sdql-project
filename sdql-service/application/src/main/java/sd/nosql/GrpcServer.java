package sd.nosql;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.service.impl.DatabaseServiceImpl;

import java.io.IOException;

public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);
    final io.grpc.Server server;

    public GrpcServer(int port) {
        server = ServerBuilder
                .forPort(port)
                .addService(new DatabaseServiceImpl())
                .build();
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        server.awaitTermination();
    }

    public void stop() {
        server.shutdown();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Starting server...");
        GrpcServer server = new GrpcServer(8080);
        server.start();
    }
}
