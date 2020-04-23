import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame{

    private int canvasWidth;  // 画布宽度
    private int canvasHeight;  // 画布高度

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title); // 调用父类的有参构造函数

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas(); // AlgoCanvas继承了JPanel类
        this.setContentPane(canvas); // 设置内容面板
        pack();  // 让窗口的大小根据画布的大小自动调整
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public AlgoFrame(String title){
        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // TODO: 设置自己的数据
    private MineSweeperData data;
    public void render(MineSweeperData data){
        this.data = data;
        repaint(); // 清空画布中的内容，然后重新加载
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // 具体绘制
            // TODO： 绘制自己的数据data
            int w = canvasWidth / data.getM();
            int h = canvasHeight / data.getN();

            for(int i=0; i<data.getN(); i++)
                for(int j=0; j<data.getM();j++){
                    if(data.open[i][j]){
                        if(data.mines[i][j])
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.mineImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.numberImageURL(data.getNumbers(i, j)));
                    }else{
                        if(data.flags[i][j])
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.flagImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j*w, i*h, MineSweeperData.blockImageURL);
                    }
                }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}


