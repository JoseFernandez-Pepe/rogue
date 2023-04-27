
public class RandomArrayGenerator {
    public static int[][] generateRandomArray(int rows, int cols){
        int[][]array = new int[rows][cols];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                array[i][j] = 0;
            }
        }
        return array;
    }
    

}

