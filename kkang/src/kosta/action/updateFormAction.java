package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.service.BoardService;

public class updateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		ActionForward forward = new ActionForward();
		BoardService service = BoardService.getInstance();
		
		service.updateFormService(request);
		
		forward.setRedirect(false);
		forward.setPath("/updateForm.jsp");
		//디스패처는 JSP까지 화면에 출력하면 디스패처
		
		//리다이렉트는 인서트를 다 했어 글 입력 다하고 저장다 됐어 그러면 list.jsp로 가야지 바로 가면되겠어 안되겠어
		//안되지 다시 컨트롤러 호출해서 list.do 해야지
		
		return forward;

	}

}
