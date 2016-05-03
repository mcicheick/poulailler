/**
 * 
 */
package jobs;

/**
 * @author sissoko
 *
 */
public class EveryMinuteJob extends Job<Void> {
	int count = 0;

	@Override
	public void doJob() throws Exception {
		System.out.printf("Je compte %d\n", ++count);
	}
}
