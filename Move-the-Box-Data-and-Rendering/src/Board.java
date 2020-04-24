import java.awt.geom.Area;

public class Board {

    public static char EMPTY = '.';
    private int N, M;
    private char[][] data;
    private Board preBoard = null;
    private String swapString = ""; // 描述当前盘面通过上一个盘面怎样的操作得到的

    public Board(String[] lines){
        if(lines == null)
            throw new IllegalArgumentException("lines cannot be null in Board constructor.");

        N = lines.length;
        if(N == 0)
            throw new IllegalArgumentException("lines cannot be empty in Board constructor.");

        M = lines[0].length();

        data = new char[N][M];
        for(int i = 0 ; i < N ; i ++){
            if(lines[i].length() != M)
                throw new IllegalArgumentException("All lines' length must be same in Board constructor.");
            for(int j = 0 ; j < M ; j ++)
                data[i][j] = lines[i].charAt(j);
        }
    }

    // 根据另外一个board对象来创建一个新的board对象
    // 新创建了一个Board，这个Board的内容来自于第一个参数，但是它是通过第二个preBoard经过一次移动之后得来的
    public Board(Board board, Board preBoard, String swapString){
        if(board == null)
            throw new IllegalArgumentException("board can not be null in Board construction!");
        this.N = board.N;
        this.M = board.M;
        this.data = new char[N][M];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
                this.data[i][j] = board.data[i][j];
        this.preBoard = preBoard;
        this.swapString = swapString;
    }

    public Board(Board board){
        this(board, null, "");
    }

    public int N(){ return N; }
    public int M(){ return M; }

    // data是私有的，只能通过这种方式获取坐标（x, y）的内容
    public char getData(int x, int y){
        if(!inArea(x, y))
            throw new IllegalArgumentException("x, y are out of index in getData!");
        return data[x][y];
    }

    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print(){
        for(int i = 0 ; i < N ; i ++)
            System.out.println(String.valueOf(data[i]));
    }

    // 判断盘面上的所有箱子是否已经被全部消除,即是否全是点（胜利状态）
    public boolean isWin() {
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++){
                if(data[i][j] != EMPTY)
                    return false;
            }
        // 打印是通过怎样的步骤来一步一步到达最终盘面的，即挪动的信息,也就是从当前盘面一步一步向前推
        printSwapInfo();
        return true;
    }

    public void swap(int x1, int y1, int x2, int y2) {

        if(!inArea(x1, y1) || !inArea(x2, y2))
            throw new IllegalArgumentException("x, y are out of index in swap!");
        char t = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = t;
    }

    // 移动一步之后，先执行掉落，在执行清除
    public void run() {
        do{
            drop();
        }while(match());
    }

    private static int d[][] = {{0, 1}, {1, 0}}; // 右下两个方向
    // 如果有可以消除的箱子，则返回true。
    private boolean match() {
        // 定义一个标记数组
        boolean isMatched = false;
        boolean tag[][] = new boolean[N][M];
        for(int x = 0; x < N; x++)
            for(int y = 0; y < M; y++)
                if(data[x][y] != EMPTY){
                     for(int i = 0; i < 2; i++){
                         int newX1 = x + d[i][0];
                         int newY1 = y + d[i][1];
                         int newX2 = newX1 + d[i][0];
                         int newY2 = newY1 + d[i][1];
                         if(inArea(newX1, newY1) && inArea(newX2, newY2) && data[newX1][newY1] == data[x][y]
                                 && data[newX2][newY2] == data[x][y]){
                             tag[x][y] = true;
                             tag[newX1][newY1] = true;
                             tag[newX2][newY2] = true;
                             isMatched = true;
                         }
                     }
                }

        // 整个标记结束之后，在进行一次for循环，将被标记的箱子全部消除掉，即替换成EMPTY
        for(int x = 0; x < N; x++)
            for(int y = 0; y < M; y++){
                if(tag[x][y])
                    data[x][y] = EMPTY;
            }
        return isMatched;
    }

    public void printSwapInfo(){
        if(preBoard != null)
            preBoard.printSwapInfo();
        System.out.println(swapString);
        return;
    }

    // 移动某个方块后，方块悬空了，此时就应该让它掉落
    private void drop() {
        for(int j = 0; j < M; j++){   // M列，每一列都自底向上进行遍历
            int cur = N-1;
            for(int i = N-1; i >=0; i--){
                if(data[i][j] != EMPTY){
                    swap(i, j, cur, j);
                    cur--;
                }
            }
        }
        return;
    }
}
