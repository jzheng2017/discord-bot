package app;

import app.commands.CoronaCommand;
import app.commands.SearchCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("MjkzODQxMjg3MTg1MTcwNDQy.XqQFew.i5xX1TQSEA1gCsGPWBTRoQ-97bA").build();
        jda.addEventListener(new SearchCommand());
        jda.addEventListener(new CoronaCommand());
    }
}
