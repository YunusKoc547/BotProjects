import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.FileUpload;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Main extends ListenerAdapter{


    public static Properties prop;
    public static FileInputStream fileInput;

    public static void main(String[] args) {

        prop = new Properties();

        //load properties file

        File file = new File(System.getProperty("user.dir") + "//src//main//resources//config.properties");
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tokenString = prop.getProperty("token");
        JDA bot = JDABuilder.createDefault(tokenString)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.listening("you suck"))
                .addEventListeners(new Main()).build();

    }

    List<String> lineup = new ArrayList<>();
    File file = null;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(!event.getAuthor().isBot()) {

            if(event.getAuthor().getName().equals("AmeghMKadegh")) {
                event.getMessage().addReaction(Emoji.fromFormatted("U+270AU+1F3FF")).complete();
            }

            if (event.getMessage().getContentRaw().contains("@Valo") && !event.getMessage().getContentRaw().contains("news")) {
                if(!lineup.contains(event.getAuthor().getName())) {
                    if (lineup.size() >= 5) {
                        event.getChannel().sendMessage("There are already 5 people in the lineup, please use !clear if you would like to clear the lineup").queue();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        event.getChannel().sendMessage("retard").queue();
                    } else {
                        lineup.add(event.getAuthor().getName());
                    }
                }
            }


            if(event.getMessage().getContentRaw().equalsIgnoreCase("!chingaTuMadre")) {
                RestAction<Message> rest;
                file = new File(System.getProperty("user.dir") + "//src//main//resources//pics//ifykyk.png");


                event.getChannel().sendFiles(FileUpload.fromData(file).asSpoiler()).queue();

            }
            if(event.getMessage().getContentRaw().contains("+1") && event.getMessage().getContentRaw().toLowerCase().contains("valo")) {

                if(!lineup.contains(event.getAuthor().getName())) {
                    if (lineup.size() >= 5) {
                        event.getChannel().sendMessage("There are already 5 people in the lineup, please use !clear if you would like to clear the lineup").queue();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        event.getChannel().sendMessage("retard").queue();
                    } else {
                        lineup.add(event.getAuthor().getName());
                    }
                }
            }

            if(event.getMessage().getContentRaw().equalsIgnoreCase("!clear")) {
                lineup.clear();
            }

            if(event.getMessage().getContentRaw().equalsIgnoreCase("!lineup")) {
                String players = "";
                for (int i = 0; i < lineup.size(); i++) {
                    if (i == lineup.size()-1) {
                        players += lineup.get(i);
                    } else {
                        players += lineup.get(i) + ", ";
                    }
                }

                event.getChannel().sendMessage("The current valo lineup is: " + players).queue();
            }

            if(event.getMessage().getContentRaw().contains("-1") && event.getMessage().getContentRaw().toLowerCase().contains("valo")) {
                lineup.remove(event.getMessage().getAuthor().getName());
            }
        }
    }
}
