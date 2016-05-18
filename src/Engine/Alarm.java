package Engine;

public class Alarm {

	private static int nextCode = 1;

	private AlarmTask task;
	private double delay;
	private double next;
	private int alarmCode;
	private boolean isRunning;

	public Alarm(AlarmTask task, double delay) {
		this.task = task;
		this.delay = delay;
		next = (System.nanoTime() / Static.NS) + delay;
		isRunning = true;
		alarmCode = nextCode++;
	}

	public void update() {
		double now = System.nanoTime() / Static.NS;
		if (now >= next && isRunning) {
			next += delay;
			alarm();
		}
	}

	public void pause() {
		isRunning = false;
	}

	public void unpause(boolean resetDelay) {
		isRunning = true;
		if (resetDelay) {
			next = (System.nanoTime() / Static.NS) + delay;
		}
	}

	public void changeDelay(double newDelay) {
		next -= delay;
		delay = newDelay;
		next += delay;
	}

	public double getDelay() {
		return delay;
	}

	public void alarm() {
		task.alarm(alarmCode);
	}

	public int getCode() {
		return alarmCode;
	}

}
