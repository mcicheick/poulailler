/**
 * 
 */
package jobs;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import tools.Time;

/**
 * @author sissoko
 *
 */
public class Job<V> implements Runnable, Callable<V> {

	public static final String invocationType = "Job";

	protected ExecutorService executor;
	protected long lastRun = 0;
	protected boolean wasError = false;
	protected Throwable lastException = null;

	Date nextPlannedExecution = null;
	
	public Job() {
		JobsPlugin.scheduledJobs.add(this);
	}

	/**
	 * Here you do the job
	 */
	public void doJob() throws Exception {
	}

	/**
	 * Here you do the job and return a result
	 */
	public V doJobWithResult() throws Exception {
		doJob();
		return null;
	}

	/**
	 * Run this job every n seconds
	 */
	public void every(String delay) {
		every(Time.parseDuration(delay));
	}

	/**
	 * Run this job every n seconds
	 */
	public void every(int seconds) {
		JobsPlugin.executor.scheduleWithFixedDelay(this, seconds, seconds,
				TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		call();
	}

	@Override
	public V call() {
		V result = null;
		try {
			result = doJobWithResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
