package bankapi.commands;

import bankapi.BankAPI;
import bankapi.util.BankLogger;
import bankapi.util.commands.Command;
import bankapi.util.commands.CommandArgument;
import bankapi.util.commands.arguments.PlayerArgument;
import bankapi.util.commands.arguments.StringArgument;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class BalanceCommand implements Command {
    @Getter
    final LinkedList<CommandArgument> commandArguments = new LinkedList<>(List.of(
            new StringArgument("balance", true)
            ));
    final BankLogger logger = BankLogger.builder()
            .SERVICE("BALANCE_COMMAND")
            .showDebug(true)
            .build();

    final String PLAYER_COMMAND_GET_BALANCE_MESSAGE = "You have a balance of $%d!";

    /**
     * /send {player} {int}
     * @param arguments represents command arguments being passed in
     */
    public void execute (final CommandSender commandSender, final List<String> arguments, BankAPI bankAPI) {
        if (commandSender instanceof Player senderPlayer) {
            UUID senderID = senderPlayer.getUniqueId();
            this.balance(senderID, bankAPI);
        }
    }

    public void balance (final UUID playerID, final BankAPI bankAPI) {
        this.logger.message(playerID, String.format(PLAYER_COMMAND_GET_BALANCE_MESSAGE, bankAPI.getIntBalance(playerID)));
    }
}
