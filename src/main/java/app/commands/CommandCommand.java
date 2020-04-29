package app.commands;

import app.config.CommandService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class CommandCommand extends CommandListener {
    public CommandCommand() {
        super("commands");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
            return;
        }

        MessageChannel channel = event.getChannel();

        CommandService service = new CommandService();

        List<String> commandList = service.getCommands();

        String listBodyAsString = generateCommandsListAsString(commandList);

        channel.sendMessage(listBodyAsString).queue();
    }


    private String generateCommandsListAsString(List<String> commandList) {
        StringBuilder commandBodyAsString = new StringBuilder("**All commands need to be used with the \"!\" prefix** \n");

        int counter = 1;

        for (String command : commandList) {
            commandBodyAsString.append(counter++).append(". ").append(command).append("\n");
        }

        return commandBodyAsString.toString();
    }
}
