package app.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JelleCommand extends CommandListener {
    public JelleCommand() {
        super("jelle");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (!isValidCommand(event)) {
            return;
        }

        MessageChannel messageChannel = event.getChannel();
        File file = new File("src/main/resources/shroud.png");
        try {
            messageChannel.sendMessage("A very rare picture of jelle (1 in 50)").addFile(Files.readAllBytes(file.toPath()), "jelle.png").queue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            messageChannel.sendMessage("Something went wrong. Try again").queue();
        }
    }
}
