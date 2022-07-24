package bankapi;

import bankapi.util.BankLogger;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class BankAPI {
    final HashMap<UUID, Account> accounts = new HashMap<>();
    final AccountLoader accountLoader = new AccountLoader();
    final BankConfig bankConfig = new BankConfig();
    final BankLogger logger = BankLogger.builder()
            .SERVICE("BANKAPI")
            .showDebug(true)
            .build();

    final static int MAX_BAL_TOP_SEARCH = 10000;
    final String CREATED_ACCOUNT_DEBUG = "Provisioned account for %s with %f starting balance";
    final String ATTEMPT_SEND_MESSAGE = "%s attempting to send $%f to %s";
    final String SEND_NOT_ENOUGH_DEBUG = "%s does not $%f to send";
    final String ATTEMPT_SUCCESS_MESSAGE = "%s successfully sent $%f to %s";
    final String GLOBAL_DONATION_MESSAGE = "Global donation of $%f was made to %d online players!";
    final String DONATION_MESSAGE = "%s received a donation of $%f";
    final String BAL_TOP_HEADER = "Top %d player balances:";
    final String BAL_TOP_PLAYER_BALANCE = "%d. %s: $%d";

    double startingBalance;

    public void load () {
        BankData bankData = bankConfig.load();
        this.startingBalance = bankData.getStartingBalance();
        HashMap<UUID, Account> accountData = this.accountLoader.load().getAccounts();
        if (accountData != null) {
            this.accounts.putAll(accountData);
        }
    }
    public void save () { this.accountLoader.save(new AccountData(this.accounts)); }

    public double balance (final UUID playerID) { return this.getOrCreateAccount(playerID).getBalance(); }
    public int getIntBalance (final UUID playerID) { return (int) this.getOrCreateAccount(playerID).getBalance(); }
    public void baltop (final UUID playerID, int count) {
        this.logger.message(playerID, String.format(BAL_TOP_HEADER, count));
        AtomicInteger order = new AtomicInteger(1);
        this.accounts.values().stream().limit(MAX_BAL_TOP_SEARCH).sorted().limit(count)
                .forEachOrdered(
                        account -> {
                            String name = Bukkit.getOfflinePlayer(playerID).getName();
                            this.logger.message(playerID, String.format(BAL_TOP_PLAYER_BALANCE, order.get(), name, (int) account.getBalance()));
                            order.addAndGet(1);
                        }
                );
    }
    public void deposit (final UUID playerID, double amount) { this.getOrCreateAccount(playerID).deposit(amount); }
    public void withdraw (final UUID playerID, double amount) { this.getOrCreateAccount(playerID).withdraw(amount); }
    public void has (final UUID playerID, double amount) { this.getOrCreateAccount(playerID).has(amount); }
    public void donate (final UUID receiverID, double amount) {
        this.logger.debug(String.format(DONATION_MESSAGE, receiverID, amount));
        this.getOrCreateAccount(receiverID).deposit(amount);
    }
    public void donate (double amount) {
        this.logger.debug(String.format(GLOBAL_DONATION_MESSAGE, amount, Bukkit.getOnlinePlayers().size()));
        Bukkit.getOnlinePlayers().forEach(player -> this.getOrCreateAccount(player.getUniqueId()).deposit(amount));
    }

    public boolean send (final UUID senderID, final UUID receiverID, double amount) {
        this.logger.debug(String.format(ATTEMPT_SEND_MESSAGE, senderID, amount, receiverID));
        Account sender = this.getOrCreateAccount(senderID);
        if (!sender.has(amount)) {
            this.logger.debug(String.format(SEND_NOT_ENOUGH_DEBUG, senderID, amount));
            return false;
        }
        Account receiver = this.getOrCreateAccount(receiverID);
        sender.withdraw(amount);
        receiver.deposit(amount);
        this.logger.debug(String.format(ATTEMPT_SUCCESS_MESSAGE, senderID, amount, receiverID));
        return true;
    }

    private Account createAccount (final UUID playerID) {
        Account account = Account.builder()
                .playerID(playerID)
                .balance(startingBalance)
                .build();
        accounts.put(playerID, account);
        this.logger.debug(String.format(CREATED_ACCOUNT_DEBUG, playerID, startingBalance));
        return account;
    }
    private Account getOrCreateAccount (final UUID playerID) {
        if (!accounts.containsKey(playerID)) {
            return createAccount(playerID);
        }
        return accounts.get(playerID);
    }
}
