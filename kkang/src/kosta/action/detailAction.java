package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.service.BoardService;

public class detailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward actionForward = new ActionForward();
		
		BoardService service = BoardService.getInstance();
		
		
		service.detailBoardService(request);
		
		
		actionForward.setPath("/detail.jsp");
		actionForward.setRedirect(false);
		
		return actionForward;
	}

}
