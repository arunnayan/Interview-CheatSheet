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






  
  public static void main(String[] args) {
        
    }
}