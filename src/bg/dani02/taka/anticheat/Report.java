package bg.dani02.taka.anticheat;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Report {
	private OfflinePlayer who;
	private OfflinePlayer reported;
	private String report;

	public Report(OfflinePlayer who, OfflinePlayer reported, String report) {
		this.who = who;
		this.reported = reported;
		this.report = report;
	}

	public void setPlayer(Player who) {
		this.who = who;
	}

	public OfflinePlayer getPlayer() {
		return who;
	}

	public void setReported(Player reported) {
		this.reported = reported;
	}

	public OfflinePlayer getReported() {
		return reported;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getReport() {
		return report;
	}

	@Override
	public String toString() {
		return reported.toString() + " reported " + who.toString() + "for: " + report;
	}
}
