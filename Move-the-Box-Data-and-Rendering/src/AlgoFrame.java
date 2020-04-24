import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public AlgoFrame(String title){this(title, 1024, 768); }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private GameData data;
    public void render(GameData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        private HashMap<Character, Color> colorMap; // 定义字母到颜色的映射
        private ArrayList<Color> colorList; // 需要为不同的字母设置的颜色，这里假设游戏中箱子的类型不超过10种
        public AlgoCanvas(){
            // 双缓存
            super(true);
            colorMap = new HashMap<Character, Color>();
            colorList = new ArrayList<Color>();
            colorList.add(AlgoVisHelper.Red);
            colorList.add(AlgoVisHelper.Purple);
            colorList.add(AlgoVisHelper.Blue);
            colorList.add(AlgoVisHelper.Teal);
            colorList.add(AlgoVisHelper.LightGreen);
            colorList.add(AlgoVisHelper.Lime);
            colorList.add(AlgoVisHelper.Amber);
            colorList.add(AlgoVisHelper.DeepOrange);
            colorList.add(AlgoVisHelper.Brown);
            colorList.add(AlgoVisHelper.BlueGrey);
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
            int w = canvasWidth/data.M();
            int h = canvasHeight/data.N();

            // 绘制当前盘面
            // starterBoard是整个游戏的初始盘面，之后随着游戏的进行，盘面会发生改变，所以显示的盘面不是starterBoard，而是
            // showBoard,是真正要显示的那个board；showBoard的内容就直接根据starterBoard的内容显示出来。相当于对Board又创造了一个
            // 新的构造函数。

            // 现在这个showBoard就是实际要绘制的盘面
            Board showBoard = data.getShowBoard();
            for(int i = 0; i < showBoard.N(); i++)
                for(int j = 0; j < showBoard.M(); j++){
                    char c = showBoard.getData(i, j);
                    if(c != showBoard.EMPTY){
                        if(!colorMap.containsKey(c)){
                            int sz = colorMap.size();
                            colorMap.put(c, colorList.get(sz));
                        }
                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j*h+2, i*w+2, w-4, h-4);
                    }
                }


        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}