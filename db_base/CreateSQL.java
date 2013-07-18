package db_base;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import calam.CalamName;
import calam.TableList;

/**
 * 簡単にSQL作成できる神クラス
 * @author OWNER
 *
 */
public class CreateSQL {

	/**
	 * SQL生成用の変数
	 */
	private String SelectPart;
	private boolean SelectFlg;
	private String FromPart;
	private boolean FromFlg;
	private String InsertPart;
	private boolean InsertFlg;
	private String ValuesPart;
	private boolean ValuesFlg;
	private String UpdatePart;
	private boolean UpdateFlg;
	private String DeletePart;
	private boolean DeleteFlg;
	//UpdateのUXをいいものにするためにListを用意
	//カラム名用
	List<CalamName> calamList;
	//setで指定する値用
	List<Object> setValueList;
	private String WherePart;
	private boolean WhereFlg;

	//TODO Whereは用途別？

	/**
	 * コンストラクタ
	 * 各種プロパティを初期化
	 */
	public CreateSQL()
	{
		SelectPart = "";
		SelectFlg = false;
		FromPart = "";
		FromFlg = false;
		InsertPart = "";
		InsertFlg = false;
		ValuesPart = "";
		ValuesFlg = false;
		UpdatePart = "";
		UpdateFlg = false;
		WherePart = "";
		WhereFlg = false;
		DeletePart = "";
		DeleteFlg = false;
	}

	/**
	 *	セレクトと取得する要素指定
	 * @param str
	 * @return
	 */
	public CreateSQL Select(String... str)
	{
		int count = 0;
		SelectPart = "SELECT ";
		//最後は,付けない
		for (String s : str)
		{
			SelectPart += s;
			count++;
			if (count != str.length)
			{
				SelectPart += ",";
			}
		}
		SelectFlg = true;

		return this;
	}

	/**
	 *	セレクトと取得する要素指定
	 * @param str
	 * @return
	 */
	public CreateSQL Select(CalamName... clum)
	{
		int count = 0;
		SelectPart = "SELECT ";
		//最後は,付けない
		for (CalamName c : clum)
		{
			SelectPart += c;
			count++;
			if (count != clum.length)
			{
				SelectPart += ",";
			}
		}
		SelectFlg = true;

		return this;
	}

	/**
	 * フロム要素を指定する為のメソッド
	 * @param c
	 * @return
	 */
	public CreateSQL From(TableList c)
	{
		FromPart = " FROM ";
		FromPart += c;
		FromFlg = true;
		return this;
	}

	/**
	 * フロム要素を指定する為のメソッド
	 * @param c
	 * @return
	 */
	public CreateSQL From(String s)
	{
		FromPart = " FROM ";
		FromPart += s;
		FromFlg = true;
		return this;
	}

	//TODO ここ演算子の引数も演算子そのまま渡したい

	/**
	 * WHERE句の部分　指定したカラム名と指定した値を指定した演算で条件付け
	 * @param culm　CalamName.判定するカラム名
	 * @param operater　条件の演算子
	 * @param value 条件の値
	 * @return this
	 */
	public CreateSQL Where(CalamName culm,String operater,Object value)
	{
		WherePart += " WHERE ";
		WherePart += culm;
		WherePart += " " + operater + " ";
		//判断用
		Class<? extends Object> C = value.getClass();
		String s = "";
		Date d = new Date(0);
		//値がString型もしくはデート型の場合
		if(C == s.getClass() || C == d.getClass() )
		{
			WherePart += "'" + value + "'";
		}
		else
		{
			WherePart += value;
		}

		WhereFlg = true;

		return this;
	}

	/**
	 * インサート文のやつ　入れたいテーブルと入れるカラム名を指定
	 * @param t TableList.テーブル名
	 * @param clum テーブル名Colums.カラム名
	 * @return　this
	 */
	public CreateSQL InsertInto(TableList t, CalamName... clum)
	{
		InsertPart = "INSERT into " + t + "( ";
		int count = 0;
		//最後は,付けない
		for (CalamName c : clum)
		{
			InsertPart += c;
			count++;
			if (count != clum.length)
			{
				InsertPart += ",";
			}
		}
		InsertPart += " )";
		InsertFlg = true;
		return this;
	}

	/**
	 * インサート文のやつ　入れたいテーブルと入れるカラム名を指定 String版
	 * @param t String テーブル名
	 * @param clum String カラム名
	 * @return　this
	 */
	public CreateSQL InsertInto(String t, String... clum)
	{
		InsertPart = "INSERT into " + t + "( ";
		int count = 0;
		//最後は,付けない
		for (String c : clum)
		{
			InsertPart += c;
			count++;
			if (count != clum.length)
			{
				InsertPart += ",";
			}
		}
		InsertPart += " )";
		InsertFlg = true;
		return this;
	}

	/**
	 * Values部分 指定した値を入れる
	 * @param o 入れたい値
	 * @return　this
	 */
	public CreateSQL Values(Object... o)
	{
		int count = 0;
		ValuesPart += "VALUES ( ";
		//型ごとに処理を分ける
		for (Object obj : o)
		{
			if (obj instanceof Integer)
			{
				this.ValuesPart += String.format("%d ", obj);
			}
			if (obj instanceof String)
			{
				this.ValuesPart += "'" + obj + "'";
			}
			if (obj instanceof Date)
			{
				this.ValuesPart += "'" + obj + "'";
			}

			//最後は,付けないで)つける
			count++;
			if (count != o.length)
			{
				ValuesPart += ",";
			}
			else
			{
				ValuesPart += ")";
			}
		}

		ValuesFlg = true;
		return this;
	}

	/**
	 *　アップデート文使うとき用のメソッド　ここだけSQLライクじゃなくて申し訳ない
	 * @param tableName TableName.テーブル名
	 * @param clum CalamName.セットしたいカラム名
	 * @return this
	 */
	public CreateSQL Update(TableList tableName, CalamName... clum)
	{
		this.UpdatePart += "UPDATE " + tableName + " SET ";
		calamList = new ArrayList<CalamName>();
		for (CalamName c : clum)
		{
			calamList.add(c);
		}
		UpdateFlg = true;
		return this;
	}

	/**
	 * Updateの後のＳｅｔ で使う用　入れたい値を順番通りに入れていく
	 * @param value
	 * @return
	 */
	public CreateSQL Set(Object... value)
	{
		setValueList = new ArrayList<Object>();
		for (Object o : value)
		{
			setValueList.add(o);
		}

		return this;
	}

	/**
	 * デリート文用
	 * @return
	 */
	public CreateSQL Delete()
	{
		DeletePart += "DELETE";
		DeleteFlg = true;
		return this;
	}

	/**
	 * 自信の持つすべてのパートのフラグをふぉるすにする
	 */
	private void AllflgFalse()
	{
		this.SelectFlg = false;
		this.FromFlg = false;
		this.InsertFlg = false;
		this.UpdateFlg = false;
		this.WhereFlg = false;
		this.DeleteFlg = false;
	}

	/**
	 * 自信の持つすべてのパートの文字列を空にする
	 */
	private void AllPartFormat()
	{
		this.SelectPart = "";
		this.FromPart = "";
		this.InsertPart = "";
		this.ValuesPart = "";
		this.UpdatePart = "";
		this.WherePart = "";
		this.DeletePart = "";
	}

	/**
	 * 最終な一つのSQLにする
	 * @return String SQL
	 */
	public String GenerateAlltoString()
	{
		//TODO ここ命令ごとに絶対ない組み合わせとか考えなあかん
		//各パートに入れた文を組み合わせる
		String SQL = "";
		if (SelectFlg)
		{
			SQL += SelectPart;
		}
		if(DeleteFlg)
		{
			SQL += DeletePart;
		}

		if (FromFlg)
		{
			SQL += FromPart;
		}

		if (InsertFlg)
		{
			SQL += InsertPart;
		}

		if (ValuesFlg)
		{
			SQL += ValuesPart;
		}

		//Updateのとこ　Updateとセットまとめる
		int count = 0;
		if (UpdateFlg)
		{

			for (CalamName c : calamList)
			{
				UpdatePart += c + " = ";

				//判断用
				Class C = setValueList.get(count).getClass();
				String s = "";
				Date d = new Date(0);
				//値がString型もしくはデート型の場合
				if (C == s.getClass() || C == d.getClass())
				{
					UpdatePart += "'" + setValueList.get(count) + "'";
				}
				else
				{
					UpdatePart += setValueList.get(count);
				}
				if (count != calamList.size() - 1)
				{
					UpdatePart += ",";
				}
				count++;
			}
			SQL += UpdatePart;
		}

		//Where
		if(WhereFlg)
		{
			SQL += WherePart;
		}

		AllflgFalse();
		AllPartFormat();

		return SQL;
	}

}
