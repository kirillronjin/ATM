import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Account getInAccount() {
        return inAccount;
    }

    public void setInAccount(Account inAccount) {
        this.inAccount = inAccount;
    }

    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount){
        this(amount, inAccount);
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "\"" + memo + "\", " + amount + ", account: " + inAccount.getUuid() + ", Date: " + timestamp;
    }
}
