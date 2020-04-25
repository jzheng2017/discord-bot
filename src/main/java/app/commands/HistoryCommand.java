package app.commands;

import app.utils.StringUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class HistoryCommand extends CommandListener {

    public HistoryCommand() {
        super("history");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
            return;
        }

        String[] parameters = splittedMessage[1].split(";");
        int topN = Integer.parseInt(parameters[0]);
        int limit = Integer.parseInt(parameters[1]);
        TextChannel channel = event.getTextChannel();
        List<Message> messages = channel.getHistoryFromBeginning(limit).complete().getRetrievedHistory();
        StringBuilder content = new StringBuilder();
        for (Message message : messages) {
            content.append(message.getContentRaw()).append(" ");
        }

        channel.sendMessage(StringUtil.topNOccurrences(content.toString(), topN)).queue();
    }
}
