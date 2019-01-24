public class Transactions {

    private Trx trx;
    private int cpu_usage_us;
    private int net_usage_words;

    public Transactions() {
    }

    public Trx getTrx() {
        return trx;
    }

    public void setTrx(Trx trx) {
        this.trx = trx;
    }

    public int getCpu_usage_us() {
        return cpu_usage_us;
    }

    public void setCpu_usage_us(int cpu_usage_us) {
        this.cpu_usage_us = cpu_usage_us;
    }

    public int getNet_usage_words() {
        return net_usage_words;
    }

    public void setNet_usage_words(int net_usage_words) {
        this.net_usage_words = net_usage_words;
    }
}
