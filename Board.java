package manager;

import java.io.Serializable;

public class Board implements Serializable {
	//필드
	private String name;
	private int price;
	private String note;
	
	//생성자
	public Board(String name) {
		this.name = name;
	}
	
	//메소드
	public String getName() { return name; }
	public void setPrice(int price) { this.price = price; }
	public int getPrice() { return price; } 
	public void setNote(String note) { this.note = note; }
	public String getNote() { return note; }
}
