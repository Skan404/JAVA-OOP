public class Main {
    public static void main(String[] args) {

        World w = new World(20,20, 3,2);
        w.printWorld();

        MainWindow mainWindow = new MainWindow(w);
        mainWindow.setVisible(true);

    }
}