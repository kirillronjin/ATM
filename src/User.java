import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public byte[] getPinHash() {
        return pinHash;
    }

    public void setPinHash(byte[] pinHash) {
        this.pinHash = pinHash;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public User(String firstName, String lastName, String pin, Bank theBank) throws NoSuchAlgorithmException {
        this.firstName = firstName;
        this.lastName = lastName;
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());

        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<>();

        System.out.printf("New user %s, %s, with ID %s created.\n", lastName, firstName, this.uuid);
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash  );
    }

    public void printAccountsSummary() {
        System.out.printf("\n%s's accounts summary:\n", this.getFirstName());
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, this.accounts.get(i).toString());
        }
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int fromAcct) {
        return this.accounts.get(fromAcct).getBalance();
    }

    public String getAcctUUID(int fromAcct) {
        return this.accounts.get(fromAcct).getUUID();
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
}
