package com.exsample.bookmark.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.exsample.bookmark.Logic.GetBookData;
import com.exsample.bookmark.Logic.Messages;
import com.exsample.bookmark.Logic.MyBookDisplay;
import com.exsample.bookmark.Logic.SortBook;
import com.exsample.bookmark.model.BookBean;
import com.exsample.bookmark.model.BookRepository;
import com.exsample.bookmark.model.LoginUserSession;
import com.exsample.bookmark.model.UserRepository;

@Controller
public class BookController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	LoginUserSession loginUser;
	@Autowired
	HttpSession session;


	@RequestMapping(value = "/searchBook" , method = RequestMethod.GET)
	public String searchBook(Model model, @RequestParam(name = "searchTitle", required = false) String searchTitle) {

		/*検索のロジック呼び出し
		 *著者検索などのオプションは開発中につき、引数は空白でOK
		*/
		List<BookBean> searchResult = new GetBookData().searchBook(searchTitle, "", "");

		//検索結果をセッションに格納する
		session.setAttribute("myBookList", searchResult);
		model.addAttribute("searchBookResultText",Messages.searchBookResultText);

		return "mainMenu";
	}

	@RequestMapping(value = "/showBook" , method = RequestMethod.POST)
	public String showBook(Model model, @RequestParam(name = "bookNumber", required = false) String bookNumber) {

		List<BookBean> myBookList = (List<BookBean>)session.getAttribute("myBookList");

		//リクエストの番号（何番目の本か）情報から該当の本を取得し、セッションに格納
		BookBean showBook = myBookList.get(Integer.parseInt(bookNumber));
		session.setAttribute("showBook",showBook);

		//自分の本棚に同じ本が格納されている場合は、登録ボタン表示用フラグをtrueにする。
		List<BookBean> dbBookList = bookRepository.findByUserId(loginUser.getUserInfoBean().getUserId());
		boolean addFlag = dbBookList.parallelStream()
				.anyMatch(s -> s.getBookTitle().equals(showBook.getBookTitle()));

		model.addAttribute("addFlag", addFlag);
		//画像サイズを大きくしてセッションに格納
		model.addAttribute("bigImgPath", showBook.getImgPath().replace("200x200", "400x400"));
		//priceの文字列を成形してセッションに格納
		model.addAttribute("price", String.valueOf(showBook.getPrice()).concat("円"));

		return "showDetail";
	}

	@RequestMapping(value = "/registBook", method = RequestMethod.POST)
	public String registBook(Model model) {

		BookBean showBook = (BookBean)session.getAttribute("showBook");

		//挿入日とユーザIDをオブジェクトに追加してから保存
		java.util.Date now = new java.util.Date();
		java.sql.Date date = new java.sql.Date(now.getTime());
		showBook.setInsertDate(date);
		showBook.setUserId(loginUser.getUserInfoBean().getUserId());

		//選択した本をDBにインサート
		bookRepository.save(showBook);

		//セッションにログインユーザの本棚をセット
		List<BookBean> myBookList = new MyBookDisplay().myBookDisplay(model, loginUser.getUserInfoBean(), bookRepository);
		session.setAttribute("myBookList", myBookList);

		return "mainMenu";
	}

	@RequestMapping(value = "/removeBook", method = RequestMethod.POST)
	public String removeBook(Model model) {

		BookBean showBook = (BookBean)session.getAttribute("showBook");

		//選択した本のレコードを削除
		bookRepository.delete(showBook);

		//セッションにログインユーザの本棚をセット
		List<BookBean> myBookList = new MyBookDisplay().myBookDisplay(model, loginUser.getUserInfoBean(), bookRepository);
		session.setAttribute("myBookList", myBookList);

		return "mainMenu";
	}

	@RequestMapping(value = "/sortBook", method = RequestMethod.POST)
	public String sortBook(Model model, @RequestParam(name = "sortType", required = false) String sortType) {

		//セッションに格納されている本棚のオブジェクトを取り出し、ソートロジックを呼び出し、ソート。
		List<BookBean> sortBook = new SortBook()
				.sortBook((List<BookBean>)session.getAttribute("myBookList"),sortType);

		session.setAttribute("myBookList", sortBook);

		return "mainMenu";
	}

	@RequestMapping(value = "/myBookShelf", method = RequestMethod.POST)
	public String myBookShelf(Model model) {

		//セッションにログインユーザの本棚をセット
		List<BookBean> myBookList = new MyBookDisplay().myBookDisplay(model, loginUser.getUserInfoBean(), bookRepository);
		session.setAttribute("myBookList", myBookList);

		return "mainMenu";
	}
}