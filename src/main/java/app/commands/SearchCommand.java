package app.commands;

import app.constants.Message;
import app.scraper.PageScraper;
import app.utils.StringUtil;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jsoup.nodes.Document;

import javax.annotation.Nonnull;

public class SearchCommand extends ListenerAdapter {
    private final String command = "search";
    private String[] splittedMessage;
    private Document document;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        splittedMessage = event.getMessage().getContentRaw().split("\\s+");
        String command = splittedMessage[0];

        final boolean messageDoesNotStartWithPrefixAndCommand = !command.equals(Message.PREFIX + this.command);
        final boolean messageMadeByBot = event.getAuthor().isBot();

        if (messageMadeByBot || messageDoesNotStartWithPrefixAndCommand) {
            return;
        }

        String url = splittedMessage[1];
        String key = splittedMessage[2];

        PageScraper pageScraper = new PageScraper(url);

        document = pageScraper.getContent();

        MessageChannel channel = event.getChannel();
        channel.sendMessage(this.generateMessage(key)).queue();
    }

    private String generateMessage(String key) {
        String message = "";

        if (key.equals("occurrences")) {
            message = generateOccurrences();
        }
        if (key.startsWith("top")) {
            message = generateNOccurrences(key);
        }
        return message;
    }

    private String generateNOccurrences(String key) {
        try {
            int topN = Integer.parseInt(key.substring(3));
            return StringUtil.topNOccurrences(document.body().text(), topN);
        } catch (NumberFormatException ex) {
            return "Invalid query";
        }
    }

    private String generateOccurrences() {
        String needle = splittedMessage[3];

        int numberOfOccurrences = StringUtil.occurrences(document.body().text(), needle);

        return String.format("**%d** number of occurrences", numberOfOccurrences);
    }
}
