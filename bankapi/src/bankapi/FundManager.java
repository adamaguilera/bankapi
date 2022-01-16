package bankapi;

import java.util.HashMap;
import java.util.UUID;

public class FundManager {
    HashMap<UUID, Account> accounts;
    List[Currency] currencies;

    private Account createAccount (UUID playerID) {
        Account account = new Account(playerID);
        accounts.put(playerID, account);
        return account;
    }

    public Account getAccount (UUID playerID) {
        return accounts.getOrDefault(playerID, createAccount(playerID));
    }

    
}
