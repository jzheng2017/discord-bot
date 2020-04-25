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
        String type = parameters[0];
        int topN = Integer.parseInt(parameters[1]);
        int limit = Integer.parseInt(parameters[2]);

        channel = event.getTextChannel();
        String content = getHistoryAndBuildContent(limit, type);

        String topNOccurrencesList = StringUtil.topNOccurrences(content, topN, type.equals("msg") ? "\\s+" : ",");

        if (!content.isEmpty()) {
            channel.sendMessage(topNOccurrencesList).queue();
        }
    }

    private String getHistoryAndBuildContent( int limit) {
        List<Message> messages = getHistory(limit);
        StringBuilder content = new StringBuilder();

        if (type.equalsIgnoreCase("msg"))
            for (Message message : messages) {
                content.append(message.getContentRaw()).append(" ");
            }
        else if (type.equalsIgnoreCase("ppl")) {
            for (Message message : messages) {
                content.append(message.getAuthor()).append(",");
            }
        }

        return content.toString();
    }

    private List<Message> getHistory(int limit) {
        try {
            return getMostRecentMessages(limit);
        } catch (IllegalArgumentException ex) {
            channel.sendMessage("Limit may not exceed 100!").queue();
            return Collections.emptyList();
        }
    }

    private List<Message> getMostRecentMessages(int limit) {
        return channel
                .getHistoryBefore(Long.parseLong(channel.getLatestMessageId()), limit)
                .complete()
                .getRetrievedHistory();
    }
}
