package app.commands;

import app.constants.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import zone.nora.coronavirus.Coronavirus;
import zone.nora.coronavirus.data.latest.Latest;
import zone.nora.coronavirus.data.locations.Locations;
import zone.nora.coronavirus.data.locations.location.Location;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

public class CoronaCommand extends ListenerAdapter {
    private final String command = "corona";
    private String[] splittedMessage;

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        splittedMessage = event.getMessage().getContentRaw().split("\\s+");
        String command = splittedMessage[0];

        final boolean messageDoesNotStartWithPrefixAndCommand = !command.equals(Message.PREFIX + this.command);
        final boolean messageMadeByBot = event.getAuthor().isBot();

        if (messageMadeByBot || messageDoesNotStartWithPrefixAndCommand) {
            return;
        }

        Coronavirus coronavirus = new Coronavirus();

        try {
            Locations locations = coronavirus.getLocationsByCountryCode("nl");

            MessageChannel channel = event.getChannel();

            channel.sendMessage(Integer.toString(locations.getLatest().getRecovered())).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
