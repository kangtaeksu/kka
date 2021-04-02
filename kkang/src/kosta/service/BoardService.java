package kosta.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kosta.model.Board;
import kosta.model.BoardDao2;
import kosta.model.ImageUtil;
import kosta.model.ListModel;
import kosta.model.Reply;
import kosta.model.Search;

public class BoardService {
	private static BoardService service = new BoardService();
	//서비스에는 DAO객체가 있어야한다.
	private static BoardDao2 dao;
	public static final int PAGE_SIZE = 2;
	
	
	public static BoardService getInstance() {
		dao = BoardDao2.getInstance();
		return service;
	}
	
	public int insertBoardService(HttpServletRequest request)throws Exception {
		//원래 MVC의 M 비즈니스를 처리해주는 역할
		request.setCharacterEncoding("utf-8");
		//원래 jsp에서 처리했던 비즈니스를 서비스에서 처리함
		
		//파일 업로드를 해야함, 파일 정보를 DB에 저장해야함
		
		//파일 업로드(경로, 파일크기, 인코딩, 파일이름중첩 정책)
		String uploadPath = request.getRealPath("upload");
		int size = 20 * 1024 * 1024; //20MB
		
		//파일을 업로드하기위한 request를 만들어야함
		MultipartRequest multi =
				new MultipartRequest(request, uploadPath, size, "utf-8", 
						new DefaultFileRenamePolicy());
		
		//request를 multi로 다 바꿔야함
//		Board board = new Board();
//		board.setTitle(request.getParameter("title"));
//		board.setWriter(request.getParameter("writer"));
//		board.setContents(request.getParameter("contents"));
		Board board = new Board();
		board.setTitle(multi.getParameter("title"));
		board.setWriter(multi.getParameter("writer"));
		board.setContents(multi.getParameter("contents"));
		board.setFname("");
		
		//파일업로드 DB(파일이름 저장)
		//파일이름이 null이 아니라는 것은 업로드 했다는거
		if(multi.getFilesystemName("fname") != null) {
			String fname = (String)multi.getFilesystemName("fname");
			board.setFname(fname);
			
			//썸네일 이미지(gif, jpg) aa.gif => aa_small.gif (새로운 썸네일 이미지를 만듬)
			String pattern = fname.substring(fname.indexOf(".")+1);//gif, jpg
			String head = fname.substring(0,fname.indexOf(".")); //aa를 가져옴
			
			//원본파일객체
			String imagePath = uploadPath + "\\"+fname; // \만 필요한데 문법적 의미때문에 \\해야됨
			File src =new File(imagePath);
			
			//썸네일 파일객체
			String thumPath = uploadPath + "\\"+head+"_small."+pattern;
			File dest = new File(thumPath);
			
			if(pattern.equals("gif") || pattern.equals("jpg")) {
				ImageUtil.resize(src, dest, 100, ImageUtil.RATIO); //높이는 RATIO
			}
		}
		
		
		return dao.insertBoard(board);
	}
	
	public ListModel listBoardService(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		Search search = new Search();
		HttpSession session = request.getSession();
		
		
		//검색할 경우
		if(request.getParameterValues("area")!=null) {
			session.removeAttribute("search");
			search.setArea(request.getParameterValues("area"));
			search.setSearchKey("%"+request.getParameter("searchKey")+"%");
			session.setAttribute("search", search);
		}
		//체크해제 후 검색버튼만 클릭 ( 전체목록으로 돌아가기 )
		else if(request.getParameterValues("area")==null &&
				request.getParameter("pageNum")==null) {
			session.removeAttribute("search");//세션에서 검색 정보 삭제
		}
		
		//검색 후 페이지를 클릭할 경우
		//이젠 검색버튼을 누른게 아니라 페이징 버튼을 누른거야 그러면 세션에서 데이터를 가져와야함
		//세션에 검색 정보가 있는 경우 그 검색정보를 가져오도록
		if(session.getAttribute("search")!=null){
			search = (Search)session.getAttribute("search");
		}
		
		
		
		//페이지처리 필수사항
		//한 페이지에 보여줄 글 개수
		//yes24는 한페이지에 20개의 글을 보여줌
		//페이지당 글개수 , 총 글 개수를 알아야한다.-> 그러면 자연스럽게 총 페이지수도 구할 수 있다.
		//현재페이지가 뭐인지도 알아야함 
		// (페이지당 글, 총 글 개수, 총 페이지수, 총 수, startPage, endPage, startRow)
		
		//로직이 또 다른 dao(글 개수)가 필요하다.
		
		//총 글갯수
		int totalCount = dao.countBoard(search);
		
		//총 페이지수
		int totalPageCount = totalCount/PAGE_SIZE;
		if(totalCount%PAGE_SIZE >0) {
			totalPageCount++;
		}
		
		//현재 페이지
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum="1"; //눌러서 온게 아니라 그냥 처음 들어왔다면 기본값을 1로하자
		}
		int requestPage = Integer.parseInt(pageNum); //리퀘스트페이지가 현재 페이지가 된다.
		
		//현재 페이지가 23이라면 첫페이지는 21이되고, 마지막 페이지는 30이 되어야한다.
		
		//맨처음에는 1부터 10까지, 11부터 20까지  .....
		
		//startPage = 현재페이지 - (현재페이지 -1) % 5   <5 페이지씩 보여주고싶을 때
		int startPage = requestPage - (requestPage-1) % 5;
		
		//endPage
		int endPage = startPage + 4;
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		//페이징처리를 하는것은 DB로부터 글을 다 가져오는 게 아니다.
		//한번에 표시할 글만 가져오는거다. 페이지당 10개 글이면 10개 로우만 db에서 가져옴
		
		//startRow = (현재페이지 -1) * 페이지당 글 개수
		int startRow = (requestPage - 1) * PAGE_SIZE;
		
		//이제 현재 페이지에 따라서 페이징 영역을 그려야 한다. 이 값들을 JSP가 알고있어야겠다. 알고있어야 그림을 그리지
		//그래서 또다른 도메인 객체를 만들기를 원합니다. 모델에 클래스 하나 추가
		
		List<Board> list = dao.listBoard(search,startRow);
		ListModel listModel =
				new ListModel(list, requestPage, totalPageCount, startPage, endPage);
		return listModel;
	}
	
	public Board detailBoardService(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		
		int seq=1;
		
		if(request.getParameter("seq")!=null){
			seq=Integer.parseInt(request.getParameter("seq"));
		}
		
		Board board = dao.detailBoard(seq);
		List<Reply> list = listReplyService(seq);
		request.setAttribute("board", board);
		System.out.println(list);
		request.setAttribute("replyList", list);
		
		
		return board;
	}
	
	
	public Board updateFormService(HttpServletRequest request) throws Exception{
		
		
		Board board = new Board();
		int seq = Integer.parseInt(request.getParameter("seq"));
		board=dao.detailBoard(seq);
		request.setAttribute("board", board);
		
		return board;
	}
	
	public int updateBoardService(HttpServletRequest request) throws Exception{
		int re = -1;
		request.setCharacterEncoding("utf-8");
		
		Board board = new Board();
		board.setSeq(Integer.parseInt(request.getParameter("seq")));
		board.setTitle(request.getParameter("title"));
		board.setWriter(request.getParameter("writer"));
		board.setContents(request.getParameter("contents"));
		
		dao.updateBoard(board);
		
		return re;
	}
	
	public int deleteBoardService(int seq) throws Exception {
		return dao.deleteBoard(seq);
	}
	
	public int insertReplyService(HttpServletRequest request) throws Exception{
		
		request.setCharacterEncoding("utf-8");
		Reply reply = new Reply();
		
		reply.setR_title(request.getParameter("r_title"));
		reply.setR_writer(request.getParameter("r_writer"));
		reply.setR_contents(request.getParameter("r_contents"));
		reply.setSeq(Integer.parseInt(request.getParameter("seq")));
		
		return dao.insertReply(reply);
	}
	
	public List<Reply> listReplyService(int seq) throws Exception{
		
		return dao.listReply(seq);
	}
}
