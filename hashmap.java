class hashmap{

/*    
Rabbits in forest
Longest consecutive 1's
number of subarrays sum exactly k
Subarray sum Divisible by k
K closest point from origin
subarray with equal number of 0 and 1
Substring with equal 0 1 and 2

Minimum number of refueling spots
Check AP sequence
Line reflection
Array of doubled Pair
Morning Assembly
Isomorphic string
X of akind in a deck
Brick wall
Longest consecutive sequence
Grid illumination
rearrange character string such that no two are same
Island perimeter
max frequency stack
length of largest subarray with continuous element
length of largest subarray with cont element 2
Sliding window maximum
Trapping Rain Water II
Kth smallest element in sorted 2d matrix
Kth smallest prime fraction
Count Pair whose sum is divisible by k
bulb switcher
Employee Free time
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
// https://leetcode.com/problems/minimum-window-substring/
//Smallest Substring Of A String Containing All Characters Of Another String

// given string s1 :   d b a e c b b a b d c a a f b d d c a b g b a
//s2 : abbcdc

// find the smallest substring of s1 that contains all the characters of s2.


//freq map for s2 which show number of character count in s2

abbcdc
a-1
b-2
c-2
d-1
==a1b2c2d1

j=-1 0 1 2 3 4 5 6 7 8 9 10 11   
     d b a e c b b a b d c a a f b d d c a b g b a
  
  j  d1b1a2e1c1b2b3a2b4d2c1

  acquire
    ------------------------  At this point first iteration completes as all the chahacter arr found of s2

  
process
    Now will make this string little shot if possible by increaing jana

 
j=-1 0 1 2 3 4 5 6 7 8 9 10 11   
     d b a e c b b a b d c a a f b d d c a b g b a
   | | 
   j j j j

  -1 0 1 2 3 4 5 6 7 8 8 10 till character c tak

    d1b1a2e1c1b2b3a2b4d2c1  yaha d=4 hai c=1 hai a=2 hai b=4 hai
  
    ab j wale chahcter ko remove karne se kya all the character of s1 mil raha hai to string ko chhota kareneg a1b2c2d1

        d1b1a2e1c1b2b3a2b4d2c2
          b1a2e1c1b2b3a2b4d1c2
            a2e1c1b1b2a2b3d1c2
              e1c1b1b2a1b3d1c2  //e1 hata sakte hai kynki s2 meni ni chahiye
                c1b1b2a1b3d1c2 // yaha 1 c haiand required bhi s1 min 1 hi c hai so yaha hai tak ka answer hai

               substring( j+1 , i+1);// i+1 mein i tak substring count hota hai
  
    is stage pe c1 sa aage badhna hai j abhi c1 pe hoga and i++ hoga
  
  
  public static String solution(String s1, String s2){

        //frequency map for s2 the original string
		 HashMap<Character, Integer> map2 = new HashMap<>();
         String ans = "";
          for(char c: s2.toCharArray()){
              map2.put(c,map2.getOrDefault(c,0)+1);
          }
        


        int minium_chaacter_in_s2 = s2.length();
        int minimum_character_in_s1 = 0;
        int len = s1.length();
        HashMap<Character, Integer> map1 = new HashMap<>();
        int j=-1;
        int i=-1;
        
        while(true) {
          boolean loopOne = false;
          boolean looptwo = false;
            
          // a1b2c2d1 = 6 = minium_chaacter_in_s2
          //minimum_character_in_s1 ye value tav badhega b <=2 milega usko s1 mein b case mein, d<=1 milega s1 mien
          while (i < len-1 && minimum_character_in_s1 < minium_chaacter_in_s2) { // i< s1.lenght()-1 kynki charAt(s1.lenght()) exception
            i++;
            char ch = s1.charAt(i);
            map1.put(ch, map1.getOrDefault(ch, 0) + 1);
    
            if (map1.getOrDefault(ch, 0) <= map2.getOrDefault(ch, 0)) {
              minimum_character_in_s1++;
            }
    
            loopOne = true;
          }
    
           // is jagah pe humkko  minimum_character_in_s1 mein saare 6 chaacter minium_chaacter_in_s2 k mil gaye honge
          while (j < i && minimum_character_in_s1 == minium_chaacter_in_s2) {
            String present_answer = s1.substring(j + 1, i + 1);
    
            if (ans.length() == 0 || present_answer.length() < ans.length()) {
              ans = present_answer;
            }
            j++;
    
            char ch = s1.charAt(j);
            //har chacater ko aage se trim karna hai
            if (map1.get(ch) == 1) {
              map1.remove(ch);
            } else {
              map1.put(ch, map1.get(ch) - 1);
            }
            //minimum_character_in_s1 iska value 6 character se kam hua to minimum_character_in_s1 ye kam hoga ab i aage badhega acquire phase mien
            if (map1.getOrDefault(ch, 0) < map2.getOrDefault(ch, 0)) {
              minimum_character_in_s1--;
             
                
            }
            looptwo = true;
          }
    
            if(loopOne==false && looptwo==false){
              break;
            }
        }
        return ans;
	}
  
  
  
  
//9  ====================================================================================================================
//Smallest Substring Of A String Containing All Unique Characters Of Itself

//isme output only length deni hai jisme saare uniq chacter honge
//input 1 hi string hog, uska minimu substring nikalenege jisme saare unique chacter hoga

public static int solution(String str){
		HashSet<Character> uniqueSet = new HashSet<>();
		int len = str.length();
		for(char ch: str.toCharArray()){
		    uniqueSet.add(ch);
		}
		//System.out.println(uniqueSet);
		
		int ans = Integer.MAX_VALUE;
		
		HashMap<Character, Integer> map = new HashMap<>();
		int i=-1;
		int j=-1;
		
		while(true){
		    //acquire
		    boolean firstLoop = false;
            boolean secondLoop = false;
            
            // map.size() < uniqueSet.size() ye is liye kynki set mein saare unique chater hai aur map ka key set ke saare ley hi honge
		    while(i< len-1 && map.size() < uniqueSet.size()){
		        i++;
		        char c = str.charAt(i);
		        map.put(c, map.getOrDefault(c,0)+1); 
		        firstLoop = true;
		    }
		    
		    while(j<i && map.size() == uniqueSet.size()){
		        int pans = i-j;
		        
		        if(pans< ans){
		            ans = pans;
		        }
		        j++;
		        
		        char c = str.charAt(j);
		        
		        if(map.get(c)==1){
		            map.remove(c);
		        }else{
		            map.put(c, map.get(c)-1);
		        }
		        
		        secondLoop=true;
		        
		    }
		    
		    if(firstLoop==false && secondLoop==false){
		        break;
		    }
		    
		}
		return ans;
    }
    


//10  ====================================================================================================================
//https://leetcode.com/problems/longest-substring-without-repeating-characters/
//Longest Substring With Non Repeating Characters

//You are given a string.
//You have to find the length of the longest substring of the given string that contains all non-repeating characters.

//ip : aabcbcdbca
//op: 4
/*
a
aa X  len=1   //delete first a [a,2]--> [a,1]  decrese its value to 1  mak sure it value shou be 1 ini relase phase
_ab   len=2
_abc  len=3 
_abcb X lwn =3 //delete first a [a:1,b:1,c:1:b:2]--> [b:1,c:1:b:2]--> [b:1,c:1:b:2]  decrese its value to 1  mak sure it value shou be 1 ini relase phase
                                              
a:0
b:2-1 === b:1 yaha value 1 mila to brak kar jayega
___cbc yaha se acquire start hoga
jaise hi b=1 milega 


*/

public static int solution(String str) {
		// write your code here
		int ans = 0;
		HashMap<Character, Integer> map = new HashMap<>();
		int i=-1;
		int j=-1;
		
		while(true){
		    boolean first = false;
		    boolean second = false;
		    while( i < str.length()-1){
                first = true;
		        i++;
		        char ch = str.charAt(i);
		        map.put(ch, map.getOrDefault(ch,0)+1);
		        
		        if(map.get(ch)==2){
		            break;
		        }else{
		            int len = i-j;
                    if(len > ans){
                        ans= len;
                    }
		        }
		    }
            
            while(j<i){
                
                
                j++;
                
                char ch = str.charAt(j);
                map.put(ch, map.get(ch)-1);
                if(map.get(ch)==1){
                    break;
                }
                
               second = false;
                
            }		    
		    
		    if(first ==  false && second == false){
		        break;
		    }
		}
		return ans;
		
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String str = scn.next();
		System.out.println(solution(str));
	}

//11  ====================================================================================================================

//Count Of Substrings Having All Unique Characters
/*
abbc
a
ab
abb X
abbd X

b
bb  X
bbd  X

    i
    a      b       c
   _a
j


           i
    a      b       c
   _______ab
          _b
   j




                   i
    a      b       c
            _______bc
   _______________abc
                 _c
           j
  

Magic of i-j
i-j

j=-1

abc
i=0 {a}

i-j = 0- -1 = 1 {a}

j=-1 
 a                b                 c                                   c
 a               {ab,b}             {abc,bc,c}
i

i=0              i=1               i=2                                  i=3
j=-1             j=-1              j=-1                                 j=0 remove charAt(j) = 0 that is a delted {b c c}, then {c,c} herx map(c=2) which will                                                                              become 1 after map.put(c,map.get(c)-1); 

i-j+=1            i-j=1- -1=2      i-j=2- -1=3                         i-j = 1 as j++ is inceaseing
*/                                                        



public static int solution(String str) {
	int count =0;
	HashMap<Character,Integer > map = new HashMap<>();
	int i=-1,j=-1;
	
	while(true){
	    boolean first = false;
	    boolean second = false;
	    //acquire
	    
	    while(i<str.length()-1){
	        first = true;
	        i++;
	        char c  = str.charAt(i);
	        map.put(c,map.getOrDefault(c,0)+1);
	        if(map.get(c)==2){
	            break;
	        }else{
	            count += (i-j);
	        }
	   }
	   
	   
	   //release
	   while(j<i){
	       second = false;
	       j++;
	       char c  = str.charAt(j);
	       
	       map.put(c,map.get(c)-1);
	       
	       if(map.get(c)==1){
	           count += (i-j);
	           break;
	       }
	   }
	    
	    if(first==false && second==false){
	        break;
	    }
	    
	    
	}
	
	
	return count;
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String str = scn.next();
		System.out.println(solution(str));
	}





//12  ====================================================================================================================
// Longest Substring With Exactly K Unique Characters

/*
//You are given a string(str) and a number K.
//You have to find length of the longest substring that has exactly k unique characters.
//If no such substring exists, print "-1".


//aabcbcdbca k=2 uniq character

// [a:2,b:1]  size==3
// aab           Here map size is two with 2 unique chaacter ab

//j=-1 i =2 : 2- -1 = 3

//i++  aabc [a:2,b:1,c:1]  size()=3 break 
realaese phase
//j++ == -j ++ = j=0
aabc
j  i   charAt(j) = a map.put(a:2-1) : [a:1,b:1,c:1] size()-3 still 

J++
_abc   map.remove(a) [b:1,c:1] size ==2  (size = 2) so old size was 3
 j=1

 */ 

public static int solution(String str, int k){
		
		HashMap<Character, Integer> map =new HashMap<>();
		int ans = 0;
		int i=-1;
		int j=-1;
		
		while(true){
		    boolean first = false;
		    boolean second = false;
		    while(i < str.length()-1){
		        first = true;
		        i++;
		        char ch = str.charAt(i);
		        map.put(ch, map.getOrDefault(ch,0) +1);
		        if(map.size() < k){
		            continue;
		        }else if (map.size()==k){
		            int len = i-j;
		            ans=Math.max(len, ans);
		        }else{
		            break; 
		        }
		    }
		    
		     while(j < i){
		         second = true;
		        j++;
		        char ch = str.charAt(j);
		        if(map.get(ch)==1){
		            map.remove(ch);
		        }else{
		            map.put(ch, map.get(ch)-1);    
		        }
		        
		        
		        if(map.size() > k){
		            continue;
		        }else if(map.size()==k){
		            int len = i-j;
		            ans=Math.max(len, ans);
		            break;
		        } 
		    }
		    
		    
		    if( first == false &&  second == false){
		        break;
		    }
		}
		

		return ans;
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        String str = scn.next();
        int k = scn.nextInt();
		System.out.println(solution(str,k));
	}



//13  ==============================================TOUGH======================================================================
//Count Of Substrings With Exactly K Unique Characters
















//14  ====================================================================================================================
// Count of Equivalent Subarrays
// count of unique integers in the subarray = count of unique integers in the given array.

// 2 5 3 5 2 4 1 3 1 4

//1 hashset mein hum ssare unque element daalsne

//2 HashMap<Number anf frequcnr>
// jause map ka size HashSet k size barabar ho jata hai 1 subarray mill gaya
/*
{2 5 3 5 2 4 1} 3 1 4
 yaha taka subaarray mein saare unique elemen milenege
{2 5 3 5 2 4 1 3} 1 4
{2 5 3 5 2 4 1 3 1} 4 
{2 5 3 5 2 4 1 3 1 4 }

Aur fir usk baad ssare hi valid hone  so total = 4 

Now release phase
2  { 5 3 5 2 4 1} 3 1 4  // 2 release hua abhi saare element hash map k part hai fir 4
2  { 5 3 5 2 4 1 3} 1 4
2 { 5 3 5 2 4 1 3 1} 4 
2 { 5 3 5 2 4 1 3 1 4 }

Tab tak release karnent zjap tak map.size < set.size()

*/

public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] arr = new int[n];
        HashSet<Integer>  uniqe_number = new HashSet<>();
        for(int i = 0 ; i  < n; i++){
            arr[i] = scn.nextInt();
            uniqe_number.add(arr[i]);
        }
        
        int k = uniqe_number.size();
        int i =-1;
        int j =-1;
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap();
        while(true){
            boolean first = false;
            boolean second = false;
            while( i< arr.length-1){
                first = true;
                i++;
                int x = arr[i];
                map.put(x, map.getOrDefault(x,0)+1);
                
                if(map.size()== k){
                    count +=arr.length-i;
                    break;
                }
                
            }
            
            while(j<i){
                second = true;
                j++;
                if(map.get(arr[j]) == 1){
                    map.remove(arr[j]);
                }else{
                    map.put(arr[j], map.get(arr[j])-1);
                }
                
                if(map.size()==k){
                    count +=arr.length-i;
                   
                }else{
                    break;
                }
            }
       
        
            if(first==false && second == false){
                break;
            }
        }
		System.out.println(count);
		
	}



//15  ====================================================================================================================

//Maximum Consecutive Ones - 2
//given an array(arr) which contains only 0's and 1's and a number K.
// find the maximum number of consecutive 1's in the given array if you can flip at most K zeroes.

/*
j=-1, k=2
acquire 
// 1 1 0 1 0 0 1 1 0 1 0 1 1 
   i  ans=1
     i  ans=2
      i  ans=3 Only 1 O we need 2 zero
         i  ans=4
           i  ans=5 // 2 0]s are used a
              yaha count ki value 3 hogai hai count > k

release j++
  jab tak count ki value 2 ya use kam ni hota hai
  j aage badhta rahaega
  arr[j] == 0 hoga, tab count-- yani count =2 ho jayega 3 se
  // 1 1 0 1 0 0 1 1 0 1 0 1 1 
                     i<- third zero tak paphuch gaya hai
         | <- ye zero delete ho jayega
       
         i  ans=4
           i  ans=5 // 2 0]s are used a
  
  */

public static int solution(int[] arr, int k){
        // write your code here
        int j=-1;
        int count = 0;
        int ans = 0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]==0){
                count++;
            }
            
            while(count > k){
                j++;
                if(arr[j] == 0){
                    count--;
                }
            }
            int len = i-j;
            ans = Math.max(ans, len);
            
        }
        return ans;
    }
    
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] arr = new int[n];
        for(int i = 0 ; i  < n; i++){
            arr[i] = scn.nextInt();
        }
        int k = scn.nextInt();
        System.out.println(solution(arr,k));
	}


//16  ====================================================================================================================
//https://leetcode.com/problems/max-consecutive-ones-iii/

Maximum Consecutive Ones - 1
Given an array(arr) which contains only 0's and 1's.
find the maximum number of consecutive 1's in the given array if you can flip at most one zero.

//Same to previous one


public static int solution(int[] arr){
        int ans = 0;
        int count = 0;
        int j=-1;
        
        for(int i=0;i<arr.length;i++){
            if(arr[i]==0){ /////////////////first change
                count++;
            }
            
            if(count>1){ ////////////////////second change
                j++;
                if(arr[j]==0){
                    count--;
                }
            }
            
            int l = i-j;
            
            ans = Math.max(l, ans);
        }

        return ans;
    }
    
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] arr = new int[n];
        for(int i = 0 ; i  < n; i++){
            arr[i] = scn.nextInt();
        }
        System.out.println(solution(arr));
    }
    






//17  ====================================================================================================================
//Longest Substring With At Most K Unique Characters

//given a string(str) and a number K.
//find the length of longest substring of the given string that contains at most K unique characters.
//Count nikalna hai k se chhote walo ka length niklana hai and then bade hua to release karna hai

//aabcbcdbca  2
/*
a k=1 acquire
a k=1 acquire
b k=2 acquire
c k>2 release
remove a, remove a 
b k=1 acquire
c k=2 acquire
b k=2 acquire
c k=2 acquire
d k=3 >2 release

relase b relase c relase b k
c k=1 acquire
d k=2 acquire
*/

public static int solution(String str, int k) {
		int ans = 0;
		HashMap<Character, Integer> map = new HashMap<>();
		int i=-1;
		int j=-1;
		
		while(true){
		    boolean first = false;
		    boolean second = false;
		    
		    while(i<str.length()-1){
		        first = true;
		        i++;
		        char ch = str.charAt(i);
		        
		        map.put(ch, map.getOrDefault(ch,0)+1);
		        
		        if(map.size()>k){
		            break;
		        }else{
		            int len = i-j;
		            ans = Math.max(len,ans);
		        }
		        
		    }
		    
		    
		    while(j<i){
		        second = true;
		        j++;
		        
		        char ch = str.charAt(j);
		        if(map.get(ch)==1){
		            map.remove(ch);
		        }else{
		            map.put(ch, map.get(ch)-1);
		        }
		        if(map.size()==k){
		            
		            int len = i-j;
		            ans = Math.max(len,ans);
		            break;
		         
		        }
		        
		    }
		    
		    if(first==false && second==false) {
		        break;
		    }
		}
		
		return ans;
		
		
	}


//18  ====================================================================================================================
// Count of Substrings with at-most K Distinct Characters
/*
 j   a  a  b  c  b  c  d  b  d  b  c  a k:2
     |     |
     aab
     ab
     b
     j=0 i=3    (i =3 pe k=3 hua tha i=2 pe sab sahi tha)
     i-j = 3 - 0 = 3    




		    if(i==str.length()-1 && map.size()==k){
		        break;
            }
            
            Siginificance of this line  jab i last tak pahuch gaya hoga and and map mein size bhi k so aage ni chalna hai

*/

public static int solution(String str, int k) {
		// write your code here
		
		int ans = 0;
		int i=-1;
		int j=-1;
		
		HashMap<Character, Integer> map = new HashMap<>();
		while(true){
		    boolean first = false;
		    boolean second = false;
		    
		    while(i<str.length()-1){
		        first = true;
		        i++;
		        char ch = str.charAt(i);
		        map.put(ch, map.getOrDefault(ch,0)+1);
		        if(map.size()<=k){
		            int len = i-j;
		            ans += len;
		            
		        }else{
		            break;
		        }
		    }
            
            
		    if(i==str.length()-1 && map.size()==k){
		        break;
		    }
		    while(j<i){
		        second=true;
		        j++;
		        char ch = str.charAt(j);
		        if(map.get(ch)==1){
		            map.remove(ch);
		        }else{
		            map.put(ch, map.getOrDefault(ch,0)-1);
		        }
		        
		        if(map.size()==k){
		            int len = i-j;
		            ans += len;
		            break;
		        }
		  }
		  
		  if(first==false && second==false){
		      break;
		  }
		  
		  
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        String str = scn.next();
        int k = scn.nextInt();
		System.out.println(solution(str,k));
	}


//**  ====================================================================================================================
// Binary String With Substrings Representing Numbers From 1 To N

















//19  ====================================================================================================================
//Count Of Subarrays Having Sum Equals To K
   
 /*       
            3 9 -2  4   1   -7  2   6 -5 8 -3 -7 6 2 1

 sum  0     3 12 10 14  15  8  10
count 1     1  1  1 1    1  1   2

                   -------------
                   4   1   -7  2 SUM =0 1 zero mil gaya map mei check akrenge ki 10 pahle se hai agar hai to ans++
                   map(10 , amp.get(10)++) 
*/


	public static int solution(int[] arr, int target){
		int count = 0;
		
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0,1);
		int sum = 0;
		
		for(int i=0;i<arr.length;i++){
		    sum+=arr[i];
		    if(map.containsKey(sum-target)){
		        count+=map.get(sum-target);
		    }
		    map.put(sum, map.getOrDefault(sum,0)+1);
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] arr = new int[n];
        for(int i = 0 ; i < n; i++){
            arr[i] = scn.nextInt();
        }
        int target = scn.nextInt();
        System.out.println(solution(arr,target));
    }
    
//20  ====================================================================================================================
//Find All Anagrams In A String

//anagarm, aaise words jinkin frequncy 1same ho

//cbaebabacd
   
//abc

//chota wala string pattern_map=abc isme jitne string hai utna mandtory hai

pattern_map{
    a:1
    b:1
    c:1
}

ab 3 character hum string_map map me {cba}ebabacd is se daal lenge

string_map{
    c:1
    b:1
    a:1
}

cbaebabacd
j  i
ab 1 comapre function banayaa agaya jo dono hashmap ko comapre karege freq k basis pe 

//BHI FALSE RETUR KARGE i++ {cbae}babacd
cbaebabacd
j   i
string_map{
    c:1
    b:1
    a:1
    e:1
}
leneht mismatch j=0 remove hoga map se and j++
cbaebabacd
 j  i
string_map{
    
    b:2
    a:1
    e:1
}
string_map{
    
    b:1
    a:1
    e:1
}
cbaebabacd
 j    i
//compare not equal aquire

public static void findAnagrams(String s, String p) {
        HashMap<Character, Integer> pattern_map = new HashMap<>();
        HashMap<Character, Integer> string_map = new HashMap<>();
        
        for(int i=0; i<p.length();i++){
            char c = p.charAt(i);
            pattern_map.put(c, pattern_map.getOrDefault(c,0)+1);
        }
        
        for(int i=0;i<p.length();i++){
            char c = s.charAt(i);
            string_map.put(c, string_map.getOrDefault(c,0)+1);
        }
        
        
        int j=0;
        int i = p.length();
        int count = 0;
        String ans = "";
        while(i < s.length()){
            
            if(compare(pattern_map, string_map)==true){
                count++;
                ans+=j +" ";
            }
            
            char c = s.charAt(i);
            string_map.put(c, string_map.getOrDefault(c,0)+1);
            
            char ch = s.charAt(i-p.length());
            
            if(string_map.get(ch)==1){
                string_map.remove(ch);
            }else{
                string_map.put(ch, string_map.get(ch)-1);
            }
            i++;
            j++;
        }
        
        
        if(compare(pattern_map,string_map)==true){
                count++;
                ans+=j +" ";
        }
         
        System.out.println(count);
        System.out.println(ans);
        
    }
    
    public static boolean compare(HashMap<Character, Integer> pattern_map, HashMap<Character, Integer> string_map){
            for(char c: pattern_map.keySet()){
                if(pattern_map.getOrDefault(c,0)!= string_map.getOrDefault(c,0)){
                    return false;
                }
            }
            return true; 
        }




//21  ====================================================================================================================

//two strings s1, s2, and a number K.
//if two strings are K-anagrams of each other or not.
/*
abbcml  {a:1,b:2,c:1,m:1,l:1}
acjbkb  {a:1,b:2,c:1,k:1,j:1}

//clearly if we change ml from first with kj in second we can get 2 amargam


map : {a:1,b:2,c:1,m:1,l:1}  store
now we will semove second string {a:1,b:2,c:1,k:1,j:1} from first which match
{a:1,b:2,c:1,m:1,l:1}
{a:1,b:2,c:1,k:1,j:1}

            {m:1,l:1}
            {k:1,j:1}

            Now there is only two lwft in map and hence it is valid
*/




 public static boolean areKAnagrams(String str1, String str2, int k) {
	    if(str1.length()!=str2.length()) return false;
		HashMap<Character, Integer> map = new HashMap<>();
		
		for(int i=0;i<str1.length();i++){
		    char ch = str1.charAt(i);
		    map.put(ch, map.getOrDefault(ch,0)+1);
		}
		
		for(int i=0;i<str2.length();i++){
		    char ch = str2.charAt(i);
		    if(map.getOrDefault(ch,0) > 0){
		         map.put(ch, map.getOrDefault(ch,0)-1);
		    }
		   
		}
		
		
		int count =0;
		
		for(char c: map.keySet()){
		    count+=map.get(c);
		}
		
		if(count >k){
		    return false;
		}else{
		    return true;
		}

		
    }
  
    






//22  ====================================================================================================================
// Find Anagram Mappings
/*
1 2 3 4 5 2
4 3 2 1 5 2

idx  0 1 2 3 4 5
     1 2 3 4 5 2 str1


       4 3 2 1 5 2
idx    0 1 2 3 4 5 str2



Answer for str 1 is 3 2 1 0 4 5 


So basicaaly str1 ka index denha hai

Map for str2

and har character k liye uska arraylist hoga jo ye batayega kaun sa index kab kab aaya hai 

1 2  3 4 5 2 jaiause [2 :{1,5}] 2 1 and 5 pe 2 baar aaaya hai

we will make a pair 

    class public static class Pair{
        int idx = 0;
        ArrayList<Integer> list = new ArrayList<>();
        }
//idx basicaaly track karega kaun sa 2 kab use hua hai

str2 ko map mein daalenege

	HashMap<Integer, Pair> map = new HashMap<>();
		for(int i=0;i<arr2.length;i++){
		    if(map.containsKey(arr2[i])){ //agae wo pahle se hai to us character k pair class k array list mein add kar denge
		        Pair p = map.get(arr2[i]);
		        p.list.add(i);
		    }else{
		        Pair newPair = new Pair(); // wo ni hai to pair class ko ucreate karne
		        newPair.list.add(i);
		        map.put(arr2[i], newPair);
		  }
        }

        ..ab arr1 se start karnene pair nikalenge [2 :{1,5}] , jaise 2 ko dhunda {1,5 pe 2 mila} first time idx ==0 hai to 1 milega aur idx++ kar denge to next time
        // jab hum 2 ko dhundege to 5 milega
        
        for(int i=0;i<arr1.length;i++){
                Pair p = map.get(arr1[i]);
                ans[i] = p.list.get(p.idx);
                p.idx++;
            }


*/

 public static class Pair{
        int idx = 0;
        ArrayList<Integer> list = new ArrayList<>();
    }
	public static int[] anagramMappings(int[] arr1, int[] arr2) {
		HashMap<Integer, Pair> map = new HashMap<>();
		for(int i=0;i<arr2.length;i++){
		    if(map.containsKey(arr2[i])){
		        Pair p = map.get(arr2[i]);
		        p.list.add(i);
		    }else{
		        Pair newPair = new Pair();
		        newPair.list.add(i);
		        map.put(arr2[i], newPair);
		  }
		}
		
		int ans[] = new int[arr1.length];
		for(int i=0;i<arr1.length;i++){
		    Pair p = map.get(arr1[i]);
		    ans[i] = p.list.get(p.idx);
		    p.idx++;
		}

		return ans;
	}


//23  ====================================================================================================================

//Valid Anagram

//easy s1 ko freq mao mein daalo and s2 ko freq mein se nikala last mein size ==0 hona chahiiey 
public static boolean solution(String s1, String s2){
		if(s1.length()!=s2.length()) return false;
		
		HashMap<Character, Integer> map = new HashMap<>();
		for(int i=0;i< s1.length();i++){
		    char ch = s1.charAt(i);
		    map.put(ch, map.getOrDefault(ch,0)+1);
		 }
		 
		 for(int i=0;i< s2.length();i++){
		    char ch = s2.charAt(i);
		    if(map.getOrDefault(ch,0)==1){
		        map.remove(ch);
		    }else{
		        map.put(ch, map.getOrDefault(ch,0)-1);
		    }
		    
		 }
		 
		 return map.size()==0?true:false;

	 
	}





  public static void main(String[] args) {
        
    }
}