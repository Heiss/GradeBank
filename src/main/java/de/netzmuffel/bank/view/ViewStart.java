package de.netzmuffel.bank.view;

import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.structure.AbstractModelView;
import de.netzmuffel.bank.tabbedpanel.ViewTabCourses;
import de.netzmuffel.bank.tabbedpanel.ViewTabStudentsFromCourse;

import javax.swing.JSeparator;

/**
 * The Class ViewStart create the main formular.
 */
public class ViewStart extends AbstractModelView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jpnl_layout. */
	public JPanel jpnl_layout;

	/** The menu bar. */
	public JMenuBar menuBar;

	/** The mn file. */
	public JMenu mnFile;

	/** The progress bar. */
	public JProgressBar progressBar;

	/** The tabbed pane. */
	public JTabbedPane tabbedPane;

	/** The mn settings. */
	public JMenu mnSettings;

	/** The mntm close. */
	public JMenuItem mntmClose;

	/** The mntm properties. */
	public JMenuItem mntmGeneralSettings;

	/** The mn questionmark. */
	public JMenu mnHelp;

	/** The jpnltab_classes. */
	public ViewTabCourses jpnltab_classes;

	/** The jpnltab_class. */
	public ViewTabStudentsFromCourse jpnltab_class;
	
	/** The mntm delete datas. */
	public JMenuItem mntmDeleteDatas;
	
	/** The mntm export datas. */
	public JMenuItem mntmExportDatas;
	
	/** The separator. */
	private JSeparator separator;
	
	/** The mntm import datas. */
	public JMenuItem mntmImportDatas;
	
	/** The mntm cloud settings. */
	public JMenuItem mntmCloudSettings;
	
	/** The mn manage. */
	public JMenu mnManage;
	
	/** The mntm teacher manage. */
	public JMenuItem mntmTeacherManage;
	public JMenuItem mntmAboutProgram;
	public JMenuItem mntmAboutAuthor;
	public JMenuItem mntmStudentManage;

	/**
	 * Instantiates a new view start.
	 *
	 * @param db the db
	 */
	public ViewStart(Database db) {
		super();
		this.setTitle(I18n.get().gradeBank() + " - " + I18n.get().titleText());
		this.setSize(800, 400);
		this.setResizable(true);

		jpnl_layout = new JPanel();
		this.getContentPane().add(jpnl_layout);
		jpnl_layout.setLayout(new BorderLayout(0, 0));

		menuBar = new JMenuBar();
		jpnl_layout.add(menuBar, BorderLayout.NORTH);

		mnFile = new JMenu(I18n.get().menuFile());
		menuBar.add(mnFile);

		mntmImportDatas = new JMenuItem(I18n.get().menuImportData());
		mnFile.add(mntmImportDatas);

		mntmExportDatas = new JMenuItem(I18n.get().menuExportData());
		mnFile.add(mntmExportDatas);

		mntmDeleteDatas = new JMenuItem(I18n.get().menuDeleteData());
		mnFile.add(mntmDeleteDatas);

		separator = new JSeparator();
		mnFile.add(separator);

		mntmClose = new JMenuItem(I18n.get().menuExit());
		mnFile.add(mntmClose);

		mnManage = new JMenu(I18n.get().menuManage());
		menuBar.add(mnManage);

		mntmTeacherManage = new JMenuItem(I18n.get().menuTeacherManage());
		mnManage.add(mntmTeacherManage);
		
		mntmStudentManage = new JMenuItem(I18n.get().menuStudentManage());
		mnManage.add(mntmStudentManage);

		mnSettings = new JMenu(I18n.get().menuSettings());
		menuBar.add(mnSettings);

		mntmGeneralSettings = new JMenuItem(I18n.get().menuProperties());
		mnSettings.add(mntmGeneralSettings);

		mntmCloudSettings = new JMenuItem(I18n.get().menuCloudSettings());
		mnSettings.add(mntmCloudSettings);

		mnHelp = new JMenu(I18n.get().menuHelp());
		menuBar.add(mnHelp);
		
		mntmAboutProgram = new JMenuItem(I18n.get().menuAboutProgram());
		mnHelp.add(mntmAboutProgram);
		
		mntmAboutAuthor = new JMenuItem(I18n.get().menuAboutAuthor());
		mnHelp.add(mntmAboutAuthor);

		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		jpnl_layout.add(progressBar, BorderLayout.SOUTH);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		jpnl_layout.add(tabbedPane, BorderLayout.CENTER);

		jpnltab_classes = new ViewTabCourses(db);
		tabbedPane.addTab(I18n.get().courseList(), null, jpnltab_classes, null);
	}
}
