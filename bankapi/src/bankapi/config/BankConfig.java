package bankapi.config;

import bankapi.config.data.AccountData;
import bankapi.config.data.BankData;
import bankapi.util.BankLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class BankConfig {
    final BankLogger logger = BankLogger.builder()
            .SERVICE("BANK_CONFIG")
            .showDebug(true)
            .build();

    final String ATTEMPT_LOADING_BANK_CONFIG = "Attempting to load bank config from %s";
    final String FINISH_LOADING_BANK_CONFIG = "Finished loading bank config from json";
    final String FAILED_LOAD_BANK_CONFIG = "Failed to load BankAPI config";
    final Path BANK_CONFIG_PATH = Paths.get("./plugins/BankAPI/config.json");
    final String ATTEMPT_SAVING_BANK_CONFIG = "Attempting to save bank config to %s";
    final String FINISH_SAVING_BANK_DATA = "Finished saving bank config to %s";
    final String FAILED_SAVING_BANK_DATA = "Failed to save bank config with error {%s}";
    final Gson GSON_BUILDER = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public BankData load () {
        this.logger.debug(String.format(ATTEMPT_LOADING_BANK_CONFIG, BANK_CONFIG_PATH));
        try {
            String json = new String(Files.readAllBytes(BANK_CONFIG_PATH));
            BankData bankData = GSON_BUILDER.fromJson(json, BankData.class);
            this.logger.debug(FINISH_LOADING_BANK_CONFIG);
            return bankData;
        } catch (IOException e) {
            this.logger.debug(FAILED_LOAD_BANK_CONFIG);
//            throw new RuntimeException(e);
            return new BankData();
        }
    }

    public void save (final BankData bankData) {
        this.logger.debug(String.format(ATTEMPT_SAVING_BANK_CONFIG, BANK_CONFIG_PATH));
        try {
            String json = GSON_BUILDER.toJson(bankData);
            Files.write(BANK_CONFIG_PATH, json.getBytes());
            this.logger.debug(String.format(FINISH_SAVING_BANK_DATA, BANK_CONFIG_PATH));
        } catch (IOException e) {
//            throw new RuntimeException(e);
            this.logger.debug(String.format(FAILED_SAVING_BANK_DATA, e.getLocalizedMessage()));
        }
    }
}
