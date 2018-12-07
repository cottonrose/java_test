public class ProxyPattern{
	public static void main(String[] args){
		SendGift send = new BoyFriendSendGift("张三","李四","花");
		send.Send();
		SendGift cour = new CourierSendGift(send);
		cour.Send();
	}
}

interface SendGift{
		public void Send();
}

class BoyFriendSendGift implements SendGift{
	private String boyFriendName;
	private String girlFriendName;
	private String giftName;

	public BoyFriendSendGift(String boyFriendName, String girlFriendName, String giftName){
		this.boyFriendName = boyFriendName;
		this.girlFriendName = girlFriendName;
		this.giftName = giftName;
	}
	public void Send(){
		System.out.println(this.boyFriendName+"送"+this.giftName+"给"+this.girlFriendName);
	}
}
class CourierSendGift implements SendGift{
	private SendGift sendGift;
	public CourierSendGift(SendGift sendGift){
		this.sendGift = sendGift;
	}
	public void Send(){
		System.out.println("快递员联系客户");
		this.sendGift.Send();
		System.out.println("客户签收");
	}


}