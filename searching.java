class searching{

//1 ==================================================================================================================
//Binary Search
 public static int BS01(int[] arr,int ele){
     int si = 0;
     int ei=arr.length-1;

     while(si <= ei){
         int mid =   (si + ei)/2  ;//(si + ei) >> 1;
         if(arr[mid] == ele) return mid;
         else if(arr[mid] < ele){
             si =mid+1;

             }else{
                ei = mid - 1;

         }
     }
 }


  public static int firstOcc(int[] arr,int ele){
      
  }

    
    public static void main(String[] args) {
        
    } 
}