package db_base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import calam.BaseBeans;
import db_base_error.DBAccessException;
import db_base_error.RollbackException;

/**
 * SELECT文専用　SQLを投げて、beansを指定してやると、beansのつまったSQL結果のリストが返ってくる メソッドしかない
 * @author OWNER
 *
 * @param <T> beans
 */
public class UseSelect<T extends BaseBeans> extends UseSQL {


	public UseSelect() {
		dataAccess = new DataAccess();
	}

	/**
	 * サーブレットから呼び出すときは、コンテキストパスを与えなければ
	 *  引数ServletContext context = Servlet.getServletContext();
	 * @param context
	 */
	public UseSelect(ServletContext contextParam) {
		this.context = contextParam;
		// TODO 自動生成されたコンストラクター・スタブ
		dataAccess = new DataAccess(context);
	}

	//TODO まだ単数ビーンにしか対応していない
	/**
	 * SELECT文専用　SQLを投げて、beans.classを指定してやると、SQL結果のリストが返ってくる
	 * @param SQL
	 * @param o エンティティ
	 * @throws DBAccessException
	 */
	public List<T> DoSelect(String SQL, Class<T> clazz) throws DBAccessException
	{
		ResultSet resultSet = null;

		try {
			resultSet = dataAccess.SelectSQL(SQL);
		} catch (RollbackException e1) {
			// TODO 自動生成された catch ブロック
			System.out.println("ロールバックえらー");
			e1.printStackTrace();
		}
		//可変長引数は配列ごと渡す
		//リザルトセット取得クラス
		SetResToBean<T> sr = new SetResToBean<T>();
		List<T> resultList = sr.set(resultSet, clazz);
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("れざるとセット閉じられへん");
		}

		return resultList;
	}


}
