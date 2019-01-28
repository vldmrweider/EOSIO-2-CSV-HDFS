import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;




public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        int blockStart, blockStop;
        if (args.length != 2) {
            out.println("blockstart blockend args expected ");
            return;
        }

        PrintWriter outCsv = new PrintWriter(String.format("%s_%s.csv", args[0], args[1]));

        blockStart = Integer.parseInt(args[0]);
        blockStop = Integer.parseInt(args[1]);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().conventions(asList(ANNOTATION_CONVENTION)).automatic(true).build()));

        MongoClient mongoClient = MongoClients.create("mongodb://node:YtrgOJbzre04Ak7q@159.69.157.38:27017/?authSource=node");

        MongoDatabase database = mongoClient.getDatabase("node").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Block> collection = database.getCollection("blocks", Block.class);

        for (int i = blockStart; i <= blockStop; i++) {
            Block block = collection.find(eq("block_num", i)).first();
            if (block == null) continue;
            BlockDetail blockDetail = block.getBlock();
            for (Transactions tr : blockDetail.getTransactions()) {
                Trx trx = tr.getTrx();
                for (Action act : trx.getTransaction().getActions()) {
                    outCsv.println(block.getBlock_num() + ";" + trx.getId() + ";" + tr.getCpu_usage_us() + ";" +
                            tr.getNet_usage_words() + ";" + blockDetail.getProducer() +
                            ";" + act.getAccount() + ";" + act.getName() + ";" +
                            act.getData().entrySet());

                }
            }
        }

        outCsv.close();


        Configuration configuration = new Configuration();
        FileSystem hdfs = FileSystem.get(new URI("http://178.63.53.79:8877"),configuration);

        Path homeDirectory = hdfs.getHomeDirectory();
        System.out.println("Home directory\t\t: " + homeDirectory);
        Path workingDirectory = hdfs.getWorkingDirectory();
        System.out.println("Working directory\t: " + workingDirectory);



      //  hdp();

    }

//    public static void hdp()  {
//
//
//        final Configuration conf = new Configuration();
//        final UserGroupInformation usgi = UserGroupInformation.createRemoteUser("root");
//
//        usgi.doAs(new PrivilegedExceptionAction<Object>() {
//            @Override
//            public Object run() throws Exception {
//                Configuration configuration = new Configuration();
//
//                try (final FileSystem hdfs = FileSystem.get(new URI("http://178.63.53.79:8877" ),conf)) {
//
//                    System.out.printf("Total Used Hdfs Storage: %d\n", hdfs.getStatus().getUsed());
//
//                    final String resourceName = "ubooks/beowulf.txt";
//
//                    final Path path = new Path("/user/root", resourceName);
//
//                    try (final InputStream inputStream = HdfsPutFile.class.getClassLoader().getResourceAsStream(resourceName);
//                         final FSDataOutputStream outputStream = hdfs.create(path, true)) {
//
//                        IOUtils.copy(inputStream, outputStream);
//                    }
//                }
//                return null;
//            }
//        });



//        Configuration configuration = new Configuration();
//        FileSystem hdfs = FileSystem.get( new URI("http://178.63.53.79:8877" ), configuration );
//        Path file = new Path("http://178.63.53.79:8877/uos/7448789_7448799.csv");
//        if ( hdfs.exists( file )) { hdfs.delete( file, true ); }
//        OutputStream os = hdfs.create( file,
//                new Progressable() {
//                    public void progress() {
//                        out.println("...bytes written: [ "+bytesWritten+" ]");
//                    } });
//        BufferedWriter br = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );
//        br.write("Hello World");
//        br.close();
//        hdfs.close();
  //  }
}
