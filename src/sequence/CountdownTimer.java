package sequence;


public class CountdownTimer {
	private long lastUsed = System.currentTimeMillis();
	private int getCooldown;
	private HotKeyRunner manager;

	public CountdownTimer(HotKeyRunner manager) {
		// TODO Auto-generated constructor stub
		this.manager=manager;
		this.getCooldown=manager.getMacrosStorage().mouseCooldown;

	}

	public void use(int keyCode,int type) {
		if (System.currentTimeMillis() - lastUsed >= getCooldown || getCooldown <= 0) {
			manager.startofEvent(keyCode,type);
			manager.endofEvent(keyCode,type);

		}
		lastUsed = System.currentTimeMillis();
	}
	
}
