package bankapi;

import bankapi.util.BankLogger;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Builder
public class Account {
    final transient BankLogger logger = BankLogger.builder()
            .SERVICE("ACCOUNT")
            .showDebug(true)
            .build();
    final transient String DEPOSIT_LOG = "%s deposited %f amount. New balance: %f";
    final transient String WITHDRAW_LOG = "%s withdrew %f amount. New balance: %f";
    final UUID playerID;
    @Getter
    double balance;

    public boolean has (double amount) { return this.balance >= amount; }
    public void deposit (double amount) {
        this.balance += amount;
        this.logger.debug(String.format(DEPOSIT_LOG, this.playerID, amount, this.balance));
    }
    public void withdraw (double amount) {
        this.balance -= amount;
        this.logger.debug(String.format(WITHDRAW_LOG, this.playerID, amount, this.balance));
    }

    public Optional<Player> getPlayer () { return Optional.ofNullable(Bukkit.getPlayer(this.playerID)); }
}
