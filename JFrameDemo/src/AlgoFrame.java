import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Collections;

// 制作属于自己的jframe类，需要继承JFrame
public class AlgoFrame extends JFrame {
    private int canvasWidth, canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){
        super(title); //调用父类的构造函数
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();
        setResizable(false);
        setDefaultCloseOperation(3);
        setVisible(true);
    }

    private Circle[] circles;
    // 根据circles数组进行绘制
    public void render(Circle[] circles){
        this.circles = circles;
        this.repaint();
        // repaint()是JFrame中提供的一个函数，而这个函数的作用就是
        //将Jframe中的所有控件重新刷新一遍，那么重新刷新的过程就包括对Frame里的画布AlgoCanvas进行了刷新，那么这个刷新的过程就将AlgoCanvas
        //给清空，然后重新调用一遍paintComponent这个方法，重新绘制
    }

    public AlgoFrame(String title){
        this(title, 1024, 1024);
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            super(true); //支持双缓存
        }

        @Override
        public void paintComponent(Graphics g) {
            // g表示绘制的上下文环境
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g; //强制转换

            // 抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            // 具体绘制
            AlgoVisHelper.setStrokeWidth(g2d, 1); //设置线条宽度
            AlgoVisHelper.setColor(g2d, Color.red);
            for(Circle circle: circles){
                if(!circle.isFilled)
                    AlgoVisHelper.strokeCircle(g2d, circle.x, circle.y, circle.getR());
                else
                    AlgoVisHelper.fillCircle(g2d, circle.x, circle.y, circle.getR());
            }

        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(new Dimension(canvasWidth, canvasHeight));
        }
    }
}
