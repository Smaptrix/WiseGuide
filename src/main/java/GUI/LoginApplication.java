/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   24/02/2022
 */
package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

        public class LoginApplication extends Application {
            @Override
            public void start(Stage stage) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 640, 400);
                stage.setTitle("Login");
                stage.setScene(scene);

                LoginController controller = fxmlLoader.getController();

                //Does the first initial connection to the server
                controller.initialConnection();

                stage.show();
            }



            public static void main(String[] args) {
                launch();
            }
        }
