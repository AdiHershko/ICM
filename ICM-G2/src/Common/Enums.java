package Common;

public class Enums {

	enum RequestStatus {
		Active, Closed, Frozen;
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

		public static int getRequestStatusByEnum(RequestStatus i) {
			switch (i) {
			case Active:
				return 0;
			case Closed:
				return 1;
			case Frozen:
				return 2;
			}
			return -1;
		}
	}

	enum SystemENUM {
		InfoStation, Moodle, Library, Computers, Labs, Site;
		public static SystemENUM getSystemByInt(int i) {
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
		
		public static int getSystemByEnum(SystemENUM i) {
			switch (i) {
			case InfoStation:
				return 0;
			case Moodle:
				return 1;
			case Library:
				return 2;
			case Computers:
				return 3;
			case Labs:
				return 4;
			case Site:
				return 5;
			}
			return -1;
		}
	}

	enum CollegeWorkerRole{
		Student, Lecturer, Worker;
		public static CollegeWorkerRole getRoleByInt(int i) {
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
		
		public static int getCollegeUserRoleByEnum(CollegeWorkerRole i) {
			switch (i) {
			case Student:
				return 0;
			case Lecturer:
				return 1;
			case Worker:
				return 2;
			}
			return -1;
		}
	}
	
	enum RequestStageENUM{
		Assesment, Examaning, Execution, Testing;
		public static RequestStageENUM getRequestStageENUM(int i) {
			switch (i) {
			case 0:
				return Assesment;
			case 1:
				return Examaning;
			case 2:
				return Execution;
			case 3:
				return Testing;
			}
			return null;
		}
		
		public static int getRequestStageENUMByEnum(RequestStageENUM i) {
			switch (i) {
			case Assesment:
				return 0;
			case Examaning:
				return 1;
			case Execution:
				return 2;
			case Testing:
				return 3;
			}
			return -1;
		}
	}
	
	enum ISWorkerRole{
		General, CommitteChairman, CommitteMember, Supervisor, Manager;
		public static ISWorkerRole getISWorkerRoleENUM(int i) {
			switch (i) {
			case 0:
				return General;
			case 1:
				return CommitteChairman;
			case 2:
				return CommitteMember;
			case 3:
				return Supervisor;
			case 4:
				return Manager;
			}
			return null;
		}
		
		public static int getISWorkerRoleByEnum(ISWorkerRole i) {
			switch (i) {
			case General:
				return 0;
			case CommitteChairman:
				return 1;
			case CommitteMember:
				return 2;
			case Supervisor:
				return 3;
			case Manager:
				return 4;
			}
			return -1;
		}
	}
}
