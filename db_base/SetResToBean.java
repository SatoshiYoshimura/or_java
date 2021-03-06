package db_base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import calam.BaseBeans;
import calam.CalamManager;
// extends BaseBeans
/**
 * ジェネリック型でなんかしたいとき
 * @author OWNER
 *
 * @param <T> 指定したいクラス
 */
public class SetResToBean<T extends BaseBeans> {

	public SetResToBean() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 *　インスタンス化するときに指定した型の
	 *　ArrayListにresultの中身を詰める
	 * @param result DBから取得した、resultset
	 * @param clazz 中身を詰めたいBeans
	 * @return BeansのつまったArraylist
	 */
	@SuppressWarnings("unchecked")
	public List<T> set(ResultSet result, Class<T> clazz)
	{
		//びーんんつめるアレイリスト
		ArrayList<T> list = new ArrayList<T>();
		//び－ンを扱うクラス
		CalamManager cm = new CalamManager();
		//データベースを扱う必須クラス
//		DataAccess bdb = new DataAccess();

		//リザルトメタ
		ResultSetMetaData rsmd = null;
		try {
			rsmd = result.getMetaData();
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
			System.out.println("SetResToBeanのgetMatadataError");
		}
		//DBカラム名
		String fName = null;
		//カラム型名
		String typeName = "";
		//bean フィールド名
		Field pFileld = null;
		//ビーン
		T bean = null;

		// 結果行をループ
		try {
			while (result.next())
			{
				//インスタンス化
				try {
					bean = clazz.newInstance();
				} catch (InstantiationException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

				//列の数だけ列名を取得
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++)
				{
					//DBフィールド名取得
					fName = rsmd.getColumnName(i);
					//ビーンのフィールド取得
					pFileld = cm.GetBeanField(fName,bean);
					//DB型名取得
					typeName = rsmd.getColumnTypeName(i);

					//型ごとの取得関数でデータ取得
					if (typeName == "INT")
					{
						//TODO intだけじゃなくOracleの型にも
						bean = (T) this.setValuetoBean(bean, pFileld, result.getInt(fName));
					}
					if(typeName == "NUMBER")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getInt(fName));
					}
					if (typeName == "VARCHAR")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getString(fName));
					}
					if(typeName == "VARCHAR2")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getString(fName));
					}
					if(typeName == "DATE")
					{
						bean = (T) this.setValuetoBean(bean, pFileld, result.getDate(fName));
					}


				}
				//リストに追加
				list.add(bean);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, int value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる
			//TODO テスト消す
			System.out.println(o);
			System.out.println(field);
			System.out.println(value);
			cm.getBeanSetter(o, field, int.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, String value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる
			cm.getBeanSetter(o, field, String.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

	/**
	 * Beansの指定したフィールドに値を突っ込み、突っ込んだ後のBeansを返す
	 * @param o Beans
	 * @param field BeansField
	 * @param value int
	 * @return Beans
	 */
	public Object setValuetoBean(Object o, Field field, Date value)
	{
		CalamManager cm = new CalamManager();
		try {
			//Beansに値を入れる
			//setメソッドを取得して値を入れる
			cm.getBeanSetter(o, field, Date.class).invoke(o, value);
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そもそものメソッドが例外してるで");
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean なんかおかしいで");
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("DataAccess.setValuetoBean そのメソッドにはアクセスできない");
		}
		return o;
	}

}
