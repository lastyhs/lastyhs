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
	int num; //항목 선택을 위한 필드
	String route = "C:/Temp/MyPurchaseList.db";
	
	//초기화면
	public void mainScreen() throws Exception {
		itemList = readList();
				
		System.out.println("애으니의 사고싶은 것 메모장입니다 큭큭");
		System.out.println("----------------------------------------------------------------");
		System.out.println("1.목록보기 | 2.상세보기 | 3.수정하기 | 4.추가하기 | 5.삭제하기 | 6.종료");
		System.out.println("----------------------------------------------------------------");
		System.out.println("선택: ");
		try {
		int mainSelectNum = Integer.parseInt(scanner.nextLine());
			switch(mainSelectNum) {
				case 1: openList(); break;
				case 2: detail(); break;
				case 3: modify(); break;
				case 4: addItem(); break;
				case 5: delItem(); break;
				case 6: System.exit(0);
				default : System.out.println("번호를 잘 입력하세요.\n\n\n\n\n\n"); mainScreen();
			} 
		}catch (Exception e) {
			System.out.println("번호를 잘 입력하세요.\n\n\n\n\n\n"); mainScreen();
		}
	}
	
	
	//목록보기
	public void openList() throws Exception {
		if(itemList.isEmpty() == true) { System.out.println("리스트가 비어있어요\n\n\n\n"); mainScreen(); }
		System.out.println("\t항목\t\t\t가격");
		for(int i=0; i<itemList.size(); i++) {
			Board board = itemList.get(i);
			System.out.println("\t" + board.getName() + "\t\t\t" + board.getPrice());
		}
		System.out.println("\n\n\n\n"); mainScreen();
	}
	
	//상세보기
	public void detail() throws Exception{
		if(itemList.isEmpty() == true) { System.out.println("리스트가 비어있어요\n\n\n\n"); mainScreen(); }
		System.out.println("\t항목\t\t\t가격\t\t\t메모");
		for(Board board : itemList) {
			System.out.println("\t" + board.getName() + "\t\t\t"+ board.getPrice() 
			+"\t\t\t"+  board.getNote());
		}
		System.out.println("\n\n\n\n"); mainScreen();
	}
	
	//ㅇ수정하기
	public void modify() throws Exception{
		if(itemList.isEmpty() == true) { System.out.println("리스트가 비어있어요\n\n\n\n"); mainScreen(); }
		//i가 계속 움직이되, 세번의 카운트가 차면 자동 엔터 되도록
		int counter = 0;
		for(int i=0; i<itemList.size(); i++) {
			System.out.print( i + ". " + itemList.get(i).getName() + "\t"); counter++;
			if(counter%3 == 0 && (counter > 2) ) System.out.println();
		}
		System.out.println("\n\n수정할 항목 번호 선택: ");
		num = Integer.parseInt(scanner.nextLine());
		
		itemList.get(num);
		System.out.println( itemList.get(num).getName() + "을 선택함.");
		System.out.println("1.가격수정 | 2.메모수정");
		int selectMenu = Integer.parseInt(scanner.nextLine());
		switch(selectMenu) {
		case 1: alterPrice(); break;
		case 2: alterNote(); break;
		}		
	}
	//ㄴ가격수정
		public void alterPrice() throws Exception{
			try {
				System.out.printf("현재: %d원 | 수정가격: ", itemList.get(num).getPrice());
				int alterPrice = Integer.parseInt(scanner.nextLine());
				itemList.get(num).setPrice(alterPrice);
				save(); System.out.println("변경 완료\n\n\n\n");
				mainScreen();
				} catch(Exception e) {
					System.out.println("숫자만 입력 가능합니다.\n");
					alterPrice();
				}
		}
		
		//ㄴ메모수정
		public void alterNote() throws Exception{
			System.out.println("<메모 수정을 취소하려면 Q를 입력하세요>");
			System.out.println("\t현재 메모: " + itemList.get(num).getNote());
			System.out.print("\t메모 수정: " );
			String alterNote = scanner.nextLine();
			if(alterNote == "Q" || alterNote == "q") { System.out.println("수정 취소\n\n\n\n"); mainScreen(); }
			itemList.get(num).setNote(alterNote);
			//엥 근데 그러면 get으로 가져온 그 인덱스의 객체에 Setter사용하면
			//다시 ArrayList에 그 가ㅈㅕ온 객체 새ㅐ로  넣어줄 필요 없이
			//ArrayList상에서 그 객체번지를 참조하고 있는 상태니까..
			//객체차원의 관리만 마치면 List에 다시 입력하는 문제는 상관 없는거겠지
			save(); System.out.println("수정 완료\n\n\n\n");
			mainScreen();	
		}
		
	//추가하기
		public void addItem() throws Exception{
			System.out.print("추가할 항목 입력: ");
			String itemAdd = scanner.nextLine();
			Board board = new Board(itemAdd);
			
			class addPrice {
				void price() {
					try {
						System.out.print("가격: ");
						int priceInput = Integer.parseInt(scanner.nextLine());
						board.setPrice(priceInput);
					} catch (Exception e) {
						System.out.println("숫자만 입력할 수 있어요\n"); price();
					}
				}
			}
			addPrice ap = new addPrice();
			ap.price();
			itemList.add(board); 
			save(); System.out.println("항목 추가 완료\n\n\n\n");
			mainScreen();
		}
	
	//삭제하기
		public void delItem() throws Exception{
			if(itemList.isEmpty() == true) { System.out.println("리스트가 비어있어요\n\n\n\n"); mainScreen(); }
			class delTry {	
				void select() {
					try {
						System.out.print("\n\n삭제할 항목 번호 선택: ");
						num = Integer.parseInt(scanner.nextLine());
						itemList.remove(num); 
						save(); System.out.println("삭제 완료\n\n\n\n"); mainScreen();
						} catch(Exception e) {
							System.out.println("숫자만 입력할 수 있어요\n"); select();
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
	
		
		
	//파일저장
		public void save() throws Exception {  //이거 호출되는 곳으로 예외 넘기는거니까 계속 써야겠지?
			
			FileOutputStream fos = new FileOutputStream(route);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(itemList);
			oos.flush(); oos.close();
		}
		
	
}	

