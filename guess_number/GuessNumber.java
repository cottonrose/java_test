import java.util.Scanner;

public class GuessNumber{
	
	public static void main(String[] args){		
		java.util.Random random = new java.util.Random();
		int value = random.nextInt(100);
		menu();
		while(true){
			System.out.println("请输入要猜的数字：");
			Scanner reader = new Scanner(System.in);
			int data = reader.nextInt();
			if(data == value){
				System.out.println("数字为："+value);
				System.out.println("猜对了！");
				break;
			}
			else if(data<value){
				System.out.println("猜小了");
				continue;
			}
			else{
				System.out.println("猜大了");
				continue;
			}
		}
	}
	
	public static void menu(){
		System.out.println("猜数字游戏开始");
		System.out.println("请输入0-100之间的数字");
	}
}