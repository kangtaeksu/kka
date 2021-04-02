package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.service.BoardService;

public class DeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward actionForward = new ActionForward();
		
		BoardService service = BoardService.getInstance();
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		service.deleteBoardService(seq);
		
		actionForward.setPath("listAction.do");
		actionForward.setRedirect(true);
		return actionForward;
	}

}
