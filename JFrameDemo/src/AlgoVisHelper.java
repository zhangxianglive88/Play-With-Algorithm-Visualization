import java.awt.*;
import java.awt.geom.Ellipse2D;

//工具类
public class AlgoVisHelper {
    private AlgoVisHelper(){}

    //设置线条粗细
    public static void setStrokeWidth(Graphics2D g2d, int w){
        int strokeWidth = w; //设置笔画宽度，默认为1
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
    }

    //设置线条颜色
    public static void setColor(Graphics2D g2d, Color color){
        g2d.setColor(color);
    }

    // 绘制空心圆
    // 传入圆心坐标及半径
    public static void strokeCircle(Graphics2D g2d, int x, int y, int r){
        // 先创建基本图形对象
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2d.draw(circle);
    }

    // 绘制实心圆
    public static void fillCircle(Graphics2D g2d, int x, int y, int r){
        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2d.fill(circle);
    }

    // 设置暂停时间
    public static void pause(int t){
        try{
            Thread.sleep(t);
        }catch (InterruptedException e){
            System.out.println("error happened!");
        }
    }

}
