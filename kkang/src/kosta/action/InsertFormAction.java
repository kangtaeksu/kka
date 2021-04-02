package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertFormAction implements Action {

	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//insert_form.jsp 이동
		
		//모델2에서는 바로 jsp로 요청하면 반칙이다. 왜 반칙이겠어요
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/insert_form.jsp");
		//디스패처는 JSP까지 화면에 출력하면 디스패처
		
		//리다이렉트는 인서트를 다 했어 글 입력 다하고 저장다 됐어 그러면 list.jsp로 가야지 바로 가면되겠어 안되겠어
		//안되지 다시 컨트롤러 호출해서 list.do 해야지
		
		return forward;
	}

}
