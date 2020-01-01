package Common;

public class Enums {

	public final int serverPort = 5555;

	public final static int numberOfStages = 6;

	public enum MessageEnum {
		CONNECT, SearchUser, LoginFail, loginGood, DISCONNECT, UPLOAD, UPLOADFINISH, GETOBLIST,
		UpdateStage, CreateRequest, NewRequestID, REFRESHMAN, CreateReport, GETUSERFILES, STAGESSCREEN, SearchReport,
		UpdateStatus, Freeze, Unfreeze, UpdateRequestDetails, downStage, GetComitte, ComitteList, AppointStageHandlers,
		ADDISUSER, GETISUSER, UPDATEISUSER, CHECKSUPERVISOREXIST, COUNTCOMMITEEMEMBERS, CHECKCHAIRMANEXIST,
		declineRequest, logOut, tryingToLogSameTime,EDITASSESMENTER,EDITEXECUTIONER, TesterRep,SETASSESMENTDATE,APPROVEASSEXTENSION,
		SETEXAMDATE,SETEXECMDATE,SETTESTDATE,REFRESHCOLLEGE,REFRESHIS,EDITTESTER,ASKFOREXTENSION;


	}

	public enum RequestStatus {
		Active, Closed, Frozen, Rejected, RejectedClosed;
		public static RequestStatus getStatusByInt(int i) {
			switch (i) {
			case 0:
				return Active;
			case 1:
				return Closed;
			case 2:
				return Frozen;
			case 3:
				return Rejected;
			case 4:
				return RejectedClosed;
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
			case Rejected:
				return 3;
			case RejectedClosed:
				return 4;
			}
			return -1;
		}
	}

	public enum SystemENUM {
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

	public enum RequestStageENUM {
		Initialization, Assesment, Examaning, Execution, Testing, Closing;
		public static RequestStageENUM getRequestStageENUM(int i) {
			switch (i) {
			case 0:
				return Initialization;
			case 1:
				return Assesment;
			case 2:
				return Examaning;
			case 3:
				return Execution;
			case 4:
				return Testing;
			case 5:
				return Closing;
			}
			return null;
		}

		public static int getRequestStageENUMByEnum(RequestStageENUM i) {
			switch (i) {
			case Initialization:
				return 0;
			case Assesment:
				return 1;
			case Examaning:
				return 2;
			case Execution:
				return 3;
			case Testing:
				return 4;
			case Closing:
				return 5;
			}
			return -1;
		}
	}

	public enum Role {
		College, GeneralIS, CommitteChairman, CommitteMember, Supervisor, Manager;
		public static Role getRoleENUM(int i) {
			switch (i) {
			case 0:
				return College;
			case 1:
				return GeneralIS;
			case 2:
				return CommitteChairman;
			case 3:
				return CommitteMember;
			case 4:
				return Supervisor;
			case 5:
				return Manager;
			}
			return null;
		}

		public static int getRoleByEnum(Role i) {
			switch (i) {
			case College:
				return 0;
			case GeneralIS:
				return 1;
			case CommitteChairman:
				return 2;
			case CommitteMember:
				return 3;
			case Supervisor:
				return 4;
			case Manager:
				return 5;
			}
			return -1;
		}
	}

}
