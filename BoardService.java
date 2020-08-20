package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class BoardService {
	
	List<Board> itemList = new ArrayList<>();
	
	public List<Board> readList() throws Exception {
		FileInputStream fis = new FileInputStream(route);
		ObjectInputStream ois = new ObjectInputStream(fis);
		List<Board> savedList = (ArrayList<Board>) ois.readObject();
		return savedList;
	}
	
	Scanner scanner = new Scanner(System.in);
	int num; //�׸� ������ ���� �ʵ�
	String route = "C:/Temp/MyPurchaseList.db";
	
	//�ʱ�ȭ��
	public void mainScreen() throws Exception {
		itemList = readList();
				
		System.out.println("�������� ������ �� �޸����Դϴ� ŪŪ");
		System.out.println("----------------------------------------------------------------");
		System.out.println("1.��Ϻ��� | 2.�󼼺��� | 3.�����ϱ� | 4.�߰��ϱ� | 5.�����ϱ� | 6.����");
		System.out.println("----------------------------------------------------------------");
		System.out.println("����: ");
		try {
		int mainSelectNum = Integer.parseInt(scanner.nextLine());
			switch(mainSelectNum) {
				case 1: openList(); break;
				case 2: detail(); break;
				case 3: modify(); break;
				case 4: addItem(); break;
				case 5: delItem(); break;
				case 6: System.exit(0);
				default : System.out.println("��ȣ�� �� �Է��ϼ���.\n\n\n\n\n\n"); mainScreen();
			} 
		}catch (Exception e) {
			System.out.println("��ȣ�� �� �Է��ϼ���.\n\n\n\n\n\n"); mainScreen();
		}
	}
	
	
	//��Ϻ���
	public void openList() throws Exception {
		if(itemList.isEmpty() == true) { System.out.println("����Ʈ�� ����־��\n\n\n\n"); mainScreen(); }
		System.out.println("\t�׸�\t\t\t����");
		for(int i=0; i<itemList.size(); i++) {
			Board board = itemList.get(i);
			System.out.println("\t" + board.getName() + "\t\t\t" + board.getPrice());
		}
		System.out.println("\n\n\n\n"); mainScreen();
	}
	
	//�󼼺���
	public void detail() throws Exception{
		if(itemList.isEmpty() == true) { System.out.println("����Ʈ�� ����־��\n\n\n\n"); mainScreen(); }
		System.out.println("\t�׸�\t\t\t����\t\t\t�޸�");
		for(Board board : itemList) {
			System.out.println("\t" + board.getName() + "\t\t\t"+ board.getPrice() 
			+"\t\t\t"+  board.getNote());
		}
		System.out.println("\n\n\n\n"); mainScreen();
	}
	
	//�������ϱ�
	public void modify() throws Exception{
		if(itemList.isEmpty() == true) { System.out.println("����Ʈ�� ����־��\n\n\n\n"); mainScreen(); }
		//i�� ��� �����̵�, ������ ī��Ʈ�� ���� �ڵ� ���� �ǵ���
		int counter = 0;
		for(int i=0; i<itemList.size(); i++) {
			System.out.print( i + ". " + itemList.get(i).getName() + "\t"); counter++;
			if(counter%3 == 0 && (counter > 2) ) System.out.println();
		}
		System.out.println("\n\n������ �׸� ��ȣ ����: ");
		num = Integer.parseInt(scanner.nextLine());
		
		itemList.get(num);
		System.out.println( itemList.get(num).getName() + "�� ������.");
		System.out.println("1.���ݼ��� | 2.�޸����");
		int selectMenu = Integer.parseInt(scanner.nextLine());
		switch(selectMenu) {
		case 1: alterPrice(); break;
		case 2: alterNote(); break;
		}		
	}
	//�����ݼ���
		public void alterPrice() throws Exception{
			try {
				System.out.printf("����: %d�� | ��������: ", itemList.get(num).getPrice());
				int alterPrice = Integer.parseInt(scanner.nextLine());
				itemList.get(num).setPrice(alterPrice);
				save(); System.out.println("���� �Ϸ�\n\n\n\n");
				mainScreen();
				} catch(Exception e) {
					System.out.println("���ڸ� �Է� �����մϴ�.\n");
					alterPrice();
				}
		}
		
		//���޸����
		public void alterNote() throws Exception{
			System.out.println("<�޸� ������ ����Ϸ��� Q�� �Է��ϼ���>");
			System.out.println("\t���� �޸�: " + itemList.get(num).getNote());
			System.out.print("\t�޸� ����: " );
			String alterNote = scanner.nextLine();
			if(alterNote == "Q" || alterNote == "q") { System.out.println("���� ���\n\n\n\n"); mainScreen(); }
			itemList.get(num).setNote(alterNote);
			//�� �ٵ� �׷��� get���� ������ �� �ε����� ��ü�� Setter����ϸ�
			//�ٽ� ArrayList�� �� �����ſ� ��ü ������  �־��� �ʿ� ����
			//ArrayList�󿡼� �� ��ü������ �����ϰ� �ִ� ���´ϱ�..
			//��ü������ ������ ��ġ�� List�� �ٽ� �Է��ϴ� ������ ��� ���°Ű���
			save(); System.out.println("���� �Ϸ�\n\n\n\n");
			mainScreen();	
		}
		
	//�߰��ϱ�
		public void addItem() throws Exception{
			System.out.print("�߰��� �׸� �Է�: ");
			String itemAdd = scanner.nextLine();
			Board board = new Board(itemAdd);
			
			class addPrice {
				void price() {
					try {
						System.out.print("����: ");
						int priceInput = Integer.parseInt(scanner.nextLine());
						board.setPrice(priceInput);
					} catch (Exception e) {
						System.out.println("���ڸ� �Է��� �� �־��\n"); price();
					}
				}
			}
			addPrice ap = new addPrice();
			ap.price();
			itemList.add(board); 
			save(); System.out.println("�׸� �߰� �Ϸ�\n\n\n\n");
			mainScreen();
		}
	
	//�����ϱ�
		public void delItem() throws Exception{
			if(itemList.isEmpty() == true) { System.out.println("����Ʈ�� ����־��\n\n\n\n"); mainScreen(); }
			class delTry {	
				void select() {
					try {
						System.out.print("\n\n������ �׸� ��ȣ ����: ");
						num = Integer.parseInt(scanner.nextLine());
						itemList.remove(num); 
						save(); System.out.println("���� �Ϸ�\n\n\n\n"); mainScreen();
						} catch(Exception e) {
							System.out.println("���ڸ� �Է��� �� �־��\n"); select();
						}
					}
			}
			delTry del = new delTry();
			int counter = 0;
			for(int i=0; i<itemList.size(); i++) {
				System.out.print(i + ". " + itemList.get(i).getName()); ++counter;
				if(counter%3 == 0 && (counter > 2) ) System.out.println();
			}
			del.select();
			
		}
	
		
		
	//��������
		public void save() throws Exception {  //�̰� ȣ��Ǵ� ������ ���� �ѱ�°Ŵϱ� ��� ��߰���?
			
			FileOutputStream fos = new FileOutputStream(route);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(itemList);
			oos.flush(); oos.close();
		}
		
	
}	

