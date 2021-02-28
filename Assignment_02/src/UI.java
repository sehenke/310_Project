package application;
	
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class UI extends Application {
	
	@Override
	public void init() throws Exception{
		System.out.println("init");
	}
	
	@Override
	public void start(Stage primaryStage){
		try {
			ChatBot cb = new ChatBot("Mitch");
			Text textarea = new Text();
			textarea.setWrappingWidth(400);
			TextField textfield = new TextField();
			textfield.setPrefWidth(300);
			
			Button send = new Button("Send");
			send.setDefaultButton(true);
			send.setPrefWidth(100);
			send.setOnAction(enter -> {
				System.out.println(textfield.getText());
				textarea.setText(textarea.getText() + "User: " + textfield.getText() + "\n");
				System.out.println(textfield.getText());
				if(textfield.getText().contains("bye".toLowerCase()))
					System.exit(0);
				textarea.setText(textarea.getText() + "Bot: " + cb.sendPhrase(textfield.getText()) + "\n");
				textfield.setText("");
			});
			
			ScrollPane p = new ScrollPane();
			p.setPrefSize(400, 400);
			p.setMaxSize(400, 400);
			p.setContent(textarea);
			HBox h = new HBox(textfield,send);
			h.setPrefSize(400, 25);
			h.setMaxSize(400, 25);
			VBox box = new VBox(p,h);
			Scene scene = new Scene(box,400,425);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Chatbot");
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println("exit");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}