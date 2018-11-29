package downloader.ui;

import downloader.fc.Downloader;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Download {
	VBox box;
	
	public Download(String url) {
		
		Downloader downloader = null;
		try {
			// Nouveau telechargement
			downloader = new Downloader(url);
		}
		catch(RuntimeException e) {
			System.err.format("skipping %s %s\n", url, e);
		}
		
		// thread qui execute le telechargement du fichier
		Thread t = new Thread(downloader);

		box = new VBox();
		
		// nom du fichier a telecharger 
		Label text = new Label(url);
		
		// ProgressBar + boutons play et pause
		HBox hb = new HBox();
		
		Button play = new Button("play");
		Button pause = new Button("supprimer");
		
		// ProgressBar qui represente le telechargement
		ProgressBar pb = new ProgressBar();
		pb.setPrefWidth(330);
		pb.setPrefHeight(25);
		
		downloader.progressProperty().addListener((obs, o, n) -> {
			Platform.runLater(() -> pb.setProgress((double) n));
		});
		
		hb.setSpacing(2);
		hb.getChildren().addAll(pb, play, pause);
		
		box.setSpacing(2);
		box.getChildren().addAll(text, hb);
		
		// lancement du thread
		t.start();	
	}
	
	VBox getPb() {
		return box;
	}
}
