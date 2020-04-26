package app.commands;

import app.utils.StringUtil;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import zone.nora.coronavirus.Coronavirus;
import zone.nora.coronavirus.data.locations.Locations;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CoronaCommand extends CommandListener {
    private MessageChannel channel;

    public CoronaCommand() {
        super("corona");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
            return;
        }
        channel = event.getChannel();

        Coronavirus coronavirus = new Coronavirus();

        try {
            String countryCode = splittedMessage[1];
            String infoType = splittedMessage[2];
            Locations locations = coronavirus.getLocationsByCountryCode(countryCode);

            int info = getLatest(locations, infoType);

            channel.sendMessage(StringUtil.capitalize(infoType) + ": " + info).queue();
        } catch (IOException e) {
            this.channel.sendMessage("Unknown").queue();
        } catch (ArrayIndexOutOfBoundsException ex) {
            this.channel.sendMessage("Invalid command! Ex: !corona <country code> <type>").queue();
        }

    }

    private int getLatest(Locations locations, String infoType) {
        int info = 0;
        try {
            if (infoType.equalsIgnoreCase("recovered")) {
                info = locations.getLatest().getRecovered();
            } else if (infoType.equalsIgnoreCase("confirmed")) {
                info = locations.getLatest().getConfirmed();
            } else if (infoType.equalsIgnoreCase("deaths")) {
                info = locations.getLatest().getDeaths();
            }
        } catch (Exception ex) {
            this.channel.sendMessage("Not found!").queue();
        }
        return info;
    }
}
