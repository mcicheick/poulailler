/**
 * 
 */
package jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sissoko
 *
 */
public class JobsPlugin {
	
	public static ScheduledThreadPoolExecutor executor = null;
    public static List<Job> scheduledJobs = null;
	
	static {
		scheduledJobs = new ArrayList<>();
		onApplicationStart();
	}

	public static void onApplicationStart() {
		int core = 10;
		executor = new ScheduledThreadPoolExecutor(core, new PThreadFactory(
				"jobs"), new ThreadPoolExecutor.AbortPolicy());
	}
	
	

}
