package views.forms;

import models.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by sissoko on 07/03/2016 10:41:31.
 */
@SuppressWarnings("serial")
public class CategoryForm extends BaseForm implements LayoutManager {

	protected JLabel titleLabel;
	protected JTextField titleField;
	protected JLabel titleLabelError;
	protected InputGroup titleInputGroup;

	private Category category;

	public CategoryForm() {
		this(new Category());
	}

	/**
	 *
	 * @param category
	 */
	public CategoryForm(Category category) {
		super();
		this.category = category;
		setLayout(this);
		init();
		bind();
	}

	private void init() {
		this.titleLabel = new JLabel("Categorie");
		this.titleField = new JTextField();
		this.titleLabelError = new JLabel();
		this.titleInputGroup = new InputGroup(titleLabel, titleField,
				titleLabelError);
		add(titleInputGroup);
		this.titleField.setText(category.getTitle());

		this.titleInputGroup.validate(new Validator() {
			@Override
			public boolean test() {
				String text = (String) titleInputGroup.getValue();
				System.out.println(text);
				return (text == null || text.trim().isEmpty());
			}
		});

		add(sendButton);
		add(cancelButton);
	}

	private void bind() {

		titleInputGroup.setKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (titleInputGroup.hasError()) {
						titleInputGroup.setError("Le titre est incorrecte");
					} else {
						titleInputGroup.setError("");
					}
				}
			}
		});

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Category binded = getCategory();
				if (binded != null) {
					binded.save();
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

	}

	@Override
	public void layoutContainer(Container parent) {
		Rectangle bound = parent.getBounds();
		int inset = 4;
		int x = bound.x;
		int y = bound.y;
		int width = bound.width;
		int height = bound.height;
		int buttonWidth = 120;
		int inputGroupHeight = 60;

		titleInputGroup.setBounds(inset, 0, width - inset, inputGroupHeight);

		sendButton.setBounds(x + width - buttonWidth - 2 * inset, y + height
				- 45, buttonWidth, 30);
		cancelButton.setBounds(x + width - (2 * buttonWidth + 2 * inset), y
				+ height - 45, buttonWidth, 30);
	}

	public Category getCategory() {
		if (titleInputGroup.hasError()) {
			titleField.grabFocus();
			titleInputGroup.setError("Le titre est incorrect.");
			return null;
		}
		titleInputGroup.setError("");
		category.setTitle((String) titleInputGroup.getValue());

		return category;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Category Form");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setBackground(Color.lightGray);
		CategoryForm mainPanel = new CategoryForm();
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setBounds(200, 200, 640, 480);
		frame.setVisible(true);
	}
}
