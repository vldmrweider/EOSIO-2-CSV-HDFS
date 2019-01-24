import java.util.ArrayList;

public class BlockDetail {

    private String producer;
    private ArrayList<Transactions> transactions;

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
}
