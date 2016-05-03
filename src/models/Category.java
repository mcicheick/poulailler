package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sissoko on 07/03/2016 10:41:31.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CATEGORIES")
public class Category extends Model {
	
	@Column(name = "TITLE")
	private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Depense> depenses;

	public Category() {
		super();
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

	@Override
	public String toString() {
		return title;
	}
}
