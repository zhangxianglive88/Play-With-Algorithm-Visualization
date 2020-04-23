import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    // TODO: 创建自己的数据
    private MineSweeperData data;        // 数据
    private AlgoFrame frame;    // 视图
    private int delay = 5;
    private static int blockSide = 32;

    public AlgoVisualizer(int N, int M, int MineNumber){

        // 初始化数据
        data = new MineSweeperData(N, M, MineNumber);
        int sceneWidth = N * blockSide;
        int sceneHeight = M * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Mine Sweeper", sceneWidth, sceneHeight);
            // TODO: 根据情况决定是否加入键盘鼠标事件监听器
//            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run(){

        // TODO: 编写自己的动画逻辑
        setData(false, -1, -1);
    }

    private void setData(boolean isLeftClicked, int x, int y){
        if(data.inArea(x, y)){
            if(isLeftClicked)
                if(data.isMine(x, y)){     // 如果点击的是雷就Game Over了，如果不是雷，是数字，则打开它，如果四周都没有雷，则展开一片区域。
                    // Game Over
                    data.open[x][y] = true;
                }else{
                    data.open(x, y);
                }
            else
                data.flags[x][y] = !data.flags[x][y];
        }
        frame.render(data);
        AlgoVisHelper.pause(delay);
    }

    // TODO: 根据情况决定是否实现键盘鼠标等交互事件监听器类
    private class AlgoKeyListener extends KeyAdapter{ }

    private class AlgoMouseListener extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e) {
            e.translatePoint(0, -25);
            Point pos = e.getPoint();
            int w = frame.getCanvasWidth() / data.getM();
            int h = frame.getCanvasHeight() / data.getN();

            int x = pos.y / h;
            int y = pos.x / w;
            // 判断鼠标左击还是右击
            if(SwingUtilities.isLeftMouseButton(e))
                setData(true, x, y);
            else
                setData(false, x, y);
        }
    }

    public static void main(String[] args) {

        int N = 20;
        int M = 20;
        int MineNumber = 20;

        // TODO: 根据需要设置其他参数，初始化visualizer
        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, MineNumber);
    }
}
