import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;



public class Main extends ListenerAdapter{



    public static Properties prop;
    public static FileInputStream fileInput;
    Random random = new Random();

    static ShardManager shardManager = null;


    public static void main(String[] args) {

        prop = new Properties();
        //load properties file



//        System.out.println(js.getString("text"));

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
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .setActivity(Activity.listening("numan int"))
                .addEventListeners(new Main()).build();

//        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(tokenString);
//        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
//        builder.setChunkingFilter(ChunkingFilter.ALL);

//        shardManager = builder.build();
//        shardManager.addEventListener(new EventListener());

    }

    List<String> lineup = new ArrayList<>();
    File file = null;
    String id = "";
    String callerName = "";
    String autoId = "";
    String previousCall = "";
    String previousLineupMessageId = "";


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor().getName().equals("AmeghMKadegh")) {
            event.getMessage().addReaction(Emoji.fromFormatted("U+270AU+1F3FF")).complete();
        }

        if(event.getAuthor().getName().equals("TNT SEXY BEAST")) {
            event.getMessage().addReaction(Emoji.fromCustom("numan", 1001519460571684966L,true)).complete();
        }
        List<Role> mentionables = event.getMessage().getMentions().getRoles();


        if (mentionables.toString().contains("Role:Valo(id=831370507999641633)") || mentionables.toString().contains("Role:CS(id=837425570656026645)")) {
            try {
                event.getChannel().removeReactionById(previousCall, Emoji.fromFormatted("U+2705")).complete();
                event.getChannel().deleteMessageById(autoId).queue();

            }catch (Exception ignored) {
            }
            id = event.getMessageId();
            System.out.println(id);
            callerName = event.getAuthor().getName();
            event.getChannel().sendMessage(callerName + ": Please click on the \"✅\" attached to your message").queue();
            event.getMessage().addReaction(Emoji.fromFormatted("U+2705")).complete();
            previousCall = event.getMessageId();
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
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    lineup.add(event.getAuthor().getName());
                }
            }
        }

        if(event.getAuthor().isBot()) {
            if (event.getMessage().getContentRaw().contains("Please click on the \"✅\" attached to your message")) {
                autoId = event.getMessage().getId();
            }

            if(event.getMessage().getContentRaw().contains("The current 5 man lineup is:")) {
                previousLineupMessageId = event.getMessageId();
            }
        }


        if(!event.getAuthor().isBot()) {


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

            if(event.getMessage().getContentRaw().contains("-1") && event.getMessage().getContentRaw().toLowerCase().contains("valo")) {
                lineup.remove(event.getMessage().getAuthor().getName());
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
//        System.out.println(Objects.requireNonNull(event.getUser()).getName());

//        String user = Objects.requireNonNull(event.getUser()).getName();
//        System.out.println(user);
//        user.getName()
            if (!event.getUser().isBot()) {

//        System.out.println(Objects.requireNonNull(event.getUser()).getName());

                String user = event.getUser().getName();
                System.out.println(user);
                if (event.getReaction().toString().contains("U+2705") && event.getReaction().toString().contains(id)) {
                    lineup.remove(user);
                    try {
                        System.out.println("entered");
//                        event.getChannel().deleteMessageById(previousLineupMessageId).queue();
                        event.getChannel().editMessageById(previousLineupMessageId,"The current 5 man lineup is: " + lineup).queue();
//                    event.getChannel().editMessageById(previousLineupMessageId,"The current valo lineup is: " + lineup).queue();


                    } catch (Exception ignored) {
                    }
//                    try {
//                        System.out.println("trying");
//                        event.getReaction().removeReaction().queue();
//                        event.getChannel().deleteMessageById(autoId).queue();
//                        event.getChannel().sendMessage("The current 5 man lineup is: " + lineup).queue();
//                    } catch (Exception ignored) {
//                    }
                }
            }
    }
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        if (!event.getUser().isBot()) {
            String user = event.getUser().getName();
            if (event.getReaction().toString().contains("U+2705") && event.getReaction().toString().contains(id)) {
                if (!lineup.contains(user)) {
                    lineup.add(user);
                }
                try {
                    if (previousLineupMessageId.isEmpty()) {
//                        event.getChannel().deleteMessageById(autoId).queue();
                        event.getChannel().sendMessage("The current 5 man lineup is: " + lineup).queue();
                    }
//                    event.getChannel().deleteMessageById(previousLineupMessageId).queue();
                    event.getChannel().editMessageById(previousLineupMessageId,"The current 5 man lineup is: " + lineup).queue();

                }catch (Exception ignored) {
                }

//                if (event.getChannel().
                try {
                    event.getReaction().removeReaction().queue();
                    if (!autoId.equals("")) {
                        event.getChannel().deleteMessageById(autoId).queue();
                        autoId = "";
                    }
//                    event.getChannel().sendMessage("The current 5 man lineup is: " + lineup).queue();
                }catch(Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("funfact")) {
            baseURI="https://uselessfacts.jsph.pl/";
            String getPlaceResponse = given().log().all().when().get("/api/v2/facts/random?language=en").then().log().all().extract().response().asString();
            JsonPath js = new JsonPath(getPlaceResponse);
            event.reply(js.getString("text")).complete();
        }else if(command.equals("commands")) {
            event.reply("/chingatumadre    /funfact    /commands    /lineup    /clear   /numanhookincident    /upcominggames").queue();
        }else if (command.equals("clear")) {
            lineup.clear();
            event.reply("The lineup has been cleared").queue();
        }else if (command.equals("lineup")) {
            try {
                event.getChannel().deleteMessageById(autoId).queue();
            }catch (Exception ignored) {

            }

            try {
                event.getChannel().deleteMessageById(previousLineupMessageId).queue();
                event.reply("The current 5 man lineup is: " + lineup).queue();
            }catch(Exception e) {
                System.out.println("caught On Message");
            }
        }else if (command.equals("numanhookincident")) {

            event.reply("https://outplayed.tv/media/zgYgyl/lol").queue();

        }else if(command.equals("upcominggames")) {

            OptionMapping chooseLeague = event.getOption("league");
            OptionMapping chooseNumberOfGames = event.getOption("numberofgames");

            assert chooseLeague != null;
            String league = chooseLeague.getAsString();

            assert chooseNumberOfGames != null;
            String numberOfGames = chooseNumberOfGames.getAsString();

            String desiredLeague = "";
            switch(league) {
                case "prem":
                    desiredLeague = "39";
                    break;
                case "french":
                    desiredLeague = "41";
                    break;
                case "bundesliga":
                    desiredLeague = "78";
                    break;
            }

            baseURI = "https://api-football-v1.p.rapidapi.com/";
            System.out.println("entered");
            String getPlaceResponse = given().log().all().queryParam("season","2022").queryParam("league",desiredLeague)
                    .queryParam("next",numberOfGames).header("X-RapidAPI-Key","fe2884d1b2msh9b003deb9acfb2bp14865ejsn20be0d42f94b")
                    .header("X-RapidAPI-Host","api-football-v1.p.rapidapi.com").when().get("/v3/fixtures").then().log().all().extract().response().asString();
            System.out.println(getPlaceResponse);

            JsonPath js = new JsonPath(getPlaceResponse);
            System.out.println(js.getString("response.teams.home.name"));
            int size = js.getString("response.teams.home.name").split(",").length;
            String[] splitHome = js.getString("response.teams.home.name").split(",");
            String[] splitAway = js.getString("response.teams.away.name").split(",");
            String[] splitTime = js.getString("response.fixture.date").split(",");
            String[] leagueName = js.getString("response.league.name").split(",");
            System.out.println(size);
            String response = "";


            for (int i = 0; i < size; i++) {
                splitTime[i] = splitTime[i].substring(0,11) + " at " + splitTime[i].substring(12,17) + " UTC" ;
                if (i == 0) {
                    response += leagueName[0] + "\n";
                }
                response += splitHome[i].trim() + " vs " + splitAway[i].trim() + " on " + splitTime[i].trim() + "\n";
            }
            response = response.replaceAll("\\[","");
            response = response.replace("]","");
            System.out.println(response);
            event.reply(response).queue();

        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData league = new OptionData(OptionType.STRING, "league","prem, french, bundesliga");
        OptionData nextNumberOfGames = new OptionData(OptionType.STRING, "numberofgames","choose how many games you want to see");
        commandData.add(Commands.slash("commands","all commands"));
        commandData.add(Commands.slash("funfact","say a fun fact"));
        commandData.add(Commands.slash("lineup","get the current 5 man lineup"));
        commandData.add(Commands.slash("clear","clear lineup"));
        commandData.add(Commands.slash("numanhookincident","you don't wanna know"));
        commandData.add(Commands.slash("upcominggames", "IN BETA").addOptions(league).addOptions(nextNumberOfGames));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
