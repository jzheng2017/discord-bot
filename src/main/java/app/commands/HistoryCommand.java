package app.commands;

import app.utils.StringUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class HistoryCommand extends CommandListener {
    private TextChannel channel;

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

        channel = event.getTextChannel();
        String content = getHistoryAndBuildContent(limit);

        if (!content.isEmpty()) {
            channel.sendMessage(StringUtil.topNOccurrences(content, topN)).queue();
        }
    }

    private String getHistoryAndBuildContent( int limit) {
        List<Message> messages = getHistory(limit);
        StringBuilder content = new StringBuilder();

        for (Message message : messages) {
            content.append(message.getContentRaw()).append(" ");
        }

        return content.toString();
    }

    private List<Message> getHistory(int limit) {
        try {
            return channel.getHistoryFromBeginning(limit).complete().getRetrievedHistory();
        } catch (IllegalArgumentException ex) {
            channel.sendMessage("Limit may not exceed 100!").queue();
            return Collections.emptyList();
        }
    }
}
