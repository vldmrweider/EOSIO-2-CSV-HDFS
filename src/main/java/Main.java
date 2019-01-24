import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;

public class Main {

    public static void main(String[] args) {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().conventions(asList(ANNOTATION_CONVENTION)).automatic(true).build()));

        MongoClient mongoClient = MongoClients.create("mongodb://node:YtrgOJbzre04Ak7q@159.69.157.38:27017/?authSource=node");

        MongoDatabase database = mongoClient.getDatabase("node").withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Block> collection = database.getCollection("blocks", Block.class);

        Block block = collection.find(eq("block_num", 7448789)).first();

        BlockDetail blockDetail = block.getBlock();
        for (Transactions tr : blockDetail.getTransactions()) {
            Trx trx = tr.getTrx();
            for (Action act : trx.getTransaction().getActions()) {
                System.out.println(block.getBlock_num() + ";" + tr.getCpu_usage_us() + ";" + tr.getNet_usage_words() +
                        ";" + blockDetail.getProducer() + ";" + act.getAccount() + ";" + act.getName() + ";" +
                        act.getData().entrySet());
                System.out.println(trx.getId());
            }
        }
    }


}
