package Common;

// TODO: Auto-generated Javadoc
/**
 * The Class Enums.
 */
public class Enums {

	/** The server port. */
	public final int serverPort = 5555;

	/** The Constant numberOfStages. */
	public final static int numberOfStages = 6;
	
	/** The Constant numberOfCommitteeMember. */
	public final static int numberOfCommitteeMember = 2;

	/**
	 * The Enum MessageEnum.
	 */
	public enum MessageEnum {
		
		/** The connect. */
		CONNECT, 
 /** The Search user. */
 SearchUser, 
 /** The Login fail. */
 LoginFail, 
 /** The login good. */
 loginGood, 
 /** The disconnect. */
 DISCONNECT, 
 /** The upload. */
 UPLOAD, 
 /** The uploadfinish. */
 UPLOADFINISH, 
 /** The getoblist. */
 GETOBLIST, 
 /** The Update stage. */
 UpdateStage,
		
		/** The Create request. */
		CreateRequest, 
 /** The New request ID. */
 NewRequestID, 
 /** The refreshman. */
 REFRESHMAN, 
 /** The Create report. */
 CreateReport, 
 /** The getuserfiles. */
 GETUSERFILES, 
 /** The stagesscreen. */
 STAGESSCREEN, 
 /** The Search report. */
 SearchReport, 
 /** The Update status. */
 UpdateStatus,
		
		/** The Freeze. */
		Freeze, 
 /** The Unfreeze. */
 Unfreeze, 
 /** The Update request details. */
 UpdateRequestDetails, 
 /** The down stage. */
 downStage, 
 /** The Get comitte. */
 GetComitte, 
 /** The Comitte list. */
 ComitteList, 
 /** The Appoint stage handlers. */
 AppointStageHandlers, 
 /** The addisuser. */
 ADDISUSER,
		
		/** The getisuser. */
		GETISUSER, 
 /** The updateisuser. */
 UPDATEISUSER, 
 /** The checksupervisorexist. */
 CHECKSUPERVISOREXIST, 
 /** The countcommiteemembers. */
 COUNTCOMMITEEMEMBERS, 
 /** The checkchairmanexist. */
 CHECKCHAIRMANEXIST, 
 /** The decline request. */
 declineRequest, 
 /** The log out. */
 logOut,
		
		/** The trying to log same time. */
		tryingToLogSameTime, 
 /** The editassesmenter. */
 EDITASSESMENTER, 
 /** The editexecutioner. */
 EDITEXECUTIONER, 
 /** The Tester rep. */
 TesterRep, 
 /** The setassesmentdate. */
 SETASSESMENTDATE, 
 /** The approveassextension. */
 APPROVEASSEXTENSION,
		
		/** The setexamdate. */
		SETEXAMDATE, 
 /** The setexecmdate. */
 SETEXECMDATE, 
 /** The settestdate. */
 SETTESTDATE, 
 /** The refreshcollege. */
 REFRESHCOLLEGE, 
 /** The refreshis. */
 REFRESHIS, 
 /** The edittester. */
 EDITTESTER, 
 /** The askforextension. */
 ASKFOREXTENSION,
		
		/** The Cannot update stage. */
		CannotUpdateStage, 
 /** The Un freeze rejected. */
 UnFreezeRejected, 
 /** The getmaxreqid. */
 GETMAXREQID, 
 /** The getfilefromserver. */
 GETFILEFROMSERVER, 
 /** The getallreports. */
 GETALLREPORTS, 
 /** The getreportslist. */
 GETREPORTSLIST,
		
		/** The getallrequests. */
		GETALLREQUESTS, 
 /** The getrequestslist. */
 GETREQUESTSLIST, 
 /** The getallstages. */
 GETALLSTAGES, 
 /** The getstageslist. */
 GETSTAGESLIST, 
 /** The getallusers. */
 GETALLUSERS, 
 /** The getuserslist. */
 GETUSERSLIST, 
 /** The Get extension freq. */
 GetExtensionFreq,
		
		/** The Get extension stat. */
		GetExtensionStat, 
 /** The Get delays stat. */
 GetDelaysStat, 
 /** The Get delays freq. */
 GetDelaysFreq, 
 /** The Get addons stat. */
 GetAddonsStat, 
 /** The Get addons freq. */
 GetAddonsFreq, 
 /** The getallmessages. */
 GETALLMESSAGES, 
 /** The getsupervisorlog. */
 GETSUPERVISORLOG,
		
		/** The getmessageslist. */
		GETMESSAGESLIST, 
 /** The getsupervisorloglist. */
 GETSUPERVISORLOGLIST, 
 /** The get period report. */
 getPeriodReport, 
 /** The Statistics. */
 Statistics, 
 /** The removeuser. */
 REMOVEUSER;
	}

	/**
	 * The Enum RequestStatus.
	 */
	public enum RequestStatus {
		
		/** The Active. */
		Active, 
 /** The Closed. */
 Closed, 
 /** The Frozen. */
 Frozen, 
 /** The Rejected. */
 Rejected, 
 /** The Rejected closed. */
 RejectedClosed;
		
		/**
		 * Gets the status by int.
		 *
		 * @param i the i
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
		 * @param i the i
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
	 * The Enum SystemENUM.
	 */
	public enum SystemENUM {
		
		/** The All. */
		All, 
 /** The Info station. */
 InfoStation, 
 /** The Moodle. */
 Moodle, 
 /** The Library. */
 Library, 
 /** The Computers. */
 Computers, 
 /** The Labs. */
 Labs, 
 /** The Site. */
 Site;
		
		/**
		 * Gets the system by int.
		 *
		 * @param i the i
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
		 * @param s the s
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
		 * @param i the i
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
	 * The Enum RequestStageENUM.
	 */
	public enum RequestStageENUM {
		
		/** The Initialization. */
		Initialization, 
 /** The Assesment. */
 Assesment, 
 /** The Examaning. */
 Examaning, 
 /** The Execution. */
 Execution, 
 /** The Testing. */
 Testing, 
 /** The Closing. */
 Closing;
		
		/**
		 * Gets the request stage ENUM.
		 *
		 * @param i the i
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
		 * Gets the request stage ENUM by enum.
		 *
		 * @param i the i
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
	 * The Enum Role.
	 */
	public enum Role {
		
		/** The College. */
		College, 
 /** The General IS. */
 GeneralIS, 
 /** The Committe chairman. */
 CommitteChairman, 
 /** The Committe member. */
 CommitteMember, 
 /** The Supervisor. */
 Supervisor, 
 /** The Manager. */
 Manager;
		
		/**
		 * Gets the role ENUM.
		 *
		 * @param i the i
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
		 * @param i the i
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
