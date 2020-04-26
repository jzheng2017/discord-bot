package app;

import app.commands.*;
import app.config.ConfigLoader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    public static void main(String[] args) throws LoginException {
        ConfigLoader configLoader = new ConfigLoader();
        JDA jda = JDABuilder.createDefault(configLoader.get().getProperty("bot_token")).build();
        jda.addEventListener(new SearchCommand());
        jda.addEventListener(new CoronaCommand());
        jda.addEventListener(new HistoryCommand());
        jda.addEventListener(new HeavyCommand());
        jda.addEventListener(new JokeCommand());
    }
}
