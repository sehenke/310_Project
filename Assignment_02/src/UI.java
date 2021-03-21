
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
	private double WIDTH = 400, HEIGHT = 425;
	private VBox app;
	private HBox userspace;
	private ScrollPane textwindow;
	private Text text;
	private TextField textfield;
	private Button send;
	private ChatBot cb;
	
	@Override
	public void init() throws Exception{
		System.out.println("init");
	}
	
	@Override
	public void start(Stage stage){
		try {
			cb = new ChatBot("Mitch");
			text = new Text();
			textwindow = new ScrollPane();
			textfield = new TextField();
			textfield.setPrefWidth(0.75*WIDTH);
			send = new Button("Send");
			send.setDefaultButton(true);
			send.setPrefWidth(0.25*WIDTH);
			send.setOnAction(enter -> {
				System.out.println(textfield.getText());
				text.setText(text.getText() + "User: " + textfield.getText() + "\n");
				if(textfield.getText().contains("bye".toLowerCase()))
					System.exit(0);
				text.setText(text.getText() + "Bot: " + cb.sendPhrase(textfield.getText()) + "\n");
				textfield.setText("");
			});
			
			userspace = new HBox(textfield,send);
			userspace.setPrefSize(WIDTH, 25);
			textwindow.setPrefSize(WIDTH, HEIGHT-userspace.getHeight());
			textwindow.setContent(text);
			app = new VBox(textwindow,userspace);
			Scene scene = new Scene(app,WIDTH,HEIGHT);
			
			stage.setScene(scene);
			stage.setTitle("Chatbot");
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
			stage.widthProperty().addListener((obs) -> {
				userspace.setPrefWidth(stage.getWidth());
				textfield.setPrefWidth(0.75*stage.getWidth());
				send.setPrefWidth(0.25*stage.getWidth());
			});
			stage.heightProperty().addListener((obs) -> {
				textwindow.setPrefHeight(stage.getHeight()-userspace.getHeight());
			});
			
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
