package Common;



public class Enums {

	
	enum RequestStatus{
		Active,Closed,Frozen;
		public static RequestStatus getStatusByInt(int i) {
			switch (i) {
			case 0:
				return Active;
			case 1:
				return Closed;
			case 2:
				return Frozen;
			}
		return null;
		}
	}
	
	enum Systems{
		InfoStation, Moodle, Library, Computers, Labs, Site;
		public static Systems getSystemByInt(int i) {
			switch (i) {
			case 0:
				return InfoStation;
			case 1:
				return Moodle;
			case 2:
				return Library;
			case 3:
				return Computers;
			case 4:
				return Labs;
			case 5:
				return Site;
			}
			return null;
		}
	}
	
	enum UserRoles{
		Student,Lecturer,Worker;
		public static UserRoles getRoleByInt(int i) {
			switch (i) {
			case 0:
				return Student;
			case 1:
				return Lecturer;
			case 2:
				return Worker;
			}
			return null;
		}
	}
}
