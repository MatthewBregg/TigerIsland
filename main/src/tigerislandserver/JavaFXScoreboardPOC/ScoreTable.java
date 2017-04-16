package tigerislandserver.JavaFXScoreboardPOC;

import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Scanner;

public class ScoreTable extends Application implements Runnable {
    private TableView scoreTable = new TableView();

    private ObservableList<TournamentScore> scores = FXCollections.observableArrayList(
            new TournamentScore("Team_A"), new TournamentScore("Team_B"));

    final HBox hbox1 = new HBox();
    final HBox hbox2 = new HBox();

    @Override
    public void run(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("SWE Tournament Challenge");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.setResizable(true);

        final Label label = new Label("Tournament Scores");
        label.setFont(new Font("Arial", 20));

        scoreTable.setEditable(true);
        scoreTable.setScaleShape(true);
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell();
            }
        };

        TableColumn teamColumn = new TableColumn("Team Name");
        teamColumn.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("teamName"));

        TableColumn tourneyScore = new TableColumn("Tournament Score");
        tourneyScore.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tourneyScore"));

        TableColumn challengeScore = new TableColumn("Challenge Score");
        challengeScore.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("challengeScore"));


        TableColumn scoreGameA = new TableColumn("Score");
        scoreGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("scoreGameA"));
        scoreGameA.setCellFactory(cellFactory);
        TableColumn villagersGameA = new TableColumn("Villagers");
        villagersGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("villagerGameA"));
        villagersGameA.setCellFactory(cellFactory);
        TableColumn totorosGameA = new TableColumn("Totoros");
        totorosGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("totoroGameA"));
        totorosGameA.setCellFactory(cellFactory);
        TableColumn tigersGameA = new TableColumn("Tigers");
        tigersGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tigerGameA"));
        tigersGameA.setCellFactory(cellFactory);
        TableColumn statusGameA = new TableColumn("Status");
        statusGameA.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("statusGameA"));
        statusGameA.setCellFactory(cellFactory);

        TableColumn gameA = new TableColumn("Game A");
        gameA.getColumns().addAll(scoreGameA, villagersGameA, totorosGameA, tigersGameA, statusGameA);

        TableColumn scoreGameB = new TableColumn("Score");
        scoreGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("scoreGameB"));
        scoreGameB.setCellFactory(cellFactory);
        TableColumn villagersGameB = new TableColumn("Villagers");
        villagersGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("villagerGameB"));
        villagersGameB.setCellFactory(cellFactory);
        TableColumn totorosGameB = new TableColumn("Totoros");
        totorosGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("totoroGameB"));
        totorosGameB.setCellFactory(cellFactory);
        TableColumn tigersGameB = new TableColumn("Tigers");
        tigersGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("tigerGameB"));
        tigersGameB.setCellFactory(cellFactory);
        TableColumn statusGameB = new TableColumn("Status");
        statusGameB.setCellValueFactory(new PropertyValueFactory<TournamentScore, String>("statusGameB"));
        statusGameB.setCellFactory(cellFactory);

        TableColumn gameB = new TableColumn("Game B");
        gameB.getColumns().addAll(scoreGameB, villagersGameB, totorosGameB, tigersGameB);


        scoreTable.setItems(scores);
        scoreTable.getColumns().addAll(teamColumn, tourneyScore, challengeScore, gameA, gameB);

//        final TextField

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, scoreTable);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();

        Thread scoreAdder = new Thread(new Runnable() {
            public void run() {
                Scanner input = new Scanner(System.in);

                while (true) {
                    System.out.println("Add a team:");
                    String newTeam = input.nextLine();
                    scores.add(new TournamentScore(newTeam));
                }
            }
        });
        scoreAdder.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
