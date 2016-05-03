package models;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 07/03/2016 09:01:05.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_REMEMBERS")
public class Remember extends Model {

	@ManyToOne
	@JoinColumn(name = "BANDE_ID")
	private Bande bande;

	@Column(name = "TITLE")
	private String title;
	
	@Basic(optional = false)
	@Column(name = "REMEMBER_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date remember_date;
	
	@Column(name = "FREQUENCY")
	private String frequency;
	
	@Column(name = "PRIORITY")
	private Integer priority;

	public Remember() {
		super();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
		this.remember_date = cal.getTime();
	}

	/**
	 * @return the bande
	 */
	public Bande getBande() {
		return bande;
	}

	/**
	 * @param aValue the bande to set
	 */
	public void setBande(Bande aValue) {
		this.bande = aValue;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the remember_date
	 */
	public Date getRemember_date() {
		return remember_date;
	}

	/**
	 * @param remember_date the remember_date to set
	 */
	public void setRemember_date(Date remember_date) {
		this.remember_date = remember_date;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	
}
