package manager;

import java.io.Serializable;

public class Board implements Serializable {
	//�ʵ�
	private String name;
	private int price;
	private String note;
	
	//������
	public Board(String name) {
		this.name = name;
	}
	
	//�޼ҵ�
	public String getName() { return name; }
	public void setPrice(int price) { this.price = price; }
	public int getPrice() { return price; } 
	public void setNote(String note) { this.note = note; }
	public String getNote() { return note; }
}
