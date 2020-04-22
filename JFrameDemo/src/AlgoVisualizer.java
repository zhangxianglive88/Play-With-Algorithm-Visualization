import java.awt.*;
import java.awt.event.*;

// 控制器，负责将视图和数据连接起来
public class AlgoVisualizer {

    private Circle[] circles; // 数据
    private AlgoFrame frame; // 视图
    private boolean isAnimated = true; // 是否开启动画

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){
        // 初始化数据
        circles = new Circle[N];
        int R = 50;
        for(int i=0; i<N; i++){
            int x = (int)(Math.random()*(sceneWidth-2*R)) + R;
            int y = (int)(Math.random()*(sceneHeight-2*R)) + R;
            int vx = (int)(Math.random()*11) - 5;
            int vy = (int)(Math.random()*11) - 5;
            circles[i] = new Circle(x, y, R, vx, vy);
        }

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome",  sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener()); // 为frame添加键盘监听事件
            frame.addMouseListener(new AlgoMouseListener()); // 为frame添加鼠标监听事件
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){
        while(true){
            // 绘制数据
            frame.render(circles);
            AlgoVisHelper.pause(20);

            // 更新数据
            if(isAnimated)
                for(Circle circle : circles)
                    circle.move(0, 0, frame.getCanvasWidth(), frame.getCanvasHeight());
        }
    }

    // 添加键盘的响应事件
    // 当实现一个接口时，必须要将接口中的所有方法都实现。而这里只实现keyReleased（）方法
    // 点击一下空格键，圆圈停止移动，再次点击，圆圈继续移动
    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' ')
                isAnimated = !isAnimated;
        }
    }

    // 添加鼠标的响应事件
    private class AlgoMouseListener extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent event) {
            // 打印的位置信息是从标题栏开始计算的，因此在打印之前需要移动坐标点
            //在默认的setResizable(true)情况下,titleDefaultHeight = 27,但如果 setResizable(false)，titleDefaultHeight = 25
            event.translatePoint(0, -25); // 对用户点击的位置进行位移
            // System.out.println(event.getPoint()); // 打印鼠标点击的真实位置信息（屏幕坐标系）

            // 对circles中的每一个Circle都进行一个判断，判断当前鼠标点击的位置是否在某一个Circle中
            for(Circle circle:circles)
                if(circle.contain(event.getPoint()))
                    circle.isFilled = !circle.isFilled;
        }
    }

    public static void main(String[] args) {

        int sceneWidth = 500;
        int sceneHeight = 500;
        int N = 10;
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }

}
