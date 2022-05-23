import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public String getNewUserUUID() {
        StringBuilder uuidBuilder = new StringBuilder("");
        Random rng = new Random();
        int len = 6;
        boolean nonUnique = false;

        do {
            for (int c = 0; c < len; c++) {
                uuidBuilder.append((Integer) rng.nextInt(10));
            }
            nonUnique = false;
            for (User u : users) {
                if (uuidBuilder.toString().equals(u.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuidBuilder.toString();
    }
    public String getNewAccountUUID() {
        StringBuilder uuidBuilder = new StringBuilder("");
        Random rng = new Random();
        int len = 10;
        boolean nonUnique = false;

        do {
            for (int c = 0; c < len; c++) {
                uuidBuilder.append((Integer) rng.nextInt(10));
            }
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuidBuilder.toString().equals(a.getUUID())) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);
        return uuidBuilder.toString();
    }

    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName, String lastname, String pin) throws NoSuchAlgorithmException {
        User newUser = new User(firstName, lastname, pin, this);
        this.users.add(newUser);
        new Account("Saving", newUser, this);
        return newUser;
    }

    public User userLoging(String userID, String pin) {
        for (User u : this.users) {
            try {
                if (Objects.equals(userID, u.getUUID()) && u.validatePin(pin)) return u;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
