package kosta.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosta.service.BoardService;

public class insertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//DAO는 쉽게 말해서 쿼리를 하나 질의하기 위한거다
		//DAO는 서비스랑 구분할 수 있어야 하는데
		//내가 URL을 하나 요청했다.
		
		//서비스가 ㅈㄴ 복잡해서 DAO가 여러개 나오겠지
		//서비스가 1번 DAO호출, 2번DAO호출, 3번 DAO호출할 경우도 생김
		//서비스가 복잡해지면서 DAO를 사용하고 또 가공해서 보내야할 때도 있다.
		//DAO쿼리문 하나 실행해서 될 일이 아니라 서비스가 필요해진다.
		//우리는 서비스 객체가 필요하다.
		//----------------------------------------------
		
		//1비지니스 로직 호출
		//2호출된 결과를 저장 -- 여기선 생략 (인서트이기 때문)
		//3뷰를 선정
		ActionForward forward = new ActionForward();
		BoardService service = BoardService.getInstance();
		
		//비즈니스 로직 호출
		service.insertBoardService(request);
		
		//뷰를 선정 (새로운 요청을 함 listAction.do)
		//걍 list.jsp 가면 되지 않아요? 근데 어떠한 비즈니스를 호출하지 않을거라고
		//가긴 가는데 화면에 아무거도 출력 안됨 어딜 돌아야해 그러니까 컨트롤러를 돌아야한다고
		//모든 요청은 컨트롤러로 가서 액션을 호출하고 액션이 비즈니스 서비스를 호출하고 그 결과값을 호출한 것을 그 액션이 우리를 뷰로 보냄
		forward.setRedirect(true);
		forward.setPath("listAction.do");
		
		return forward;
	}

}
