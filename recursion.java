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

}