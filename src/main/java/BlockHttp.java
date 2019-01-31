import java.util.ArrayList;

public class BlockHttp {

    private String producer;
    private int block_num;
    private ArrayList<Transactions> transactions;



    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setBlock_num(int block_num) {
        this.block_num = block_num;
    }

    public int getBlock_num() {
        return block_num;
    }

    public void setTransactions(ArrayList<Transactions> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Transactions> getTransactions() {
        return transactions;
    }
}
