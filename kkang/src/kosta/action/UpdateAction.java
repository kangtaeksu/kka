package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.service.BoardService;

public class UpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = new ActionForward();
		
		BoardService service = BoardService.getInstance();
		
		service.updateBoardService(request);
		
		actionForward.setRedirect(true);
		actionForward.setPath("listAction.do");
		
		
		return actionForward;
	}

}
