//Java数组章节练习题
public class ArrayUtils{

    //1.计算数组中最大值
    public static int arrayMaxElement(int[] data){
		int i = 0 ;
		int max = data[0];
		for(i=0; i<data.length; i++){
			if(data[i]>max){
				max = data[i];
			}
		}
        return max;
    }
    
    //2.计算数组中最小值
    public static int arrayMinElement(int[] data){
		int i = 0;
		int min = data[0];
		for(i=0; i<data.length; i++){
			if(data[i]<min){
				min = data[i];
			}
		}
        return min;
    }
    
    
    //3.计算数组值之和
    public static int arrayElementSum(int[] data){
		int i = 0;
		int sum = 0;
		for(i=0;i<data.length;i++){
			sum = sum + data[i];
		}
        return sum;
    }
    
    //4.数组拼接
    public static int[] arrayJoin(int[] a, int[] b){
		int[] c = new int[a.length+b.length];
		int i = 0;
		int j = 0;
		for(i=0; i<a.length; i++){
			c[i] = a[i];
		}
		for(i=a.length,j=0; i<a.length+b.length; i++,j++){
			c[i] = b[j];
		}
        return c;
    }

    //5.数组截取
    //[start, end)
    public static int[] arraySub(int[] data, int start , int end){
		if((start<0)||(end>0)||(end-start>data.length)||(start>end)){
			System.out.println("截取失败");
			return data;
		}
        int[] b = new int[end-start+1];
		int i = 0;
		for(i=0; start<=end; i++){
			b[i] = data[start++];
		}
        return b;
    }
    
    //6.数组打印
    public static void printArray(int[] data){
		int i = 0;
		for(i=0; i<data.length; i++){
			System.out.print(data[i]+" ");
		}
		System.out.println();
    }
    
    //7.数组反转
    // 比如：[1,2,3,4] => [4,3,2,1]
    public static void printReversal(int[] data){
        int left = 0;
		int right = data.length-1;
		int tmp = 0;
		while(left<right){
			tmp = data[left];
			data[left] = data[right];
			data[right] = tmp;
			left++;
			right--;
		}
		printArray(data);
    }
    
    public static void main(String[] args){     
		int[] array1 = new int[]{3,5,9,1,7,13,25};
		int[] array2 = new int[]{2,4,6,10,12,18};
		int arrayMax = arrayMaxElement(array1);
		int arrayMin = arrayMinElement(array1);
		int arraySum = arrayElementSum(array1);
		System.out.print("最大值为：");
		System.out.println(arrayMax);
		System.out.print("最小值为：");
		System.out.println(arrayMin);
		System.out.print("数组之和为：");
		System.out.println(arraySum);
		System.out.println("拼接前数组1为：");
		printArray(array1);
		System.out.println("拼接前数组2为：");
		printArray(array2);
		System.out.println("数组1和数组2拼接之后为：");
		printArray(arrayJoin(array1,array2));
		System.out.println("数组1中第3个到第6个数为：");
		printArray(arraySub(array1, 2, 5));
		System.out.println("数组1反转之后为：");
		printReversal(array1);
		
    }
}