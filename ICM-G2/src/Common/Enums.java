package Common;

/**
 * The Class Enums.
 * Here we state the defines and the enums we use in our system.
 */
public class Enums {

	/** The server port. */
	public final int serverPort = 5555;

	/** The Constant numberOfStages. */
	public final static int numberOfStages = 6;

	/** The Constant numberOfCommitteeMember. */
	public final static int numberOfCommitteeMember = 2;

	/**
	 * The Message Enum.
	 */
	public enum MessageEnum {

		CONNECT, SearchUser, LoginFail, loginGood, DISCONNECT, UPLOAD, UPLOADFINISH, GETOBLIST, UpdateStage,
		CreateRequest, NewRequestID, REFRESHMAN, CreateReport, GETUSERFILES, STAGESSCREEN, SearchReport, UpdateStatus,
		Freeze, Unfreeze, UpdateRequestDetails, downStage, GetComitte, ComitteList, AppointStageHandlers, ADDISUSER,
		GETISUSER, UPDATEISUSER, CHECKSUPERVISOREXIST, COUNTCOMMITEEMEMBERS, CHECKCHAIRMANEXIST, declineRequest, logOut,
		tryingToLogSameTime, EDITASSESMENTER, EDITEXECUTIONER, TesterRep, SETASSESMENTDATE, APPROVEASSEXTENSION,
		SETEXAMDATE, SETEXECMDATE, SETTESTDATE, REFRESHCOLLEGE, REFRESHIS, EDITTESTER, ASKFOREXTENSION,
		CannotUpdateStage, UnFreezeRejected, GETMAXREQID, GETFILEFROMSERVER, GETALLREPORTS, GETREPORTSLIST,
		GETALLREQUESTS, GETREQUESTSLIST, GETALLSTAGES, GETSTAGESLIST, GETALLUSERS, GETUSERSLIST, GetExtensionFreq,
		GetExtensionStat, GetDelaysStat, GetDelaysFreq, GetAddonsStat, GetAddonsFreq, GETALLMESSAGES, GETSUPERVISORLOG,
		GETMESSAGESLIST, GETSUPERVISORLOGLIST, getPeriodReport, Statistics, REMOVEUSER,SHOWFILES, SETASSESMENTDATESUP, SETEXECMDATESUP
		,GETREPORTHISTORY,LOADREPORTHISTORY;
	}

	/**
	 * The RequestStatus enum.
	 */
	public enum RequestStatus {

		Active, Closed, Frozen, Rejected, RejectedClosed;

		/**
		 * Gets the status by int.
		 *
		 * @param i the number
		 * @return the status by int
		 */
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

		/**
		 * Gets the request status by enum.
		 *
		 * @param i the request status enum
		 * @return the request status by enum
		 */
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

	/**
	 * The System ENUM.
	 */
	public enum SystemENUM {

		All, InfoStation, Moodle, Library, Computers, Labs, Site;

		/**
		 * Gets the system by int.
		 *
		 * @param i the number
		 * @return the system by int
		 */
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

		/**
		 * Gets the system by string.
		 *
		 * @param s the string
		 * @return the system by string
		 */
		public static SystemENUM getSystemByString(String s) {
			switch (s) {
			case "InfoStation":
				return InfoStation;
			case "Moodle":
				return Moodle;
			case "Library":
				return Library;
			case "Computers":
				return Computers;
			case "Labs":
				return Labs;
			case "Site":
				return Site;
			}
			return null;
		}

		/**
		 * Gets the system by enum.
		 *
		 * @param i the system enum
		 * @return the system by enum
		 */
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
			default:
				return -1;
			}
		}
	}

	/**
	 * The Request Stage ENUM.
	 */
	public enum RequestStageENUM {

		Initialization, Assesment, Examaning, Execution, Testing, Closing;

		/**
		 * Gets the request stage ENUM.
		 *
		 * @param i the number
		 * @return the request stage ENUM
		 */
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

		/**
		 * Gets the request stage int by enum.
		 *
		 * @param i the stage enum
		 * @return the request stage ENUM by enum
		 */
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

	/**
	 * The Role enum.
	 */
	public enum Role {

		College, GeneralIS, CommitteChairman, CommitteMember, Supervisor, Manager;

		/**
		 * Gets the role ENUM.
		 *
		 * @param i the int
		 * @return the role ENUM
		 */
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

		/**
		 * Gets the role by enum.
		 *
		 * @param i the enum
		 * @return the role by enum
		 */
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
