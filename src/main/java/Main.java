import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        int blockStart, blockStop;
        if (args.length != 2) {
            System.out.println("blockstart blockend args expected ");
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


    }


}
