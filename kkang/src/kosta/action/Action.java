package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response)throws Exception;
	//표준 규약을 정하자 리턴타입이 ActionForward로 객체 일관성을 
}
