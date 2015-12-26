package de.netzmuffel.bank;

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import de.netzmuffel.bank.controller.CtrlStart;
import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Shutdown;

public class Bootstrap {

	/** The db. */
	static Database db = null;

	/** The t test. */
	private static boolean tTest = false;

	public void execute(String[] args) {
		setUIManager();

		PropertiesHelper.checkPropertiesInFile();
		Locale.setDefault(PropertiesHelper.getLanguage());

		this.openConnectionToDatabase();
		Update.execute();

		this.checkCmdArguments(args);

		if (tTest) {
			this.createTestDatabase();
		}

		Config.logger.info("{} starts now the first controller.", Config.getDatabaseName());
		new CtrlStart(null, db);
	}

	/**
	 * Sets the ui manager.
	 */
	private static void setUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Error.APPLICATION_UIMANAGER_CANT_SET.showDialog(e);
		}
	}

	/**
	 * Open the connection to the "database.db"-File
	 */
	private void openConnectionToDatabase() {
		if (PropertiesHelper.isEncryptionEnabled()) {
			boolean bExit = true;
			do {
				try {
					bExit = true;
					String pwd = JOptionPane.showInputDialog(null, I18n.get().inputYourPassword());

					if (pwd != null && !pwd.isEmpty()) {
						db = new Database(pwd);
					} else {
						Config.shutdown(Shutdown.Okay, db);
					}
				} catch (SQLException e) {
					Error.APPLICATION_DATABASE_CONNECT_EXISTING_DATABASE.showDialog(e);
					bExit = false;
				}
			} while (!bExit);
		} else {
			try {
				db = Database.createNewDatabaseConnection();
			} catch (SQLException e) {
				Error.APPLICATION_DATABASE_CONNECT_NEW_DATABASE.showDialog(e);
				Config.shutdown(Shutdown.Error, db);
			}
		}
	}

	/**
	 * Add the testDatas to the Database.
	 */
	private void createTestDatabase() {
		try {
			this.moveOldDatabase();

			Config.logger.info("{} creates the test database.", Config.getDatabaseName());
			testDataCreator tdc = new testDataCreator();
			tdc.exec(db);
		} catch (SQLException e) {
			Error.APPLICATION_DATABASE_CONNECT_TESTDATABASE.showDialog();
			e.printStackTrace();
			Config.shutdown(Shutdown.Error, db);
		}
	}

	/**
	 * Check, if you have to add testdatas Check bTest for the "t" Argument.
	 *
	 * @param args
	 *            the CmdArguments
	 */
	private void checkCmdArguments(String[] args) {
		for (String string : args) {
			if (string.equals("t")) {
				Config.logger.info("{} starts with the argument t to create a test database.",
						Config.getDatabaseName());
				tTest = true;
			}
		}
	}

	/**
	 * if you have to add testDatas, than create a new database, but before you
	 * have to move the old Database (because it could be a failure or sth!
	 * Never delete customer datas!).
	 *
	 * @throws SQLException
	 */
	private void moveOldDatabase() throws SQLException {
		db.shutdown();

		File f = new File(Config.getDatabaseFilePath());
		if (f.exists()) {
			String strg = ".copy";
			while (!f.renameTo(new File(f.getAbsolutePath() + strg))) {
				strg += strg;
			}
			Config.logger.info("{} finds an old database and moved it to {}.", Config.getDatabaseName(),
					f.getName() + strg);
		}

		db = new Database();
	}

}
