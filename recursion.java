class recursion{
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        printDecreasing(n);
        
    }
//======================================================================================================================================================
    //1 print decreasing 
    public static void printDecreasing(int n){
        if(n==0) return;
        System.out.println(n);
        printDecreasing(n-1);
        
    }
//======================================================================================================================================================
    //2 
    public static void printIncreasing(int n){
        if(n==0) return;
        printIncreasing(n-1);
        System.out.println(n);
        
    }

//3======================================================================================================================================================
//print increasing decreasing

public static void pdi(int n){
        if(n==0) return;
        System.out.println(n);
        pdi(n-1);
        System.out.println(n);
        
    }

//4 ======================================================================================================================================================
//Factorial
public static int factorial(int n){
        if(n==1) return n;
         
        int ans = n * factorial(n-1);
        return ans;
        
    }

//5======================================================================================================================================================
//Power Linear

    public static int power(int x, int n){
            if(n==0) return 1;
            return x * power(x,n-1);
    }


//8 ======================================================================================================================================================
//Power Logrithmic

//x ka pow 14 ==>> X*X*X*X*X*X*X*X*X*X*X*X*X*X ==>(X*X*X*X*X*X*X) * (X*X*X*X*X*X*X)
//if it is even that can be diveded into two
    public static int power(int x, int n){
                if(n==0) return 1;
                int n_ka_power_by_two = power(x,n/2);
                int x_ka_power = n_ka_power_by_two * n_ka_power_by_two;
                if(n%2==1) x_ka_power = x_ka_power * x;//odd k case mein

                return x_ka_power;
    }

//9 ======================================================================================================================================================
//Tower of Hanoi

    public static void toh(int n, int t1id, int t2id, int t3id){
        if(n==0) return;
         toh(n-1, t1id,t3id,t2id); // n-1 ko A to C
         System.out.println(n +"["+t1id+" -> "+t2id+"]");//1[10 -> 11] // yaha A se B  bada wala jayega using C 
         toh(n-1, t3id,t2id, t1id); // // n-1 ko C to B using A
        
    }


//10 ======================================================================================================================================================
//Get Subsequence

}   //faith: bc :[__, _b, _c, bc] ye milega
    //expect : abc [___, __b, __c, _bc,   a__, a_b, a_c, abc  ]
    public static ArrayList<String> gss(String str) {
        if(str.length()==0){
            ArrayList<String> zeroAns = new ArrayList<String>();
            zeroAns.add(""); // zeroAns.add() ye ni chalega
            return zeroAns;
        }
        
        //abc
        char char_at_zero = str.charAt(0);  // a
        
        String remaining_string = str.substring(1); //bc
        
        ArrayList<String> myans = gss(remaining_string);// for bc
        
        ArrayList<String> newAns = new ArrayList<String>();
        
        for(String s: myans){
            newAns.add(""+s);
           // newAns.add(char_at_zero + s);
        }
        for(String s: myans){
            //newAns.add(""+s);
            newAns.add(char_at_zero + s);
        }
        
        return newAns;
        
        
    }

//11 ======================================================================================================================================================
// Get Keypad Combination
    0 -> .;
    1 -> abc
    2 -> def
    3 -> ghi
    4 -> jkl
    5 -> mno
    6 -> pqrs
    7 -> tu
    8 -> vwx
    9 -> yz



    //78 : [tv, tw, tx, uv, uw, ux]


    //yaha char jab number ho to wo number ki tarah behave ni karta is liye usko '0' se substract karte Hanoi
    //  String codeFor_char_at_zero = codes[char_at_zero - '0'];
    //Not codes[char_at_zero]

    static String codes[] = {".;","abc","def","ghi","jkl","mno","pqrs","tu","vwx","yz"};
    
    
    //678
    public static ArrayList<String> getKPC(String str) {
        if(str.length()==0){
            ArrayList<String> zeroAns = new  ArrayList<String>();
            zeroAns.add("");
            return zeroAns;
        }
        
        char char_at_zero = str.charAt(0);//6
        String zero_k_baad_ka_string = str.substring(1);//78
        
        ArrayList<String> remAns = getKPC(zero_k_baad_ka_string);
        
        ArrayList<String> myAns = new  ArrayList<String>();
        
        String codeFor_char_at_zero = codes[char_at_zero - '0'];
        for(int i=0;i<codeFor_char_at_zero.length();i++){
         //codes[char_at_zero]) -- codes[6] 
            char c = codeFor_char_at_zero.charAt(i);
            for(String s : remAns){
                myAns.add(c+s);
            }
        }
        
        return myAns;
        
    }



//12 ======================================================================================================================================================

//Stair case problem

//ya to 1 step lega : bolnege 

    public static void main(String[] args) throws Exception {
          Scanner s =new Scanner(System.in);
          int n  = s.nextInt();
        
          ArrayList<String> ans =  printStairPaths(n);
          System.out.println(ans);

    }

    public static ArrayList<String> printStairPaths(int n) {
        
        if(n==0){
            ArrayList<String> a = new ArrayList<String>();
            a.add("");
            return a;
        }
        if(n<0){
            ArrayList<String> a = new ArrayList<String>();
            return a;
        }
        
        
        
        
        ArrayList<String> path_from_one_step = printStairPaths(n-1);
        ArrayList<String> path_from_two_step = printStairPaths(n-2);
        ArrayList<String> path_from_three_step = printStairPaths(n-3);
        
        ArrayList<String> myAns = new ArrayList<String>();
        //Post area of recursion, this myAns will run under all recursion post area
        for(String path: path_from_one_step){
            myAns.add(1+path);
        }
        for(String path: path_from_two_step){
            myAns.add(2+path);
        }
        for(String path: path_from_three_step){
            myAns.add(3+path);
        }
        
        return myAns;
    }

    //13 ======================================================================================================================================================

// Maze path horizontal and vertical

public static void main(String[] args) throws Exception {
         Scanner s =new Scanner(System.in);
         int n  = s.nextInt();
         int m  = s.nextInt();
         ArrayList<String> ans = getMazePaths(1,1, n,m);
         System.out.println(ans);
    }

    // sr - source row
    // sc - source column
    // dr - destination row
    // dc - destination column
    public static ArrayList<String> getMazePaths(int sr, int sc, int dr, int dc) {
        
        if(sr==dr && sc==dc){
            ArrayList<String> a =  new ArrayList();
            a.add("");
            return a;
        } 
        
        ArrayList<String> horizontal_move = new ArrayList();
        ArrayList<String> vertical_move =  new ArrayList();
        
        if(sc< dc){
             horizontal_move =  getMazePaths(sr, sc+1,dr,dc);
        }
        if(sr< dr){
              vertical_move =  getMazePaths(sr+1, sc,dr,dc);
        }
       
        ArrayList<String> myAns = new ArrayList<>();
        
        for(String a: horizontal_move){
            myAns.add("h"+ a);
        }
        for(String a: vertical_move){
            myAns.add("v"+ a);
        }
        
        return myAns;
       
    }


//13 ======================================================================================================================================================
// Maze path with multilple jump

     public static void main(String[] args) throws Exception {
        Scanner s =new Scanner(System.in);
         int n  = s.nextInt();
         int m  = s.nextInt();
         ArrayList<String> ans = getMazePaths(1,1, n,m);
         System.out.println(ans);

    }

    // sr - source row
    // sc - source column
    // dr - destination row
    // dc - destination column
    public static ArrayList<String> getMazePaths(int sr, int sc, int dr, int dc) {
         if(sr==dr && sc==dc){
            ArrayList<String> a =  new ArrayList();
            a.add("");
            return a;
        } 
        ArrayList<String> myAns = new ArrayList<>();
        
        //horizontal move
        for(int i=1;i <= dc-sc;i++){
             ArrayList<String> horizontal_move = getMazePaths(sr, sc+i,dr,dc);
             for(String ans: horizontal_move){
                 myAns.add("h"+i+ans);
             }
            
        }
        //vertical move
         for(int i=1;i <= dr-sr;i++){
             ArrayList<String> vertical_move = getMazePaths(sr+i, sc,dr,dc);
             for(String ans: vertical_move){
                 myAns.add("v"+i+ans);
             }
            
        }
        for(int i=1;i <= dr-sr && i <= dc-sc;i++){
             ArrayList<String> diagonal_move = getMazePaths(sr+i, sc+i,dr,dc);
             for(String ans: diagonal_move){
                 myAns.add("d"+i+ans);
             }
            
        }
        return myAns;
    }

//14 ======================================================================================================================================================
//FLood Fill

     public static void main(String[] args) throws Exception {
        Scanner s =new Scanner(System.in);
        int n  = s.nextInt();
        int m  = s.nextInt();
        int matrix[][] = new int[n][m];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                matrix[i][j] = s.nextInt();;
            }
        }
        boolean[][] visited = new boolean[n][m];
        floodfill(matrix,0,0,"",visited);
    }

    public static void floodfill(int[][] maze, int row, int col, String psf, boolean[][] visited){
        
        if(row < 0  || col < 0 || row == maze.length ||  col == maze[0].length || maze[row][col] == 1 || visited[row][col] == true ){
            return;
        }
        
        if(row==maze.length-1 && col==maze[0].length-1){
            System.out.println(psf);
            return;
        }
        
        visited[row][col] = true;
        floodfill(maze,row-1,col,psf+"t",visited);
        floodfill(maze,row,col-1,psf+ "l",visited);
        floodfill(maze,row+1,col,psf+"d",visited);
        floodfill(maze,row,col+1,psf + "r",visited);
        visited[row][col] = false;
        
    }


//15 ======================================================================================================================================================
//Target Sum Subsets 

 public static void main(String[] args) throws Exception {
         Scanner s =new Scanner(System.in);
         int n  = s.nextInt();
         int arr[] = new int[n];
        for(int i=0;i<arr.length;i++){
            arr[i] = s.nextInt();
        }
        
        int tar = s.nextInt();
        printTargetSumSubsets(arr, 0,"", 0, tar);
    }

    // set is the subset
    // sos is sum of subset
    // tar is target
    public static void printTargetSumSubsets(int[] arr, int idx, String set, int sumsofar, int tar) {
        
        if(idx == arr.length){
            if(tar==sumsofar){
                System.out.println(set+".");
            }
            return;
        }
        printTargetSumSubsets(arr, idx+1,  set+arr[idx]+", ",    sumsofar+arr[idx] ,    tar);
        printTargetSumSubsets(arr, idx+1, set, sumsofar , tar);
        
    }


//16 ======================================================================================================================================================
//NQueen



    public static void main(String[] args) throws Exception {
        Scanner s =new Scanner(System.in);
        int n  = s.nextInt();
        int matrix[][] = new int[n][n];
         
        printNQueens(matrix, "",0);
        
    }

    public static void printNQueens(int[][] chess, String qsf, int row) {
        
        if(row==chess.length){
            System.out.println(qsf+".");
            return;
        }
        for(int col = 0;col<chess[0].length;col++){
            if(isSafetoPlace(chess, row,col)){
                chess[row][col] = 1;
                printNQueens(chess, qsf + row +"-" + col+", ",row+1);
                chess[row][col] = 0;
            }
            
            
        }
    }
    public static boolean isSafetoPlace(int arr[][], int r, int c){
        
        for(int i = r-1,j = c-1; i>=0 && j>=0 ;i--,j--){
            
            if(arr[i][j]==1) return false;
        }
        
        for(int i = r-1,j=c; i>=0;i--){
            if(arr[i][j]==1) return false;
        }
        
        for(int i = r-1,j = c+1; i>=0 &&  j<arr.length;i--,j++){
            if(arr[i][j]==1) return false;
        }
        
        return true;
    }

//17 ======================================================================================================================================================
//  Knights Tour 
// Horse in chase

    public static void main(String[] args) throws Exception {
        Scanner s =new Scanner(System.in);
        int n  = s.nextInt();
        int chess[][] = new int[n][n];
        int r = s.nextInt();
        int c = s.nextInt();
        printKnightsTour(chess,r,c, 1);
    }


    //chess[row][col] > 0  iska matlab already iska answer calculated hai

    public static void printKnightsTour(int[][] chess,   int row, int col, int move_number) {
        if(row< 0 || col < 0 || row >= chess.length || col >= chess.length || chess[row][col] > 0 ){
            return;
        }else if (move_number == chess.length *chess.length)  {
            chess[row][col] = move_number;
            displayBoard(chess);
            chess[row][col] = 0;
            return;
        }
        
        
        chess[row][col] = move_number;
        printKnightsTour(chess,row-2, col+1,move_number+1);
        printKnightsTour(chess,row-1, col+2,move_number+1);
        printKnightsTour(chess,row+1, col+2,move_number+1);
        printKnightsTour(chess,row+2, col+1,move_number+1);
        printKnightsTour(chess,row+2, col-1,move_number+1);
        printKnightsTour(chess,row+1, col-2,move_number+1);
        printKnightsTour(chess,row-1, col-2,move_number+1);
        printKnightsTour(chess,row-2, col-1,move_number+1);
        chess[row][col] = 0;
        
    }

    public static void displayBoard(int[][] chess){
        for(int i = 0; i < chess.length; i++){
            for(int j = 0; j < chess[0].length; j++){
                System.out.print(chess[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }






//18 ======================================================================================================================================================
 // Abbreviation Using Backtracking

 //pep
 /*

pe1
p1p
p2_
1ep
1e1
2p_
3__
*/

pep -> (pep/./1)  (pep/p/0)  
|
//for first char p there are two call p aayega ya p ni aayeg
//p ni aaya count = 1 (pep/./1)
// p aaya count = 0 (pep/p/0) 

//count basicaaly number of emty space bata raha hai

pep -> (pep/pe/1)  (pep/pep/0)  
  |

// similary last pk liye p  aayega ya ni aayeg
//ni aaya pe + 1 = pe1  solution(str, asf + count + str.charAt(pos),0,pos+1); eg p1p

//ayaa pep 


 public static void solution(String str, String asf,int count, int pos){
        
        if(pos == str.length()){
            if(count == 0){
                System.out.println(asf);
            }else{
                System.out.println(asf+count);
            }
            return;
            
        }
        if(count > 0){
            solution(str, asf + count + str.charAt(pos),0,pos+1);   
        }else{
            solution(str, asf + str.charAt(pos), 0, pos+1);
        }
        
        
        solution(str, asf ,count+1, pos+1);
        
    }
	public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String str = scn.nextLine();
        solution(str,"",0,0);
    }


//19 ======================================================================================================================================================
//N Queens - Branch And Bound

[0 1 2]
[3 4 5 ]
[6 7 8]                 
            /   /  /   /
              /   /  /  /
                / 

           1 2 3  4  5
           
           total 5 forward diagonal in 3*3 matrix  2*3-1

//lok any of the diagona

[* # $]
[# $ % ]
[# % @] 

row + col will be same

while
in reverse duagonal

[* # $]
[% * # ]
[@ % *] 
here [row-col + m -1] whree m = arr[0].length


    public static void main(String[] args) throws Exception {
    Scanner scn = new Scanner(System.in);
    int n = scn.nextInt();
    boolean[][] board = new boolean[n][n];
    
    boolean[] cols = new boolean[n];
    
    boolean diagonal[] = new boolean[2*n-1];
    boolean reverse_diagonal[] = new boolean[2*n-1];
    solve(board, 0, cols,diagonal, reverse_diagonal,"");
  }
  
  public static void solve(boolean[][] board, int row, boolean[] cols,
  boolean[] diagonal,boolean[] reverse_diagonal, String psf){
      if(row==board.length){
          System.out.println(psf+".");
          return;
      }
      for(int c = 0; c< board[0].length; c++){
          if(cols[c]==false && reverse_diagonal[row-c+board.length-1]==false && diagonal[row+c]==false && board[row][c]==false)
          {
               
          
             board[row][c] = true;
             cols[c] = true;
             diagonal[row+c] = true;
             reverse_diagonal[row-c + board.length -1] = true;
             
             solve(board,row+1,cols,diagonal,reverse_diagonal, psf + row+"-"+c +", ");
             
             reverse_diagonal[row-c + board.length -1] = false;
             diagonal[row+c] = false;
             cols[c] = false;
             board[row][c] = false;
          }
      }
      
  }



//20 ======================================================================================================================================================
//Max Score Not done








//21 ======================================================================================================================================================
//Game of Execution - Josephus Problem

k=3
n =6
har baar 3rd banda kill hoga
[0,1,2,3,4,5]
     | 
Pahle 2 delete hua
[3,4,5,0,1] // yaha 3 rd position se 0 pe shift ho gaya to uska index back tarckingng mein kaise lenege
     |      //(0+3)%6 = 3  //so 3rd ka original poition 3 mil gaya (current_poition + k) % total number of eleemnt
 //fir 5 delte hoga and 5  se pahle walo ko end mein la jayenge
 
 
 //Now ye index re



 public static int solution(int n, int k){
    if(n==1) return 0;
    int new_posotion = solution(n-1,k); // faith : mere ko n-1 me se wiiner ka pos pata chal jayega
    int winner_ka_positon = (k+new_posotion)%n;
    return winner_ka_positon;
  }





//22 ======================================================================================================================================================
//Lexicographical Numbers

// ip 14
//1
10
11
12
13
14
2
3
4
5
6
7
8
9//

                                    0
    1       2       3       4       5       6       7       8       9
    10 11   20 21   30 31 ........
    101 111
    1011 1111


    //tree kucch is traha banega


    	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		for(int i=1;i<=9;i++){
		    dfs(i,n);
		}

	 
	}
	
	public static void dfs(int i,int n){
	    if(i>n){
	        return;
	    }
	    System.out.println(i);//2 20......
	    for(int x=0;x<=9;x++){
	        dfs(i*10 + x,n);// 20, 21 .......
	                        // 200, 201 .......
	    }
    }
    

//23 ======================================================================================================================================================
//Gold Mine 2

// dfs
//0 pe ni ja sakte hai
// total max gold
// connected componene


public class Main {
	static int max = 0;
	static int n = 0;
	static int m= 0;
	public static void getMaxGold(int[][] arr){
	    
		boolean[][] visited = new boolean[n][m];
		for(int i=0;i<n;i++){
		    for(int j=0;j<m;j++){
		        if(arr[i][j] != 0 && visited[i][j]==false){
		            ArrayList<Integer> bag = new ArrayList<>();
		            findgold(arr,visited,i,j, bag);
		            int sum = 0;
		            for(int b: bag){
		                sum+=b;
		            }
		            max = Math.max(max, sum);
		        }
		    }
		}
		
	}
	
	public static void findgold(int[][] arr, boolean visited[][], int sr, int sc,ArrayList<Integer> bag){
	    if(sr<0 || sc < 0 || sr>= n || sc >=m || arr[sr][sc] == 0 || visited[sr][sc]==true){
	        return;
	    }
	    visited[sr][sc] = true;
	    bag.add(arr[sr][sc]);
	    
	    findgold(arr,visited,sr+1,sc, bag);
	    findgold(arr,visited,sr,sc+1, bag);
	    findgold(arr,visited,sr-1,sc, bag);
	    
	    findgold(arr,visited,sr,sc-1, bag);
	}
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		n = scn.nextInt();
		m = scn.nextInt();
		int[][] arr = new int[m][n];
		for(int i = 0; i < arr.length; i++){
			for(int j = 0 ; j  < arr[0].length; j++){
				arr[i][j] = scn.nextInt();
			}
		}
		getMaxGold(arr);
		System.out.println(max);
    }
    

//24 ======================================================================================================================================================
//Solve Sudoku 


3 0 6 5 0 8 4 0 0
5 2 0 0 0 0 0 0 0
0 8 7 0 0 0 0 3 1
0 0 3 0 1 0 0 8 0
9 0 0 8 6 3 0 0 5
0 5 0 0 9 0 6 0 0
1 3 0 0 0 0 2 5 0
0 0 0 0 0 0 0 7 4
0 0 5 2 0 6 3 0 0

code is running linke
3 0 6 5 0 8 4 0 0   5 2 0 0 0 0 0 0 0  0 8 7 0 0 0 0 3 1
                j
                then new row and j=0
                ni = i+1;
                nj = 0;


                // is valid one is vertical looking
                // one horizontal looking and one in this give square looking
     0 1 2  

 0   3 0 6  
 1   5 2 0  
 2   0 8 7  
 

 // to calculate the start cordinate if any square

    //int smi = i / 3 * 3;
    //int smj = j / 3 * 3;
 



public static void display(int[][] board){
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[0].length; j++){
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static boolean isValid(int[][] board, int i, int j, int val) {
      for(int p=0;p<board[0].length;p++){
          if(board[i][p] == val){
              return false;
          }
      }
      
      for(int p=0;p<board.length;p++){
          if(board[p][j] == val){
              return false;
          }
      }
      
      
      
      
      int smi = i / 3 * 3;
      int smj = j / 3 * 3;
      
      for(int x=0;x<3;x++){
          for(int y=0;y<3;y++){
              if(board[smi+x][smj+y] == val){
                  return false;
              }
          }
          
      }
      
      
      return true;
  }
  public static void solveSudoku(int[][] board, int i, int j) {
    
    if(i==board.length) {
        display(board);
        return;
    }
    
    int ni=0;
    int nj = 0;
    
    if(j==board[0].length-1){ //3 0 6 5 0 8 4 0 0 (J) ab iske niche jayega
        ni = i+1;
        nj = 0;
    }else{
        nj = j+1;
        ni = i;
    }
    
    if(board[i][j]!=0){
        solveSudoku(board, ni, nj);
    }else{
        for(int pos=1;pos<=9;pos++){
            if(isValid(board, i,j,pos)){
                board[i][j] = pos;
                solveSudoku(board, ni, nj);
                board[i][j] = 0;
            }
        }
    }
    
    
    
    
    
  }

  public static void main(String[] args) throws Exception {
    Scanner scn = new Scanner(System.in);
    int[][] arr = new int[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        arr[i][j] = scn.nextInt();
      }
    }

    solveSudoku(arr, 0, 0);
  }


//25 ======================================================================================================================================================



}