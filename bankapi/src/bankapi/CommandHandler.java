package bankapi;

import bankapi.commands.BalanceCommand;
import bankapi.commands.SendCommand;
import bankapi.util.BankLogger;
import bankapi.util.commands.CommandTree;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class CommandHandler {
    final BankLogger logger = BankLogger.builder()
            .SERVICE("COMMAND_HANDLER")
            .showDebug(true)
            .build();

    final CommandTree commandTree = new CommandTree();
    final String RECEIVED_COMMAND = "Handling command of size %d";
    final String COMMAND_FOUND = "Command was found, executing now";
    final String COMMAND_NOT_FOUND = "Command was not found";

    public CommandHandler () {
        SendCommand sendCommand = new SendCommand();
        this.commandTree.insert(sendCommand.getCommandArguments(), sendCommand);
        BalanceCommand balanceCommand = new BalanceCommand();
        this.commandTree.insert(balanceCommand.getCommandArguments(), balanceCommand);
    }


    public void handle(final CommandSender sender, final String firstArg, final String[] args, final BankAPI bankAPI) {
        LinkedList<String> arguments = new LinkedList<>();
        if (firstArg != null) {
            arguments.add(firstArg);
        }
        arguments.addAll(List.of(args));
        this.logger.debug(String.format(RECEIVED_COMMAND, arguments.size()));
        this.commandTree.findCommand(arguments, 0)
                .ifPresentOrElse(
                        command -> {
                            this.logger.debug(COMMAND_FOUND);
                            command.execute(sender, arguments, bankAPI);
                        },
                        () -> this.logger.debug(COMMAND_NOT_FOUND)
                );
    }
}

