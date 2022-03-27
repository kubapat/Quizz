package client.scenes;

import client.Session;
import client.utils.Utils;
import commons.Emoji;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.*;

public class MultiplayerLobbyCtrl {
    private MainCtrl mainCtrl;

    private boolean isLeader;
    private Timer playerUpdateTimer;
    private Pair<StackPane, Text> ownPlayerTag;
    private LinkedHashMap<Pair<StackPane, Text>, String> playerTags;

    @Inject
    public MultiplayerLobbyCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;

        this.playerTags = new LinkedHashMap<>();
        this.ownPlayerTag = null;
    }

    @FXML
    private Button startButton;
    @FXML
    private Pane emoteBox;
    @FXML
    private Button emoteMenu;
    @FXML
    private Button emoteButtonSmile;
    @FXML
    private Button emoteButtonSad;
    @FXML
    private Button emoteButtonAngry;
    @FXML
    private Button emoteButtonSurprise;
    @FXML
    private Button emoteButtonCelebrate;
    @FXML
    private Button emoteButtonSunglasses;

    @FXML
    private StackPane playerNameBackground1;
    @FXML
    private Text playerNameLabel1;
    @FXML
    private StackPane playerNameBackground2;
    @FXML
    private Text playerNameLabel2;
    @FXML
    private StackPane playerNameBackground3;
    @FXML
    private Text playerNameLabel3;
    @FXML
    private StackPane playerNameBackground4;
    @FXML
    private Text playerNameLabel4;
    @FXML
    private StackPane playerNameBackground5;
    @FXML
    private Text playerNameLabel5;
    @FXML
    private StackPane playerNameBackground6;
    @FXML
    private Text playerNameLabel6;
    @FXML
    private StackPane playerNameBackground7;
    @FXML
    private Text playerNameLabel7;
    @FXML
    private StackPane playerNameBackground8;
    @FXML
    private Text playerNameLabel8;
    @FXML
    private StackPane playerNameBackground9;
    @FXML
    private Text playerNameLabel9;
    @FXML
    private StackPane playerNameBackground10;
    @FXML
    private Text playerNameLabel10;

    public void init() {

        initialisePlayers();

        initialiseEmoteMenu();

        if (isLeader) {
            startButton.setDisable(false);
            startButton.setVisible(true);
        }
        else {
            startButton.setDisable(true);
            startButton.setVisible(false);
        }

        //TODO
    }

    public void goBackToSplash() {
        playerUpdateTimer.cancel();
        //TODO
        // IF PLAYER WAS LEADER THEY MUST PASS IT ON TO ANOTHER PLAYER
        mainCtrl.showSplash();
    }

    public void startGameButtonPressed() {
        startButton.setDisable(true);
        startButton.setVisible(false);

        //TODO
        // SEND START OF GAME TO OTHER PLAYERS AND SWITCH ALL PLAYERS TO QUESTION SCREEN
        // ALSO LOCK THE LOBBY, NO MORE PLAYERS CAN JOIN
    }

    /**
     * Initialise all player tags and display their names
     */
    private void initialisePlayers() {

        playerTags.put(new Pair<>(playerNameBackground1, playerNameLabel1), null);
        playerTags.put(new Pair<>(playerNameBackground2, playerNameLabel2), null);
        playerTags.put(new Pair<>(playerNameBackground3, playerNameLabel3), null);
        playerTags.put(new Pair<>(playerNameBackground4, playerNameLabel4), null);
        playerTags.put(new Pair<>(playerNameBackground5, playerNameLabel5), null);
        playerTags.put(new Pair<>(playerNameBackground6, playerNameLabel6), null);
        playerTags.put(new Pair<>(playerNameBackground7, playerNameLabel7), null);
        playerTags.put(new Pair<>(playerNameBackground8, playerNameLabel8), null);
        playerTags.put(new Pair<>(playerNameBackground9, playerNameLabel9), null);
        playerTags.put(new Pair<>(playerNameBackground10, playerNameLabel10), null);

        playerUpdateTimer = new Timer();
        playerUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("PLAYER UPDATE TIMER");
                playerUpdate();

                receiveEmotes();

                System.out.println("isleader: " + isLeader);

//                    if (!isLeader) {
//                        //TODO
//                        // If leader is changed update start button accordingly
//                        // If leader starts game display transition countdown
//
//                    }
            }

        }, 0, 1000);

    }

    /**
     * Initialise all event handling for the emote menu
     */
    private void initialiseEmoteMenu() {
        emoteMenu.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                showEmotes(true);
            }
        });
        emoteBox.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                showEmotes(false);
            }
        });

        emoteButtonSmile.setOnAction(e -> sendEmote("smile"));
        emoteButtonSad.setOnAction(e -> sendEmote("sad"));
        emoteButtonAngry.setOnAction(e -> sendEmote("angry"));
        emoteButtonSurprise.setOnAction(e -> sendEmote("surprise"));
        emoteButtonCelebrate.setOnAction(e -> sendEmote("celebrate"));
        emoteButtonSunglasses.setOnAction(e -> sendEmote("sunglasses"));
    }

    /**
     * Sets the status of the emote buttons, either allows the user to view and click the emote buttons
     * or disables and hides the emote buttons
     *
     * @param enable value for enabled (true) or disabled (false)
     */
    private void showEmotes(boolean enable) {
        emoteMenu.setVisible(!enable);

        emoteButtonSmile.setDisable(!enable);
        emoteButtonSmile.setVisible(enable);
        emoteButtonSad.setDisable(!enable);
        emoteButtonSad.setVisible(enable);
        emoteButtonAngry.setDisable(!enable);
        emoteButtonAngry.setVisible(enable);
        emoteButtonSurprise.setDisable(!enable);
        emoteButtonSurprise.setVisible(enable);
        emoteButtonCelebrate.setDisable(!enable);
        emoteButtonCelebrate.setVisible(enable);
        emoteButtonSunglasses.setDisable(!enable);
        emoteButtonSunglasses.setVisible(enable);
    }

    /**
     * Once the player clicks an emote button, this method is called
     * It then sends the emote to the other players in the session
     *
     * @param emoteType identifies which emote button was clicked and what emote to send
     */
    private void sendEmote(String emoteType) {
        System.out.println("emote button pressed: " + emoteType); //DEBUG LINE
        System.out.println(Utils.setEmoji(Session.getNickname(), emoteType));
    }

    /**
     * Receive any emotes sent by other players and display them next to the corresponding player tag
     */
    private void receiveEmotes() {
        List<Emoji> activeEmojiList = Utils.getLobbyStatus().getEmojiList();
        System.out.println("\nEMOJI LIST: " + activeEmojiList + "\n");

        // Loop through all active emojis and display them according to the user that sent it
        for (Emoji emoji : activeEmojiList) {
            System.out.println("\nActive emoji: " + emoji.getEmojiType() + "\n");

            String userApplying = emoji.getUserApplying();
            StackPane playerLabel = null;

            // Find the right player tag for the given user that applied the emoji
            for (Pair<StackPane, Text> tag : playerTags.keySet()) {
                if (Objects.equals(userApplying, tag.getValue().getText())) {
                    playerLabel = tag.getKey();
                }
            }
            if (playerLabel != null) {
                Text test = new Text(
                        playerLabel.getLayoutX() + 350, playerLabel.getLayoutY(),
                        userApplying + " used: " + emoji.getEmojiType());
                test.setVisible(true);
            }
        }
    }

    /**
     * Constantly called method to update any changes in the players in the lobby
     * Their player tags, emotes and who the lobby leader is
     */
    private void playerUpdate() {
        List<String> playerList = Utils.getCurrentSessionPlayers();

        System.out.println("Player list: " + playerList);

        for (Pair<StackPane, Text> tag : playerTags.keySet()) {
            if (!playerList.contains(playerTags.get(tag))) { // If player is no longer in the session's player list
                playerTags.replace(tag, null);
            }

            // If it is the player's tag, and it has not been set yet
            if (Objects.equals(tag.getValue().getText(), Session.getNickname()) && ownPlayerTag == null) {
                ownPlayerTag = tag;
                ownPlayerTag.getValue().setFill(Color.web("#f15025"));
            }
        }

        for (String player : playerList) {

            if (!playerTags.containsValue(player)) { // If the player is not yet displayed
                for (Pair<StackPane, Text> tag : playerTags.keySet()) {
                    if (playerTags.get(tag) == null) { // If the player tag is empty
                        playerTags.replace(tag, player);
                        tag.getValue().setText(player); // Set text to display players name
                        break;
                    }
                }
            }
        }


        //Run through all players and player labels and check if the players are still in the session
        //TODO
    }

    private void startGame() {
        //TODO
        // Start the game locally
    }

    public void setLeader(boolean leader) {
        this.isLeader = leader;
    }

    public boolean getLeader() {
        return this.isLeader;
    }
}
