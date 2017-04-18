package tigerislandserver.JavaFXScoreboardPOC;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ScoreTable extends Application implements Runnable {
    private TableView scoreTable = new TableView();
    private TableView roundTable = new TableView();

    private ObservableList<TournamentScore> scores = FXCollections.observableArrayList(
            new TournamentScore("TEAM_A_TEST"), new TournamentScore("TEAM_B_TEST"));

    private ObservableList<RoundInfo> roundInfo = FXCollections.observableArrayList();
    private TourneySvrMgr tourneyMgr = new TourneySvrMgr(scores, roundInfo);
    private Thread tournament;

    final HBox hbox1 = new HBox();

    @Override
    public void run() {
        launch();
    }

    private void initTableProperties(TableView tableView){
        tableView.setEditable(true);
        tableView.setScaleShape(true);
        tableView.setMinWidth(1500);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("SWE Tournament Challenge");
        primaryStage.setWidth(1500);
        primaryStage.setHeight(500);
        primaryStage.setResizable(true);

        final Label label = new Label("Tournament Scores");
        label.setFont(new Font("Arial", 20));


        initTableProperties(roundTable);
        initTableProperties(scoreTable);
        roundTable.setMaxHeight(100);



        scoreTable.setItems(scores);
        roundTable.setItems(roundInfo);


        setupTable(scoreTable);
        setupRoundTable(roundTable);

        setupTourneyInfoRow(hbox1);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label,roundTable, scoreTable, hbox1);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupRoundTable(TableView tableView){
        TableColumn currentRoundCol = new TableColumn("Current Round");
        currentRoundCol.setCellValueFactory(new PropertyValueFactory<RoundInfo, String>("currentRound"));

        TableColumn endRoundCol = new TableColumn("End Round");
        endRoundCol.setCellValueFactory(new PropertyValueFactory<RoundInfo, String>("endRound"));

        TableColumn currentChallenge = new TableColumn("Current Challenge");
        currentChallenge.setCellValueFactory(new PropertyValueFactory<RoundInfo, String>("currentChallenge"));

        TableColumn endChallenge = new TableColumn("End Challenge");
        endChallenge.setCellValueFactory(new PropertyValueFactory<RoundInfo, String>("endChallenge"));


        tableView.getColumns().addAll(currentRoundCol,endRoundCol,currentChallenge,endChallenge);

    }

    private void setupTable(TableView tableView) {
        TableColumn teamColumn = new TableColumn("Team Name");
        teamColumn.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("teamName"));

        TableColumn tourneyScore = new TableColumn("Tournament Score");
        tourneyScore.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tourneyScore"));

        TableColumn challengeScore = new TableColumn("Challenge Score");
        challengeScore.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("challengeScore"));

        TableColumn opponentName = new TableColumn("Opponent");
        opponentName.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("opponentName"));


        TableColumn scoreGameA = new TableColumn("Score");
        scoreGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("scoreGameA"));

        TableColumn villagersGameA = new TableColumn("Villagers");
        villagersGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("villagerGameA"));

        TableColumn totorosGameA = new TableColumn("Totoros");
        totorosGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("totoroGameA"));

        TableColumn tigersGameA = new TableColumn("Tigers");
        tigersGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tigerGameA"));

        TableColumn statusGameA = new TableColumn("Status");
        statusGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("statusGameA"));

        TableColumn gameA = new TableColumn("Game A");
        gameA.getColumns().addAll(scoreGameA, villagersGameA, totorosGameA, tigersGameA, statusGameA);


        TableColumn scoreGameB = new TableColumn("Score");
        scoreGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("scoreGameB"));

        TableColumn villagersGameB = new TableColumn("Villagers");
        villagersGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("villagerGameB"));

        TableColumn totorosGameB = new TableColumn("Totoros");
        totorosGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("totoroGameB"));

        TableColumn tigersGameB = new TableColumn("Tigers");
        tigersGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tigerGameB"));

        TableColumn statusGameB = new TableColumn("Status");
        statusGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("statusGameB"));

        TableColumn gameB = new TableColumn("Game B");
        gameB.getColumns().addAll(scoreGameB, villagersGameB, totorosGameB, tigersGameB, statusGameB);

        tableView.getColumns().addAll(teamColumn, tourneyScore, challengeScore, opponentName, gameA, gameB);
    }

    private void setupTourneyInfoRow(HBox hbox) {

        final TextField ipAddr = new TextField();
        ipAddr.setEditable(false);
        ipAddr.setPromptText("IP Addr");
        String actualIP = null;
        try {
            actualIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipAddr.setText(actualIP);

        final Label ipLabel = new Label("IP:");
        ipLabel.setFont(new Font("Arial", 10));

        HBox ipBox = new HBox(3,ipLabel, ipAddr);

        final TextField portNbr = new TextField();
        portNbr.setEditable(true);
        portNbr.setPrefColumnCount(4);
        portNbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    portNbr.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        portNbr.setPromptText("Port");
        portNbr.setText("" + tourneyMgr.getTourneyPort());

        final Label portLabel = new Label("Port:");
        portLabel.setFont(new Font("Arial", 10));

        HBox portBox = new HBox(3,portLabel, portNbr);

        final TextField password = new TextField();
        password.setEditable(true);
        password.setPromptText("Password");
        String actualPass = tourneyMgr.getTourneyPassword();
        password.setText(actualPass);

        final Label passLabel = new Label("Password:");
        passLabel.setFont(new Font("Arial", 10));

        HBox passBox = new HBox(3,passLabel, password);

        final TextField accessTime = new TextField();
        accessTime.setEditable(true);
        accessTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    accessTime.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        accessTime.setPromptText("Access Time");
        accessTime.setText("" + tourneyMgr.getAccessTime());

        final Label accessLabel = new Label("Time to Login:");
        accessLabel.setFont(new Font("Arial", 10));

        HBox accessBox = new HBox(3, accessLabel, accessTime);

        final TextField userPass = new TextField();
        userPass.setEditable(true);
        userPass.setPromptText("File Path");
        userPass.setText("" + tourneyMgr.getAccessFilePath());

        final Label userPassLabel = new Label("User & Pass File:");
        userPassLabel.setFont(new Font("Arial", 10));

        HBox userPassBox = new HBox(3, userPassLabel, userPass);

        final TextField nbrChallenges = new TextField();
        nbrChallenges.setEditable(true);
        nbrChallenges.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    nbrChallenges.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        nbrChallenges.setPromptText("# Challenges");
        nbrChallenges.setText("" + tourneyMgr.getNbrOfChallenges());

        final Label chalCountLabel = new Label("Number of Challenges:");
        chalCountLabel.setFont(new Font("Arial", 10));

        HBox chalNbrBox = new HBox(3, chalCountLabel, nbrChallenges);

        final TextField tourneySeed = new TextField();
        tourneySeed.setEditable(true);
        tourneySeed.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    tourneySeed.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        tourneySeed.setPromptText("Seed Value");
        tourneySeed.setText("" + tourneyMgr.getTourneySeed());

        final Label seedLabel = new Label("Challenge Seed:");
        seedLabel.setFont(new Font("Arial", 10));

        HBox seedBox = new HBox(3, seedLabel, tourneySeed);

        EventHandler<ActionEvent> setup = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tourneyMgr.setTourneyPort(Integer.parseInt(portNbr.getText()));
                tourneyMgr.setTourneyPassword(password.getText());
                tourneyMgr.setAccessTime(Integer.parseInt(accessTime.getText()));
                tourneyMgr.setTourneySeed(Integer.parseInt(tourneySeed.getText()));
                tourneyMgr.setNbrOfChallenges(Integer.parseInt(nbrChallenges.getText()));
                tourneyMgr.setAccessFilePath(userPass.getText());
            }
        };

        EventHandler<ActionEvent> runTourny = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(tournament == null){
                    tournament = new Thread(tourneyMgr);
                } else if(tournament.getState() == Thread.State.TERMINATED) {
                    tourneyMgr = new TourneySvrMgr(scores, roundInfo);
                    tournament = new Thread(tourneyMgr);
                }
                if(tournament.getState() == Thread.State.NEW){
                    setup.handle(event);
                    tournament.start();
                }
            }
        };

        final Button tourneySetupButton = new Button("Set Values");
        tourneySetupButton.setOnAction(setup);

        final Button startButton = new Button("Start Tournament");
        startButton.setOnAction(runTourny);

        HBox serverSettings = new HBox(ipBox, portBox, passBox, accessBox);
        serverSettings.setSpacing(10);
        HBox tourneySettings = new HBox(userPassBox, chalNbrBox, seedBox);
        tourneySettings.setSpacing(10);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(serverSettings, tourneySettings, startButton);


        hbox.getChildren().addAll(vbox);
        hbox.setSpacing(3);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
