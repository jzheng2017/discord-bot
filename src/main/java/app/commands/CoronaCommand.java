package app.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import zone.nora.coronavirus.Coronavirus;
import zone.nora.coronavirus.data.locations.Locations;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CoronaCommand extends CommandListener {

    public CoronaCommand() {
        super("corona");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
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
