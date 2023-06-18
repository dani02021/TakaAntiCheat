package bg.dani02.taka.anticheat.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import bg.dani02.taka.anticheat.Report;

public class PlayerReportEvent extends Event implements Cancellable {
	private boolean cancel;
	private Report rep;
	private ReportType report;
	
	private static final HandlerList hand = new HandlerList();

	public PlayerReportEvent(Report rep, ReportType value) {
		this.rep = rep;
		report = value;
	}

	public Report getReport() {
		return rep;
	}

	public ReportType getMode() {
		return report;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return hand;
	}

	public static HandlerList getHandlerList() {
		return hand;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancel = arg0;
	}
	
	public enum ReportType {
		ADD_PLAYER, REMOVE_PLAYER;
	}
}