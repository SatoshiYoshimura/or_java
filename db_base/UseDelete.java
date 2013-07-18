package db_base;

import javax.servlet.ServletContext;

import db_base_error.RollbackException;

/**
 * デリート行うとき用クラス
 * @author OWNER
 *
 */
public class UseDelete extends UseSQL {

	public UseDelete() {
		dataAccess = new DataAccess();
	}


	/**
	 * サーブレットから呼び出すときは、コンテキストパスを与えなければ
	 *  引数ServletContext context = Servlet.getServletContext();
	 * @param context
	 */
	public UseDelete(ServletContext contextParam) {
		this.context = contextParam;
		// TODO 自動生成されたコンストラクター・スタブ
		dataAccess = new DataAccess(context);
	}


	/**
	 *デリート文使うときのメソッド
	 * @param sql
	 * @return
	 */
	public int doDelete(String sql)
	{
		int resultRow = 0;
		try {
			resultRow = this.dataAccess.NonQuerySQL(sql);
		} catch (RollbackException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("ロールバックエラー");
		}

		return resultRow;
	}


}
