package tigerislandserver.JavaFXScoreboardPOC;

import javafx.application.Application;
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

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ScoreTable extends Application implements Runnable {
    private TableView scoreTable = new TableView();
    private TableView roundTable = new TableView();

    private ObservableList<TournamentScore> scores = FXCollections.observableArrayList(
            new TournamentScore("TEAM_A_TEST"), new TournamentScore("TEAM_B_TEST"));

    private ObservableList<TournamentScore> roundInfo = FXCollections.observableArrayList(new TournamentScore())
    private TourneySvrMgr tourneyMgr = new TourneySvrMgr(scores);
    private Thread tournament;

    final HBox hbox1 = new HBox();
    final HBox hbox2 = new HBox();

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



        scoreTable.setItems(scores);
        setupTable(scoreTable);
        setupTourneyInfoRow(hbox1);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, scoreTable, hbox1);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
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
        ipAddr.setPromptText("IP Addr:");
        String actualIP = null;
        try {
            actualIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipAddr.setText(actualIP);

        final TextField portNbr = new TextField();
        portNbr.setEditable(true);
        portNbr.setPromptText("Port:");
        portNbr.setText("" + tourneyMgr.getTourneyPort());
        // will need to convert back to int for passing to tourny mgr

        final TextField password = new TextField();
        password.setEditable(true);
        password.setPromptText("Password:");
        String actualPass = tourneyMgr.getTourneyPassword();
        password.setText(actualPass);

        final TextField accessTime = new TextField();
        accessTime.setEditable(true);
        accessTime.setPromptText("Access Time:");
        accessTime.setText("" + tourneyMgr.getAccessTime());

        final TextField tourneySeed = new TextField();
        tourneySeed.setEditable(true);
        tourneySeed.setPromptText("Seed Value:");
        tourneySeed.setText("" + tourneyMgr.getTourneySeed());

        final Button startButton = new Button("Start Tournament");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tournament = new Thread(tourneyMgr);
                if(!tournament.isAlive()) {
                    tournament.start();
                }
            }
        });

        hbox.getChildren().addAll(ipAddr, portNbr, password, accessTime, tourneySeed, startButton);
        hbox.setSpacing(3);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
