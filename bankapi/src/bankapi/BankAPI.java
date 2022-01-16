package bankapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BankAPI extends JavaPlugin {
    final String ON_ENABLE = "BankAPI has been enabled!";
    final String ON_DISABLE = "BankAPI has been disabled!";
    final String COMMAND_RECEIVED = "A command has been received!";

    @Override
    public void onEnable () {
        this.gameListener = new GameListener();
        this.getServer().getPluginManager().registerEvents(gameListener, this);
        Bukkit.broadcastMessage(ON_ENABLE);
    }

    @Override
    public void onDisable () {
        Bukkit.broadcastMessage(ON_DISABLE);
    }


    @Override
    public boolean onCommand (CommandSender sender,Command command,
                              String label, String[] args) {
        Bukkit.broadcastMessage(COMMAND_RECEIVED);
        return true;
    }
    /*
        BankAPI functions 
    */

}