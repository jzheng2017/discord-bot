package app.commands;

import app.constants.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    protected String command;
    protected String[] splittedMessage;

    protected boolean isValidCommand(MessageReceivedEvent event) {
        splittedMessage = event.getMessage().getContentRaw().split("\\s+");
        String command = splittedMessage[0];

        final boolean messageDoesNotStartWithPrefixAndCommand = !command.equals(Message.PREFIX + this.command);
        final boolean messageMadeByBot = event.getAuthor().isBot();

        if (messageMadeByBot || messageDoesNotStartWithPrefixAndCommand) {
            return false;
        }

        return true;
    }
}
