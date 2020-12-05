class hashmap{

/*    
Rabbits in forest
Longest consecutive 1's
number of subarrays sum exactly k
Subarray sum Divisible by k
K closest point from origin
subarray with equal number of 0 and 1
Substring with equal 0 1 and 2
14-Jun-20
Minimum number of refueling spots
Check AP sequence
Line reflection
Array of doubled Pair
Morning Assembly
Isomorphic string
X of akind in a deck
Brick wall
Longest consecutive sequence
15-Jun
Grid illumination
rearrange character string such that no two are same
Island perimeter
max frequency stack
length of largest subarray with continuous element
length of largest subarray with cont element 2
Sliding window maximum
17-Jun
Trapping Rain Water II
Kth smallest element in sorted 2d matrix
Kth smallest prime fraction
Count Pair whose sum is divisible by k
bulb switcher
Employee Free time
19-Jun
Pairs of coinciding points
same frequency after one removal
smallest number whose digit mult to given no.
A simple fraction
Find all anagrams in a string
Anagram Pallindrome
Anagram mapping
Group anagram
Find smallest size of string containing all char of other
smallest subarray with all the occurence of MFE
20-Jun
K anagram
longest substring with unique character
Insert Delete GetRandom O(1)
Binary heap
Build heap from array
Heap sort

*/
    //1  ====================================================================================================================
    //Number Of Employees Under Every Manager

    //Har employee k andar kitne report karte hai
    /*
    A C
    B C
    C F
    D E
    E F
    F F
*/

    <Managers, LIST OF EMPLOYEES UNDER HIM>


     public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();

        HashMap < String, String > emp_map = new HashMap < > ();

        for (int i = 0; i < n; i++) {
            emp_map.put(scn.next(), scn.next());
        }

        findCount(emp_map);

    }

    //Create a tree with ceo at top and all reporting under him

    public static void findCount(HashMap < String, String > emp_map) {

        HashMap<String, HashSet<String>> tree = new HashMap();
        String ceo = "";

        for (String emp: emp_map.keySet()) {  // [A,C] C  is manager and A is reporting to him
            
            //C                                     //A
            String manager_of_the_emp = emp_map.get(emp);

            if (emp.equals(manager_of_the_emp)) { //[F F] here F is reporting to F and hence CEO
                ceo = manager_of_the_emp;
            } else {
                if (tree.containsKey(manager_of_the_emp)) {
                    HashSet <String> employees = tree.get(manager_of_the_emp);
                    employees.add(emp);
                    tree.put(manager_of_the_emp, employees);
                } else {
                    HashSet < String > e = new HashSet();
                    e.add(emp);
                    tree.put(manager_of_the_emp, e);
                }
            }
        }


        HashMap < String, Integer > manager_number_of_emp_reporting = new HashMap < > ();
        getSize(tree, ceo, manager_number_of_emp_reporting);
        
        for(String emp: manager_number_of_emp_reporting.keySet()){
            System.out.println(emp + " " + manager_number_of_emp_reporting.get(emp) );
        }
    }


    public static int getSize(HashMap<String,HashSet<String>> tree, String man,HashMap<String, Integer> manager_number_of_emp_reporting) {
        
        if(tree.containsKey(man)== false){  // AGAR us emp ko koi report ni karta to bus uska 1 retunr karna hai
            manager_number_of_emp_reporting.put(man, 0);
            return 1;
        }
        
        int size = 0; // ye man k andar kitne log hai usko stor karega
        
        for(String emp : tree.get(man)){ // us manager k saar repotee and recursively unk
            int a = getSize(tree, emp, manager_number_of_emp_reporting);
            size = size + a;
        }
        
        manager_number_of_emp_reporting.put(man, size); // yaha manage ko left se rigth se jo size mila
        
        return size + 1; //bhejenge manage ko jod k
    
    }



//2  ====================================================================================================================
// Find Itinerary From Tickets

4
Chennai Banglore 
Bombay Delhi 
Goa Chennai 
Delhi Goa 

Isme important source find karnea hai
Chennai Banglore  in dono mein Bangolre source ni ho sakta
<CITY, VISITED>
<Banglore, FALSE>

Yaha Chennal Src ho sakt hai
<Chennai, TRUE>

But uaha CHENNAI DESTINATION hai to gaina map ko update kar denge
Goa Chennai 

<Chennai, FALSE>


public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int noofpairs_src_des = scn.nextInt();
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < noofpairs_src_des; i++) {
			String s1 = scn.next();
			String s2 = scn.next();
			map.put(s1, s2);	
		}
		
		 
        HashMap<String, Boolean> psrc = new HashMap<>();
        for(String src : map.keySet()){

            //<Chennai Banglore>
           
            String dst = map.get(src);
            psrc.put(dst, false); // <Banglore, FALSE> ye kabhi src ni ho sakta
            if(psrc.containsKey(src) == false){ //naya src add karo
                psrc.put(src, true);
            }
        }
        
        // find the orifginal src
        String oSrc = "";
        for(String src : psrc.keySet()){
            if(psrc.get(src) == true){
                oSrc = src;
                break;
            }
        }
        
        // src se dest tak traverse karo
        while(true){
            if(map.containsKey(oSrc)){
                System.out.print(oSrc+ " -> ");
                oSrc =map.get(oSrc); 
            }else{
                System.out.print(oSrc + ".");
                break;
            }
             
        }

    }
    




//3  ====================================================================================================================
//Check If An Array Can Be Divided Into Pairs Whose Sum Is Divisible By K


//9 7 5 3
//6
/*
9 and 3  = 12 can be divide by 6

//9%6 == 3  so 

9%6 + 3 can be divide by 6

k=6

9%6 = 3
9%7 = 2
9%5 = 4
9%3 = 6

frequency  of remminders:
3-1
2-1
4-1
6-1


ab jo frequnce mein kets hone k1 k2 k3 ...

k1+k2 = 6 k barabar honge

Second case : 40 20 30 and k = 10

is case mein 20 +40 ko 10 divide kar dega
is case agar 20%10 = 0 iski freqncy 0 ka alwa kuchh hui to pair ni hai


//third case
(9 21 15 ) % 6

3:1
3:1
3:1

So half yani 6/2 freq deta hai na uski bhi count odd ni honi chichiey
*/

    public static void solution(int[] arr, int k){
		HashMap<Integer, Integer> map = new HashMap<>();
		
		
		for(int val : arr){
		    int rem = val%k;
		    int f = map.getOrDefault(rem,0);
		    map.put(rem, f+1);
		}
		
		for(int val: arr){
		    int rem = val%k;
		    if(rem == 0){
		        int fq = map.get(rem);
		        if(fq%2 == 1){
		            System.out.println(false);
		            return;
		        }
		        
		    }else if(2*rem == k){ //rem == k/2
		        int fq = map.get(rem);
		        if(fq%2==1){
		            System.out.println(false);
		            return;
		        }
		    }else{
		        int fq = map.get(rem);
		        int rmFq = map.getOrDefault(k-rem, 0);
		        if(fq!=rmFq){
		            System.out.println(false);
		            return;
		        }
		         
		        }
		    }
	 
    System.out.println(true);
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] arr = new int[n];
		for(int i = 0 ; i < n; i++){
			arr[i] = scn.nextInt();
		}
		int k = scn.nextInt();
		solution(arr,k);
    }
    



//4  ====================================================================================================================

Count Distinct Elements In Every Window Of Size K










//5  ====================================================================================================================

1 2 1 3 4 2 3
k=4

to suru k 3  elemet (1 2 1) k freq ko HM me store karenge

//Ab j hoga index 0 pe
1 2 1 3 4 2 3
j     k
 j     k
   j     k
    j     k 
    <-----> ye size har baar hashmap mein stor karna hai aur usi tarah arr[j] ka frequence mein se remove karte jana hai 



	public static ArrayList<Integer> solution(int[] arr, int k) {
		ArrayList<Integer> ans = new ArrayList<>();
		HashMap<Integer, Integer> map = new HashMap<>();
	  1 2 1 3 4 2 3        
idx   0 1 2 3 4 5 6
k=4   _ _ _ _
          |
//        k-1 //K start from 0 k=4-1 =3 {0,1,2,3} idx of array
        
        int i=0;
        
        while(i < k-1){//0-2 tak ke element if k == 4
                   map.put(arr[i], map.getOrDefault(arr[i],0)+1);
                   i++;
        }
        //kynki i ki value 3 ho gai hai so i ko i-- kar 3 lana ha
        i--;
        int j=-1;
        
        while(i < arr.length-1){
            i++;
            map.put(arr[i], map.getOrDefault(arr[i],0)+1);//acquire
            ans.add(map.size());
            
            j++;
            int freq = map.get(arr[j]);
            if(freq == 1){
                map.remove(arr[j]);
            }else{
                map.put(arr[j], freq-1);
            }
        }
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int[] arr = new int[scn.nextInt()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = scn.nextInt();
		}
		int k = scn.nextInt();
		ArrayList<Integer> ans = solution(arr,k);
		for(int a : ans){
			System.out.print(a + " ");
		}
    }
    
//6  ====================================================================================================================
//Largest Subarray With Zero Sum

    2 8 -3 -5 2 -4 6 1 2 1 -3 4
-1  0 1  2  3 4  5 6 7 8 9 10 11
0   2 10 7  2 4  0 6 7 9 10 7 11  < prefix sum 

    |       |
             yaha pe bhi do hai iska matlab indono ki bich mein jo tha wo zero tha
     yha 
     pe 2 ha 
  
       - - -  IN 3 KA SUM ZERO hai
        
       
    public static int solution(int[] arr) {
		// write your code here
		int max = 0;
		HashMap<Integer,Integer> map = new HashMap<>();
		map.put(0,-1);//-1 index pe sum 0 hoga
		int sum = 0;
		for(int i=0;i<arr.length;i++){
		    sum+=arr[i]; //pahle sum ko add karnege
		    if(!map.containsKey(sum)){ //[sum-key] agar 2 pahle index 0 pe aaya tha
		        map.put(sum, i);// [2,0]
		         
		    }else{
		       int old_index = map.get(sum); // 2 sum pahle se available hai 2 ka index 0 and abhi wala 2 ki index in dono k bich mein number of element honge
		       max = Math.max(max, i-old_index);
		    }
		}
		return max;
	}



//7 ====================================================================================================================
// Count Of All Subarrays With Zero Sum



/*
isme HM<SUM and freq ki banege>


        2 8 -3 -5 2 -4 6 1 2 1 -3 4
    -1  0 1  2  3 4  5 6 7 8 9 10 11
    0   2 10 7  2 4  0 6 7 9 10 7 11  < prefix sum 
fq  1   1  1 1  2 1  2 1 2 1  2 3 
                |       
    yaha 2 second time aaya hai to 1 zero sum mil gaya

        2 8 -3 -5 2 -4 6 1 2 1 -3 4
    -1  0 1  2  3 4  5 6 7 8 9 10 11
    0   2 10 7  2 4  0 6 7 9 10 7 11  < prefix sum 
fq  1   1  1 1  2 1  2 1 2 1  2 3 
             |            |     |
         
             ------1------          
            ------1----------1---
            
            so 7 k karana 3 zero sum hue

            Remeber : 
            if(map.containsKey(sum)){ 
		        int f = map.get(sum);
		        count = count +f;  // count me abhi 1 hi jayega
		        map.put(sum, f+1); // but ab uski freq 2 ho jaygi next time count mein 2 add hoga
		        
		         
		    }


*/

	public static int solution(int[] arr) {
	// write your code here
		//<SUM, FREQUNCY>
		HashMap<Integer,Integer> map = new HashMap<>();
		map.put(0,1);//0 sum 1 baar aaya hai:freq
		int count = 0;
		int sum = 0;
		for(int i=0;i<arr.length;i++){
		    sum+=arr[i]; //pahle sum ko add karnege
		    if(map.containsKey(sum)){ //[sum-key] agar 2 pahle index 0 pe aaya tha
		        int f = map.get(sum);
		        count = count +f;
		        map.put(sum, f+1);
		        
		         
		    }else{
		        map.put(sum, 1);
		    }
		}
		return count;
	}


//8  ====================================================================================================================



    public static void main(String[] args) {
        
    }
}