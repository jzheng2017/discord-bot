package app.commands;

import app.models.charts.Data;
import app.models.charts.Dataset;
import app.utils.StringUtil;
import app.wrappers.ChartApiWrapper;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static app.utils.StringUtil.truncate;

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

        try {
            String[] parameters = splittedMessage[1].split(";");
            String type = parameters[0];
            int topN = Integer.parseInt(parameters[1]);
            int limit = Integer.parseInt(parameters[2]);
            Optional<String> chartType = Optional.empty();

            if (parameters.length >= 4) {
                chartType = Optional.of(parameters[3]);
            }

            channel = event.getTextChannel();
            String content = getHistoryAndBuildContent(limit, type);

            String delimiter = type.equals("msg") ? "\\s+" : ",";

            String topNOccurrencesList = StringUtil.topNOccurrencesAsString(content, topN, delimiter);
            ChartApiWrapper chartApiWrapper = new ChartApiWrapper();

            Data data = generateChartData(StringUtil.topNOccurrences(content, topN, delimiter));

            if (chartType.isPresent()) {
                byte[] image = chartApiWrapper.getChart(data, chartType.get());

                channel.sendMessage("**Text longer than 15 characters in the chart are truncated**").addFile(image, "chart.png").queue();
            }

            if (!content.isEmpty()) {
                channel.sendMessage(topNOccurrencesList).queue();
            }
        } catch (IndexOutOfBoundsException ex) {
            channel.sendMessage("Invalid command! Ex: !history <type>;<top>;<limit>;<chartType>").queue();
        }

    }

    private Data generateChartData(List<Map.Entry<String, Long>> groupedOccurrencesList) {
        String[] labels = new String[groupedOccurrencesList.size()];
        String[] dataArray = new String[groupedOccurrencesList.size()];
        int counter = 0;

        for (Map.Entry<String, Long> entry : groupedOccurrencesList) {
            labels[counter] = truncate(entry.getKey(), 15);
            dataArray[counter] = entry.getValue().toString();
            counter++;
        }

        Dataset dataset = new Dataset("Messages", dataArray);

        return new Data(labels, new Dataset[]{dataset});
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
