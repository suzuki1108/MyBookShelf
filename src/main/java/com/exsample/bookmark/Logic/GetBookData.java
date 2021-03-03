package com.exsample.bookmark.Logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.exsample.bookmark.model.BookBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetBookData {

	String accessKey = "&applicationId=1043481609307048393";
	String bookUri = "https://app.rakuten.co.jp/services/api/BooksBook/Search/20170404?format=json";
	String title = "&title=";
	String author = "&author=";
	String sort = "&sort=";

	public List<BookBean> searchBook(String searchTitle, String searchAuthor, String sortingMethod) {

		List<BookBean> bookBeanList = new ArrayList<>();

		//URIの生成
		StringBuilder uri = new StringBuilder();
		uri.append(bookUri);

		// タイトルもしくは、著者、並び替え方法が検索窓に含まれていなかった場合、楽天APIのURIに該当接続文字列を追加しない処理
		if(searchTitle != null && !searchTitle.isEmpty()) {
			uri.append(title);
			uri.append(searchTitle);
		}
		if(searchAuthor != null && !searchAuthor.isEmpty()) {
			uri.append(author);
			uri.append(searchAuthor);
		}
		if(sortingMethod != null && !sortingMethod.isEmpty()) {
			uri.append(sort);
			uri.append(sortingMethod);
		}

		uri.append(accessKey);

		//Json取得メソッドからJson取得（API接続）
		JsonNode root = getJson(uri.toString());

		    //オブジェクト生成
		    for(int i = 0; i < root.get("hits").asInt(); i++) {
		    	BookBean bookBean = new BookBean();
		    	JsonNode Item = root.get("Items").get(i).get("Item");

		    	bookBean.setBookTitle(Item.get("title").asText());
		    	bookBean.setBookTitleKana(Item.get("titleKana").asText());
		    	bookBean.setAuthor(Item.get("author").asText());
		    	bookBean.setSalesDate(Item.get("salesDate").asText());
		    	bookBean.setImgPath(Item.get("largeImageUrl").asText());
		    	bookBean.setDescription(Item.get("itemCaption").asText());
		    	bookBean.setPrice(Item.get("itemPrice").asInt());
		    	bookBeanList.add(bookBean);
		    }
		return bookBeanList;
	}

	//API接続、JSON取得
	private JsonNode getJson(String uri) {

		StringBuilder result = new StringBuilder();
		JsonNode root = null;
		try {
			URL url = new URL(uri.toString());
		    HttpURLConnection con = (HttpURLConnection)url.openConnection();
		    con.connect();
		    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    String tmp = "";

	        while ((tmp = in.readLine()) != null) {
	    	    result.append(tmp);
		    }

		    ObjectMapper mapper = new ObjectMapper();
		    root = mapper.readTree(result.toString());
		    in.close();
		    con.disconnect();

		}catch(Exception e) {
			e.printStackTrace();
		}

		return root;
	}
}
