import javax.swing.*;
import java.awt.*;

public class Main {
    // 官方推荐的创建一个窗口的写法，把创建的过程放入一个事件分发的过程中，这样当我们的GUI越来越大的时候，会避免一些线程之间发生的错误
    public static void main(String[] args){
/*
        int sceneHeight = 600;
        int sceneWidth = 600;
        int R = 50;

        int N = 10;
        Circle[] circles = new Circle[N];
        for(int i=0; i<N; i++){
            int x = (int)(Math.random()*(sceneWidth - 2 * R)) + R; // Math.random()返回[0,1)之间的数
            int y = (int)(Math.random()*(sceneHeight - 2 * R)) + R;
            int vx = (int)(Math.random() * 11) - 5; // vx的取值在-5至+5之间
            int vy = (int)(Math.random() * 11) - 5;
            circles[i] = new Circle(x, y, R, vx, vy);
        }


        EventQueue.invokeLater(() -> {
            AlgoFrame frame = new AlgoFrame("welcome", sceneWidth, sceneHeight);

            new Thread(()->{
                while(true){
                    //绘制当前的数据
                    frame.render(circles);
                    AlgoVisHelper.pause(20);
                    //更新当前的数据
                    for(Circle circle: circles){
                        circle.move(0, 0, sceneWidth, sceneHeight);
                    }
                }
            }).start();
        });*/
    }
}
