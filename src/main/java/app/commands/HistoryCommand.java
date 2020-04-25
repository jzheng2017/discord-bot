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

//        String key = splittedMessage[1];
//        String value = splittedMessage[2];

        TextChannel channel = event.getTextChannel();
        List<Message> messages = channel.getHistoryFromBeginning(100).complete().getRetrievedHistory();
        StringBuilder content = new StringBuilder();
        for (Message message : messages) {
            content.append(message.getContentRaw()).append(" ");
        }

        channel.sendMessage(StringUtil.topNOccurrences(content.toString(), 10)).queue();
    }
}
