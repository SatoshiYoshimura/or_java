/**
 *
 */
package db_base;

import java.sql.ResultSet;

/**
 * @author OWNER
 * テーブル毎の処理のベースクラス
 */
public abstract class BaseTableManager {

	/**
	 *結果セット用プロパティ
	 */
	protected ResultSet resultSet;

	/**
	 * 結果行数プロパティ
	 */
	protected int resultRow;

	/**
	 *コンストラクタいる？
	 */
	public BaseTableManager() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 *Select用メソッド
	 * @return ResultSet
	 */
	public ResultSet SelectSQL()
	{
		return this.resultSet;
	}

	/**
	 *Insert Update Delete用メソッド
	 * @return resultRow
	 */
	public int NonQuerySQL()
	{
		return resultRow;
	}


}
