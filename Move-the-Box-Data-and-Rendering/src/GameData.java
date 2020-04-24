import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class GameData {

    private int maxTurn;  // 最大的移动步数
    private int N, M;   // N行M列
    private Board starterBoard; // 用于初始化一个盘面
    private Board showBoard; // 用于显示一个盘面,并且不希望外界随便的修改showBoard相对应的引用，不能随便的让它指向其他的Board对象。

    public GameData(String filename){

        // 读取文件
        if(filename == null)
            throw new IllegalArgumentException("Filename cannot be null!");

        Scanner scanner = null;
        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File " + filename + " doesn't exist!");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String turnline = scanner.nextLine();

            this.maxTurn = Integer.parseInt(turnline);

            ArrayList<String> lines = new ArrayList<String>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lines.add(line);
            }
            starterBoard = new Board(lines.toArray(new String[lines.size()]));

            this.N = starterBoard.N();
            this.M = starterBoard.M();
            starterBoard.print();
            showBoard = new Board(starterBoard);

        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if(scanner != null)
                scanner.close();
        }
    }

    // 外界只能通过这种方式获取showBoard
    public Board getShowBoard() {
        return showBoard;
    }

    public int N(){ return N; }
    public int M(){ return M; }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    // 自动求问题的解
    public boolean solve(){

        if(maxTurn < 0)  // 如果game存储的maxTurn<0的话，显然是没有解的，否则才真正开始使用回溯算法来寻找问题的解。
            return false;
        return solve(starterBoard, maxTurn);
    }

    private static int d[][] = {{1, 0}, {0, 1}, {0, -1}};  // 箱子偏移量
    // 递归函数
    // 通过盘面board相关的信息，使用turn次move,解决move the box的问题
    // 若可以解决成功，则返回true，否则返回false。
    private boolean solve(Board board, int turn){

        // 检查传入参数的合法性
        if(board == null || turn < 0)
            throw new IllegalArgumentException("Illegal arguments in solve function!");

        // 递归终止条件
        if(turn == 0)  // 当前可移动次数为0时，需要判断盘面上的所有箱子是否已经被全部消除。
            return board.isWin();

        // 尽管还有可以移动的步数，但是我们已经不用做任何移动了
        if(board.isWin())
            return true;

        // 尝试对所有箱子的所有移动可能进行遍历
        for(int x = 0; x < N; x++)
            for(int y = 0; y < M; y++)
                if(board.getData(x, y) != Board.EMPTY){  // 将箱子挪动到所有可能相邻的位置（只有下左右三种情况，往上移动箱子是没有意义的）
                    for(int i = 0; i < 3; i++){
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];
                        if(inArea(newX, newY)){
                            String swapString = String.format("swap (%d, %d) and (%d, %d)", x, y, newX, newY);
                            // 现在下面的board里面已经含有足够多的信息，可以在我们一旦找到问题的最终盘面的时候，可以从这个最终盘面向前推
                            // 来找到是通过怎样的步骤一点一点的来完成这个游戏的。
                            Board nextBoard = new Board(board, board, swapString);
                            nextBoard.swap(x, y, newX, newY);  // 移动的这一步可能会导致箱子的掉落或者消除，将这个过程放在nextBoard.run()方法中模拟
                            nextBoard.run(); // 模拟完之后nextBoard才是移动一步之后的盘面
                            if(solve(nextBoard, turn-1))
                                return true;
                        }
                    }
                }
        return false;
    }
}
