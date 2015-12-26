package de.netzmuffel.bank;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JOptionPane;

import de.netzmuffel.bank.localization.I18n;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Shutdown;

public final class Update {
	public Update() {
	}

	public static void execute() {
		Config.logger.info("Working folder {}", new File("./").getAbsolutePath());

		if (Config.getLatestVersion() > Config.getLocalVersion()) {
			Config.logger.debug("update found");
			if (JOptionPane.showConfirmDialog(null, I18n.get().updateRequired(), I18n.get().updateTitle(),
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				File f = new File(Config.UPDATE_PROGRAM_FILE);
				if (!f.exists()) {
					try {
						downloadFile(new URL(Config.SERVER_UPDATER_FILE), "./");
					} catch (IOException e) {
						Error.UPDATE_CANNOT_DOWNLOAD_UPDATER.showDialog(e);
					}
				}
				forceExec();
			}
		}
	}

	public static void forceExec() {
		Runtime rt = Runtime.getRuntime();
		Config.logger.debug("update program loading");

		try {
			rt.exec("java -jar GradeBank-Update.jar", null, new File("./"));
		} catch (IOException e) {
			Error.UPDATE_CANNOT_START_UPDATER.showDialog(e);
		}
		Config.logger.debug("update executed");
		Config.shutdown(Shutdown.Restart, null);
	}

	public static void downloadFile(URL fileURL, String saveDir) throws IOException {
		System.out.println("Start download, Downloadfile: " + fileURL.getFile());

		// Code to download
		InputStream in = new BufferedInputStream(fileURL.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in.read(buf))) {
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();

		String fileName = fileURL.getPath();
		fileName = fileName.substring(fileName.lastIndexOf('/') + 1);

		File f = new File(saveDir);
		if (!f.exists()) {
			f.mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(saveDir + fileName);
		fos.write(response);
		fos.close();
		// End download code
		System.out.println("Done");
	}
}