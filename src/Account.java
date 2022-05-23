import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<>();
        holder.addAccount(this);
        theBank.addAccount(this);
    }

    public String getUUID() {
        return this.uuid;
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    @Override
    public String toString() {
        return name + ", uuid: " + uuid + ", balance: " + this.getBalance();
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        this.transactions.forEach(t -> {
            System.out.println(t.toString());
        });
    }

    public void addTransaction(double amount, String memo) {
        Transaction t = new Transaction(amount, memo, this);
        this.transactions.add(t);
    }
}
