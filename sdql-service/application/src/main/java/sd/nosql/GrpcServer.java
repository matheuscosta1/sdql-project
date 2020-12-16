package sd.nosql;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sd.nosql.prototype.service.impl.DatabaseServiceImpl;

import java.io.IOException;

/*
========================================================================================================================
            1. Firstly, create a folder inside root, called database
            2. Inside the database folder, create a folder called data
            3. Now you're read to go

  - Folder structure:
   |-> application
     |-> src
   |-> prototype
     |-> src
   |-> database
     |-> data
     |-> ...backup files automatically created
     |-> version.db // Also programmatically created
========================================================================================================================
*/
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
        //server.createBasePath();
        server.start();
    }

    /*
    public void createBasePath() {
        logger.info("Creating base path...");
        File index = Paths.get("database/").toFile();
        if (!index.exists()) {
            index.mkdir();
            logger.info("\tCreating database/");
            Paths.get("database/data/").toFile().mkdir();
            logger.info("\tCreating database/data");
        } else {
            logger.info("Base path already exists...");
        }
    }

    public void cleanFiles() {
        logger.info("Resetting base path...");
        File index = Paths.get("database/").toFile();
        if (index.exists()) {
            logger.info("\tResetting database/data");
            File file = Paths.get("database/data").toFile();
            Arrays.stream(Optional.ofNullable(file.listFiles()).orElse(new File[]{})).peek(file1 -> logger.info("Deletting " + file1.getName())).forEach(File::delete);
            Paths.get("database/version.db").toFile().delete();

        } else {
            logger.info("Base path already exists...");
        }
    }

     */
}
