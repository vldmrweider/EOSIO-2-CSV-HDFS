import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.out;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Options options = new Options();

        Option blockStart = new Option("b", "blockstart", true, "start block");
        blockStart.setRequired(true);
        options.addOption(blockStart);

        Option blockStop = new Option("e", "blockstop", true, "stop block");
        blockStop.setRequired(true);
        options.addOption(blockStop);

        Option sourse = new Option("s", "source", true, "source  type mongo, http");
        sourse.setRequired(true);
        options.addOption(sourse);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        int startblock = Integer.parseInt(cmd.getOptionValue("blockstart"));
        int stopblock = Integer.parseInt(cmd.getOptionValue("blockstop"));
        String sourcetype = cmd.getOptionValue("source");

        PrintWriter outCsv = new PrintWriter(String.format("%s_%s.csv", startblock, stopblock));

        if (sourcetype == "mongo") {
            mongoSource(startblock, stopblock, outCsv);
        } else {

        }
        outCsv.close();
    }

    private static void mongoSource(int blockStart, int blockStop, PrintWriter outCsv) {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().conventions(Collections.singletonList(ANNOTATION_CONVENTION)).automatic(true).build()));

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
    }

}
