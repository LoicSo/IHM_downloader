package downloader.ui;

import downloader.fc.Downloader;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Download extends VBox{
	
	VBox root;
	Downloader downloader;
	
	public Download(String url, VBox root) {
		
		// On récupère la liste des téléchargements
		this.root = root;
		
		try {
			// Nouveau telechargement
			downloader = new Downloader(url);
		} catch (RuntimeException e) {
			System.err.format("skipping %s %s\n", url, e);
			throw e;
		}

		// thread qui execute le telechargement du fichier
		Thread t = new Thread(downloader);
		
		// nom du fichier a telecharger
		Label text = new Label(url);

		// ProgressBar + boutons play/pause et supprimer
		HBox hb = new HBox();

		// Bouton play/pause
		ToggleButton play = new ToggleButton("pause");
		play.setOnAction(e -> {
			if(play.isSelected()) {
				play.setText("play");
				downloader.setPause(true);
			}
			else {
				play.setText("pause");
				downloader.play();
			}
		});

		// Bouton supprimer
		Button suppr = new Button("supprimer");
		suppr.setOnAction(e -> {
			t.interrupt();
			remove();
		});

		// ProgressBar qui represente le telechargement
		ProgressBar pb = new ProgressBar();
		pb.setPrefWidth(330);
		pb.setPrefHeight(25);

		downloader.progressProperty().addListener((obs, o, n) -> {
			Platform.runLater(() -> pb.setProgress((double) n));
		});

		hb.setSpacing(2);
		hb.getChildren().addAll(pb, play, suppr);

		this.setSpacing(2);
		this.getChildren().addAll(text, hb);

		// lancement du thread
		t.start();
	}

	void remove() {
		root.getChildren().remove(this);
	}
}
