package app.config;

import java.util.List;

public class CommandService {


    public List<String> getCommands() {
        return List.of(new String[]{"history", "heavy", "jelle", "joke", "search"});
    }

}
