package de.netzmuffel.bank.localization;

/**
 * The Interface AppMessages.
 */
public interface AppMessages {

	String student();

	String students();

	String course();

	String courses();

	String studentCount();

	String courseType();

	String subject();

	String middleSchool();

	String highSchool();

	String gradeBank();

	String titleText();

	String menuImportData();

	String menuExportData();

	String menuDeleteData();

	String menuExit();

	String menuSettings();

	String menuProperties();

	String menuCloudSettings();

	String menuFile();

	String showClass();

	String createClass();

	String editClass();

	String deleteClass();

	String showStudent();

	String createStudent();

	String editStudent();

	String deleteStudent();

	String closeCourse();

	String courseList();

	String errorCourseAlreadyOpened();

	String errorConnectionToDatabase();

	String errorCreateTestData();

	String componentButtonOk();

	String componentButtonApply();

	String componentButtonCancel();

	String german();

	String english();

	String settingsLanguage();

	String infoHaveToRestart();

	String infoHaveToRestartTitle();

	String errorNoSelectionOnTable(String s);

	String name();

	String forename();

	String telephone();

	String mail();

	String confirmation();

	String confirmRemoveStudentFromCourse(String name, String forename);

	String addStudent();

	String errorNoStudentAvailable();

	String selectStudent();

	String showGrades();

	String confirmRemoveCourse(String course, String teacher, String string);

	String coursename();

	String menuManage();

	String teacher();

	String menuTeacherManage();

	String errorNoTextInField(String course);

	String errorNameAlreadyExists(String coursename);

	String sizeCourses();

	String componentButtonClose();

	String teacherRemove();

	String teacherEdit();

	String teacherCreate();

	String confirmRemoveTeacher(String name, String forename);

	String gradeCreate();

	String gradeEdit();

	String gradeRemove();

	String showStudentName(String name, String forename);

	String studentNotEvaluate();

	String grade();

	String topicEdit();

	String topicCreate();

	String topics();

	String studentPrint(String name, String forename);

	String integer();

	String errorNoValidValue(String integer);

	String topicRemove();

	String singleStudentGradeEdit();

	String errorQuerySelectionOnTable();

	String noTeacherFound();

	String titleTeacherNotFound();

	String updateTitle();

	String updateRequired();

	String menuAboutProgram();

	String menuAboutAuthor();

	String menuHelp();

	String menuStudentManage();

	String studentCreate();

	String studentEdit();

	String studentRemove();

	String topic();

	String topicName();

	String version();

	String webVersion();

	String pleaseUpdate();

	String updateNotAvailable();

	String btnUpdate();

	String inputYourPassword();

	String settingsEncryptEnable();

	String settingsEncryptEnableCheckBox();

	String inputYourNewPassword();

	String infoHaveToRestartImmeditialy();

	String errorWrongPasswordDatabase();

	String topicSelect();

	String confirmRemoveTopic(String name);

	String errorInQuery();

	String errorOccured(int code, String string);

	String gradeNotValued();

	String error();

	String confirmDataDeletion();

	String ConfirmAllDeleted();

	String confirmOpenTeacherAdd();
}
