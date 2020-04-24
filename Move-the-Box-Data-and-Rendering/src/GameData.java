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
}
