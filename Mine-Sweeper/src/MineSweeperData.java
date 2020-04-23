public class MineSweeperData {

    public static final String blockImageURL = "resources/block.png";
    public static final String flagImageURL = "resources/flag.png";
    public static final String mineImageURL = "resources/mine.png";

    public static String numberImageURL(int num) {
        if (num < 0 || num > 8)
            throw new IllegalArgumentException("No such a number image");
        return "resources/" + num + ".png";
    }

    private int N, M;
    public boolean[][] mines;
    public boolean[][] open;  // 表示最初的时候每一个格子是否都处于被打开的状态  public 方便与用户进行交互式修改open
    public boolean[][] flags; // flags[i][j]表示该位置是否被被标记成小旗子
    private int[][] numbers; // 存储每一个位置上如果没有雷的话它的上下左右有多少个雷  在初始化的时候会根据雷的位置进行，外界无法修改！

    public MineSweeperData(int N, int M, int mineNumber) {

        if (N <= 0 || M <= 0)
            throw new IllegalArgumentException("Mine sweeper size is invalid");
        if (mineNumber < 0 || mineNumber > N * M)
            throw new IllegalArgumentException("Mine number is larger then the size");
        this.N = N;
        this.M = M;
        mines = new boolean[N][M];
        open = new boolean[N][M];
        flags = new boolean[N][M];
        numbers = new int[N][M];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                mines[i][j] = false;
                open[i][j] = false;
                flags[i][j] = false;
                numbers[i][j] = 0;
            }

        // 布雷
        generateMines(mineNumber);

        // 将雷布置好之后，计算每个位置如果不是雷的话，它的的上下左右雷的数量
        calculateNumbers();

    }

    private void calculateNumbers() {
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++){
                if(mines[i][j])
                    numbers[i][j] = -1;
                else{
                    numbers[i][j] = 0;
                    for(int ii = i-1; ii <= i+1; ii++)
                        for(int jj = j-1; jj <= j+1; jj++){
                            if(inArea(ii, jj) && mines[ii][jj])
                                numbers[i][j]++;
                        }
                }
            }
    }

    public int getNumbers(int i, int j){
        if(!inArea(i, j))
            throw new IllegalArgumentException("index out of in getNumbers function");
        return numbers[i][j];
    }
    // 将mineNumber个雷随机的分布在N*M的区域内
    // 先将mineNumber个雷按从上到下从左到右的顺序排列，之后再打乱盘面的顺序
    private void generateMines(int mineNumber) {
        for (int i = 0; i < mineNumber; i++) {
            int x = i / M;
            int y = i % M;
            mines[x][y] = true;
        }
        for (int i = N * M - 1; i >= 0; i--) {
            int iX = i / M;
            int iY = i % M;

            int randNumber = (int) (Math.random() * (i + 1));

            int randX = randNumber / M;
            int randY = randNumber % M;
            swap(iX, iY, randX, randY);
        }
    }

    private void swap(int x1, int y1, int x2, int y2) {
        boolean t = mines[x1][y1];
        mines[x1][y1] = mines[x2][y2];
        mines[x2][y2] = t;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    // 判断（x, y）对应的小方块是否有雷
    public boolean isMine(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("out of index in isMine function");
        return mines[x][y];
    }

    // 判断（x,y）是否是在规定的区域内
    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    // 深度优先遍历，展开一片区域
    public void open(int x, int y) {

        if(!inArea(x, y))
            throw new IllegalArgumentException("Out of index in open function");

        if(isMine(x, y))
            throw new IllegalArgumentException("Cannot open an mine block in open!");

        // 打开一片雷区
        open[x][y] = true;
        if(numbers[x][y] > 0)
            return;

        for(int i = x-1; i <= x+1; i++)
            for(int j = y-1; j <= y+1; j++){
                if(inArea(i, j) && !open[i][j] && !mines[i][j])
                    open(i, j);
            }
    }

}
