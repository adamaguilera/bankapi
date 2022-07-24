package bankapi;

import bankapi.util.BankLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
class BankData {
    double startingBalance;
}

public class BankConfig {
    final BankLogger logger = BankLogger.builder()
            .SERVICE("BANK_CONFIG")
            .showDebug(true)
            .build();

    final String ATTEMPT_LOADING_BANK_CONFIG = "Attempting to load bank config from %s";
    final String FINISH_LOADING_BANK_CONFIG = "Finished loading bank config from json";
    final String FAILED_LOAD_BANK_CONFIG = "Failed to load BankAPI config";
    final Path PLAYER_BALANCES_PATH = Paths.get("./plugins/BankAPI/config.json");
    final Gson GSON_BUILDER = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public BankData load () {
        this.logger.debug(String.format(ATTEMPT_LOADING_BANK_CONFIG, PLAYER_BALANCES_PATH));
        try {
            String json = new String(Files.readAllBytes(PLAYER_BALANCES_PATH));
            BankData bankData = GSON_BUILDER.fromJson(json, BankData.class);
            this.logger.debug(FINISH_LOADING_BANK_CONFIG);
            return bankData;
        } catch (IOException e) {
            this.logger.debug(FAILED_LOAD_BANK_CONFIG);
            throw new RuntimeException(e);
        }

    }
}
