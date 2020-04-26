package app.commands;

import app.wrappers.JokeApiWrapper;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class JokeCommand extends CommandListener {
    private JokeApiWrapper jokeApiWrapper = new JokeApiWrapper();

    public JokeCommand() {
        super("joke");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
            return;
        }

        MessageChannel channel = event.getChannel();


        try {
            jokeApiWrapper.getJoke(null, null);
            channel.sendMessage(jokeApiWrapper.getJokeBody()).queue();
        } catch (Exception ex) {
            System.out.println("poep");
        }
    }
}
