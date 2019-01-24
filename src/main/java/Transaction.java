import java.util.ArrayList;

public class Transaction {

    private ArrayList<Action> actions;

    public Transaction() {

    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}
