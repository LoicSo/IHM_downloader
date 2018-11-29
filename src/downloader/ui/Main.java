package downloader.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// Fenetre principale
		BorderPane root = new BorderPane();

		// box qui contient les progresse barres
		VBox box = new VBox();		

		// Composant principal
		ScrollPane liste = new ScrollPane(box);

		// Pour chaque fichier 
		for(String url: getParameters().getRaw()) {

			Download d = new Download(url);

			// ajoute la progressBar a la VBox
			box.getChildren().add(d.getPb());

		}

		// Champs pour ajouter un fichier
		TextField txt = new TextField();

		// Bouton pour ajouter le fichier a telecharger
		Button add = new Button("add");
		add.setOnAction( e -> {
			Download d =  new Download(txt.getText());
			box.getChildren().add(d.getPb());
		});

		HBox hb = new HBox();
		hb.getChildren().addAll(txt, add);
		
		root.setCenter(liste);
		root.setBottom(hb);
		
		stage.setTitle("Downloader - Loic Souchon && Jade Vandal");
		stage.setScene(new Scene(root, 500, 500));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
