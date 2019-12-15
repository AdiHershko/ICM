package Common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private int type;

	enum SystemEnum {
		SQLCMD;
		public static SystemEnum getByInt(int i) {
			switch (i) {
			case 0:
				return SQLCMD;
			}
			return null;
		}
	}
}
