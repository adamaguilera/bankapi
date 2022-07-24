package bankapi.commands;

import bankapi.BankAPI;
import bankapi.util.BankLogger;
import bankapi.util.commands.Command;
import bankapi.util.commands.CommandArgument;
import bankapi.util.commands.arguments.StringArgument;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class BalTopCommand implements Command {
    @Getter
    final LinkedList<CommandArgument> commandArguments = new LinkedList<>(List.of(
            new StringArgument("baltop", true)
            ));
    final BankLogger logger = BankLogger.builder()
            .SERVICE("BALTOP_COMMAND")
            .showDebug(true)
            .build();

    /**
     * /send {player} {int}
     * @param arguments represents command arguments being passed in
     */
    public void execute (final CommandSender commandSender, final List<String> arguments, BankAPI bankAPI) {
        if (commandSender instanceof Player senderPlayer) {
            UUID senderID = senderPlayer.getUniqueId();
            bankAPI.baltop(senderID, bankAPI.getBankData().getBaltopCount());
        }
    }
}
