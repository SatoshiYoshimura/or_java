package db_base;

import javax.servlet.ServletContext;

import db_base_error.RollbackException;

/**
 * アップデート行うとき用クラス
 * @author OWNER
 *
 */
public class UseUpdate extends UseSQL {

	public UseUpdate() {
		dataAccess = new DataAccess();
	}

	/**
	 * サーブレットで使うときはこっち使わなうんこになる
	 * @param contextParam
	 */
	public UseUpdate(ServletContext contextParam)
	{
		this.context = contextParam;
		dataAccess = new DataAccess(context);
	}

	/**
	 *アップデート文使うときのメソッド
	 * @param sql
	 * @return
	 */
	public int DoUpdate(String sql)
	{
		int resultRow = 0;
		try {
			resultRow = this.dataAccess.NonQuerySQL(sql);
		} catch (RollbackException e) {
			// TODO 自動生成された catch ブロック
			System.out.println("ロールバックエラー");
			e.printStackTrace();
		}

		return resultRow;
	}


}
