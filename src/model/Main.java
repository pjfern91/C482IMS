package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** This class creates our Inventory Management Application. A program designed for a company to be able Add, Modify,
 and Delete new parts and products as well as search for parts and products.
 without having to hit enter while empty, and just overall optimization of code.
 * RUNTIME ERROR: I encountered a runtime error when first trying to run my program. When attempting to close the program,
 "Error resolving onAction=#closeApp" occured. To fix this, I had to first add the method to my MainController. In doing
 so, the program closed properly when exit was pressed.
 */
public class Main extends Application {
    public static int partIdNumber = 1; //tracker for auto-generated partID
    public static int prodIdNumber = 1; //tracker for auto-generated productID

    /**
     * the start method opens the application main window
     * @param primaryStage the stage to be shown
     * @throws Exception throws exception if resource for MainForm.fxml cannot be found
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * launches the application.
     * FUTURE ENHANCEMENT might include use of a database as that makes it easier to keep track of data as the data grows for the inventory.
     * FUTURE ENHANCEMENT I would like to implement, is the table automatically populates allParts and allProducts when the search bar is empty
     * The location of Javadoc is in the base directory for this project.
     * @param args launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
