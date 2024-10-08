package _1008_과제_openIdea;

import java.util.ArrayList;
import java.util.Scanner;

public class IdeaMenu {
	
	IdeaDAO ideadao = IdeaDAO.getInstance();
	
	public void menu() {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("1. 등록");
			System.out.println("2. 수정");
			System.out.println("3. 검색");
			System.out.println("4. 삭제");
			System.out.println("5. 전체보기");
			System.out.println("6. 종료");
			System.out.println("----메뉴선택----");
			int selNum = in.nextInt(); in.nextLine();
			if(selNum == 1) {
				add();
			}else if(selNum==2) {
				update();
			}else if(selNum==3) {
				search();
			}else if(selNum==4) {
				del();
			}else if(selNum==5) {
				list();
			}else if(selNum ==6) {
				break;
			}			
		}
	}

	private void add() {
		Scanner in = new Scanner(System.in);
		System.out.println("제목입력");
		String title = in.nextLine();
		System.out.println("내용입력");
		String content = in.nextLine();
		System.out.println("작성자 이름");
		String writer = in.nextLine();
		IdeaDTO ideadto = new IdeaDTO();
		ideadto.setTitle(title);
		ideadto.setContent(content);
		ideadto.setWriter(writer);
		ideadao.add(ideadto);		
		
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}

	private void search() {
		// TODO Auto-generated method stub
		
	}

	private void del() {
		// TODO Auto-generated method stub
		
	}

	private void list() {
		ArrayList<IdeaDTO> i = ideadao.selectAll();
		for(IdeaDTO tempi : i) {
			System.out.println(tempi.toString());
		}
		
	}

}
