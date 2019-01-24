import java.util.ArrayList;

public class BlockDetail {

    private String producer;
    private ArrayList<Transactions> transactions;
    private String timestamp;

    public BlockDetail() {

    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setTransactions(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
