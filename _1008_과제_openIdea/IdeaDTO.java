package _1008_과제_openIdea;

public class IdeaDTO {
//	private int num = 0;
	private String title = null;
	private String content = null;
	private String writer = null;
	
	
//	public int getNum() {
//		return num;
//	}
//	public void setNum(int num) {
//		this.num = num;
//	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Override
	public String toString() {
		return "IdeaDTO [getTitle()=" + getTitle() + ", getContent()=" + getContent() + ", getWriter()=" + getWriter()
				+ "]";
	}
	
}
