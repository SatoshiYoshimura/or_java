/**
 *
 */
package db_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;

import calam.CalamManager;
import db_base_error.DBAccessException;
import db_base_error.RollbackException;

/**
 * @author OWNER
 * データベースアクセスクラス
 */
public class DataAccess extends BaseTableManager {

	/**
	 * DBアクセス用プロパティ
	 */
	protected String url;
	protected String driver;
	protected String user;
	protected String pass;
	Connection connection;
	//Servletのパス取得用
	public ServletContext context;

	//カラムマネージャーは常にいる
	CalamManager cm;

	/**
	 *データベースアクセスクラス
	 *コンストラクタでアクセスしちゃう
	 */
	public DataAccess()
	{
		//ビーンズ
		DBProp dbprop = new DBProp();

		// プロパティの読み込みServletじゃない時
		String proppass = "WEB-INF/Properties/DB.properties";

		LoadProperty loadprop = new LoadProperty();
		loadprop.load(proppass);

		//一旦変数に格納
		dbprop = loadprop.GiveDBprop();

		url = dbprop.url;
		driver = dbprop.driver;
		user = dbprop.user;
		pass = dbprop.pass;

		/*データベース接続します*/
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			//ここでオートコミットオフ
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e)
		{
			System.out.println("ドライバーが見つかりません");
		} catch (SQLException e)
		{
			System.out.println("データベース接続エラーです");
		}

	}

	/**
	 *データベースアクセスクラス
	 *コンストラクタでアクセスしちゃう
	 *サーブレットでアクセスする場合、コンテキストを与える
	 * ServletContext context = Servlet.getServletContext();
	 */
	public DataAccess(ServletContext contextParam)
	{
		//ビーンズ
		DBProp dbprop = new DBProp();

		// プロパティの読み込みServletじゃない時
		//Servletの時
		// プロパティファイルのパスを取得する
        ServletContext context = contextParam;
        String proppass = context.getRealPath("/WEB-INF/Properties/DB.properties");

		LoadProperty loadprop = new LoadProperty();
		loadprop.load(proppass);

		//一旦変数に格納
		dbprop = loadprop.GiveDBprop();

		url = dbprop.url;
		driver = dbprop.driver;
		user = dbprop.user;
		pass = dbprop.pass;

		/*データベース接続します*/
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
			//ここでオートコミットオフ
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e)
		{
			System.out.println("ドライバーが見つかりません");
		} catch (SQLException e)
		{
			System.out.println("データベース接続エラーです");
		}

	}

	/**
	 * @throws RollbackException
	 * @throws DBAccessException
	 * セレクト文用
	 * @return ResultSet
	 * @throws
	 * 	 */
	public ResultSet SelectSQL(String SQL) throws DBAccessException, RollbackException
	{
		try
		{
			PreparedStatement selectstatement = connection.prepareStatement(SQL);
			resultSet = selectstatement.executeQuery();
		} catch (SQLException e)
		{
			System.out.println("Preparestatementのデータベースアクセスエラー 多分SQLが違う");
			throw new DBAccessException();
		}

		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("コミットできませんでした。ロールバック処理に移行します");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
				System.out.println("ロールバックできないとかお手上げですわ");
				throw new RollbackException();
			}
		}
		return resultSet;
	}

	//TODO 確認ひっす
	/**
	 *
	 * インサート、アップデート、デリート用
	 * RETURN this.resultRow 結果行数を返す
	 * @throws RollbackException
	 */
	public int NonQuerySQL(String SQL) throws RollbackException
	{
		try {
			PreparedStatement NonQueryStmt = connection.prepareStatement(SQL);
		  this.resultRow = NonQueryStmt.executeUpdate(SQL);
			NonQueryStmt.close();
		}catch (SQLException e){
		  System.out.println("SQLException:" + e.getMessage());
		}

		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			System.out.println("コミットできず、ロールバック処理に移行します");
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
				System.out.println("ロールバックできないとかお手上げですわ");
				throw new RollbackException();
			}
		}

		return this.resultRow;
	}

	/**
	 *接続切断
	 * @throws SQLException
	 */
	public void Close() throws SQLException
	{
		connection.close();
	}

	/**
	 * テスト用データ
	 */
	public void debug()
	{
		System.out.println(url);
		System.out.println(driver);
		System.out.println(user);
		System.out.println(pass);
	}

	//TODO これ今イラン konngootameninokou
	//	//リザルトセットの列の型からget関数を選択する
	//	public Method choseGetResult(String typeName,String calamName) throws ChoseGetResultSecException
	//	{
	//		Method m = null;
	//
	//		try {
	//			if (typeName == "INT")
	//			{
	//				ResultSet.class.getDeclaredMethod("getInt", int.class);
	//			}
	//		} catch (SecurityException e) {
	//			// TODO 自動生成された catch ブロック
	//			e.printStackTrace();
	//			throw new ChoseGetResultSecException();
	//		} catch (NoSuchMethodException e) {
	//			// TODO 自動生成された catch ブロック
	//			e.printStackTrace();
	//		}
	//
	//		return m;
	//	}

}