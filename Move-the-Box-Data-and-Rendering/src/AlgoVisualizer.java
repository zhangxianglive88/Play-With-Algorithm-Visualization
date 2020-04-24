import java.awt.*;
import java.awt.event.*;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 80;

    private GameData data;
    private AlgoFrame frame;

    public AlgoVisualizer(String filename){

        data = new GameData(filename);
        // 设置窗口的宽高
        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Move the Box Solver", sceneWidth,sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        setData();
    }

    private void setData(){
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String filename = "level/boston_09.txt";

        AlgoVisualizer vis = new AlgoVisualizer(filename);
    }
}