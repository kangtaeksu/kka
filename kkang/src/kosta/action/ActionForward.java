package kosta.action;

public class ActionForward {
	private boolean isRedirect; //true면 리다이렉트 하겠다. false면 디스패처 하겠다.
	private String path; //url경로를 어떻게 이동하겠다. 하는 path 
	
	public ActionForward() {}
	
	
	
	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
