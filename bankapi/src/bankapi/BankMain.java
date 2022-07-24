package bankapi;

import bankapi.util.BankLogger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public class BankMain extends JavaPlugin {
    final String ON_ENABLE = "BankAPI has been enabled!";
    final String ON_DISABLE = "BankAPI has been disabled!";
    final String COMMAND_RECEIVED = "A command has been received!";
    final BankLogger logger =BankLogger.builder()
            .SERVICE("MAIN")
            .showDebug(true)
            .build();
    final String BANKAPI_CONFIG_FOLDER_PATH = "./plugins/BankAPI/";
    final String CREATED_BANKAPI_CONFIG_FOLDER = "Created BankAPI config folder";
    final CommandHandler commandHandler = new CommandHandler();
    @Getter
    final BankAPI bankAPI = new BankAPI();

    // 20 ticks in second, save every hour
    final long AUTO_SAVE_DELAY = 20 * 60 * 60;
    final long AUTO_SAVE_PERIOD = 0L;
    int autosaveTaskId;


    @Override
    public void onEnable () {
        this.generateConfigIfNotPresent();
        this.bankAPI.load();
        this.autosaveTaskId =Bukkit.getScheduler().scheduleSyncRepeatingTask(
                this, this.bankAPI::save, AUTO_SAVE_DELAY, AUTO_SAVE_PERIOD
        );
        this.logger.debug(ON_ENABLE);
    }

    @Override
    public void onDisable () {
        this.bankAPI.save();
        this.logger.debug(ON_DISABLE);
    }


    @Override
    public boolean onCommand (@NotNull CommandSender sender, Command command,
                              @NotNull String label, String[] args) {
        this.logger.debug(COMMAND_RECEIVED);
        this.commandHandler.handle(sender, command.getName(), args, bankAPI);
        return true;
    }

    public void generateConfigIfNotPresent () {
        File bankAPIFolder =  new File(BANKAPI_CONFIG_FOLDER_PATH);
        if (bankAPIFolder.mkdirs()) {
            this.logger.debug(CREATED_BANKAPI_CONFIG_FOLDER);
        }
    }

}