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

        // 显示盘面信息
        setData();

        // 使用solve()方法来求问题的解,求解过程不使用动画显示出来，程序在接受到盘面信息以后自动计算出这个解，并且将这个解打印出来，
        // 然后用户自己实践，根据解的提示来完成这个游戏。那么对于solve函数它有一个返回值，就是当前盘面是否有解。
        if(data.solve())
            System.out.println("The game has a solution!");
        else
            System.out.println("The game does not have a solution!");


    }

    private void setData(){
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String filename = "level/boston_17.txt";

        AlgoVisualizer vis = new AlgoVisualizer(filename);
    }
}
