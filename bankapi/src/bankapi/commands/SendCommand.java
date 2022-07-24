package bankapi.commands;

import bankapi.BankAPI;
import bankapi.util.BankLogger;
import bankapi.util.commands.Command;
import bankapi.util.commands.CommandArgument;
import bankapi.util.commands.arguments.IntArgument;
import bankapi.util.commands.arguments.PlayerArgument;
import bankapi.util.commands.arguments.StringArgument;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class SendCommand implements Command {
    @Getter
    final String name = "SEND_COMMAND";
    @Getter
    final LinkedList<CommandArgument> commandArguments = new LinkedList<>(List.of(
            new StringArgument("send", true),
            new PlayerArgument(),
            new IntArgument()
            ));
    final BankLogger logger = BankLogger.builder()
            .SERVICE("SEND_COMMAND")
            .showDebug(true)
            .build();

    final String PLAYER_COMMAND_SEND_SENDER_MESSAGE = "You have sent $%d to %s!";
    final String PLAYER_COMMAND_SEND_RECEIVER_MESSAGE = "You have received $%d from %s!";
    final String PLAYER_COMMAND_SEND_FAILED_MESSAGE = "You do not have $%d to send!";
    final String NON_PLAYER_ATTEMPTED_SEND_COMMAND = "Non-user attempted to send a %s $%d";
    final String CANNOT_SEND_TO_SELF = "You cannot send money to yourself!";

    /**
     * /send {player} {int}
     * @param arguments represents command arguments being passed in
     */
    public void execute (final CommandSender commandSender, final List<String> arguments, BankAPI bankAPI) {
        Player receiverPlayer = Bukkit.getPlayer(arguments.get(1));
        int amount = Integer.parseInt(arguments.get(2));
        if (commandSender instanceof Player senderPlayer) {
            this.send(senderPlayer, receiverPlayer, amount, bankAPI);
        } else {
            this.logger.debug(String.format(NON_PLAYER_ATTEMPTED_SEND_COMMAND, receiverPlayer.getName(), amount));
        }
    }

    public void send (final Player senderPlayer, final Player receiverPlayer, double amount, final BankAPI bankAPI) {
        UUID senderID = senderPlayer.getUniqueId();
        UUID receiverID = receiverPlayer.getUniqueId();
        if (senderID == receiverID) {
            this.logger.message(senderID, String.format(CANNOT_SEND_TO_SELF));
            return;
        }
        int amountInt = (int) amount;
        if (bankAPI.send(senderID, receiverID, amount)) {
            this.logger.message(senderID, String.format(PLAYER_COMMAND_SEND_SENDER_MESSAGE, amountInt, receiverPlayer.getName()));
            this.logger.message(receiverID, String.format(PLAYER_COMMAND_SEND_RECEIVER_MESSAGE, amountInt, senderPlayer.getName()));
        } else {
            this.logger.message(senderID, String.format(PLAYER_COMMAND_SEND_FAILED_MESSAGE, amountInt));
        }
    }
}
