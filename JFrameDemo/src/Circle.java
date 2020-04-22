import java.awt.*;

public class Circle {

    public int x, y;  // 圆心坐标 外面可以读取到圆的坐标，与此同时远在移动过程中外部也要能够改变它的坐标
    private int r;  // 半径 指定了一个圆的半径之后就不能改变了
    public int vx, vy; // 对于每一个圆来说都有相应的速度，也是public，因为圆圈在运动过程中可能会遇到一些撞击之后速度会发生改变
    public boolean isFilled; // 空心圆还是实心圆 默认为false，实心圆

    public Circle(int x, int y, int r, int vx, int vy){
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
        this.isFilled = false;
    }

    public int getR(){return r;}

    public void move(int minx, int miny, int maxx, int maxy){
        x += vx;
        y += vy;
        checkCollision(minx, miny, maxx, maxy);
    }

    // 碰撞检测，防止圆圈跑出屏幕  0 0 sceneWidth sceneHeight
    private void checkCollision(int minx, int miny, int maxx, int maxy){
        if(x - r < minx){x = r; vx = -vx;}
        if(x + r > maxx){x = maxx - r; vx = -vx;}
        if(y - r < miny){y = r; vy = -vy;}
        if(y + r > maxy){y = maxy - r; vy = -vy;}
    }

    // 判断用户点击的坐标位置是否在圆圈内
    public boolean contain(Point p) {
        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= r * r;
    }
}
