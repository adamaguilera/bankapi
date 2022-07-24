package bankapi;

import bankapi.util.BankLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@Data
@Builder
class AccountData {
    HashMap<UUID, Account> accounts;
}

public class AccountLoader {
    final BankLogger logger = BankLogger.builder()
            .SERVICE("ACCOUNT_LOADER")
            .showDebug(true)
            .build();

    final String ATTEMPT_LOADING_PLAYER_BALANCES = "Attempting to load player balances from %s";
    final String FINISH_LOADING_PLAYER_BALANCES = "Finished loading %d accounts from json";
    final String ATTEMPT_SAVING_PLAYER_BALANCES = "Attempting to save %d player balances to %s";
    final String FINISH_SAVING_PLAYER_BALANCES = "Finished saving %d player balances to %s";
    final String FAILED_TO_LOAD_ACCOUNT_DATA = "Failed to load account data";
    final Path PLAYER_BALANCES_PATH = Paths.get("./plugins/BankAPI/player_balances.json");
    final Gson GSON_BUILDER = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public AccountData load () {
        this.logger.debug(String.format(ATTEMPT_LOADING_PLAYER_BALANCES, PLAYER_BALANCES_PATH));
        try {
            String json = new String(Files.readAllBytes(PLAYER_BALANCES_PATH));
            AccountData accountData = GSON_BUILDER.fromJson(json, AccountData.class);
            HashMap<UUID, Account> accounts = accountData.getAccounts();
            int size = accounts == null ? 0 : accounts.size();
            this.logger.debug(String.format(FINISH_LOADING_PLAYER_BALANCES, size));
            return accountData;
        } catch (IOException e) {
            this.logger.debug(FAILED_TO_LOAD_ACCOUNT_DATA);
            throw new RuntimeException(e);
        }

    }

    public void save (final AccountData accountData) {
        this.logger.debug(String.format(ATTEMPT_SAVING_PLAYER_BALANCES, accountData.getAccounts().size(), PLAYER_BALANCES_PATH));
        try {
            String json = GSON_BUILDER.toJson(accountData);
            Files.write(PLAYER_BALANCES_PATH, json.getBytes());
            this.logger.debug(String.format(FINISH_SAVING_PLAYER_BALANCES, accountData.getAccounts().size(), PLAYER_BALANCES_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
